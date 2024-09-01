package online.gonlink.repository;

import online.gonlink.entity.DayTraffic;
import online.gonlink.entity.TrafficID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface DayTrafficRepository extends MongoRepository<DayTraffic, TrafficID> {
    @Query("{id: ?0}")
    @Update("{ '$inc' : { 'trafficHours.?1' : 1 } }")
    long increaseTraffic(TrafficID id, int index);

    @Query(value = "{'id.shortCode': ?0}", delete = true)
    void deleteAllByShortCode(String shortCode);
}
