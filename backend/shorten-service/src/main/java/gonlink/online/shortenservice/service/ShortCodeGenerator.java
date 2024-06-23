package gonlink.online.shortenservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ShortCodeGenerator {
    @Value("${shorten-service.ALLOWED_CHARACTERS}")
    private String ALLOWED_CHARACTERS;

    @Value("${shorten-service.LENGTH_SHORT_CODE}")
    private int LENGTH_SHORT_CODE;

    private final SecureRandom secureRandom;

    public ShortCodeGenerator(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public String generateShortCode() {
        StringBuilder shortCode = new StringBuilder(LENGTH_SHORT_CODE);
        for (int i = 0; i < LENGTH_SHORT_CODE; i++) {
            int randomCharIndex = secureRandom.nextInt(ALLOWED_CHARACTERS.length());
            shortCode.append(ALLOWED_CHARACTERS.charAt(randomCharIndex));
        }
        return shortCode.toString();
    }
}
