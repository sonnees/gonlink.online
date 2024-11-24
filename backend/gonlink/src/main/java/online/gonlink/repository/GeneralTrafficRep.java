package online.gonlink.repository;

import online.gonlink.entity.GeneralTraffic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface GeneralTrafficRep extends MongoRepository<GeneralTraffic, String> {
    @Query("{shortCode: ?0}")
    @Update("{'$inc': {traffic: 1}}")
    long increaseTraffic(String shortCode);

    @Aggregation(pipeline = {
            "{ $match: { 'owner': ?0 } }",
            "{ $addFields: { 'convertedTrafficDate': { $toDate: '$trafficDate' } } }",
            "{ $match: { 'convertedTrafficDate': { $gte: ?1, $lte: ?2 } } }",
            "{ $sort: { 'convertedTrafficDate': ?3 } }",
            "{ $skip: ?4 }",
            "{ $limit: ?5 }"
    })
    List<GeneralTraffic> findAllByOwnerAndTrafficDateBetweenWithPaginationAndSort(
            String owner,
            Date startDate,
            Date endDate,
            int sortDirection,
            int skip,
            int limit);

    @Aggregation(pipeline = {
            "{ $match: { 'owner': ?0 } }",
            "{ $addFields: { 'convertedTrafficDate': { $toDate: '$trafficDate' } } }",
            "{ $match: { 'convertedTrafficDate': { $gte: ?1, $lte: ?2 } } }"
    })
    List<GeneralTraffic> countByOwnerAndTrafficDateBetween(String owner, Date startDate, Date endDate);



    List<GeneralTraffic> findAllByOwner(String owner);


}
