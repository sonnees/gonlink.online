package online.gonlink.util;

import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.dto.ShortUrlGenerateDto;
import online.gonlink.entity.ShortUrl;
import online.gonlink.exception.ResourceException;
import online.gonlink.exception.enumdef.ExceptionEnum;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public final class ShortUrlUtil {

    public static ShortUrlGenerateDto mapFromGenerateShortCodeRequest(SimpleDateFormat simpleDateFormatWithTime, GenerateShortCodeRequest request, PasswordEncoder passwordEncoder){
        return getShortUrlGenerateDto(simpleDateFormatWithTime, passwordEncoder, request.getZoneId(), request.getShortCode(), request.getOriginalUrl(), request.getAlias(), request.getDesc(), request.getTimeExpired(), request.getPassword(), request.getMaxUsage());
    }

    public static ShortUrlGenerateDto mapFromGenerateShortCodeAccountRequest(SimpleDateFormat simpleDateFormatWithTime, GenerateShortCodeAccountRequest request, PasswordEncoder passwordEncoder){
        return getShortUrlGenerateDto(simpleDateFormatWithTime, passwordEncoder, request.getZoneId(), request.getShortCode(), request.getOriginalUrl(), request.getAlias(), request.getDesc(), request.getTimeExpired(), request.getPassword(), request.getMaxUsage());
    }

    private static ShortUrlGenerateDto getShortUrlGenerateDto(SimpleDateFormat simpleDateFormatWithTime,  PasswordEncoder passwordEncoder, String zoneId, String shortCode, String originalUrl, String alias, String desc, String timeExpired, String password, long maxUsage) {
        String exp = null;
        if(!timeExpired.equals("")){
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(zoneId));
            ZonedDateTime timeExpiredZonedDateTime = ZonedDateTime.parse(timeExpired).withZoneSameInstant(ZoneId.of(zoneId));

            if(timeExpiredZonedDateTime.isBefore(now))
                throw new ResourceException(ExceptionEnum.TIME_EXPIRED.name(), null);
            exp = simpleDateFormatWithTime.format(Date.from(timeExpiredZonedDateTime.toInstant()));
        }
        return new ShortUrlGenerateDto(
                ZonedDateTime.now(ZoneId.of(zoneId)),
                shortCode,
                originalUrl,
                alias,
                desc,
                exp,
                password.equals("") ? null : passwordEncoder.encode(password),
                maxUsage
        );
    }

    public static ShortUrl mapFromShortUrlGenerateDto(ShortUrlGenerateDto dto){
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(dto.shortCode());
        shortUrl.setOriginalUrl(dto.originalUrl());
        shortUrl.setMaxUsage(dto.maxUsage());
        shortUrl.setAlias(dto.alias());
        shortUrl.setDesc(dto.desc());
        shortUrl.setPassword(dto.password());
        shortUrl.setTimeExpired(dto.timeExpired());
        return shortUrl;
    }

}
