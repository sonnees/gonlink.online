package rutlink.online.shortenservice.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import rutlink.online.shortenservice.entity.ShortUrl;
import rutlink.online.shortenservice.repository.ShortUrlRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @InjectMocks UrlShortenerService urlShortenerService;
    @Mock ShortCodeGenerator shortCodeGenerator;
    @Mock ShortUrlRepository shortUrlRepository;
    @Mock StringRedisTemplate redisTemplate;
    @Mock ValueOperations<String, String> valueOperationsMock;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void generateShortCode_NotExits() {
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(redisTemplate.opsForValue().get(originalUrl)).thenReturn(null);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.empty());
        when(shortCodeGenerator.generateShortCode()).thenReturn(shortCodeE);
        when(shortUrlRepository.save(shortUrl)).thenReturn(shortUrl);

        String shortCodeA = urlShortenerService.generateShortCode(originalUrl);

        assertEquals(shortCodeE, shortCodeA);

        verify(shortUrlRepository).save(any(ShortUrl.class));
    }

    @Test
    void generateShortCode_ExitsInRedis() {
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";

        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(redisTemplate.opsForValue().get(originalUrl)).thenReturn(shortCodeE);

        String shortCodeA = urlShortenerService.generateShortCode(originalUrl);

        assertEquals(shortCodeE, shortCodeA);
    }

    @Test
    void generateShortCode_ExitsInRepository() {
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(redisTemplate.opsForValue().get(originalUrl)).thenReturn(null);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.of(shortUrl));

        String shortCodeA = urlShortenerService.generateShortCode(originalUrl);

        assertEquals(shortCodeE, shortCodeA);
    }

    @Test
    void getOriginalUrl_ExitsInRedis() {
        String originalUrlE = "https://www.youtube.com/sonnees";
        String shortCode = "12abCD";

        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(redisTemplate.opsForValue().get(shortCode)).thenReturn(originalUrlE);

        String originalUrlA = urlShortenerService.getOriginalUrl(shortCode);

        assertEquals(originalUrlE, originalUrlA);
    }

    @Test
    void getOriginalUrl_ExitsInRepository() {
        String originalUrlE = "https://www.youtube.com/sonnees";
        String shortCode = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCode, originalUrlE);

        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(redisTemplate.opsForValue().get(shortCode)).thenReturn(null);
        when(shortUrlRepository.findById(shortCode)).thenReturn(Optional.of(shortUrl));

        String originalUrlA = urlShortenerService.getOriginalUrl(shortCode);

        assertEquals(originalUrlE, originalUrlA);

        verify(shortUrlRepository).findById(any(String.class));
    }

    @Test
    void getOriginalUrl_NotExits() {
        String shortCode = "12abCD";

        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        when(redisTemplate.opsForValue().get(shortCode)).thenReturn(null);
        when(shortUrlRepository.findById(shortCode)).thenReturn(Optional.empty());

        StatusRuntimeException exceptionE = assertThrows(StatusRuntimeException.class, () -> {
            urlShortenerService.getOriginalUrl(shortCode);
        });

        assertEquals(Status.NOT_FOUND.getCode(), exceptionE.getStatus().getCode());

        verify(shortUrlRepository).findById(any(String.class));
    }
}