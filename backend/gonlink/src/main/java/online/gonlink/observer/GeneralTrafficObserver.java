package online.gonlink.observer;

import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficCreateDto;
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

@Component
public class GeneralTrafficObserver implements TrafficObserver{
    private final GeneralTrafficRepository repository;
    private final SimpleDateFormat simpleDateFormatWithTime;

    public GeneralTrafficObserver(GeneralTrafficRepository repository,
                                  @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request) {
        boolean isIncreased = true;
        long increased = repository.increaseTraffic(request.getShortCode());
        if(increased<=0)
            throw new ResourceException(ExceptionEnum.MONTH_TRAFFIC_INCREASE_FAIL.name(), null);
        return isIncreased;
    }

    @Override
    public void deletesTraffic(String shortCode) {
        repository.deleteById(shortCode);
    }

    @Override
    public boolean createsTraffic(TrafficCreateDto record) {
        boolean isCreated = true;
        ZonedDateTime clientTime = ZonedDateTime.parse(record.trafficDate()).withZoneSameInstant(ZoneId.of(record.zoneId()));
        String date = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        GeneralTraffic traffic = (GeneralTraffic) TrafficFactory.createTraffic(TrafficType.GENERAL, record.shortCode(), record.owner(), record.originalUrl(), date);
        repository.insert(traffic);
        return isCreated;
    }
}
