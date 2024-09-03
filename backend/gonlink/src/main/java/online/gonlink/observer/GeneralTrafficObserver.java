package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.dto.CreateTraffic;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.dto.Standard;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.type.TrafficType;
import online.gonlink.repository.GeneralTrafficRepository;
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
            if(increased<=0)
                throw new ResourceException(Standard.MONTH_TRAFFIC_INCREASE_FAIL.name(), null);
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
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }

    @Override
    public boolean create(CreateTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        GeneralTraffic traffic = (GeneralTraffic) TrafficFactory.createTraffic(TrafficType.GENERAL, record.shortCode(), record.owner(), record.originalUrl(), date);
        try {
            repository.insert(traffic);
            return true;
        } catch (DuplicateKeyException e){
            throw new ResourceException(Standard.SHORTEN_GENERATE_ALREADY_EXISTS.name(), e);
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }
}
