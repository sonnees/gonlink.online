package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;
import online.gonlink.accountservice.entity.TrafficID;

public interface TrafficObserver {
    boolean increaseTraffic(IncreaseTraffic increaseTraffic);
}
