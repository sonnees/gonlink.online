package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.constant.GonLinkConstant;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.dto.Standard;
import online.gonlink.entity.MonthTraffic;
import online.gonlink.entity.TrafficID;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.type.TrafficType;
import online.gonlink.repository.MonthTrafficRepository;
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
public class MonthTrafficObserver implements TrafficObserver{
    private final MonthTrafficRepository repository;
    @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YM) private final SimpleDateFormat simpleDateFormat;
    @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter dateTimeFormatter;

    public MonthTrafficObserver(
            MonthTrafficRepository repository,
            @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YM) SimpleDateFormat simpleDateFormat,
            @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime,
            DateTimeFormatter dateTimeFormatter) {
        this.repository = repository;
        this.simpleDateFormat = simpleDateFormat;
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
        int index = localDateTime.getDayOfMonth()-1;

        TrafficID trafficID = new TrafficID(record.shortCode(), date);
        Optional<MonthTraffic> byId = repository.findById(trafficID);
        if(byId.isEmpty()) insert(record.shortCode(), date);

        try {
            long increased = repository.increaseTraffic(trafficID, index);
            if(increased<=0)
                throw new ResourceException(Standard.GENERAL_TRAFFIC_INCREASE_FAIL.name(), null);
            return true;
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }

    @Override
    public void deleteTraffic(String shortCode) {
        try {
            repository.deleteAllByShortCode(shortCode);
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }

    @Transactional
    public void insert(String shortCode, String trafficDate) {
        MonthTraffic traffic = (MonthTraffic) TrafficFactory.createTraffic(TrafficType.MONTH, shortCode, trafficDate);
        try {
            repository.insert(traffic);
        } catch (DuplicateKeyException e){
            throw new ResourceException(Standard.SHORTEN_GENERATE_ALREADY_EXISTS.name(), e);
        } catch (Exception e){
            throw new ResourceException(Standard.INTERNAL.name(), e);
        }
    }
}
