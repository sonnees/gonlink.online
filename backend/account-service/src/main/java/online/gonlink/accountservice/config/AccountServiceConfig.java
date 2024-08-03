package online.gonlink.accountservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
@Getter
public class AccountServiceConfig {

    @Value("${account-service.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${account-service.kafka.topic}")
    private String topicName;

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
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
