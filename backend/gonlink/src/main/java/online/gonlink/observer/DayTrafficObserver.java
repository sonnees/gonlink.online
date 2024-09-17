package online.gonlink.observer;

import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.TrafficID;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.enumdef.TrafficType;
import online.gonlink.repository.DayTrafficRepository;
import online.gonlink.util.DateUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request){
        boolean isIncreased = true;
        ZonedDateTime clientTime = DateUtil.getZonedDateTime(request.getClientTime(), request.getZoneId());
        String date = simpleDateFormat.format(clientTime.toInstant());

        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int index = localDateTime.getHour();

        TrafficID trafficID = new TrafficID(request.getShortCode(), date);
        if(!repository.existsById(trafficID)){
            TrafficCreateDto trafficCreateDto = new TrafficCreateDto(request.getShortCode(), owner, request.getShortCode(), request.getClientTime(), request.getZoneId());
            this.createsTraffic(trafficCreateDto);
        }

        long increased = repository.increaseTraffic(trafficID, index);
        if(increased<=0)
            throw new ResourceException(ExceptionEnum.DAY_TRAFFIC_INCREASE_FAIL.name(), null);
        return isIncreased;
    }

    @Override
    public void deletesTraffic(String shortCode){
        repository.deleteAllByShortCode(shortCode);
    }

    @Override
    public boolean createsTraffic(TrafficCreateDto trafficCreateDto){
        boolean isCreated = true;
        ZonedDateTime clientTime = DateUtil.getZonedDateTime(trafficCreateDto.trafficDate(), trafficCreateDto.zoneId());
        String date = simpleDateFormat.format(Date.from(clientTime.toInstant()));
        DayTraffic traffic = (DayTraffic) TrafficFactory.createTraffic(TrafficType.DAY, trafficCreateDto.shortCode(), date);
        repository.insert(traffic);
        return isCreated;
    }
}
