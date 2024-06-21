package rutlink.online.accountservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import rutlink.online.accountservice.entity.Traffic;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrafficRepositoryTest {

    @Autowired TrafficRepository trafficRepository;
    @Autowired @Qualifier("simpleDateFormat")
    SimpleDateFormat simpleDateFormat;
    @Autowired @Qualifier("simpleDateFormatWithTime")
    SimpleDateFormat simpleDateFormatWithTime;
    @Autowired DateTimeFormatter dateTimeFormatter;

    @Test
    void save() {
        String shortCode = "abcdef";
        String date = simpleDateFormat.format(Date.from(ZonedDateTime.now().toInstant()));

        Traffic traffic = new Traffic(shortCode, date);

        Traffic save = trafficRepository.save(traffic);
        assertNotNull(save);
    }

    @Test
    void findTrafficByShortCodeAndTrafficDate() {
        String shortCode = "abcdef";
        String date = simpleDateFormat.format(Date.from(ZonedDateTime.now().toInstant()));

        Optional<Traffic> trafficByShortCodeAndTrafficDate = trafficRepository.findTrafficByShortCodeAndTrafficDate(shortCode, date);

        assertTrue(trafficByShortCodeAndTrafficDate.isPresent());
    }

    @Test
    void addValueToTrafficHour() {
        String shortCode = "abcdef";
        String date = simpleDateFormatWithTime.format(Date.from(ZonedDateTime.now().toInstant()));
        LocalDateTime dateTime = LocalDateTime.parse(date, dateTimeFormatter);
        int hour = dateTime.getHour()-1;

        Long aLong = trafficRepository.increaseTraffic(shortCode, date, hour);
//        assertTrue(l>0);
    }
}