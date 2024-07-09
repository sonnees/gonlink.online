package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.Traffic;
import online.gonlink.accountservice.entity.TrafficID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends MongoRepository<Traffic, TrafficID> {
    @Query("{id: ?0}")
    @Update("{ '$inc' : { 'trafficHours.?1' : 1 } }")
    Long increaseTraffic(TrafficID id, int index);

}
