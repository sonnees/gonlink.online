package online.gonlink.config;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import online.gonlink.jwt.JwtUtil;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    AuthInterceptor authInterceptor(JwtUtil jwtUtil) {
        return new AuthInterceptor(jwtUtil);
    }

}
