package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.dto.CreateTraffic;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.dto.Standard;
import online.gonlink.entity.RealTimeTraffic;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.type.TrafficType;
import online.gonlink.repository.RealTimeTrafficRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Component
public class RealTimeTrafficObserver implements TrafficObserver, CreateTrafficObserver{
    private final RealTimeTrafficRepository repository;
    private final DateTimeFormatter dateTimeFormatter;
    @Qualifier("simpleDateFormatWithTime") private final SimpleDateFormat simpleDateFormatWithTime;

    public RealTimeTrafficObserver(RealTimeTrafficRepository repository, DateTimeFormatter dateTimeFormatter, SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.dateTimeFormatter = dateTimeFormatter;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    public boolean increaseTraffic(IncreaseTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int minute = localDateTime.getMinute();

        try {
            long increased = repository.increaseTraffic(record.shortCode(), minute);
            if(increased<=0)
                throw new ResourceException(Standard.REALTIME_TRAFFIC_INCREASE_FAIL.name(), null);
            return true;
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }

    @Override
    public void deleteTraffic(String shortCode) {
        try {
            repository.deleteById(shortCode);
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), null);
        }
    }

    @Override
    public boolean create(CreateTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        RealTimeTraffic traffic = (RealTimeTraffic) TrafficFactory.createTraffic(TrafficType.REAL_TIME, record.shortCode(), date);
        try {
            repository.insert(traffic);
            return true;
        } catch (DuplicateKeyException e){
            throw new ResourceException(Standard.SHORTEN_GENERATE_ALREADY_EXISTS.name(), null);
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }
}
