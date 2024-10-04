package online.gonlink.service;

import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.RemoveUrlResponse;
import online.gonlink.dto.TrafficCreateDto;
import online.gonlink.dto.TrafficDataDto;
import online.gonlink.dto.TrafficDayDto;
import online.gonlink.entity.GeneralTraffic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrafficService {
    boolean createsTraffic(TrafficCreateDto trafficCreateDto);
    boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request);
    RemoveUrlResponse deletesTraffic(RemoveUrlRequest request);
    Page<GeneralTraffic> searchGeneralTraffics(GeneralTrafficsSearchRequest request);
    GeneralTraffic searchGeneralTrafficByShortCode(String shortCode);
    List<TrafficDataDto> getAllMonthTraffic(MonthTrafficsGetAllRequest request);
    TrafficDayDto getDayTrafficInRange(DayTrafficInRangeRequest request);
    short[] getRealTimeTraffic(RealTimeTrafficRequest request);
}
