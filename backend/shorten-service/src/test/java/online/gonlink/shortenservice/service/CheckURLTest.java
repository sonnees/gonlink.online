package online.gonlink.shortenservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckURLTest {

    @Autowired
    CheckURL checkURL;

    @Test
    void isExits_HTTP_OKE() {
        String urlString = "https://www.youtube.com";

        boolean result = false;
        try {
            result = checkURL.isExits(urlString);
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

        assertTrue(result);
    }

    @Test
    void isExits_HTTP_NOTOKE() {
        String urlString = "https://www.youtube.com/s";

        boolean result = false;
        try {
            result = checkURL.isExits(urlString);
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }

        assertFalse(result);
    }

    @Test
    void isExits_ThrowsE() {
        String urlString = "https://www.youtube";

        assertThrows(IOException.class, () -> {
            checkURL.isExits(urlString);
        });
    }

    @Test
    void isNotForbidden_OKE() {
        String urlString = "https://www.youtube.com";

        boolean result = checkURL.isNotForbidden(urlString);

        assertTrue(result);
    }

    @Test
    void isNotForbidden_NOTOKE() {
        String urlString = "https://www.rutlink.online";

        boolean result = checkURL.isNotForbidden(urlString);

        assertFalse(result);
    }
}