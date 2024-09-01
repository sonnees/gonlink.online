package online.gonlink.repository;

import online.gonlink.entity.Account;
import online.gonlink.entity.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    @Query(value = "{email: ?0}")
    @Update("{$push:{urls: ?1}}")
    long appendUrl(String email, ShortUrl shortUrl);

    @Query(value = "{email: ?0}")
    @Update("{$pull:{urls: {shortCode: ?1}}}")
    long removeUrl(String email, String removeUrl);
}
