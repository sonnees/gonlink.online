package online.gonlink.shortenservice.config;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

}
