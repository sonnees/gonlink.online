package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.Traffic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseTrafficRepository<T extends Traffic, ID> extends MongoRepository<T, ID> {
}
