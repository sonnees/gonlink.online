package online.gonlink.observer;

import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.MonthTraffic;
import online.gonlink.entity.TrafficID;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.enumdef.TrafficType;
import online.gonlink.repository.MonthTrafficRep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class MonthTrafficObserver implements TrafficObserver{
    private final MonthTrafficRep repository;
    private final SimpleDateFormat simpleDateFormat;
    private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter dateTimeFormatter;

    public MonthTrafficObserver(
            MonthTrafficRep repository,
            @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YM) SimpleDateFormat simpleDateFormat,
            @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime,
            DateTimeFormatter dateTimeFormatter) {
        this.repository = repository;
        this.simpleDateFormat = simpleDateFormat;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request){
        boolean isIncreased = true;
        ZonedDateTime clientTime = ZonedDateTime.now(ZoneId.of(request.getZoneId()));
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getDayOfMonth()-1;

        TrafficID trafficID = new TrafficID(request.getShortCode(), date);
        if(!repository.existsById(trafficID)){
            TrafficCreateDto trafficCreateDto = new TrafficCreateDto(request.getShortCode(), owner, request.getShortCode(), clientTime);
            this.createsTraffic(trafficCreateDto);
        }
        long increased = repository.increaseTraffic(trafficID, index);
        if(increased<=0)
            throw new ResourceException(ExceptionEnum.GENERAL_TRAFFIC_INCREASE_FAIL.name(), null);
        return isIncreased;
    }

    @Override
    public void deletesTraffic (String shortCode) {
        repository.deleteAllByShortCode(shortCode);
    }

    @Override
    public boolean createsTraffic(TrafficCreateDto dto) {
        boolean isCreated = true;
        String date = simpleDateFormat.format(Date.from(dto.time().toInstant()));
        MonthTraffic traffic = (MonthTraffic) TrafficFactory.createTraffic(TrafficType.MONTH, dto.shortCode(), date);
        repository.insert(traffic);
        return isCreated;
    }
}
