package online.gonlink.shortenservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShortCodeGeneratorTest {
    @Autowired
    ShortCodeGenerator shortCodeGenerator;

    @Test
    void generateShortCode() {
        Set<String> shortCodeSet = new HashSet<>();

        for(int i=0; i< 100; i++)
            shortCodeSet.add(shortCodeGenerator.generateShortCode());

        assertEquals(100, shortCodeSet.size());

        shortCodeSet.forEach(s -> assertEquals(6, s.length()));
    }
}