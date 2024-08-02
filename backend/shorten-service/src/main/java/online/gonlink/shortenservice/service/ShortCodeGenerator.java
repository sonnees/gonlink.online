package online.gonlink.shortenservice.service;

import lombok.AllArgsConstructor;
import online.gonlink.shortenservice.config.AccountServiceConfig;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class ShortCodeGenerator {
    private final SecureRandom secureRandom;
    private AccountServiceConfig config;

    public String generateShortCode() {
        StringBuilder shortCode = new StringBuilder(config.getLENGTH_SHORT_CODE());
        for (int i = 0; i < config.getLENGTH_SHORT_CODE(); i++) {
            int randomCharIndex = secureRandom.nextInt(config.getALLOWED_CHARACTERS().length());
            shortCode.append(config.getALLOWED_CHARACTERS().charAt(randomCharIndex));
        }
        return shortCode.toString();
    }
}
