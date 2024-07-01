package online.gonlink.accountservice.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    @Value("${account-service.jwt.JWT_SECRET}")
    private String SECRET_KEY;

    public Claims validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(Map<String, Object> claims) {
        Integer exp = (Integer) claims.get("exp");
        return exp < (System.currentTimeMillis() / 1000);
    }
}