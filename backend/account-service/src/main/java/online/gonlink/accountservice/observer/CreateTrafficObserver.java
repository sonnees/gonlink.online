package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;

public interface CreateTrafficObserver {
    boolean create(IncreaseTraffic increaseTraffic);
}
