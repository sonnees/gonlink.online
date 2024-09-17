package online.gonlink.config;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.AllArgsConstructor;
import online.gonlink.constant.AuthConstant;
import online.gonlink.dto.UserInfoDto;
import online.gonlink.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class AuthInterceptor implements ServerInterceptor {
    private final GlobalValue globalValue;
    private final JwtUtil jwtUtil;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        Context contextHead = Context.current()
                .withValue(AuthConstant.IP_ADDRESS, getClientIp(headers));

        String fullMethodName = call.getMethodDescriptor().getFullMethodName();
        if(globalValue.getPUBLIC_METHODS().contains(fullMethodName)) return Contexts.interceptCall(contextHead, call, headers, next);

        String token = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        if (token != null && token.startsWith("Bearer ")) {
            String bearerToken = token.substring(7);

            UserInfoDto userInfoDto =  authenticateAndGetUserInfo(bearerToken);
            if(userInfoDto !=null) {
                Context context = Context.current()
                        .withValue(AuthConstant.USER_EMAIL, userInfoDto.user_email())
                        .withValue(AuthConstant.USER_NAME, userInfoDto.user_name())
                        .withValue(AuthConstant.USER_AVATAR, userInfoDto.user_avatar())
                        .withValue(AuthConstant.USER_ROLE, userInfoDto.user_role());
                return Contexts.interceptCall(context, call, headers, next);
            }
        }
        call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing token"), new Metadata());
        return new ServerCall.Listener<>() {};
    }

    private UserInfoDto authenticateAndGetUserInfo(String token) {
        try{
            Map<String, Object> objectMap = jwtUtil.validateToken(token);
            boolean tokenExpired = jwtUtil.isTokenExpired(objectMap);
            if(tokenExpired) return null;
            return new UserInfoDto(
                    (String) objectMap.get("email"),
                    (String) objectMap.get("name"),
                    (String) objectMap.get("avatar"),
                    (String) objectMap.get("role")
            );
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    private String getClientIp(Metadata headers) {
        return headers.get(Metadata.Key.of("X-Forwarded-For", Metadata.ASCII_STRING_MARSHALLER));
    }
}