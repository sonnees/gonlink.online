package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TrafficSubject {
    protected final Set<TrafficObserver> observers = new HashSet<>();

    public boolean notifyObservers(IncreaseTraffic increaseTraffic) {
        for (TrafficObserver observer : observers)
            observer.increaseTraffic(increaseTraffic);

        return true;
    }

    public void deleteTraffic(String shortCode) {
        for (TrafficObserver observer : observers)
            observer.deleteTraffic(shortCode);
    }

    public void addObserver(TrafficObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(TrafficObserver observer) {
        observers.remove(observer);
    }
}
