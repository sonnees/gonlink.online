package rutlink.online.shortenservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rutlink.online.shortenservice.entity.ShortUrl;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findShortUrlsByOriginalUrl(String originalUrl);
}
