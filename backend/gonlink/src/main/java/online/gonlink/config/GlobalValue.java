package online.gonlink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean(name = "simpleDateFormat")
    SimpleDateFormat simpleDateFormat() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd");
        sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdfUTC;
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean(name = "simpleDateFormat_YM")
    SimpleDateFormat simpleDateFormat_YM() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM");
        sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdfUTC;
    }

    @Bean(name = "simpleDateFormat_YMD")
    SimpleDateFormat simpleDateFormat_YMD() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd");
        sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdfUTC;
    }

    @Bean(name = "simpleDateFormatWithTime")
    SimpleDateFormat simpleDateFormatWithTime() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdfUTC;
    }

    @Bean
    DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

}
