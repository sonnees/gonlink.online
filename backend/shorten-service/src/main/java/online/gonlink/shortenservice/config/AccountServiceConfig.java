package online.gonlink.shortenservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Getter
@Configuration
public class AccountServiceConfig {

    @Value("${shorten-service.qr.width}")
    private int WIDTH;

    @Value("${shorten-service.qr.height}")
    private int HEIGHT;

    @Value("${shorten-service.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${shorten-service.kafka.topic}")
    private String TOPIC_NAME;

    @Value("${shorten-service.url-forbidden}")
    private String[] URL_FORBIDDEN;

    @Value("${shorten-service.allowed-characters}")
    private String ALLOWED_CHARACTERS;

    @Value("${shorten-service.length-short-code}")
    private int LENGTH_SHORT_CODE;

    @Value("${front-end.domain}")
    private String FRONTEND_DOMAIN;

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean(name = "simpleDateFormat")
    SimpleDateFormat simpleDateFormat() {
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
