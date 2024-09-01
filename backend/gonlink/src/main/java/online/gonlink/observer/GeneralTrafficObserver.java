package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.type.TrafficType;
import online.gonlink.repository.GeneralTrafficRepository;
import online.gonlink.util.FormatLogMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class GeneralTrafficObserver implements TrafficObserver, CreateTrafficObserver{
    private final GeneralTrafficRepository repository;
    @Qualifier("simpleDateFormatWithTime") private final SimpleDateFormat simpleDateFormatWithTime;

    public GeneralTrafficObserver(GeneralTrafficRepository repository, SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    public boolean increaseTraffic(IncreaseTraffic record) {
        try {
            long increased = repository.increaseTraffic(record.shortCode());
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
            repository.deleteById(shortCode);
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

    @Override
    public boolean create(IncreaseTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        GeneralTraffic traffic = (GeneralTraffic) TrafficFactory.createTraffic(TrafficType.GENERAL, record.shortCode(), date);
        try {
            repository.insert(traffic);
            return true;
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
