package online.gonlink.shortenservice.service.impl;

import com.mongodb.DuplicateKeyException;
import online.gonlink.shortenservice.dto.*;
import online.gonlink.shortenservice.entity.ShortUrl;
import online.gonlink.shortenservice.exception.GrpcStatusException;
import online.gonlink.shortenservice.service.CheckURL;
import online.gonlink.shortenservice.service.ShortCodeGenerator;
import online.gonlink.shortenservice.service.UrlShortenerService;
import online.gonlink.shortenservice.service.base.ProducerService;
import online.gonlink.shortenservice.util.FormatLogMessage;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import online.gonlink.shortenservice.repository.ShortUrlRepository;

import java.net.MalformedURLException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlRepository shortUrlRepository;
    private final CheckURL checkURL;
    private final ProducerService producerService;

    @Override
    public ResponseGenerateShortCode generateShortCode(String originalUrl, String trafficDate, String zoneId) {
        try {
            if(!checkURL.isNotForbidden(originalUrl))
                throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("URL Is Forbidden"));
            if(!checkURL.isExits(originalUrl))
                throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found"));
        }
        catch (StatusRuntimeException e){
            throw new GrpcStatusException(e);
        }
        catch (MalformedURLException e){
            throw new GrpcStatusException(new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found")));
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
        if (existingShortUrl.isPresent()) return new ResponseGenerateShortCode(existingShortUrl.get().getShortCode(), false);

        while (true){
            String shortCode = shortCodeGenerator.generateShortCode();
            ShortUrl shortUrl = new ShortUrl(shortCode,originalUrl);
            try{
                shortUrlRepository.insert(shortUrl);
                KafkaMessage message = new KafkaMessage(ActionCode.APPEND_URL, new KafkaAnonymousUrl(shortCode, trafficDate, zoneId));
                producerService.sendMessage(message);
                return new ResponseGenerateShortCode(shortCode, true);
            } catch (DuplicateKeyException ignored){
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
    }

    @Override
    public ResponseGenerateShortCode generateShortCode(String email, String originalUrl, String trafficDate, String zoneId) {
        try {
            if(!checkURL.isNotForbidden(originalUrl))
                throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("URL Is Forbidden"));
            if(!checkURL.isExits(originalUrl))
                throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found"));
        }
        catch (StatusRuntimeException e){
            throw new GrpcStatusException(e);
        }
        catch (MalformedURLException e){
            throw new GrpcStatusException(new StatusRuntimeException(Status.NOT_FOUND.withDescription("URL Not Found")));
        }
        catch (Exception e ){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "generateShortCode account < checkURL",
                    "Unexpected error: {}",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("URL Error"));
        }

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findShortUrlsByOriginalUrl(originalUrl);
        if (existingShortUrl.isPresent()) return new ResponseGenerateShortCode(existingShortUrl.get().getShortCode(), false);

        while (true){
            String shortCode = shortCodeGenerator.generateShortCode();
            ShortUrl shortUrl = new ShortUrl(shortCode,originalUrl);
            try{
                shortUrlRepository.insert(shortUrl);
                KafkaMessage message = new KafkaMessage(ActionCode.APPEND_URL, new KafkaAppendUrl(email, shortCode, originalUrl, trafficDate, zoneId));
                producerService.sendMessage(message);
                return new ResponseGenerateShortCode(shortCode, true);
            } catch (DuplicateKeyException ignored){
            } catch (Exception e){
                log.error(FormatLogMessage.formatLogMessage(
                        this.getClass().getSimpleName(),
                        "generateShortCode account",
                        "Unexpected error: {}",
                        e
                ));
                throw new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error"));
            }
        }
    }

    @Override
    public String getOriginalUrl(String shortCode, String clientTime, String zoneId) {
        Optional<ShortUrl> shortUrlOpt = shortUrlRepository.findById(shortCode);
        if (shortUrlOpt.isPresent()) {
            KafkaMessage message = new KafkaMessage(ActionCode.INCREASE_TRAFFIC, new KafkaIncreaseTraffic(shortCode, clientTime, zoneId));
            producerService.sendMessage(message);
            return shortUrlOpt.get().getOriginalUrl();
        }
        throw new StatusRuntimeException(Status.NOT_FOUND.withDescription("Short URL not found for code: " + shortCode));
    }

}
