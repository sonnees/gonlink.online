package online.gonlink.shortenservice.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.shortenservice.util.FormatLogMessage;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RateLimitInterceptor implements ServerInterceptor {
    private final Cache<String, Bucket> cache;

    public RateLimitInterceptor() {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String ip = getClientIp(headers);
        Bucket bucket = null;
        try {
            bucket = cache.get(ip, this::newBucket);
        } catch (ExecutionException e) {
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "interceptCall",
                    "Unexpected error: Bucket not create success!",
                    e
            ));
            throw new StatusRuntimeException(Status.INTERNAL.withDescription("Bucket not create success!"));
        }

        if (!bucket.tryConsume(1)) {
            call.close(Status.RESOURCE_EXHAUSTED
                    .withDescription("Rate limit exceeded"), new Metadata());
            return new ServerCall.Listener<ReqT>() {};
        }

        return next.startCall(call, headers);
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(6)
                                .refillGreedy(6, Duration.ofMinutes(1))
                                .build()
                ).build();
    }

    private String getClientIp(Metadata headers) {
        return headers.get(Metadata.Key.of("X-Forwarded-For", Metadata.ASCII_STRING_MARSHALLER));
    }
}
