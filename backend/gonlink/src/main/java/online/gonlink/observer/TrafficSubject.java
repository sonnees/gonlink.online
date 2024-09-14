package online.gonlink.observer;

import jakarta.annotation.PostConstruct;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.TrafficIncreaseDto;
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

    public boolean notifyObservers(TrafficIncreaseDto trafficIncreaseDto) {
        for (TrafficObserver observer : observers)
            executor.submit(() -> observer.increaseTraffic(trafficIncreaseDto));
        return true;
    }

    public void deleteTraffic(String shortCode) {
        for (TrafficObserver observer : observers)
            executor.submit(() -> observer.deleteTraffic(shortCode));
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
