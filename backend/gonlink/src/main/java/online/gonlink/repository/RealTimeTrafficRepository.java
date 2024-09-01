package online.gonlink.repository;

import online.gonlink.entity.RealTimeTraffic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface RealTimeTrafficRepository extends MongoRepository<RealTimeTraffic, String> {
    @Query("{shortCode: ?0}")
    @Update("{ '$inc' : { 'trafficHours.?1' : 1 } }")
    long increaseTraffic(String shortCode, String trafficDate);

}
