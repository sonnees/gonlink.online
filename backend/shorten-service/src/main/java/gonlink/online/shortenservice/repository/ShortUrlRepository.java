package gonlink.online.shortenservice.repository;

import gonlink.online.shortenservice.entity.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findShortUrlsByOriginalUrl(String originalUrl);
}
