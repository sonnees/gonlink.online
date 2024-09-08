package online.gonlink.observer;

import online.gonlink.dto.IncreaseTraffic;

public interface TrafficObserver {
    boolean increaseTraffic(IncreaseTraffic increaseTraffic);
    void deleteTraffic(String shortCode);
}
