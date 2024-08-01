package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.DayTraffic;
import online.gonlink.accountservice.entity.MonthTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MonthTrafficRepositoryTest {
    @Autowired MonthTrafficRepository repository;
    @Autowired @Qualifier("simpleDateFormat_YM") SimpleDateFormat simpleDateFormat;
    @Autowired @Qualifier("simpleDateFormatWithTime") SimpleDateFormat simpleDateFormatWithTime;
    @Autowired DateTimeFormatter dateTimeFormatter;

    @Test
    void insert() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-08-01T15:31:27.442Z").withZoneSameInstant(ZoneId.of(zoneID));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        MonthTraffic monthTraffic = new MonthTraffic(shortCode, date);

        MonthTraffic insert = repository.insert(monthTraffic);
        assertNotNull(insert);
        repository.delete(insert);
    }

    @Test
    void increaseTraffic() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-08-01T15:31:27.442Z").withZoneSameInstant(ZoneId.of(zoneID));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        MonthTraffic insert = repository.insert(new MonthTraffic(shortCode, date));

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getDayOfMonth();

        Long aLong = repository.increaseTraffic(new TrafficID(shortCode, date), index);

        assertTrue(aLong>0);
        repository.delete(insert);
    }
}