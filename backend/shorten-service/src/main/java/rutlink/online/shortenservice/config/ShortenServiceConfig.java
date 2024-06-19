package rutlink.online.shortenservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class ShortenServiceConfig {

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }
}
