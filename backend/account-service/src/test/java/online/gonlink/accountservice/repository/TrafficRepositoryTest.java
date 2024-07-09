package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.TrafficID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import online.gonlink.accountservice.entity.Traffic;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-07-02T07:40:27.442Z").withZoneSameInstant(ZoneId.of(zoneID));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        Traffic traffic = new Traffic(shortCode, date);

        Traffic insert = trafficRepository.insert(traffic);
        assertNotNull(insert);
        trafficRepository.delete(insert);
    }

    @Test
    void increaseTraffic() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-07-02T07:40:27.442Z").withZoneSameInstant(ZoneId.of(zoneID));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        Traffic insert = trafficRepository.insert(new Traffic(shortCode, date));

        String dateTime = simpleDateFormatWithTime.format(Date.from(ZonedDateTime.now().toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getHour();

        Long aLong = trafficRepository.increaseTraffic(new TrafficID(shortCode, date), index);

        assertTrue(aLong>0);
        trafficRepository.delete(insert);
    }

}