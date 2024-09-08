package online.gonlink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import online.gonlink.constant.GonLinkConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TimeZone;

@Getter
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalValue {
    @Value("${gonlink.paging.page}")
    int PAGE;

    @Value("${gonlink.paging.size}")
    int SIZE;

    @Value("${gonlink.paging.sort-direction}")
    Sort.Direction SORT_DIRECTION;

    @Value("${gonlink.qr.width}")
    int WIDTH;

    @Value("${gonlink.qr.height}")
    int HEIGHT;

    @Value("${gonlink.jwt.secret-key}")
    String SECRET_KEY;

    @Value("${gonlink.url-forbidden}")
    String[] URL_FORBIDDEN;

    @Value("${gonlink.allowed-characters}")
    String ALLOWED_CHARACTERS;

    @Value("${gonlink.length-short-code}")
    int LENGTH_SHORT_CODE;

    @Value("${front-end.domain}")
    String FRONTEND_DOMAIN;

    @Value("${gonlink.public-method}")
    Set<String> PUBLIC_METHODS;

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean(name = GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YM)
    SimpleDateFormat simpleDateFormat_YM() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat(GonLinkConstant.SIMPLE_DATE_FORMAT_YM);
        sdfUTC.setTimeZone(TimeZone.getTimeZone(GonLinkConstant.TIME_ZONE));
        return sdfUTC;
    }

    @Primary
    @Bean(name = GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD)
    SimpleDateFormat simpleDateFormat_YMD() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat(GonLinkConstant.SIMPLE_DATE_FORMAT_YMD);
        sdfUTC.setTimeZone(TimeZone.getTimeZone(GonLinkConstant.TIME_ZONE));
        return sdfUTC;
    }

    @Bean(name = GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS)
    SimpleDateFormat simpleDateFormatWithTime() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat(GonLinkConstant.SIMPLE_DATE_FORMAT_YMD_HMS);
        sdfUTC.setTimeZone(TimeZone.getTimeZone(GonLinkConstant.TIME_ZONE));
        return sdfUTC;
    }

    @Bean
    DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern(GonLinkConstant.DATE_TIME_FORMAT);
    }

}
