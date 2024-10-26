package online.gonlink.observer;

import io.grpc.Context;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.constant.AuthConstant;
import online.gonlink.constant.CommonConstant;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.TrafficID;
import online.gonlink.exception.ResourceException;
import online.gonlink.factory.TrafficFactory;
import online.gonlink.factory.enumdef.TrafficType;
import online.gonlink.repository.DayTrafficRep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DayTrafficObserver implements TrafficObserver{
    private final DayTrafficRep repository;
    private final SimpleDateFormat simpleDateFormat;
    private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter dateTimeFormatter;

    public DayTrafficObserver(DayTrafficRep dayTrafficRep,
                              SimpleDateFormat simpleDateFormat_YMD,
                              @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS) SimpleDateFormat simpleDateFormatWithTime,
                              DateTimeFormatter dateTimeFormatter) {
        this.repository = dayTrafficRep;
        this.simpleDateFormat = simpleDateFormat_YMD;
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
        int index = localDateTime.getHour();

        TrafficID trafficID = new TrafficID(request.getShortCode(), date);
        if(!repository.existsById(trafficID)){
            TrafficCreateDto trafficCreateDto = new TrafficCreateDto(request.getShortCode(), owner, request.getShortCode(), clientTime);
            this.createsTraffic(trafficCreateDto);
        }
        this.increaseAdvance(request, trafficID);
        long increased = repository.increaseTraffic(trafficID, index);
        if(increased<=0){
            throw new ResourceException(ExceptionEnum.DAY_TRAFFIC_INCREASE_FAIL.name(), null);
        }
        return isIncreased;
    }

    @Override
    public void deletesTraffic(String shortCode){
        repository.deleteAllByShortCode(shortCode);
    }

    @Override
    public boolean createsTraffic(TrafficCreateDto dto){
        boolean isCreated = true;
        String date = simpleDateFormat.format(Date.from(dto.time().toInstant()));
        DayTraffic traffic = (DayTraffic) TrafficFactory.createTraffic(TrafficType.DAY, dto.shortCode(), date);
        repository.insert(traffic);
        return isCreated;
    }

    private void increaseAdvance(GetOriginalUrlRequest request, TrafficID trafficID) {
        this.increaseCityClick(trafficID, request.getCity());
        this.increaseCountryClick(trafficID, request.getCountry());
        this.increaseZoneIdClick(trafficID, request.getZoneId());
        this.increaseBrowserClick(trafficID, request.getBrowser());
        this.increaseBrowserVersionClick(trafficID, request.getBrowserVersion());
        this.increaseOperatingSystemClick(trafficID, request.getOperatingSystem());
        this.increaseDeviceTypeClick(trafficID, request.getDeviceType());
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseCityClick(TrafficID id, String city) {
        long updatedCount = repository.increaseCityClick(id, city);
        if (updatedCount == 0) {
            return repository.insertNewCityClick(id, city);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseCountryClick(TrafficID id, String country) {
        long updatedCount = repository.increaseCountryClick(id, country);
        if (updatedCount == 0) {
            return repository.insertNewCountryClick(id, country);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseZoneIdClick(TrafficID id, String timezone) {
        long updatedCount = repository.increaseZoneIdClick(id, timezone);
        if (updatedCount == 0) {
            return repository.insertNewZoneIdClick(id, timezone);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseBrowserClick(TrafficID id, String browserName) {
        long updatedCount = repository.increaseBrowserClick(id, browserName);
        if (updatedCount == 0) {
            return repository.insertNewBrowserClick(id, browserName);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseBrowserVersionClick(TrafficID id, String browserVersion) {
        long updatedCount = repository.increaseBrowserVersionClick(id, browserVersion);
        if (updatedCount == 0) {
            return repository.insertNewBrowserVersionClick(id, browserVersion);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseOperatingSystemClick(TrafficID id, String operatingSystem) {
        long updatedCount = repository.increaseOperatingSystemClick(id, operatingSystem);
        if (updatedCount == 0) {
            return repository.insertNewOperatingSystemClick(id, operatingSystem);
        }
        return updatedCount;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public long increaseDeviceTypeClick(TrafficID id, String deviceType) {
        long updatedCount = repository.increaseDeviceTypeClick(id, deviceType);
        if (updatedCount == 0) {
            return repository.insertNewDeviceTypeClick(id, deviceType);
        }
        return updatedCount;
    }

}
