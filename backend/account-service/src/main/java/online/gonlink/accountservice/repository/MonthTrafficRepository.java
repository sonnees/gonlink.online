package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.MonthTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthTrafficRepository extends BaseTrafficRepository<MonthTraffic, TrafficID> {
}
