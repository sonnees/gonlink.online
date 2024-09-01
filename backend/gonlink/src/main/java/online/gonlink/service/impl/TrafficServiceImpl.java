package online.gonlink.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.observer.DayTrafficObserver;
import online.gonlink.observer.GeneralTrafficObserver;
import online.gonlink.observer.MonthTrafficObserver;
import online.gonlink.observer.TrafficSubject;
import online.gonlink.service.TrafficService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrafficServiceImpl implements TrafficService {
    private final TrafficSubject trafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final MonthTrafficObserver monthTrafficObserver;
    private final DayTrafficObserver dayTrafficObserver;

    @PostConstruct
    public void initObservers() {
        trafficSubject.addObserver(generalTrafficObserver);
        trafficSubject.addObserver(monthTrafficObserver);
        trafficSubject.addObserver(dayTrafficObserver);
    }

    @Override
    public boolean increaseTraffic(String shortCode, String trafficDate, String zoneId) {
        return trafficSubject.notifyObservers(new IncreaseTraffic(shortCode, trafficDate, zoneId));
    }

    @Override
    public void deleteTraffic(String shortCode) {
        trafficSubject.deleteTraffic(shortCode);
    }

}
