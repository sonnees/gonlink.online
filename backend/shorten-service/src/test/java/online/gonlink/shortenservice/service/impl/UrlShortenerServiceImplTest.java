package online.gonlink.shortenservice.service.impl;

import online.gonlink.shortenservice.dto.ResponseGenerateShortCode;
import online.gonlink.shortenservice.dto.KafkaMessage;
import online.gonlink.shortenservice.entity.ShortUrl;
import online.gonlink.shortenservice.service.CheckURL;
import online.gonlink.shortenservice.service.ShortCodeGenerator;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import online.gonlink.shortenservice.service.base.ProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import online.gonlink.shortenservice.repository.ShortUrlRepository;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceImplTest {
    @InjectMocks UrlShortenerServiceImpl urlShortenerService;
    @Mock ShortCodeGenerator shortCodeGenerator;
    @Mock ShortUrlRepository shortUrlRepository;
    @Mock CheckURL checkURL;
    @Mock ProducerService producerService;

    @Test
    void generateShortCode_NotExits(){
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";
        ResponseGenerateShortCode responseGenerateShortCode = new ResponseGenerateShortCode(shortCodeE, true);
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        try {
            when(checkURL.isExits(originalUrl)).thenReturn(true);
        } catch (IOException e){
            fail("IOException should not be thrown");
        }
        when(checkURL.isNotForbidden(originalUrl)).thenReturn(true);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.empty());
        when(shortCodeGenerator.generateShortCode()).thenReturn(shortCodeE);
        when(shortUrlRepository.insert(shortUrl)).thenReturn(shortUrl);

        ResponseGenerateShortCode shortCodeA = urlShortenerService.generateShortCode(originalUrl, trafficDate, zoneID);

        assertEquals(responseGenerateShortCode, shortCodeA);

        verify(shortUrlRepository).insert(any(ShortUrl.class));
    }

    @Test
    void generateShortCode_Exits() {
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";
        ResponseGenerateShortCode responseGenerateShortCode = new ResponseGenerateShortCode(shortCodeE, false);
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        try {
            when(checkURL.isExits(originalUrl)).thenReturn(true);
        } catch (IOException e){
            fail("IOException should not be thrown");
        }

        when(checkURL.isNotForbidden(originalUrl)).thenReturn(true);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.of(shortUrl));

        ResponseGenerateShortCode shortCodeA = urlShortenerService.generateShortCode(originalUrl, trafficDate, zoneID);
        assertEquals(responseGenerateShortCode, shortCodeA);
    }

    @Test
    void generateShortCodeAccount_NotExits(){
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        String email = "son@gmail.com";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";
        ResponseGenerateShortCode responseGenerateShortCode = new ResponseGenerateShortCode(shortCodeE, true);
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        try {
            when(checkURL.isExits(originalUrl)).thenReturn(true);
        } catch (IOException e){
            fail("IOException should not be thrown");
        }
        when(checkURL.isNotForbidden(originalUrl)).thenReturn(true);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.empty());
        when(shortCodeGenerator.generateShortCode()).thenReturn(shortCodeE);
        when(shortUrlRepository.insert(shortUrl)).thenReturn(shortUrl);
        doNothing().when(producerService).sendMessage(any(KafkaMessage.class));

        ResponseGenerateShortCode shortCodeA = null;
        try{
            shortCodeA = urlShortenerService.generateShortCode(email, originalUrl, trafficDate, zoneID);
        } catch (Exception e){
            fail();
        }

        assertEquals(responseGenerateShortCode, shortCodeA);

        verify(shortUrlRepository).insert(any(ShortUrl.class));
    }

    @Test
    void generateShortCodeAccount_Exits() {
        String originalUrl = "https://www.youtube.com/sonnees";
        String shortCodeE = "12abCD";
        String email = "son@gmail.com";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";
        ResponseGenerateShortCode responseGenerateShortCode = new ResponseGenerateShortCode(shortCodeE, false);
        ShortUrl shortUrl = new ShortUrl(shortCodeE, originalUrl);

        try {
            when(checkURL.isExits(originalUrl)).thenReturn(true);
        } catch (IOException e){
            fail("IOException should not be thrown");
        }

        when(checkURL.isNotForbidden(originalUrl)).thenReturn(true);
        when(shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl)).thenReturn(Optional.of(shortUrl));

        ResponseGenerateShortCode shortCodeA = urlShortenerService.generateShortCode(email, originalUrl, trafficDate, zoneID);
        assertEquals(responseGenerateShortCode, shortCodeA);
    }

    @Test
    void getOriginalUrl_Exits() {
        String originalUrlE = "https://www.youtube.com/sonnees";
        String shortCode = "12abCD";
        ShortUrl shortUrl = new ShortUrl(shortCode, originalUrlE);
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";

        when(shortUrlRepository.findById(shortCode)).thenReturn(Optional.of(shortUrl));
        doNothing().when(producerService).sendMessage(any(KafkaMessage.class));
        String originalUrlA = null;

        try{
            originalUrlA = urlShortenerService.getOriginalUrl(shortCode, trafficDate, zoneID);

        } catch (Exception e){
            fail();
        }
        assertEquals(originalUrlE, originalUrlA);

        verify(shortUrlRepository).findById(any(String.class));
    }

    @Test
    void getOriginalUrl_NotExits() {
        String shortCode = "12abCD";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";

        when(shortUrlRepository.findById(shortCode)).thenReturn(Optional.empty());

        StatusRuntimeException exceptionE = assertThrows(StatusRuntimeException.class, () -> urlShortenerService.getOriginalUrl(shortCode, trafficDate, zoneID));
        assertEquals(Status.NOT_FOUND.getCode(), exceptionE.getStatus().getCode());

        verify(shortUrlRepository).findById(any(String.class));
    }
}