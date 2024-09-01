package online.gonlink.service.impl;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.entity.ShortUrl;
import online.gonlink.observer.CreateTrafficSubject;
import online.gonlink.observer.GeneralTrafficObserver;
import online.gonlink.observer.RealTimeTrafficObserver;
import online.gonlink.repository.ShortUrlRepository;
import online.gonlink.service.AccountService;
import online.gonlink.service.TrafficService;
import online.gonlink.util.CheckURL;
import online.gonlink.util.ShortCodeGenerator;
import online.gonlink.service.UrlShortenerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlRepository shortUrlRepository;
    private final CheckURL checkURL;
    private final TrafficService trafficService;
    private final AccountService accountService;

    private final CreateTrafficSubject createTrafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final RealTimeTrafficObserver realTimeTrafficObserver;

    private final Boolean IS_OWNER = true;

    @PostConstruct
    public void initObservers() {
        createTrafficSubject.addObserver(generalTrafficObserver);
        createTrafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    public ResponseGenerateShortCode generateShortCode(String originalUrl, String trafficDate, String zoneId) {
        if(!checkURL.isNotForbidden(originalUrl))
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("URL Is Forbidden"));
        if(!checkURL.isExits(originalUrl))
            throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found"));

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent())
            return new ResponseGenerateShortCode(existingShortUrl.get().getShortCode(), !IS_OWNER);

        while (true){
            String shortCode = shortCodeGenerator.generateShortCode();
            ShortUrl shortUrl = new ShortUrl(shortCode,originalUrl);
            shortUrlRepository.insert(shortUrl);
            createTrafficSubject.create(new IncreaseTraffic(shortCode, trafficDate, zoneId));
            return new ResponseGenerateShortCode(shortCode, IS_OWNER);
        }
    }

    @Override
    public ResponseGenerateShortCode generateShortCode(String email, String originalUrl, String trafficDate, String zoneId) {
        if(!checkURL.isNotForbidden(originalUrl))
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("URL Is Forbidden"));
        if(!checkURL.isExits(originalUrl))
            throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found"));

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent())
            return new ResponseGenerateShortCode(existingShortUrl.get().getShortCode(), false);

        while (true){
            String shortCode = shortCodeGenerator.generateShortCode();
            ShortUrl shortUrl = new ShortUrl(shortCode,originalUrl);
            shortUrlRepository.insert(shortUrl);
            boolean appended = accountService.appendUrl(email, new ShortUrl(shortCode, originalUrl));
//            if(!appended)
//                 err
            return new ResponseGenerateShortCode(shortCode, true);
        }
    }

    @Override
    public String getOriginalUrl(String shortCode, String clientTime, String zoneId) {
        Optional<ShortUrl> shortUrlOpt = shortUrlRepository.findById(shortCode);
        if (shortUrlOpt.isPresent()) {
            trafficService.increaseTraffic(shortCode, clientTime, zoneId);
            return shortUrlOpt.get().getOriginalUrl();
        }
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("Short URL not found for code: " + shortCode));
    }

}
