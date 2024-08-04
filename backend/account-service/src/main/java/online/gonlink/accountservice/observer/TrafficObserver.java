package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;

public interface TrafficObserver {
    boolean increaseTraffic(IncreaseTraffic increaseTraffic);
    void deleteTraffic(String shortCode);
}
