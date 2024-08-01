package online.gonlink.shortenservice.config;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @GrpcGlobalServerInterceptor
    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor();
    }
}
