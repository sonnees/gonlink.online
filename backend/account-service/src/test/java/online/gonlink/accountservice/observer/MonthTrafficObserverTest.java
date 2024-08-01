package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;
import online.gonlink.accountservice.entity.MonthTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import online.gonlink.accountservice.repository.DayTrafficRepository;
import online.gonlink.accountservice.repository.MonthTrafficRepository;
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
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MonthTrafficObserverTest {
    @Mock MonthTrafficRepository repository;
    @Mock DateTimeFormatter dateTimeFormatter;

    MonthTrafficObserver observer;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormatWithTime;

    @Test
    void increaseTraffic() {
        String shortCode = "demo";
        String zoneID = "Asia/Saigon";
        String trafficDate = "2024-08-01T08:52:37.442Z";
        String date = "2024-08";
        TrafficID trafficID = new TrafficID(shortCode, date);
        int index = 1;
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 8, 1, 8, 52, 37);
        ZonedDateTime clientTime = ZonedDateTime.parse(trafficDate).withZoneSameInstant(ZoneId.of(zoneID));

        try (
                MockedStatic<LocalDateTime> localDateTimeMockedStatic = mockStatic(LocalDateTime.class);
                MockedStatic<ZonedDateTime> zonedDateTimeMockedStatic = mockStatic(ZonedDateTime.class)
        ) {
            localDateTimeMockedStatic.when(() -> LocalDateTime.parse(any(), any(DateTimeFormatter.class))).thenReturn(fixedDateTime);
            zonedDateTimeMockedStatic.when(()->ZonedDateTime.parse(trafficDate).withZoneSameInstant(ZoneId.of(zoneID))).thenReturn(clientTime);

            simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            simpleDateFormatWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormatWithTime.setTimeZone(TimeZone.getTimeZone("UTC"));

            observer = new MonthTrafficObserver(repository, simpleDateFormat, simpleDateFormatWithTime, dateTimeFormatter);

            when(repository.findById(trafficID)).thenReturn(Optional.empty());
            when(repository.increaseTraffic(trafficID, index)).thenReturn(1L);

            observer.increaseTraffic(new IncreaseTraffic(shortCode, trafficDate, zoneID));

            verify(repository).findById(any());
            verify(repository).increaseTraffic(any(), eq(index));
        }

    }

    @Test
    void insert() {
    }
}