package online.gonlink.shortenservice.repository;

import online.gonlink.shortenservice.entity.ShortUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShortUrlRepositoryTest {

    @Autowired
    ShortUrlRepository shortUrlRepository;

    @Test
    void insert() {
        String shortCode = "12abCD";
        String originalUrl = "https://www.youtube.com/sonnees";
        ShortUrl shortUrl = new ShortUrl(shortCode, originalUrl);

        ShortUrl insert = shortUrlRepository.insert(shortUrl);
        assertNotNull(insert);

        shortUrlRepository.delete(shortUrl);
    }

    @Test
    void findShortUrlsByOriginalUrl_OKE() {
        String originalUrl = "https://www.youtube.com/sonnees";
        ShortUrl shortUrl = insert_();

        Optional<ShortUrl> shortUrlsByOriginalUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        assertTrue(shortUrlsByOriginalUrl.isPresent());
        shortUrlRepository.delete(shortUrl);
    }

    @Test
    void findShortUrlsByOriginalUrl_NOT_FOUND() {
        String originalUrlTemp = "https://www.youtube.com/sonnees/temp";
        ShortUrl shortUrl = insert_();

        Optional<ShortUrl> shortUrlsByOriginalUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrlTemp);
        assertTrue(shortUrlsByOriginalUrl.isEmpty());
        shortUrlRepository.delete(shortUrl);
    }

    ShortUrl insert_() {
        String shortCode = "12abCD";
        String originalUrl = "https://www.youtube.com/sonnees";
        ShortUrl shortUrl = new ShortUrl(shortCode, originalUrl);

        ShortUrl insert = shortUrlRepository.insert(shortUrl);
        assertNotNull(insert);
        return insert;
    }
}