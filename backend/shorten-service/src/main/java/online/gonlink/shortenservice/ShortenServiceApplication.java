package online.gonlink.shortenservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.security.SecureRandom;

@SpringBootApplication
public class ShortenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortenServiceApplication.class, args);
    }

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
