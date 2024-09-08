package online.gonlink.service.impl;

import com.google.common.base.Objects;
import io.grpc.Context;
import jakarta.annotation.PostConstruct;
import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.config.GlobalValue;
import online.gonlink.constant.GonLinkConstant;
import online.gonlink.dto.AuthConstants;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.dto.Standard;
import online.gonlink.dto.TrafficData;
import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.entity.MonthTraffic;
import online.gonlink.entity.RealTimeTraffic;
import online.gonlink.exception.ResourceException;
import online.gonlink.observer.DayTrafficObserver;
import online.gonlink.observer.GeneralTrafficObserver;
import online.gonlink.observer.MonthTrafficObserver;
import online.gonlink.observer.RealTimeTrafficObserver;
import online.gonlink.observer.TrafficSubject;
import online.gonlink.repository.DayTrafficRepository;
import online.gonlink.repository.GeneralTrafficRepository;
import online.gonlink.repository.MonthTrafficRepository;
import online.gonlink.repository.RealTimeTrafficRepository;
import online.gonlink.repository.ShortUrlRepository;
import online.gonlink.service.TrafficService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrafficServiceImpl implements TrafficService {
    private final GlobalValue globalValue;
    private final GeneralTrafficRepository generalTrafficRepository;
    private final MonthTrafficRepository monthTrafficRepository;
    private final DayTrafficRepository dayTrafficRepository;
    private final RealTimeTrafficRepository realTimeTrafficRepository;

    private final ShortUrlRepository shortUrlRepository;


    private final TrafficSubject trafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final MonthTrafficObserver monthTrafficObserver;
    private final DayTrafficObserver dayTrafficObserver;
    private final RealTimeTrafficObserver realTimeTrafficObserver;

    private final SimpleDateFormat simpleDateFormatWithTime;

    public TrafficServiceImpl(GlobalValue globalValue, GeneralTrafficRepository generalTrafficRepository, MonthTrafficRepository monthTrafficRepository, DayTrafficRepository dayTrafficRepository, RealTimeTrafficRepository realTimeTrafficRepository, ShortUrlRepository shortUrlRepository, TrafficSubject trafficSubject, GeneralTrafficObserver generalTrafficObserver, MonthTrafficObserver monthTrafficObserver, DayTrafficObserver dayTrafficObserver, RealTimeTrafficObserver realTimeTrafficObserver, @Qualifier(GonLinkConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS)  SimpleDateFormat simpleDateFormatWithTime) {
        this.globalValue = globalValue;
        this.generalTrafficRepository = generalTrafficRepository;
        this.monthTrafficRepository = monthTrafficRepository;
        this.dayTrafficRepository = dayTrafficRepository;
        this.realTimeTrafficRepository = realTimeTrafficRepository;
        this.shortUrlRepository = shortUrlRepository;
        this.trafficSubject = trafficSubject;
        this.generalTrafficObserver = generalTrafficObserver;
        this.monthTrafficObserver = monthTrafficObserver;
        this.dayTrafficObserver = dayTrafficObserver;
        this.realTimeTrafficObserver = realTimeTrafficObserver;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @PostConstruct
    public void initObservers() {
        trafficSubject.addObserver(generalTrafficObserver);
        trafficSubject.addObserver(monthTrafficObserver);
        trafficSubject.addObserver(dayTrafficObserver);
        trafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    public boolean increaseTraffic(String shortCode, String trafficDate, String zoneId) {
        return trafficSubject.notifyObservers(new IncreaseTraffic(shortCode, trafficDate, zoneId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTraffic(String shortCode) {
        shortUrlRepository.deleteById(shortCode);
        trafficSubject.deleteTraffic(shortCode);
    }

    @Override
    public Page<GeneralTraffic> searchGeneralTraffics(GeneralTrafficsSearchRequest request){
        Page<GeneralTraffic> traffics;
        Context context = Context.current();
        Pageable pageable = PageRequest.of(
                request.getPage()==0?globalValue.getPAGE():request.getPage(),
                request.getSize()==0?globalValue.getSIZE():request.getSize()
        );
        traffics = generalTrafficRepository.findAllByOwner(AuthConstants.USER_EMAIL.get(context), pageable);
        return traffics;
    }

    @Override
    public List<TrafficData> getAllMonthTraffic(MonthTrafficsGetAllRequest request) {
        List<TrafficData> trafficDataList = new ArrayList<>();
        List<MonthTraffic> monthTrafficList = monthTrafficRepository.getAll(request.getShortCode());
        long cumulativeSum = 0;
        LocalDate today = LocalDate.now();

        LocalDate previousDate = null;

        for (MonthTraffic monthTraffic : monthTrafficList) {
            String trafficDate = monthTraffic.getId().getTrafficDate();
            short[] trafficDays = monthTraffic.getTrafficDays();

            for (int i = 0; i < trafficDays.length; i++) {
                String dateStr = trafficDate + "-" + String.format("%02d", i + 1);
                LocalDate date = LocalDate.parse(dateStr);

                if (date.isAfter(today)) break;

                if (previousDate != null && !previousDate.plusDays(1).equals(date)) {
                    LocalDate missingDate = previousDate.plusDays(1);
                    while (!missingDate.equals(date) && !missingDate.isAfter(today)) {
                        trafficDataList.add(new TrafficData(missingDate.toString(), cumulativeSum));
                        missingDate = missingDate.plusDays(1);
                    }
                }

                cumulativeSum += trafficDays[i];
                trafficDataList.add(new TrafficData(dateStr, cumulativeSum));
                previousDate = date;
            }
        }

        if (previousDate != null && previousDate.isBefore(today)) {
            LocalDate missingDate = previousDate.plusDays(1);
            while (!missingDate.isAfter(today)) {
                trafficDataList.add(new TrafficData(missingDate.toString(), cumulativeSum));
                missingDate = missingDate.plusDays(1);
            }
        }
        return trafficDataList;
    }

    @Override
    public List<TrafficData> getDayTrafficInRange(DayTrafficInRangeRequest request) {
        List<TrafficData> trafficDataList = new ArrayList<>();
        List<DayTraffic> dayTrafficList = dayTrafficRepository.findByShortCodeAndTrafficDate(request.getShortCode(), request.getFromDate(), request.getToDate());
        LocalDate toDate = LocalDate.parse(request.getToDate());
        LocalDate curDate = LocalDate.parse(request.getFromDate());
        LocalDate toDay = LocalDate.now();
        int index = 0;
        LocalDate indexDate = null;
        short[] trafficHours = null;

        while (curDate.isEqual(toDate) || curDate.isBefore(toDate)){
            if(!Objects.equal(null, indexDate)){
                if(Objects.equal(indexDate, curDate)){
                    trafficDataList.addAll(appendData(curDate, trafficHours));
                    indexDate = null;
                } else {
                    trafficDataList.addAll(appendData(curDate, new short[24]));
                }
                curDate = curDate.plusDays(1);
            } else {
                // không lấy tương lai
                if(curDate.isAfter(toDay)) break;

                if(index < dayTrafficList.size()){
                    DayTraffic dayTraffic = dayTrafficList.get(index);
                    indexDate = LocalDate.parse(dayTraffic.getId().getTrafficDate());
                    trafficHours = dayTraffic.getTrafficHours();
                    index++;
                } else {
                    trafficDataList.addAll(appendData(curDate, new short[24]));
                    curDate = curDate.plusDays(1);
                }
            }
        }

        return trafficDataList;
    }

    private List<TrafficData> appendData(LocalDate indexDate, short[] trafficHours) {
        List<TrafficData> trafficDataList = new ArrayList<>();
        for (int i = 0; i < trafficHours.length; i++) {
            LocalDateTime dateTime = indexDate.atStartOfDay();
            LocalDateTime currentHour = dateTime.plusHours(i);
            trafficDataList.add(new TrafficData(
                    currentHour.toLocalDate().toString() + "T" + String.format("%02d", i) + ":00",
                    trafficHours[i]
            ));
        }
        return trafficDataList;
    }

    @Override
    public short[] getRealTimeTraffic(RealTimeTrafficRequest request) {
        short[] trafficMinute;
        Optional<RealTimeTraffic> realTimeTrafficOptional = realTimeTrafficRepository.findById(request.getShortCode());

        ZonedDateTime clientTime = ZonedDateTime.parse(request.getClientTime()).withZoneSameInstant(ZoneId.of(request.getZoneId()));
        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));

        if(realTimeTrafficOptional.isPresent()){
            RealTimeTraffic realTimeTraffic = realTimeTrafficOptional.get();
            String updateAt = realTimeTraffic.getUpdateAt();
            LocalDateTime oldTime = LocalDateTime.parse(updateAt.replace(' ', 'T'));
            LocalDateTime newTime = LocalDateTime.parse(dateTime.replace(' ', 'T'));
            int offset = (int) Duration.between(oldTime, newTime).getSeconds()/60;

            short[] trafficMinuteShorts = realTimeTraffic.getTrafficMinute();
            if(offset > 0 && offset < 60){
                trafficMinute = new short[60];
                int y = 59-offset;
                for (int i = 0; i < 60; i++){
                    if(y >= i)
                        trafficMinute[i] = trafficMinuteShorts[offset+i];
                    else trafficMinute[i] = 0;
                }
            } else if (offset>=60) {
                trafficMinute = new short[60];
            } else {
                trafficMinute = trafficMinuteShorts;
            }
        } else {
            throw new ResourceException(Standard.NOT_FOUND_SHORT_CODE.name(), null);
        }
        return trafficMinute;
    }
}
