package online.gonlink.accountservice.repository;

import online.gonlink.accountservice.entity.Account;
import online.gonlink.accountservice.entity.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    @Query(value = "{'email': ?0}")
    @Update("{$push:{'urls': ?1}}")
    Long appendUrl(String email, ShortUrl shortUrl);
}
