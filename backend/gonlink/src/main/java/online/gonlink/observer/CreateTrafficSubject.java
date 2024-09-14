package online.gonlink.observer;

import jakarta.annotation.PostConstruct;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.TrafficCreateDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CreateTrafficSubject {
    private final GlobalValue globalValue;
    protected Set<CreateTrafficObserver> observers;
    protected ExecutorService executor;

    public CreateTrafficSubject(GlobalValue globalValue) {
        this.globalValue = globalValue;
    }

    @PostConstruct
    public void setUp(){
        observers = new HashSet<>();
        executor = Executors.newFixedThreadPool(globalValue.getTHREAD_FIX_POOL());
    }

    public boolean create(TrafficCreateDto trafficCreateDto) {
        for (CreateTrafficObserver observer : observers)
            executor.submit(() -> observer.create(trafficCreateDto));
        return true;
    }

    public void addObserver(CreateTrafficObserver observer) {
        observers.add(observer);
    }

    public void deleteObserver(CreateTrafficObserver observer) {
        observers.remove(observer);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
