package online.gonlink.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.exception.enumdef.ExceptionEnum;
import online.gonlink.exception.ResourceException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RateLimitInterceptor implements ServerInterceptor {
    Cache<String, Bucket> cache;

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
        Bucket bucket;
        try {
            bucket = cache.get(ip, this::newBucket);
        } catch (ExecutionException e) {
            throw new ResourceException(ExceptionEnum.RLI_CREATE_BUCKET_FAIL, e);
        }

        if (!bucket.tryConsume(1)) {
            throw new ResourceException(ExceptionEnum.RLI_LIMIT_EXCEEDED, null);
        }

        return next.startCall(call, headers);
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(
                        Bandwidth.builder()
                                .capacity(100)
                                .refillGreedy(100, Duration.ofMinutes(1))
                                .build()
                ).build();
    }

    private String getClientIp(Metadata headers) {
        return headers.get(Metadata.Key.of("X-Forwarded-For", Metadata.ASCII_STRING_MARSHALLER));
    }
}
