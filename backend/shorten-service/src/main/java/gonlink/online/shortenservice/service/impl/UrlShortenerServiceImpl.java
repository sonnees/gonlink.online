package gonlink.online.shortenservice.service.impl;

import com.mongodb.DuplicateKeyException;
import gonlink.online.shortenservice.entity.ShortUrl;
import gonlink.online.shortenservice.exception.GrpcStatusException;
import gonlink.online.shortenservice.service.CheckURL;
import gonlink.online.shortenservice.service.ShortCodeGenerator;
import gonlink.online.shortenservice.service.UrlShortenerService;
import gonlink.online.shortenservice.util.FormatLogMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import gonlink.online.shortenservice.repository.ShortUrlRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlRepository shortUrlRepository;
    private final CheckURL checkURL;

    @Override
    public String generateShortCode(String originalUrl) {
        try {
            if(!checkURL.isNotForbidden(originalUrl))
                throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("URL Is Forbidden"));
            if(!checkURL.isExits(originalUrl))
                throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found"));
        }
        catch (StatusRuntimeException e){
            throw new GrpcStatusException(e);
        }
        catch (Exception e ){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "generateShortCode < checkURL",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("URL Error"));
        }

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent()) return existingShortUrl.get().getShortCode();

        String shortCode = shortCodeGenerator.generateShortCode();

        ShortUrl shortUrl = new ShortUrl(shortCode,originalUrl);
        try{
            shortUrlRepository.insert(shortUrl);
            return shortCode;
        } catch (DuplicateKeyException e){
            throw new StatusRuntimeException( Status.ALREADY_EXISTS.withDescription("Duplicate Key Error"));
        } catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "generateShortCode",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
        }
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        Optional<ShortUrl> shortUrlOpt = shortUrlRepository.findById(shortCode);
        if (shortUrlOpt.isPresent())return shortUrlOpt.get().getOriginalUrl();
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("Short URL not found for code: " + shortCode));
    }

}
