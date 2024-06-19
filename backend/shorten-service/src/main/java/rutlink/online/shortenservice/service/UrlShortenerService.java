package rutlink.online.shortenservice.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import rutlink.online.shortenservice.entity.ShortUrl;
import rutlink.online.shortenservice.repository.ShortUrlRepository;

import java.util.Optional;

@Service
public class UrlShortenerService {
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlRepository shortUrlRepository;
    private final StringRedisTemplate redisTemplate;

    public UrlShortenerService(ShortCodeGenerator shortCodeGenerator, ShortUrlRepository shortUrlRepository, StringRedisTemplate redisTemplate) {
        this.shortCodeGenerator = shortCodeGenerator;
        this.shortUrlRepository = shortUrlRepository;
        this.redisTemplate = redisTemplate;
    }

    public String generateShortCode(String originalUrl) {
        String shortCode = redisTemplate.opsForValue().get(originalUrl);

        if (shortCode != null) return shortCode;

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent()) {
            shortCode = existingShortUrl.get().getShortCode();
            setOpsForValue(redisTemplate, originalUrl, shortCode);
            return shortCode;
        }

        shortCode = shortCodeGenerator.generateShortCode();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl(originalUrl);

        shortUrlRepository.save(shortUrl);
        setOpsForValue(redisTemplate, originalUrl, shortCode);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        String originalUrl = redisTemplate.opsForValue().get(shortCode);
        if (originalUrl != null) {
            return originalUrl;
        }

        Optional<ShortUrl> shortUrlOpt = shortUrlRepository.findById(shortCode);
        if (shortUrlOpt.isPresent()) {
            originalUrl = shortUrlOpt.get().getOriginalUrl();
            redisTemplate.opsForValue().set(shortCode, originalUrl);
            return originalUrl;
        }

        throw new StatusRuntimeException(
                Status.NOT_FOUND.withDescription("Short URL not found for code: " + shortCode)
        );
    }


    private void setOpsForValue(StringRedisTemplate redisTemplate, String originalUrl, String shortCode) {
        redisTemplate.opsForValue().set(shortCode, shortCode);
        redisTemplate.opsForValue().set(originalUrl, shortCode);
    }
}
