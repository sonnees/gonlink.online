package online.gonlink.accountservice.observer;

import lombok.AllArgsConstructor;
import online.gonlink.accountservice.dto.IncreaseTraffic;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class TrafficSubject {
    protected final Set<TrafficObserver> observers;

    public boolean notifyObservers(IncreaseTraffic increaseTraffic) {
        for (TrafficObserver observer : observers) {
            observer.increaseTraffic(increaseTraffic);
        }
        return true;
    }

    public boolean addObserver(TrafficObserver observer) {
        return observers.add(observer);
    }

    public boolean deleteObserver(TrafficObserver observer) {
        return observers.remove(observer);
    }
}
