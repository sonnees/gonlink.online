package online.gonlink.accountservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import online.gonlink.accountservice.entity.Traffic;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrafficRepositoryTest {
    @Autowired TrafficRepository trafficRepository;
    @Autowired @Qualifier("simpleDateFormat") SimpleDateFormat simpleDateFormat;
    @Autowired @Qualifier("simpleDateFormatWithTime") SimpleDateFormat simpleDateFormatWithTime;
    @Autowired DateTimeFormatter dateTimeFormatter;

    @Test
    void insert() {
        String shortCode = "demo";
        String date = simpleDateFormat.format(Date.from(ZonedDateTime.now().toInstant()));

        Traffic traffic = new Traffic(shortCode, date);

        Traffic insert = trafficRepository.insert(traffic);
        assertNotNull(insert);
        trafficRepository.delete(insert);
    }

    @Test
    void findTrafficByShortCodeAndTrafficDate() {
        String shortCode = "demo";
        String date = simpleDateFormat.format(Date.from(ZonedDateTime.now().toInstant()));
        Traffic insert = insert_();

        Optional<Traffic> trafficByShortCodeAndTrafficDate = trafficRepository.findTrafficByShortCodeAndTrafficDate(shortCode, date);
        assertTrue(trafficByShortCodeAndTrafficDate.isPresent());

        trafficRepository.delete(insert);
    }

    @Test
    void increaseTraffic() {
        String shortCode = "demo";
        String date = simpleDateFormat.format(Date.from(ZonedDateTime.now().toInstant()));
        Traffic insert = insert_();

        String dateTime = simpleDateFormatWithTime.format(Date.from(ZonedDateTime.now().toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int hour = localDateTime.getHour()-1;

        Long aLong = trafficRepository.increaseTraffic(shortCode, date, hour);
        assertTrue(aLong>0);
        trafficRepository.delete(insert);
    }

    Traffic insert_() {
        String shortCode = "demo";
        String date = simpleDateFormat.format(Date.from(ZonedDateTime.now().toInstant()));
        Traffic traffic = new Traffic(shortCode, date);
        Traffic insert = trafficRepository.insert(traffic);
        assertNotNull(insert);
        return insert;
    }


}