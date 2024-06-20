package rutlink.online.accountservice.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import rutlink.online.accountservice.entity.Traffic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TrafficRepository extends MongoRepository<Traffic, String> {
    Optional<Traffic> findTrafficByShortCodeAndTrafficDate(String shortCode, ZonedDateTime trafficDate);

    @Query(value = "{ 'shortCode' : ?0, 'trafficDate' : ?1 }", fields = "{ 'trafficHours.$[index]' : { $add: 1 } }")
    Long addValueToTrafficHour(String shortCode, ZonedDateTime trafficDate, int index);
}
