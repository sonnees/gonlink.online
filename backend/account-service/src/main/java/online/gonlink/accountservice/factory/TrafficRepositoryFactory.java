package online.gonlink.accountservice.factory;

import lombok.AllArgsConstructor;
import online.gonlink.accountservice.entity.*;
import online.gonlink.accountservice.repository.BaseTrafficRepository;
import online.gonlink.accountservice.repository.DayTrafficRepository;
import online.gonlink.accountservice.repository.GeneralTrafficRepository;
import online.gonlink.accountservice.repository.MonthTrafficRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrafficRepositoryFactory {
    private final GeneralTrafficRepository generalTrafficRepository;
    private final MonthTrafficRepository monthTrafficRepository;
    private final DayTrafficRepository dayTrafficRepository;

    @SuppressWarnings("unchecked")
    public <T extends Traffic, ID> BaseTrafficRepository<T, ID> getRepository(T traffic) {
        if (traffic instanceof DayTraffic) {
            return (BaseTrafficRepository<T, ID>) dayTrafficRepository;
        } else if (traffic instanceof MonthTraffic) {
            return (BaseTrafficRepository<T, ID>) monthTrafficRepository;
        } else if (traffic instanceof GeneralTraffic) {
            return (BaseTrafficRepository<T, ID>) generalTrafficRepository;
        }
        throw new IllegalArgumentException("Unknown traffic type");
    }
}
