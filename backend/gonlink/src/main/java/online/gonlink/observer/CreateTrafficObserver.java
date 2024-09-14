package online.gonlink.observer;

import online.gonlink.dto.TrafficCreateDto;

public interface CreateTrafficObserver {
    boolean create(TrafficCreateDto trafficCreateDto);
}
