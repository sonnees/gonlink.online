package online.gonlink.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.dto.CreateTraffic;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.dto.Standard;
import online.gonlink.entity.ShortUrl;
import online.gonlink.exception.ResourceException;
import online.gonlink.observer.CreateTrafficSubject;
import online.gonlink.observer.GeneralTrafficObserver;
import online.gonlink.observer.RealTimeTrafficObserver;
import online.gonlink.repository.ShortUrlRepository;
import online.gonlink.service.TrafficService;
import online.gonlink.util.CheckURL;
import online.gonlink.util.ShortCodeGenerator;
import online.gonlink.service.UrlShortenerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlRepository shortUrlRepository;
    private final CheckURL checkURL;
    private final TrafficService trafficService;

    private final CreateTrafficSubject createTrafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final RealTimeTrafficObserver realTimeTrafficObserver;

    private final Boolean IS_OWNER = true;
    private final Boolean HAVE_ACCOUNT = true;

    @PostConstruct
    public void initObservers() {
        createTrafficSubject.addObserver(generalTrafficObserver);
        createTrafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    public ResponseGenerateShortCode generateShortCode(String originalUrl, String trafficDate, String zoneId) {
        return generateShortCode(!HAVE_ACCOUNT, null, originalUrl, trafficDate, zoneId);
    }

    @Override

    public ResponseGenerateShortCode generateShortCode(String email, String originalUrl, String trafficDate, String zoneId) {
        return generateShortCode(HAVE_ACCOUNT, email, originalUrl, trafficDate, zoneId);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseGenerateShortCode generateShortCode(Boolean haveAccount, String email, String originalUrl, String trafficDate, String zoneId){
        checkURL.isNotForbidden(originalUrl);
        checkURL.isExits(originalUrl);

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent())
            return new ResponseGenerateShortCode(existingShortUrl.get().getShortCode(), !IS_OWNER);

        while (true){
            String shortCode = shortCodeGenerator.generateShortCode();
            if(shortUrlRepository.existsById(shortCode))continue;
            ShortUrl shortUrl = new ShortUrl(shortCode,originalUrl);
            shortUrlRepository.insert(shortUrl);
            boolean isCreated = createTrafficSubject.create(new CreateTraffic(shortCode, haveAccount?"":email, originalUrl, trafficDate, zoneId));
            if(!isCreated)
                throw new ResourceException(Standard.INTERNAL.name(), null);
            return new ResponseGenerateShortCode(shortCode, IS_OWNER);
        }
    }

    @Override
    public String getOriginalUrl(String shortCode, String clientTime, String zoneId) {
        Optional<ShortUrl> shortUrlOpt = shortUrlRepository.findById(shortCode);
        if (shortUrlOpt.isEmpty())
            throw new ResourceException(Standard.NOT_FOUND_URL.name(), null);
        trafficService.increaseTraffic(shortCode, clientTime, zoneId);
        return shortUrlOpt.get().getOriginalUrl();
    }

}
