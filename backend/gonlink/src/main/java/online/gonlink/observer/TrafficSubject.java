package online.gonlink.observer;

import jakarta.annotation.PostConstruct;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.TrafficCreateDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TrafficSubject {
    private final GlobalValue globalValue;
    protected Set<TrafficObserver> observers;
    protected ExecutorService executor;

    public TrafficSubject(GlobalValue globalValue) {
        this.globalValue = globalValue;
    }

    @PostConstruct
    public void setUp(){
        observers = new HashSet<>();
        executor = Executors.newFixedThreadPool(globalValue.getTHREAD_FIX_POOL());
    }

    public boolean increasesTraffic(String owner, String originalUrl, GetOriginalUrlRequest request) {
        for (TrafficObserver observer : observers)
            executor.submit(() -> observer.increasesTraffic(owner, originalUrl, request));
        return true;
    }

    public boolean deletesTraffic(String shortCode) {
        for (TrafficObserver observer : observers)
            executor.submit(() -> observer.deletesTraffic(shortCode));
        return true;
    }

    public boolean createsTraffic(TrafficCreateDto trafficCreateDto) {
        for (TrafficObserver observer : observers)
            executor.submit(() -> observer.createsTraffic(trafficCreateDto));
        return true;
    }

    public void addObserver(TrafficObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(TrafficObserver observer) {
        observers.remove(observer);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
