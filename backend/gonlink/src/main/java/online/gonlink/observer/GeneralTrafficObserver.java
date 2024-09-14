package online.gonlink.observer;

import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.dto.TrafficIncreaseDto;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.enumdef.TrafficType;
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
    @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) private final SimpleDateFormat simpleDateFormatWithTime;

    public GeneralTrafficObserver(GeneralTrafficRepository repository,
                                  @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    public boolean increaseTraffic(TrafficIncreaseDto record) {
        try {
            long increased = repository.increaseTraffic(record.shortCode());
            if(increased<=0)
                throw new ResourceException(ExceptionEnum.MONTH_TRAFFIC_INCREASE_FAIL.name(), null);
            return true;
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }

    @Override
    public void deleteTraffic(String shortCode) {
        try {
            repository.deleteById(shortCode);
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }

    @Override
    public boolean create(TrafficCreateDto record) {
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        GeneralTraffic traffic = (GeneralTraffic) TrafficFactory.createTraffic(TrafficType.GENERAL, record.shortCode(), record.owner(), record.originalUrl(), date);
        try {
            repository.insert(traffic);
            return true;
        } catch (DuplicateKeyException e){
            throw new ResourceException(ExceptionEnum.SHORTEN_GENERATE_ALREADY_EXISTS.name(), e);
        } catch (Exception e){
            throw new ResourceException(ExceptionEnum.INTERNAL.name(), e);
        }
    }
}
