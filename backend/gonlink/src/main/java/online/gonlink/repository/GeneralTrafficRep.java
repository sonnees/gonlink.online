package online.gonlink.repository;

import online.gonlink.entity.GeneralTraffic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralTrafficRep extends MongoRepository<GeneralTraffic, String> {
    @Query("{shortCode: ?0}")
    @Update("{'$inc': {traffic: 1}}")
    long increaseTraffic(String shortCode);

    Page<GeneralTraffic> findAllByOwner(String owner, Pageable pageRequest);

    List<GeneralTraffic> findAllByOwner(String owner);


}
