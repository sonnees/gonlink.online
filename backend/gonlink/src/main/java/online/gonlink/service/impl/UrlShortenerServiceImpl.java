package online.gonlink.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordResponse;
import online.gonlink.ShortCodeCheckExistRequest;
import online.gonlink.ShortCodeCheckExistResponse;
import online.gonlink.dto.ShortUrlGenerateDto;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.exception.enumdef.ExceptionEnum;
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
import online.gonlink.util.ShortUrlUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Objects;
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

    private final PasswordEncoder passwordEncoder;

    private final Boolean IS_OWNER = true;
    private final Boolean HAVE_ACCOUNT = true;

    @PostConstruct
    public void initObservers() {
        createTrafficSubject.addObserver(generalTrafficObserver);
        createTrafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    public ShortCodeCheckExistResponse checkExistShortCode(ShortCodeCheckExistRequest request) {
       return ShortCodeCheckExistResponse.newBuilder()
               .setIsExistShortCode(shortUrlRepository.existsById(request.getShortCode()))
               .build();
    }

    @Override
    public boolean checkExistShortCode(String shortCode) {
        return shortUrlRepository.existsById(shortCode);
    }

    @Override
    public OriginalUrlCheckNeedPasswordResponse checkNeedPassword(OriginalUrlCheckNeedPasswordRequest request){
        ShortUrl shortUrl = this.shortUrlRepository.findById(request.getShortCode())
                .orElseThrow(() -> new ResourceException(ExceptionEnum.NOT_FOUND_SHORT_CODE, null));
        return OriginalUrlCheckNeedPasswordResponse.newBuilder()
                .setIsNeedPassword(Objects.nonNull(shortUrl.getPassword()))
                .build();
    }

    @Override
    public ResponseGenerateShortCode generateShortCode(GenerateShortCodeRequest request) {
        return generateShortCode(!HAVE_ACCOUNT, null, ShortUrlUtil.mapFromGenerateShortCodeRequest(request, passwordEncoder));
    }

    @Override
    public ResponseGenerateShortCode generateShortCode(String email, GenerateShortCodeAccountRequest request) {
        return generateShortCode(HAVE_ACCOUNT, email, ShortUrlUtil.mapFromGenerateShortCodeAccountRequest(request, passwordEncoder));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseGenerateShortCode generateShortCode(Boolean haveAccount, String email, ShortUrlGenerateDto shortUrlGenerateDto){
        ResponseGenerateShortCode response;
        if(!shortUrlGenerateDto.shortCode().equals("") && this.checkExistShortCode(shortUrlGenerateDto.shortCode()))
            throw new ResourceException(ExceptionEnum.EXIST_SHORT_CODE, null);

        checkURL.isNotForbidden(shortUrlGenerateDto.originalUrl());
        checkURL.isExits(shortUrlGenerateDto.originalUrl());

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(shortUrlGenerateDto.originalUrl());

        if (existingShortUrl.isPresent())
            response = new ResponseGenerateShortCode(existingShortUrl.get().getShortCode(), !IS_OWNER);
        else{
            while (true){
                String shortCode = !shortUrlGenerateDto.shortCode().equals("")
                        ? shortUrlGenerateDto.shortCode()
                        : shortCodeGenerator.generateShortCode();

                if(shortUrlRepository.existsById(shortCode)) continue;
                ShortUrl shortUrl = ShortUrlUtil.mapFromShortUrlGenerateDto(shortUrlGenerateDto);
                shortUrl.setShortCode(shortCode);

                shortUrlRepository.insert(shortUrl);
                boolean isCreated = createTrafficSubject.create(new TrafficCreateDto(shortCode, haveAccount?email:"", shortUrlGenerateDto.originalUrl(), shortUrlGenerateDto.dateTime(), shortUrlGenerateDto.zoneId()));
                if(!isCreated)
                    throw new ResourceException(ExceptionEnum.INTERNAL.name(), null);
                else {
                    response = new ResponseGenerateShortCode(shortCode, IS_OWNER);
                    break;
                }
            }
        }
        return response;
    }

    @Override
    public String getOriginalUrl(GetOriginalUrlRequest request) {
        ShortUrl shortUrl = shortUrlRepository.findById(request.getShortCode()).orElseThrow(() -> new ResourceException(ExceptionEnum.NOT_FOUND_URL.name(), null));
        validateUrlAccessibility(shortUrl, request);
        trafficService.increaseTraffic(request.getShortCode(), request.getClientTime(), request.getZoneId());
        return shortUrl.getOriginalUrl();
    }

    private void validateUrlAccessibility(ShortUrl shortUrl,GetOriginalUrlRequest request) {
        if(Objects.nonNull(shortUrl.getPassword()))
            if(!passwordEncoder.matches(request.getPassword(),shortUrl.getPassword()))
                throw new ResourceException(ExceptionEnum.PASSWORD_NOT_CORRECT.name(), null);

        // note
        if(Objects.nonNull(shortUrl.getTimeExpired())){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(request.getZoneId()));
            LocalDateTime timeExpired = shortUrl.getTimeExpired();
            if(timeExpired.isBefore(ChronoLocalDateTime.from(now)))
                throw new ResourceException(ExceptionEnum.TIME_EXPIRED.name(), null);
        }

        if(shortUrl.getMaxUsage() != 0){
            GeneralTraffic generalTraffic = trafficService.searchGeneralTrafficByShortCode(shortUrl.getShortCode());
            if(generalTraffic.getTraffic()>=shortUrl.getMaxUsage())
                throw new ResourceException(ExceptionEnum.MAX_ACCESS.name(), null);
        }
    }


}
