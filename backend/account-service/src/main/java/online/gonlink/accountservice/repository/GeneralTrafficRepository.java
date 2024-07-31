package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.GeneralTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralTrafficRepository extends BaseTrafficRepository<GeneralTraffic, TrafficID> {
}
