package online.gonlink.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import io.grpc.Context;
import jakarta.annotation.PostConstruct;
import online.gonlink.DayTrafficAccount;
import online.gonlink.DayTrafficAccountRequest;
import online.gonlink.DayTrafficAccountResponse;
import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.RealTimeTrafficAccountRequest;
import online.gonlink.RealTimeTrafficAccountResponse;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.RemoveUrlResponse;
import online.gonlink.config.GlobalValue;
import online.gonlink.constant.CommonConstant;
import online.gonlink.constant.AuthConstant;
import online.gonlink.dto.GeneralTrafficDto;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.dto.TrafficDayDto;
import online.gonlink.entity.ShortUrl;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.dto.TrafficDataDto;
import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.entity.MonthTraffic;
import online.gonlink.entity.RealTimeTraffic;
import online.gonlink.exception.ResourceException;
import online.gonlink.observer.AccountObserver;
import online.gonlink.observer.DayTrafficObserver;
import online.gonlink.observer.GeneralTrafficObserver;
import online.gonlink.observer.MonthTrafficObserver;
import online.gonlink.observer.RealTimeTrafficObserver;
import online.gonlink.observer.TrafficSubject;
import online.gonlink.repository.DayTrafficRep;
import online.gonlink.repository.GeneralTrafficRep;
import online.gonlink.repository.MonthTrafficRep;
import online.gonlink.repository.RealTimeTrafficRep;
import online.gonlink.service.TrafficService;
import online.gonlink.service.UrlShortenerService;
import online.gonlink.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrafficServiceImpl implements TrafficService {
    /** Config */
    private final GlobalValue globalValue;
    private final SimpleDateFormat simpleDateFormatWithTime;
    private final DateTimeFormatter formatter;
    private static final DateTimeFormatter FORMATTER_YYYY_MM_DD_HH = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    /** Repository */
    private final GeneralTrafficRep generalTrafficRep;
    private final MonthTrafficRep monthTrafficRep;
    private final DayTrafficRep dayTrafficRep;
    private final RealTimeTrafficRep realTimeTrafficRep;

    /** Observer */
    private final TrafficSubject trafficSubject;
    private final AccountObserver accountObserver;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final MonthTrafficObserver monthTrafficObserver;
    private final DayTrafficObserver dayTrafficObserver;
    private final RealTimeTrafficObserver realTimeTrafficObserver;

    /** Service */
    @Autowired
    private UrlShortenerService urlShortenerService;

    public TrafficServiceImpl(GlobalValue globalValue, DateTimeFormatter formatter, GeneralTrafficRep generalTrafficRep,
                              MonthTrafficRep monthTrafficRep, DayTrafficRep dayTrafficRep,
                              RealTimeTrafficRep realTimeTrafficRep,
                              TrafficSubject trafficSubject, GeneralTrafficObserver generalTrafficObserver,
                              MonthTrafficObserver monthTrafficObserver, DayTrafficObserver dayTrafficObserver,
                              RealTimeTrafficObserver realTimeTrafficObserver, @Qualifier(CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS)  SimpleDateFormat simpleDateFormatWithTime,
                              AccountObserver accountObserver) {
        this.globalValue = globalValue;
        this.formatter = formatter;
        this.generalTrafficRep = generalTrafficRep;
        this.monthTrafficRep = monthTrafficRep;
        this.dayTrafficRep = dayTrafficRep;
        this.realTimeTrafficRep = realTimeTrafficRep;
        this.trafficSubject = trafficSubject;
        this.accountObserver = accountObserver;
        this.generalTrafficObserver = generalTrafficObserver;
        this.monthTrafficObserver = monthTrafficObserver;
        this.dayTrafficObserver = dayTrafficObserver;
        this.realTimeTrafficObserver = realTimeTrafficObserver;
        this.simpleDateFormatWithTime = simpleDateFormatWithTime;
    }

    @PostConstruct
    public void initObservers() {
        trafficSubject.addObserver(accountObserver);
        trafficSubject.addObserver(generalTrafficObserver);
        trafficSubject.addObserver(monthTrafficObserver);
        trafficSubject.addObserver(dayTrafficObserver);
        trafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean createsTraffic(TrafficCreateDto trafficCreateDto) {
        return trafficSubject.createsTraffic(trafficCreateDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request) {
        return trafficSubject.increasesTraffic(owner, originalUrl, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public RemoveUrlResponse deletesTraffic(RemoveUrlRequest request) {
        trafficSubject.deletesTraffic(request.getShortCode());
        return RemoveUrlResponse.newBuilder()
                .build();
    }

    @Override
    public Page<GeneralTrafficDto> searchGeneralTraffics(GeneralTrafficsSearchRequest request) throws ParseException {
        String fromDateReq = request.getFromDate()+"T00:00:00Z";
        String toDateReq = request.getToDate()+"T23:59:00Z";
        ZoneId zoneId = ZoneId.of(request.getZoneId().equals("") ? globalValue.getTIME_ZONE_DEFAULT() : request.getZoneId());
        Page<GeneralTraffic> traffics;
        Context context = Context.current();
        Pageable pageable = PageRequest.of(
                request.getPage()==0?globalValue.getPAGE():request.getPage(),
                request.getSize()==0?globalValue.getSIZE():request.getSize(),
                Sort.by(Sort.Direction.valueOf(
                                request.getSortDirection().toUpperCase()),
                        "trafficDate"
                )
        );
        String fromDate = simpleDateFormatWithTime.format(Date.from(ZonedDateTime.parse(fromDateReq).withZoneSameInstant(zoneId).toInstant()));
        String toDate = simpleDateFormatWithTime.format(Date.from(ZonedDateTime.parse(toDateReq).withZoneSameInstant(zoneId).toInstant()));
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstant.SIMPLE_DATE_FORMAT_YMD_HMS);
        Date start = dateFormat.parse(fromDate);
        Date end = dateFormat.parse(toDate);

        traffics = findTrafficByOwnerAndDateRangeWithSort(AuthConstant.USER_EMAIL.get(context), start, end, pageable);
        /* Convert server time to client time */
        traffics.forEach(gt-> gt.setTrafficDate(DateUtil.getStringZonedDateTime(gt.getTrafficDate(), formatter, zoneId)));

        /* Convert to GeneralTrafficDto */
        return traffics.map(gt->{
            ShortUrl shortUrl = urlShortenerService.search(gt.getShortCode());
            GeneralTrafficDto generalTrafficDto = new GeneralTrafficDto(gt);
            generalTrafficDto.setActive(shortUrl.isActive());
            generalTrafficDto.setAlias(shortUrl.getAlias());
            generalTrafficDto.setDesc(shortUrl.getDesc());
            generalTrafficDto.setTimeExpired(Objects.nonNull(shortUrl.getTimeExpired())?DateUtil.getStringZonedDateTime(shortUrl.getTimeExpired(), formatter, zoneId):"");
            generalTrafficDto.setUsingPassword(Objects.nonNull(shortUrl.getPassword()));
            generalTrafficDto.setMaxUsage(shortUrl.getMaxUsage());
            return generalTrafficDto;
        });
    }

    public Page<GeneralTraffic> findTrafficByOwnerAndDateRangeWithSort(
            String owner, Date startDate, Date endDate, Pageable pageable) {
        int skip = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        int sortDirection = pageable.getSort()
                .getOrderFor("trafficDate")
                .isAscending() ? 1 : -1;
        List<GeneralTraffic> results = generalTrafficRep.findAllByOwnerAndTrafficDateBetweenWithPaginationAndSort(
                owner, startDate, endDate, sortDirection, skip, limit);
        long total = countTrafficByOwnerAndDateRange(owner, startDate, endDate);
        return new PageImpl<>(results, pageable, total);
    }

    public long countTrafficByOwnerAndDateRange(String owner, Date startDate, Date endDate) {
        List<GeneralTraffic> objects = generalTrafficRep.countByOwnerAndTrafficDateBetween(owner, startDate, endDate);
        if (objects.isEmpty()) {
            return 0;
        }
        return objects.size();
    }


    @Override
    public GeneralTraffic searchGeneralTrafficByShortCode(String shortCode){
        return generalTrafficRep.findById(shortCode)
                .orElseThrow(()-> new ResourceException(ExceptionEnum.SHORT_CODE_NOT_FOUND.name(), null));
    }

    @Override
    public List<TrafficDataDto> getAllMonthTraffic(MonthTrafficsGetAllRequest request) {
        GeneralTraffic generalTraffic = generalTrafficRep.findById(request.getShortCode()).orElseThrow(
                () -> new ResourceException(ExceptionEnum.NOT_FOUND_SHORT_CODE, null)
        );
        ZoneId zoneId = ZoneId.of(request.getZoneId().equals("") ? globalValue.getTIME_ZONE_DEFAULT() : request.getZoneId());

        List<TrafficDataDto> trafficDataDtoList = new ArrayList<>();
        List<MonthTraffic> monthTrafficList = monthTrafficRep.getAll(request.getShortCode());
        long cumulativeSum = 0;
        LocalDate today = LocalDate.now(zoneId);
        LocalDate previousDate = null;

        int dayInMonth = DateUtil.getDayInMonthFromDateFormatter(generalTraffic.getTrafficDate(), formatter, zoneId);
        int i = dayInMonth-1;
        /* ### case giao tháng*/
        for (MonthTraffic monthTraffic : monthTrafficList) {
            String trafficDate = monthTraffic.getId().getTrafficDate();
            short[] trafficDays = monthTraffic.getTrafficDays();
            for (; i < trafficDays.length; i++) {
                String dateStr = trafficDate + "-" + String.format("%02d", i + 1);
                LocalDate date = LocalDate.parse(dateStr);

                date = DateUtil.getLocalDateForZoneID(date, zoneId);

                /* not get future date */
                if (date.isAfter(today)) break;

                if (previousDate != null && !previousDate.plusDays(1).equals(date)) {
                    LocalDate missingDate = previousDate.plusDays(1);
                    while (!missingDate.equals(date) && !missingDate.isAfter(today)) {
                        trafficDataDtoList.add(new TrafficDataDto(missingDate.toString(), cumulativeSum));
                        missingDate = missingDate.plusDays(1);
                    }
                }
                cumulativeSum += trafficDays[i];
                trafficDataDtoList.add(new TrafficDataDto(DateUtil.getCusFormatFromLocalDate(date), cumulativeSum));
                previousDate = date;
            }
            i = 0;
        }

        if (previousDate != null && previousDate.isBefore(today)) {
            LocalDate missingDate = previousDate.plusDays(1);
            while (!missingDate.isAfter(today)) {
                trafficDataDtoList.add(new TrafficDataDto(missingDate.toString(), cumulativeSum));
                missingDate = missingDate.plusDays(1);
            }
        }
        return trafficDataDtoList;
    }

    @Override
    public TrafficDayDto getDayTrafficInRange(DayTrafficInRangeRequest request) {
        TrafficDayDto trafficDayDto = new TrafficDayDto();
        GeneralTraffic generalTraffic = generalTrafficRep.findById(request.getShortCode()).orElseThrow(
                () -> new ResourceException(ExceptionEnum.NOT_FOUND_SHORT_CODE, null)
        );

        List<TrafficDataDto> trafficDataDtoList = new ArrayList<>();
        Map<String, Long>  cities = new HashMap<>();
        Map<String, Long>  countries = new HashMap<>();
        Map<String, Long>  zoneIds = new HashMap<>();
        Map<String, Long>  browsers = new HashMap<>();
        Map<String, Long>  browserVersions = new HashMap<>();
        Map<String, Long>  operatingSystems = new HashMap<>();
        Map<String, Long>  deviceTypes = new HashMap<>();

        ZoneId zoneId = ZoneId.of(request.getZoneId().equals("") ? globalValue.getTIME_ZONE_DEFAULT() : request.getZoneId());
        List<DayTraffic> dayTrafficList = dayTrafficRep.findByShortCodeAndTrafficDate(request.getShortCode(), request.getFromDate(), request.getToDate());
        LocalDate toDate = DateUtil.getLocalDateForZoneID(LocalDate.parse(request.getToDate()), zoneId);
        LocalDate curDate = DateUtil.getLocalDateForZoneID(LocalDate.parse(request.getFromDate()), zoneId);

        /* Allow more than 1 day to avoid time zone discrepancies, then block future times. */
        LocalDate toDay = LocalDate.now(zoneId).plusDays(1);
        int index = 0;
        LocalDate indexDate = null;
        short[] trafficHours = null;
        DayTraffic dayTraffic = null;
        while (curDate.isEqual(toDate) || curDate.isBefore(toDate)){
            if(!Objects.equals(null, indexDate)){
                if(Objects.equals(indexDate, curDate)){
                    trafficDataDtoList.addAll(appendData(curDate, trafficHours));
                    dayTraffic.getCities().forEach(i -> cities.merge(i.getName(), i.getData(), Long::sum));
                    dayTraffic.getCountries().forEach(i -> countries.merge(i.getName(), i.getData(), Long::sum));
                    dayTraffic.getZoneIds().forEach(i -> zoneIds.merge(i.getName(), i.getData(), Long::sum));
                    dayTraffic.getBrowsers().forEach(i -> browsers.merge(i.getName(), i.getData(), Long::sum));
                    dayTraffic.getBrowserVersions().forEach(i -> browserVersions.merge(i.getName(), i.getData(), Long::sum));
                    dayTraffic.getOperatingSystems().forEach(i -> operatingSystems.merge(i.getName(), i.getData(), Long::sum));
                    dayTraffic.getDeviceTypes().forEach(i -> deviceTypes.merge(i.getName(), i.getData(), Long::sum));
                    indexDate = null;
                } else {
                    trafficDataDtoList.addAll(appendData(curDate, new short[24]));
                }
                curDate = curDate.plusDays(1);
            } else {
                /* không lấy tương lai */
                if(curDate.isAfter(toDay)) break;

                if(index < dayTrafficList.size()){
                    dayTraffic = dayTrafficList.get(index);
                    indexDate = LocalDate.parse(dayTraffic.getId().getTrafficDate());
                    trafficHours = dayTraffic.getTrafficHours();
                    index++;
                } else {
                    trafficDataDtoList.addAll(appendData(curDate, new short[24]));
                    curDate = curDate.plusDays(1);
                }
            }
        }
        String timeCreate = DateUtil.getStringZonedDateTime(generalTraffic.getTrafficDate(), formatter, zoneId).substring(0,13);
        String timeNow = LocalDateTime.now(zoneId).format(FORMATTER_YYYY_MM_DD_HH);
        trafficDataDtoList = trafficDataDtoList.stream()
                .filter(dt ->{
                    dt.setDate(DateUtil.getStringZonedDateTime(dt.getDate(), formatter, zoneId));
                    return DateUtil.isBeforeLocalDateTime(timeCreate, dt.getDate().substring(0,13))
                            && DateUtil.isBeforeLocalDateTime(dt.getDate().substring(0,13), timeNow );
                    }).collect(Collectors.toList());

        trafficDayDto.setTrafficDataDtoList(trafficDataDtoList);
        trafficDayDto.setCities(cities);
        trafficDayDto.setCountries(countries);
        trafficDayDto.setZoneIds(zoneIds);
        trafficDayDto.setBrowsers(browsers);
        trafficDayDto.setBrowserVersions(browserVersions);
        trafficDayDto.setOperatingSystems(operatingSystems);
        trafficDayDto.setDeviceTypes(deviceTypes);
        return trafficDayDto;
    }

    private List<TrafficDataDto> appendData(LocalDate indexDate, short[] trafficHours) {
        List<TrafficDataDto> trafficDataDtoList = new ArrayList<>();
        for (int i = 0; i < trafficHours.length; i++) {
            LocalDateTime dateTime = indexDate.atStartOfDay();
            LocalDateTime currentHour = dateTime.plusHours(i);
            trafficDataDtoList.add(new TrafficDataDto(
                    currentHour.toLocalDate().toString() + " " + String.format("%02d", i) + ":00:00",
                    trafficHours[i]
            ));
        }
        return trafficDataDtoList;
    }

    @Override
    public short[] getRealTimeTraffic(RealTimeTrafficRequest request) {
        short[] trafficMinute;
        RealTimeTraffic realTimeTraffic = realTimeTrafficRep.findById(request.getShortCode())
                .orElseThrow(() -> new ResourceException(ExceptionEnum.NOT_FOUND_SHORT_CODE.name(), null));

        ZonedDateTime clientTime = ZonedDateTime.now(ZoneId.of(request.getZoneId()));
        String dateTime = simpleDateFormatWithTime.format(Date.from(clientTime.toInstant()));
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
        return trafficMinute;
    }

    @Override
    public RealTimeTrafficAccountResponse getRealTimeTrafficByAccountId(RealTimeTrafficAccountRequest request){
        RealTimeTrafficAccountResponse.Builder newBuilder = RealTimeTrafficAccountResponse.newBuilder();
        Context context = Context.current();
        List<GeneralTraffic> allByOwner = generalTrafficRep.findAllByOwner(AuthConstant.USER_EMAIL.get(context));
        short[] realTimeTrafficMain =  new short[60];
        List<online.gonlink.GeneralTraffic> trafficsList = new ArrayList<>();
        allByOwner.forEach(generalTraffic ->{
            short[] realTimeTraffic = this.getRealTimeTraffic(
                    RealTimeTrafficRequest.newBuilder()
                            .setShortCode(generalTraffic.getShortCode())
                            .setZoneId(request.getZoneId()).build()
            );
            long sum = 0;
            for (int i = 0; i < 60; i++) {
                sum += realTimeTraffic[i];
            }
            for (int i = 0; i < 60; i++) {
                realTimeTrafficMain[i] += realTimeTraffic[i];
            }
            ShortUrl shortUrl = urlShortenerService.search(generalTraffic.getShortCode());
            trafficsList.add(
                    online.gonlink.GeneralTraffic.newBuilder()
                            .setShortCode(generalTraffic.getShortCode())
                            .setAlias(shortUrl.getAlias())
                            .setOriginalUrl(shortUrl.getOriginalUrl())
                            .setDesc(shortUrl.getDesc())
                            .setTraffic(sum)
                            .build()
            );
        });
        List<Integer> realTimeTrafficAsIntegers = new ArrayList<>();
        for (short value : realTimeTrafficMain) {
            realTimeTrafficAsIntegers.add((int) value);
        }
        trafficsList.sort((o1, o2) -> Long.compare(o2.getTraffic(), o1.getTraffic()));
        newBuilder.addAllGeneralTraffics(trafficsList);
        newBuilder.addAllData(realTimeTrafficAsIntegers);
        return newBuilder.build();
    }

    @Override
    public DayTrafficAccountResponse getDayTrafficAccount(DayTrafficAccountRequest request){
        DayTrafficAccountResponse.Builder newBuilder = DayTrafficAccountResponse.newBuilder();
        Context context = Context.current();
        List<GeneralTraffic> allByOwner = generalTrafficRep.findAllByOwner(AuthConstant.USER_EMAIL.get(context));

        TrafficDayDto response = null;
        Map<String, Long>  cities = new HashMap<>();
        Map<String, Long>  countries = new HashMap<>();
        Map<String, Long>  zoneIds = new HashMap<>();
        Map<String, Long>  browsers = new HashMap<>();
        Map<String, Long>  browserVersions = new HashMap<>();
        Map<String, Long>  operatingSystems = new HashMap<>();
        Map<String, Long>  deviceTypes = new HashMap<>();

        List<online.gonlink.GeneralTraffic> trafficsList = new ArrayList<>();
        for (GeneralTraffic gt : allByOwner){
            int sum = 0;
            DayTrafficInRangeRequest dayTrafficInRangeRequest = DayTrafficInRangeRequest.newBuilder()
                    .setShortCode(gt.getShortCode())
                    .setZoneId(request.getZoneId())
                    .setFromDate(request.getFromDate())
                    .setToDate(request.getToDate())
                    .build();
            TrafficDayDto dayTrafficInRange = this.getDayTrafficInRange(dayTrafficInRangeRequest);
            if(response==null){
                response = dayTrafficInRange;
                for (int i = 0; i < response.getTrafficDataDtoList().size(); i++){
                    sum += dayTrafficInRange.getTrafficDataDtoList().get(i).getData();
                }
            } else {
                for (int i = 0; i < response.getTrafficDataDtoList().size(); i++){
                    response.getTrafficDataDtoList().get(i).setData(
                            response.getTrafficDataDtoList().get(i).getData() + dayTrafficInRange.getTrafficDataDtoList().get(i).getData()
                    );
                    sum += dayTrafficInRange.getTrafficDataDtoList().get(i).getData();
                }
            }
            dayTrafficInRange.getCities().forEach((k,v)  -> cities.merge(k, v, Long::sum));
            dayTrafficInRange.getCountries().forEach((k,v)  -> countries.merge(k, v, Long::sum));
            dayTrafficInRange.getZoneIds().forEach((k,v)  -> zoneIds.merge(k, v, Long::sum));
            dayTrafficInRange.getBrowsers().forEach((k,v)  -> browsers.merge(k, v, Long::sum));
            dayTrafficInRange.getBrowserVersions().forEach((k,v)  -> browserVersions.merge(k, v, Long::sum));
            dayTrafficInRange.getOperatingSystems().forEach((k,v)  -> operatingSystems.merge(k, v, Long::sum));
            dayTrafficInRange.getDeviceTypes().forEach((k,v)  -> deviceTypes.merge(k, v, Long::sum));

            ShortUrl shortUrl = urlShortenerService.search(gt.getShortCode());
            trafficsList.add(
                    online.gonlink.GeneralTraffic.newBuilder()
                            .setShortCode(gt.getShortCode())
                            .setAlias(shortUrl.getAlias())
                            .setOriginalUrl(shortUrl.getOriginalUrl())
                            .setDesc(shortUrl.getDesc())
                            .setTraffic(sum)
                            .build()
            );
        }
        if(response != null){
            response.setCities(cities);
            response.setCountries(countries);
            response.setZoneIds(zoneIds);
            response.setBrowsers(browsers);
            response.setBrowserVersions(browserVersions);
            response.setOperatingSystems(operatingSystems);
            response.setDeviceTypes(deviceTypes);
        }

        trafficsList.sort((o1, o2) -> Long.compare(o2.getTraffic(), o1.getTraffic()));
        newBuilder.addAllData(
                response.getTrafficDataDtoList().stream()
                .map(o -> online.gonlink.DataClient
                        .newBuilder()
                        .setName(o.getDate())
                        .setData(o.getData())
                        .build()
                )
                .collect(Collectors.toList())
        );
        newBuilder.addAllGeneralTraffics(trafficsList);
        newBuilder.setDayTrafficAccount(
                DayTrafficAccount.newBuilder()
                        .addAllCities(cities.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .addAllCountries(countries.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .addAllZoneIds(zoneIds.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .addAllBrowsers(browsers.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .addAllBrowserVersions(browserVersions.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .addAllOperatingSystems(operatingSystems.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .addAllDeviceTypes(deviceTypes.entrySet().stream()
                                .map(o -> online.gonlink.DataClient
                                        .newBuilder()
                                        .setName(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList()))
                        .build()
        );
        return newBuilder.build();
    }



}
