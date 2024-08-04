package online.gonlink.accountservice.observer;

import online.gonlink.accountservice.dto.IncreaseTraffic;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreateTrafficSubject {
    protected final Set<CreateTrafficObserver> observers = new HashSet<>();

    public boolean create(IncreaseTraffic increaseTraffic) {
        for (CreateTrafficObserver observer : observers)
            observer.create(increaseTraffic);
        return true;
    }

    public void addObserver(CreateTrafficObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(CreateTrafficObserver observer) {
        observers.remove(observer);
    }
}
