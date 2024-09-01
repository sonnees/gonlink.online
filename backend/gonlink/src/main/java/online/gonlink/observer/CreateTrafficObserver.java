package online.gonlink.observer;

import online.gonlink.dto.IncreaseTraffic;

public interface CreateTrafficObserver {
    boolean create(IncreaseTraffic increaseTraffic);
}
