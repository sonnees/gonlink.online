package rutlink.online.accountservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import rutlink.online.accountservice.entity.Traffic;

import java.util.Optional;

@Repository
public interface TrafficRepository extends MongoRepository<Traffic, String> {
    @Query("{ 'shortCode': ?0, 'trafficDate': ?1}")
    Optional<Traffic> findTrafficByShortCodeAndTrafficDate(String shortCode, String trafficDate);


//    @Query("{'shortCode' : ?0, 'trafficDate' : ?1 }")
    @Update("{'$set' : {'trafficDate':'dsad' }}")
    Long increaseTraffic(String shortCode, String trafficDate, int index);
}
