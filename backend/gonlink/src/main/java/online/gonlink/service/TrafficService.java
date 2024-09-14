package online.gonlink.service;

import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.dto.TrafficDataDto;
import online.gonlink.entity.GeneralTraffic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrafficService {
    boolean increaseTraffic(String shortCode, String trafficDate, String zoneId);
    void deleteTraffic(String shortCode);
    Page<GeneralTraffic> searchGeneralTraffics(GeneralTrafficsSearchRequest request);
    GeneralTraffic searchGeneralTrafficByShortCode(String shortCode);
    List<TrafficDataDto> getAllMonthTraffic(MonthTrafficsGetAllRequest request);
    List<TrafficDataDto> getDayTrafficInRange(DayTrafficInRangeRequest request);
    short[] getRealTimeTraffic(RealTimeTrafficRequest request);
}
