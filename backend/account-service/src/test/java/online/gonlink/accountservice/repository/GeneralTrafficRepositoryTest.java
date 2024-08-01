package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.DayTraffic;
import online.gonlink.accountservice.entity.GeneralTraffic;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeneralTrafficRepositoryTest {

    @Autowired GeneralTrafficRepository repository;
    @Autowired @Qualifier("simpleDateFormatWithTime") SimpleDateFormat simpleDateFormatWithTime;

    @Test
    void insert() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-08-01T20:05:30.300Z").withZoneSameInstant(ZoneId.of(zoneID));
        String trafficDate = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));

        GeneralTraffic insert = repository.insert(new GeneralTraffic(shortCode, trafficDate));

        assertNotNull(insert);
        repository.delete(insert);
    }

    @Test
    void increaseTraffic() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        ZonedDateTime clientTime = ZonedDateTime.parse("2024-08-01T20:05:30.300Z").withZoneSameInstant(ZoneId.of(zoneID));
        String trafficDate = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));

        GeneralTraffic insert = repository.insert(new GeneralTraffic(shortCode, trafficDate));
        assertNotNull(insert);

        Long aLong = repository.increaseTraffic(shortCode);

        assertTrue(aLong>0);
        repository.delete(insert);
    }
}