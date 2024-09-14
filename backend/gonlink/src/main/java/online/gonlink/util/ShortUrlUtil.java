package online.gonlink.util;

import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.ShortUrlGenerateDto;
import online.gonlink.entity.ShortUrl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class ShortUrlUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT);

    public static ShortUrlGenerateDto mapFromGenerateShortCodeRequest(GenerateShortCodeRequest request, PasswordEncoder passwordEncoder){
        return getShortUrlGenerateDto(passwordEncoder, request.getTrafficDate(), request.getZoneId(), request.getShortCode(), request.getOriginalUrl(), request.getAlias(), request.getDesc(), request.getTimeExpired(), request.getPassword(), request.getMaxUsage());
    }

    public static ShortUrlGenerateDto mapFromGenerateShortCodeAccountRequest(GenerateShortCodeAccountRequest request, PasswordEncoder passwordEncoder){
        return getShortUrlGenerateDto(passwordEncoder, request.getTrafficDate(), request.getZoneId(), request.getShortCode(), request.getOriginalUrl(), request.getAlias(), request.getDesc(), request.getTimeExpired(), request.getPassword(), request.getMaxUsage());
    }

    private static ShortUrlGenerateDto getShortUrlGenerateDto(PasswordEncoder passwordEncoder, String trafficDate, String zoneId, String shortCode, String originalUrl, String alias, String desc, String timeExpired, String password, long maxUsage) {
        LocalDateTime timeExpiredDateTime = null;
        if(!timeExpired.equals("")){
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(timeExpired).withZoneSameInstant(ZoneId.of(zoneId));
            timeExpiredDateTime = LocalDateTime.ofInstant(zonedDateTime.toInstant(), ZoneId.of(zoneId));
        }
        return new ShortUrlGenerateDto(
                trafficDate,
                zoneId,
                shortCode,
                originalUrl,
                alias,
                desc,
                timeExpiredDateTime,
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
