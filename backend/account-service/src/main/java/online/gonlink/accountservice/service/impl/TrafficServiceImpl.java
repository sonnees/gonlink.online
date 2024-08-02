package online.gonlink.accountservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.accountservice.dto.IncreaseTraffic;
import online.gonlink.accountservice.observer.DayTrafficObserver;
import online.gonlink.accountservice.observer.GeneralTrafficObserver;
import online.gonlink.accountservice.observer.MonthTrafficObserver;
import online.gonlink.accountservice.observer.TrafficSubject;
import online.gonlink.accountservice.service.TrafficService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TrafficServiceImpl implements TrafficService {
    private final TrafficSubject trafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final MonthTrafficObserver monthTrafficObserver;
    private final DayTrafficObserver dayTrafficObserver;

    public TrafficServiceImpl(TrafficSubject trafficSubject, GeneralTrafficObserver generalTrafficObserver, MonthTrafficObserver monthTrafficObserver, DayTrafficObserver dayTrafficObserver) {
        this.trafficSubject = trafficSubject;
        this.generalTrafficObserver = generalTrafficObserver;
        this.monthTrafficObserver = monthTrafficObserver;
        this.dayTrafficObserver = dayTrafficObserver;
    }

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
}
