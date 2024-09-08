package online.gonlink.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.gonlink.config.GlobalValue;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShortCodeGenerator {
    SecureRandom secureRandom;
    GlobalValue config;

    public String generateShortCode() {
        StringBuilder shortCode = new StringBuilder(config.getLENGTH_SHORT_CODE());
        for (int i = 0; i < config.getLENGTH_SHORT_CODE(); i++) {
            int randomCharIndex = secureRandom.nextInt(config.getALLOWED_CHARACTERS().length());
            shortCode.append(config.getALLOWED_CHARACTERS().charAt(randomCharIndex));
        }
        return shortCode.toString();
    }
}
