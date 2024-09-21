package online.gonlink.repository;

import online.gonlink.entity.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRep extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findShortUrlsByOriginalUrl(String originalUrl);
}
