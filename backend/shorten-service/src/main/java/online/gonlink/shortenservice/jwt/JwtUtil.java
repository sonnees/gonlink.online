package online.gonlink.shortenservice.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.gonlink.shortenservice.config.AccountServiceConfig;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class JwtUtil {
    private AccountServiceConfig config;

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