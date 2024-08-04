package online.gonlink.accountservice.observer;

import com.mongodb.DuplicateKeyException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.accountservice.dto.IncreaseTraffic;
import online.gonlink.accountservice.entity.MonthTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import online.gonlink.accountservice.factory.TrafficFactory;
import online.gonlink.accountservice.factory.type.TrafficType;
import online.gonlink.accountservice.repository.MonthTrafficRepository;
import online.gonlink.accountservice.util.FormatLogMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class MonthTrafficObserver implements TrafficObserver{
    private final MonthTrafficRepository repository;
    @Qualifier("simpleDateFormat_YM") private final SimpleDateFormat simpleDateFormat;
    @Qualifier("simpleDateFormatWithTime") private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter dateTimeFormatter;

    public MonthTrafficObserver(MonthTrafficRepository repository, SimpleDateFormat simpleDateFormat_YM, SimpleDateFormat simpleDateFormatWithTime, DateTimeFormatter dateTimeFormatter) {
        this.repository = repository;
        this.simpleDateFormat = simpleDateFormat_YM;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    @Transactional
    public boolean increaseTraffic(IncreaseTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getDayOfMonth();

        TrafficID trafficID = new TrafficID(record.shortCode(), date);
        Optional<MonthTraffic> byId = repository.findById(trafficID);
        if(byId.isEmpty()) insert(record.shortCode(), date);

        try {
            long increased = repository.increaseTraffic(trafficID, index);
            if(increased>0) return true;
            else{
                log.error(FormatLogMessage.formatLogMessage(
                        this.getClass().getSimpleName(),
                        "increaseTraffic < increaseTraffic",
                        "Unexpected error: {}",
                        ""
                ));
                throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
            }
        } catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "increaseTraffic",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }

    @Override
    public void deleteTraffic(String shortCode) {
        try {
            repository.deleteAllByShortCode(shortCode);
        } catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "removeUrl",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }

    @Transactional
    public void insert(String shortCode, String trafficDate) {
        MonthTraffic traffic = (MonthTraffic) TrafficFactory.createTraffic(TrafficType.MONTH, shortCode, trafficDate);
        try {
            repository.insert(traffic);
        } catch (DuplicateKeyException e){
            throw new StatusRuntimeException( Status.ALREADY_EXISTS.withDescription("Duplicate Key Error"));
        } catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "insert",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }
}
