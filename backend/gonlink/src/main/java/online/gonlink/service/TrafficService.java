package online.gonlink.service;

import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.entity.GeneralTraffic;
import org.springframework.data.domain.Page;

public interface TrafficService {
    boolean increaseTraffic(String shortCode, String trafficDate, String zoneId);
    void deleteTraffic(String shortCode);
    Page<GeneralTraffic> searchGeneralTraffics(GeneralTrafficsSearchRequest request);
}
