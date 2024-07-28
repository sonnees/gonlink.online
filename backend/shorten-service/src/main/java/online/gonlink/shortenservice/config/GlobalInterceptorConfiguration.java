package online.gonlink.shortenservice.config;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
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
