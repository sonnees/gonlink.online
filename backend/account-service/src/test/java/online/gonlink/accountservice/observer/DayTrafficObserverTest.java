package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import online.gonlink.accountservice.repository.DayTrafficRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DayTrafficObserverTest {

    @Mock DayTrafficRepository repository;
    @Mock DateTimeFormatter dateTimeFormatter;

    DayTrafficObserver observer;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormatWithTime;

    @Test
    void increaseTraffic_OKE() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-07-02T08:52:37.442Z";
        String date = "2024-07-02";
        TrafficID trafficID = new TrafficID(shortCode, date);
        int index = 8;
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 7, 2, 8, 52, 37);
        ZonedDateTime clientTime = ZonedDateTime.parse(trafficDate).withZoneSameInstant(ZoneId.of(zoneID));

        try (
                MockedStatic<LocalDateTime> localDateTimeMockedStatic = mockStatic(LocalDateTime.class);
                MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = mockStatic(ZonedDateTime.class)
        ) {

            localDateTimeMockedStatic.when(() -> LocalDateTime.parse(any(), any(DateTimeFormatter.class)))
                    .thenReturn(fixedDateTime);

            zonedDateTimeMockedStatic.when(()->ZonedDateTime.parse(trafficDate).withZoneSameInstant(ZoneId.of(zoneID))).thenReturn(clientTime);


            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            simpleDateFormatWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormatWithTime.setTimeZone(TimeZone.getTimeZone("UTC"));

            observer = new DayTrafficObserver(repository, simpleDateFormat, simpleDateFormatWithTime, dateTimeFormatter);

            when(repository.findById(trafficID)).thenReturn(Optional.empty());
            when(repository.increaseTraffic(trafficID, index)).thenReturn(1L);

            observer.increaseTraffic(new IncreaseTraffic(shortCode, trafficDate, zoneID));

            verify(repository).findById(any());
            verify(repository).increaseTraffic(any(), eq(index));
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void insert() {
    }
}