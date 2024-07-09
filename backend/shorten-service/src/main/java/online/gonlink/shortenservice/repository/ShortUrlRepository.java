package online.gonlink.shortenservice.repository;

import online.gonlink.shortenservice.entity.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findShortUrlsByOriginalUrl(String originalUrl);
}
