package online.gonlink.observer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import online.gonlink.dto.IncreaseTraffic;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class CreateTrafficSubject {
    Set<CreateTrafficObserver> observers = new HashSet<>();

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