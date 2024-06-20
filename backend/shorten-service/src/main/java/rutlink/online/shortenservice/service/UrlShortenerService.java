package rutlink.online.shortenservice.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rutlink.online.shortenservice.dto.LogObj;
import rutlink.online.shortenservice.entity.ShortUrl;
import rutlink.online.shortenservice.repository.ShortUrlRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class UrlShortenerService {
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlRepository shortUrlRepository;
    private final JsonConverter jsonConverter;
    private final CheckURL checkURL;

    public UrlShortenerService(ShortCodeGenerator shortCodeGenerator, ShortUrlRepository shortUrlRepository, JsonConverter jsonConverter, CheckURL checkURL) {
        this.shortCodeGenerator = shortCodeGenerator;
        this.shortUrlRepository = shortUrlRepository;
        this.jsonConverter = jsonConverter;
        this.checkURL = checkURL;
    }

    public String generateShortCode(String originalUrl) {

        try {
            if(!checkURL.isNotForbidden(originalUrl))
                throw new StatusRuntimeException(
                        Status.UNAUTHENTICATED.withDescription("URL Is Forbidden")
                );

            if(!checkURL.isExits(originalUrl))
                throw new StatusRuntimeException(
                        Status.UNKNOWN.withDescription("URL Not Exits")
                );

        } catch (IOException e ){
            log.error("@ {}", jsonConverter.objToString(e));
            throw new StatusRuntimeException(
                    Status.INTERNAL.withDescription("URL Error")
            );
        }

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent())
            return existingShortUrl.get().getShortCode();

        String shortCode = shortCodeGenerator.generateShortCode();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortCode(shortCode);
        shortUrl.setOriginalUrl(originalUrl);

        ShortUrl save = shortUrlRepository.save(shortUrl);
        if(save.getShortCode()!=null)
            return save.getShortCode();

        LogObj<? extends UrlShortenerService, ShortUrl> logObj = new LogObj<>(
                this.getClass(),
                "generateShortCode",
                shortUrl
        );
        log.error("@ {}", jsonConverter.objToString(logObj));
        throw new StatusRuntimeException(
                Status.INTERNAL.withDescription("Internal Server Error")
        );
    }

    public String getOriginalUrl(String shortCode) {
        Optional<ShortUrl> shortUrlOpt = shortUrlRepository.findById(shortCode);

        if (shortUrlOpt.isPresent())
            return shortUrlOpt.get().getOriginalUrl();

        throw new StatusRuntimeException(
                Status.NOT_FOUND.withDescription("Short URL not found for code: " + shortCode)
        );
    }

}
