package online.gonlink.observer;

import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.dto.TrafficCreateDto;

public interface TrafficObserver {
    boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request);
    void deletesTraffic(String shortCode);
    boolean createsTraffic(TrafficCreateDto trafficCreateDto);
}
