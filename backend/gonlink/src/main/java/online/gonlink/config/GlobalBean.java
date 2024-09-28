package online.gonlink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import online.gonlink.constant.CommonConstant;
import online.gonlink.exception.GrpcExceptionInterceptor;
import online.gonlink.service.base.impl.IPInfoServiceImpl;
import online.gonlink.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class GlobalBean {

    @GrpcGlobalServerInterceptor
    AuthInterceptor authInterceptor(GlobalValue globalValue, JwtUtil jwtUtil, IPInfoServiceImpl ipGeolocationService) {
        return new AuthInterceptor(globalValue, jwtUtil, ipGeolocationService);
    }

    @GrpcGlobalServerInterceptor
    GrpcExceptionInterceptor grpcExceptionInterceptor() {
        return new GrpcExceptionInterceptor();
    }

    @GrpcGlobalServerInterceptor
    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor();
    }

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean(name = CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YM)
    SimpleDateFormat simpleDateFormat_YM() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat(CommonConstant.SIMPLE_DATE_FORMAT_YM);
        sdfUTC.setTimeZone(TimeZone.getTimeZone(CommonConstant.TIME_ZONE));
        return sdfUTC;
    }

    @Primary
    @Bean(name = CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD)
    SimpleDateFormat simpleDateFormat_YMD() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat(CommonConstant.SIMPLE_DATE_FORMAT_YMD);
        sdfUTC.setTimeZone(TimeZone.getTimeZone(CommonConstant.TIME_ZONE));
        return sdfUTC;
    }

    @Bean(name = CommonConstant.QUALIFIER_SIMPLE_DATE_FORMAT_YMD_HMS)
    SimpleDateFormat simpleDateFormatWithTime() {
        SimpleDateFormat sdfUTC = new SimpleDateFormat(CommonConstant.SIMPLE_DATE_FORMAT_YMD_HMS);
        sdfUTC.setTimeZone(TimeZone.getTimeZone(CommonConstant.TIME_ZONE));
        return sdfUTC;
    }

    @Bean
    DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
