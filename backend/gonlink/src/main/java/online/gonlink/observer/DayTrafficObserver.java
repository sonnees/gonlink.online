package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficIncreaseDto;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.TrafficID;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.enumdef.TrafficType;
import online.gonlink.repository.DayTrafficRepository;
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

@Slf4j
@Component
public class DayTrafficObserver implements TrafficObserver{
    private final DayTrafficRepository repository;
    private final SimpleDateFormat simpleDateFormat;
    private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter dateTimeFormatter;

    public DayTrafficObserver(DayTrafficRepository dayTrafficRepository,
                              SimpleDateFormat simpleDateFormat_YMD,
                              @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime,
                              DateTimeFormatter dateTimeFormatter) {
        this.repository = dayTrafficRepository;
        this.simpleDateFormat = simpleDateFormat_YMD;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    @Transactional
    public boolean increaseTraffic(TrafficIncreaseDto record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getHour();

        TrafficID trafficID = new TrafficID(record.shortCode(), date);
        Optional<DayTraffic> byId = repository.findById(trafficID);
        if(byId.isEmpty()) insert(record.shortCode(), date);

        try {
            long increased = repository.increaseTraffic(trafficID, index);
            if(increased<=0)
                throw new ResourceException(ExceptionEnum.DAY_TRAFFIC_INCREASE_FAIL.name(), null);
            return true;
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }

    @Override
    public void deleteTraffic(String shortCode) {
        try {
            repository.deleteAllByShortCode(shortCode);
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }

    @Transactional
    public void insert(String shortCode, String trafficDate) {
        DayTraffic traffic = (DayTraffic) TrafficFactory.createTraffic(TrafficType.DAY, shortCode, trafficDate);
        try {
            repository.insert(traffic);
        } catch (DuplicateKeyException e){
            throw new ResourceException(ExceptionEnum.SHORTEN_GENERATE_ALREADY_EXISTS.name(), e);
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }
}
