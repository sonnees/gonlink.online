package online.gonlink.config;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.exception.GrpcExceptionInterceptor;
import online.gonlink.jwt.JwtUtil;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    AuthInterceptor authInterceptor(GlobalValue globalValue, JwtUtil jwtUtil) {
        return new AuthInterceptor(globalValue, jwtUtil);
    }

    @GrpcGlobalServerInterceptor
    GrpcExceptionInterceptor grpcExceptionInterceptor(StandardResponseGrpc standardResponseGrpc) {
        return new GrpcExceptionInterceptor(standardResponseGrpc);
    }

    @GrpcGlobalServerInterceptor
    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor();
    }
}
