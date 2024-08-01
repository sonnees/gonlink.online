package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.GeneralTraffic;
import online.gonlink.accountservice.entity.TrafficID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralTrafficRepository extends MongoRepository<GeneralTraffic, String> {
    @Query("{shortCode: ?0}")
    @Update("{'$inc': {traffic: 1}}")
    Long increaseTraffic(String shortCode);

}
