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
import rutlink.online.shortenservice.entity.ShortUrl;
import rutlink.online.shortenservice.repository.ShortUrlRepository;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @InjectMocks UrlShortenerService urlShortenerService;
    @Mock ShortCodeGenerator shortCodeGenerator;
    @Mock ShortUrlRepository shortUrlRepository;
    @Mock CheckURL checkURL;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void generateShortCode_NotExits(){
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        try {
            when(checkURL.isExits(originalUrl)).thenReturn(true);
        } catch (IOException e){
            fail("IOException should not be thrown");
        }
        when(checkURL.isNotForbidden(originalUrl)).thenReturn(true);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.empty());
        when(shortCodeGenerator.generateShortCode()).thenReturn(shortCodeE);
        when(shortUrlRepository.save(shortUrl)).thenReturn(shortUrl);

        String shortCodeA = urlShortenerService.generateShortCode(originalUrl);

        assertEquals(shortCodeE, shortCodeA);

        verify(shortUrlRepository).save(any(ShortUrl.class));
    }

    @Test
    void generateShortCode_Exits() {
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        try {
            when(checkURL.isExits(originalUrl)).thenReturn(true);
        } catch (IOException e){
            fail("IOException should not be thrown");
        }
        when(checkURL.isNotForbidden(originalUrl)).thenReturn(true);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.of(shortUrl));

        String shortCodeA = urlShortenerService.generateShortCode(originalUrl);

        assertEquals(shortCodeE, shortCodeA);
    }

    @Test
    void getOriginalUrl_Exits() {
        String originalUrlE = "https://www.youtube.com/sonnees";
        String shortCode = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCode, originalUrlE);

        when(shortUrlRepository.findById(shortCode)).thenReturn(Optional.of(shortUrl));

        String originalUrlA = urlShortenerService.getOriginalUrl(shortCode);

        assertEquals(originalUrlE, originalUrlA);

        verify(shortUrlRepository).findById(any(String.class));
    }

    @Test
    void getOriginalUrl_NotExits() {
        String shortCode = "12abCD";

        when(shortUrlRepository.findById(shortCode)).thenReturn(Optional.empty());

        StatusRuntimeException exceptionE = assertThrows(StatusRuntimeException.class, () -> {
            urlShortenerService.getOriginalUrl(shortCode);
        });

        assertEquals(Status.NOT_FOUND.getCode(), exceptionE.getStatus().getCode());

        verify(shortUrlRepository).findById(any(String.class));
    }
}