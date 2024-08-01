package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.DayTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DayTrafficRepositoryTest {
    @Autowired DayTrafficRepository repository;
    @Autowired @Qualifier("simpleDateFormat_YMD") SimpleDateFormat simpleDateFormat;
    @Autowired @Qualifier("simpleDateFormatWithTime") SimpleDateFormat simpleDateFormatWithTime;
    @Autowired DateTimeFormatter dateTimeFormatter;

    @Test
    void insert() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-07-02T07:40:27.442Z").withZoneSameInstant(ZoneId.of(zoneID));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        DayTraffic dayTraffic = new DayTraffic(shortCode, date);

        DayTraffic insert = repository.insert(dayTraffic);
        assertNotNull(insert);
        repository.delete(insert);
    }

    @Test
    void increaseTraffic() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-07-02T01:40:27.442Z").withZoneSameInstant(ZoneId.of(zoneID));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        DayTraffic insert = repository.insert(new DayTraffic(shortCode, date));
        assertNotNull(insert);

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getHour();

        Long aLong = repository.increaseTraffic(new TrafficID(shortCode, date), index);

        assertTrue(aLong>0);
        repository.delete(insert);
    }

}