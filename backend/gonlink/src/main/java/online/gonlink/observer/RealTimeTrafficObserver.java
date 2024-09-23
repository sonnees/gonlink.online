package online.gonlink.observer;

import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.RealTimeTraffic;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.enumdef.TrafficType;
import online.gonlink.repository.RealTimeTrafficRep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class RealTimeTrafficObserver implements TrafficObserver{
    private final RealTimeTrafficRep repository;
    private final SimpleDateFormat simpleDateFormatWithTime;


    public RealTimeTrafficObserver(RealTimeTrafficRep repository,
                                   @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime) {
        this.repository = repository;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @Override
    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request) {
        boolean isIncreased = true;
        ZonedDateTime clientTime = ZonedDateTime.now(ZoneId.of(request.getZoneId()));
        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
        RealTimeTraffic realTimeTraffic = repository.findById(request.getShortCode())
                .orElseThrow(() -> new ResourceException(ExceptionEnum.NOT_FOUND_SHORT_CODE.name(), null));

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
        return isIncreased;
    }

    @Override
    public void deletesTraffic(String shortCode) {
        repository.deleteById(shortCode);
    }

    @Override
    public boolean createsTraffic(TrafficCreateDto dto) {
        boolean isIncreased = true;
        String date = simpleDateFormatWithTime.format(Date.from(dto.time().toInstant()));
        RealTimeTraffic traffic = (RealTimeTraffic) TrafficFactory.createTraffic(TrafficType.REAL_TIME, dto.shortCode(), date);
        repository.insert(traffic);
        return isIncreased;
    }
}
