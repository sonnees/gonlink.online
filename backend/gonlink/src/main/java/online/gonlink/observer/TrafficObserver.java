package online.gonlink.observer;

import online.gonlink.dto.TrafficIncreaseDto;

public interface TrafficObserver {
    boolean increaseTraffic(TrafficIncreaseDto trafficIncreaseDto);
    void deleteTraffic(String shortCode);
}
