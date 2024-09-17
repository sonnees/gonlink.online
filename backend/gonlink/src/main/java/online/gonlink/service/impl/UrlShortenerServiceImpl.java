package online.gonlink.service.impl;

import lombok.AllArgsConstructor;
import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.GetOriginalUrlResponse;
import online.gonlink.OriginalUrlCheckNeedPasswordRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordResponse;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.RemoveUrlResponse;
import online.gonlink.ShortCodeCheckExistRequest;
import online.gonlink.ShortCodeCheckExistResponse;
import online.gonlink.ShortCodeUpdateRequest;
import online.gonlink.ShortCodeUpdateResponse;
import online.gonlink.dto.ShortUrlGenerateDto;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.ShortUrl;
import online.gonlink.exception.ResourceException;
import online.gonlink.repository.ShortUrlRepository;
import online.gonlink.service.AccountService;
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

@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    /** Config */
    private final PasswordEncoder passwordEncoder;

    /** Repository */
    private final ShortUrlRepository shortUrlRepository;

    /** Service */
    private final TrafficService trafficService;
    private final AccountService accountService;
    private final ShortCodeGenerator shortCodeGenerator;
    private final CheckURL checkURL;


    /** Constant */
    private final Boolean IS_OWNER = true;
    private final Boolean HAVE_ACCOUNT = true;


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
                shortUrl.setOwner(email);
                shortUrl.setActive(true);
                shortUrlRepository.insert(shortUrl);
                trafficService.createsTraffic(new TrafficCreateDto(shortCode, haveAccount?email:"", shortUrlGenerateDto.originalUrl(), shortUrlGenerateDto.dateTime(), shortUrlGenerateDto.zoneId()));
                response = new ResponseGenerateShortCode(shortCode, IS_OWNER);
                break;
            }
        }
        return response;
    }

    @Override
    public GetOriginalUrlResponse getOriginalUrl(GetOriginalUrlRequest request) {
        ShortUrl shortUrl = shortUrlRepository.findById(request.getShortCode()).orElseThrow(() -> new ResourceException(ExceptionEnum.NOT_FOUND_URL.name(), null));
        validateUrlAccessibility(shortUrl, request);
        trafficService.increasesTraffic(shortUrl.getOwner(), shortUrl.getOriginalUrl(), request);
        return GetOriginalUrlResponse.newBuilder()
                .setOriginalUrl(shortUrl.getOriginalUrl())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RemoveUrlResponse removeByShortCode(RemoveUrlRequest request) {
        shortUrlRepository.deleteById(request.getShortCode());
        trafficService.deletesTraffic(request);
        return RemoveUrlResponse.newBuilder()
                .setIsRemoved(true)
                .build();
    }

    @Override
    public ShortCodeUpdateResponse updateByID(ShortCodeUpdateRequest request) {
        ShortUrl shortUrl = shortUrlRepository.findById(request.getShortCode())
                .orElseThrow(() -> new ResourceException(ExceptionEnum.NOT_FOUND_URL.name(), null));
        shortUrl.setAlias(request.getAlias().equals("")?shortUrl.getAlias():request.getAlias());
        shortUrl.setDesc(request.getDesc().equals("")?shortUrl.getDesc():request.getDesc());

        /* request.getPassword() = 'null' means no password will be used */
        shortUrl.setPassword(request.getPassword().equals("null")?null: (request.getPassword().equals("")?shortUrl.getPassword():passwordEncoder.encode(request.getPassword())));

        /* request.getTimeExpired() = 'null' means no timeExpired will be used */
        if(request.getTimeExpired().equals("null")){
            shortUrl.setTimeExpired(null);
        } else{
            if (!request.getTimeExpired().equals("")){
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getTimeExpired()).withZoneSameInstant(ZoneId.of(request.getZoneId()));
                LocalDateTime timeExpiredDateTime = LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of(request.getZoneId()));
                shortUrl.setTimeExpired(timeExpiredDateTime);
            }
        }
        shortUrl.setMaxUsage(request.getMaxUsage()==0?shortUrl.getMaxUsage():request.getMaxUsage());
        shortUrl.setActive(request.getActive());

        ShortUrl shortUrlUpdated = shortUrlRepository.save(shortUrl);
        return ShortCodeUpdateResponse.newBuilder()
                .setShortCode(shortUrlUpdated.getShortCode())
                .setOriginalUrl(shortUrlUpdated.getOriginalUrl())
                .setAlias(shortUrlUpdated.getAlias())
                .setDesc(shortUrlUpdated.getDesc())
                .setPassword(Objects.isNull(shortUrlUpdated.getPassword())?"":shortUrlUpdated.getPassword())
                .setTimeExpired(Objects.isNull(shortUrlUpdated.getTimeExpired())?"":shortUrlUpdated.getTimeExpired().toString())
                .setMaxUsage(shortUrlUpdated.getMaxUsage())
                .setActive(shortUrlUpdated.isActive())
                .build();
    }

    private void validateUrlAccessibility(ShortUrl shortUrl,GetOriginalUrlRequest request) {
        if(!shortUrl.isActive())
            throw new ResourceException(ExceptionEnum.NOT_FOUND_URL.name(), null);

        if(Objects.nonNull(shortUrl.getPassword()))
            if(!passwordEncoder.matches(request.getPassword(),shortUrl.getPassword()))
                throw new ResourceException(ExceptionEnum.PASSWORD_NOT_CORRECT.name(), null);
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
