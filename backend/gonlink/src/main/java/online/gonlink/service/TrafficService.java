package online.gonlink.service;

import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.dto.TrafficData;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.entity.RealTimeTraffic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrafficService {
    boolean increaseTraffic(String shortCode, String trafficDate, String zoneId);
    void deleteTraffic(String shortCode);
    Page<GeneralTraffic> searchGeneralTraffics(GeneralTrafficsSearchRequest request);
    List<TrafficData> getAllMonthTraffic(MonthTrafficsGetAllRequest request);
    List<TrafficData> getDayTrafficInRange(DayTrafficInRangeRequest request);
    short[] getRealTimeTraffic(RealTimeTrafficRequest request);
}
