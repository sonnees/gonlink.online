package online.gonlink.config;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.gonlink.dto.AuthConstants;
import online.gonlink.dto.UserInfo;
import online.gonlink.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthInterceptor implements ServerInterceptor {
    JwtUtil jwtUtil;

    static Set<String> PUBLIC_METHODS = Set.of();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String fullMethodName = call.getMethodDescriptor().getFullMethodName();
        if(PUBLIC_METHODS.contains(fullMethodName)) return next.startCall(call, headers);

        String token = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        if (token != null && token.startsWith("Bearer ")) {
            String bearerToken = token.substring(7);

            UserInfo userInfo =  authenticateAndGetUserInfo(bearerToken);
            if(userInfo!=null) {
                Context context = Context.current()
                        .withValue(AuthConstants.USER_EMAIL, userInfo.user_email())
                        .withValue(AuthConstants.USER_NAME, userInfo.user_name())
                        .withValue(AuthConstants.USER_AVATAR, userInfo.user_avatar())
                        .withValue(AuthConstants.USER_ROLE, userInfo.user_role());
                return Contexts.interceptCall(context, call, headers, next);
            }
        }
        call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing token"), new Metadata());
        return new ServerCall.Listener<>() {};
    }

    private UserInfo authenticateAndGetUserInfo(String token) {
        try{
            Map<String, Object> objectMap = jwtUtil.validateToken(token);
            boolean tokenExpired = jwtUtil.isTokenExpired(objectMap);
            if(tokenExpired) return null;
            return new UserInfo(
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
}