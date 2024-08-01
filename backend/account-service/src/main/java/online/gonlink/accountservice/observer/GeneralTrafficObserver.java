package online.gonlink.accountservice.observer;

import com.mongodb.DuplicateKeyException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.accountservice.dto.IncreaseTraffic;
import online.gonlink.accountservice.entity.GeneralTraffic;
import online.gonlink.accountservice.repository.GeneralTrafficRepository;
import online.gonlink.accountservice.util.FormatLogMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class GeneralTrafficObserver implements TrafficObserver{
    private final GeneralTrafficRepository repository;
    @Qualifier("simpleDateFormatWithTime") private final SimpleDateFormat simpleDateFormatWithTime;

    public GeneralTrafficObserver(GeneralTrafficRepository repository, SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    @Transactional
    public boolean increaseTraffic(IncreaseTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));

        Optional<GeneralTraffic> byId = repository.findById(record.shortCode());
        if(byId.isEmpty()) insert(record.shortCode(), date);

        try {
            Long increased = repository.increaseTraffic(record.shortCode());
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

    @Transactional
    public void insert(String shortCode, String trafficDate) {
        GeneralTraffic generalTraffic = new GeneralTraffic(shortCode, trafficDate);
        try {
            repository.insert(generalTraffic);
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
