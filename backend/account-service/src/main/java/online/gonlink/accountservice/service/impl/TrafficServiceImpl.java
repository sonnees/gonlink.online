package online.gonlink.accountservice.service.impl;

import com.mongodb.DuplicateKeyException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.accountservice.entity.Traffic;
import online.gonlink.accountservice.entity.TrafficID;
import online.gonlink.accountservice.repository.TrafficRepository;
import online.gonlink.accountservice.service.TrafficService;
import online.gonlink.accountservice.util.FormatLogMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class TrafficServiceImpl implements TrafficService {
    private final TrafficRepository trafficRepository;
    @Qualifier("simpleDateFormat") private final SimpleDateFormat simpleDateFormat;
    @Qualifier("simpleDateFormatWithTime") private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter dateTimeFormatter;

    public TrafficServiceImpl(TrafficRepository trafficRepository, SimpleDateFormat simpleDateFormat, SimpleDateFormat simpleDateFormatWithTime, DateTimeFormatter dateTimeFormatter) {
        this.trafficRepository = trafficRepository;
        this.simpleDateFormat = simpleDateFormat;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    @Transactional
    public Boolean increaseTraffic(String shortCode, String trafficDate, String zoneId) {
        ZonedDateTime clientTime = ZonedDateTime.parse(trafficDate).withZoneSameInstant(ZoneId.of(zoneId));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getHour();

        TrafficID trafficID = new TrafficID(shortCode, date);
        Optional<Traffic> byId = trafficRepository.findById(trafficID);
        if(byId.isEmpty()) insert(shortCode, date);

        try {
            Long increased = trafficRepository.increaseTraffic(trafficID, index);
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
        Traffic traffic = new Traffic(shortCode, trafficDate);
        try {
            trafficRepository.insert(traffic);
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
