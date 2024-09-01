package online.gonlink.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.config.GlobalValue;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class JwtUtil {
    private GlobalValue config;

    public Claims validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(config.getSECRET_KEY().getBytes());
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