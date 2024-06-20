package rutlink.online.accountservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class ShortenServiceConfig {

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
