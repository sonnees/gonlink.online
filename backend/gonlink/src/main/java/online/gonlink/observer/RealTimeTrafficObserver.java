package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.constant.GonLinkConstant;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class RealTimeTrafficObserver implements TrafficObserver, CreateTrafficObserver{
    private final RealTimeTrafficRepository repository;
    private final DateTimeFormatter dateTimeFormatter;
    @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) private final SimpleDateFormat simpleDateFormatWithTime;

    public RealTimeTrafficObserver(RealTimeTrafficRepository repository, DateTimeFormatter dateTimeFormatter, @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.dateTimeFormatter = dateTimeFormatter;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    public boolean increaseTraffic(IncreaseTraffic record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        Optional<RealTimeTraffic> realTimeTrafficOptional = repository.findById(record.shortCode());

        if(realTimeTrafficOptional.isPresent()){
            RealTimeTraffic realTimeTraffic = realTimeTrafficOptional.get();

            String updateAt = realTimeTraffic.getUpdateAt();
            LocalDateTime oldTime = LocalDateTime.parse(updateAt.replace(' ', 'T'));
            LocalDateTime newTime = LocalDateTime.parse(dateTime.replace(' ', 'T'));
            int offset = (int) Duration.between(oldTime, newTime).getSeconds()/60;

            short[] trafficMinute = realTimeTraffic.getTrafficMinute();
            if(offset==0){
                trafficMinute[59] += 1;
                realTimeTraffic.setTrafficMinute(trafficMinute);
            }
            else if(offset > 0 && offset < 60){
                short[] updatedTrafficMinute = new short[60];
                int y = 59-offset;
                for (int i = 0; i < 60; i++){
                    if(y >= i)
                        updatedTrafficMinute[i] = trafficMinute[offset+i];
                    else updatedTrafficMinute[i] = 0;
                }
                updatedTrafficMinute[59] += 1;
                realTimeTraffic.setTrafficMinute(updatedTrafficMinute);
            } else {
                short[] shorts = new short[60];
                shorts[59] = 1;
                realTimeTraffic.setTrafficMinute(shorts);
            }
            realTimeTraffic.setUpdateAt(dateTime);
            repository.save(realTimeTraffic);
        } else {
            throw new ResourceException(Standard.NOT_FOUND_SHORT_CODE.name(), null);
        }
        return true;
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
