package online.gonlink.service.impl;

import io.grpc.Context;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.AuthConstants;
import online.gonlink.dto.IncreaseTraffic;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.observer.DayTrafficObserver;
import online.gonlink.observer.GeneralTrafficObserver;
import online.gonlink.observer.MonthTrafficObserver;
import online.gonlink.observer.RealTimeTrafficObserver;
import online.gonlink.observer.TrafficSubject;
import online.gonlink.repository.GeneralTrafficRepository;
import online.gonlink.service.TrafficService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrafficServiceImpl implements TrafficService {
    private GlobalValue globalValue;
    private final GeneralTrafficRepository generalTrafficRepository;
    private final TrafficSubject trafficSubject;
    private final GeneralTrafficObserver generalTrafficObserver;
    private final MonthTrafficObserver monthTrafficObserver;
    private final DayTrafficObserver dayTrafficObserver;
    private final RealTimeTrafficObserver realTimeTrafficObserver;

    @PostConstruct
    public void initObservers() {
        trafficSubject.addObserver(generalTrafficObserver);
        trafficSubject.addObserver(monthTrafficObserver);
        trafficSubject.addObserver(dayTrafficObserver);
        trafficSubject.addObserver(realTimeTrafficObserver);
    }

    @Override
    public boolean increaseTraffic(String shortCode, String trafficDate, String zoneId) {
        return trafficSubject.notifyObservers(new IncreaseTraffic(shortCode, trafficDate, zoneId));
    }

    @Override
    public void deleteTraffic(String shortCode) {
        trafficSubject.deleteTraffic(shortCode);
    }

    @Override
    public Page<GeneralTraffic> searchGeneralTraffics(GeneralTrafficsSearchRequest request){
        Page<GeneralTraffic> traffics;
        Context context = Context.current();
        Pageable pageable = PageRequest.of(
                request.getPage()==0?globalValue.getPAGE():request.getPage(),
                request.getSize()==0?globalValue.getSIZE():request.getSize()
        );
        traffics = generalTrafficRepository.findAllByOwner(AuthConstants.USER_EMAIL.get(context), pageable);
        return traffics;
    }
}
