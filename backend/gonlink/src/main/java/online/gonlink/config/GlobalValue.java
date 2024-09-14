package online.gonlink.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Set;

@Getter
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalValue {
    @Value("${gonlink.paging.page}")
    int PAGE;

    @Value("${gonlink.paging.size}")
    int SIZE;

    @Value("${gonlink.paging.sort-direction}")
    Sort.Direction SORT_DIRECTION;

    @Value("${gonlink.qr.width}")
    int WIDTH;

    @Value("${gonlink.qr.height}")
    int HEIGHT;

    @Value("${gonlink.jwt.secret-key}")
    String SECRET_KEY;

    @Value("${gonlink.url-forbidden}")
    String[] URL_FORBIDDEN;

    @Value("${gonlink.allowed-characters}")
    String ALLOWED_CHARACTERS;

    @Value("${gonlink.length-short-code}")
    int LENGTH_SHORT_CODE;

    @Value("${front-end.domain}")
    String FRONTEND_DOMAIN;

    @Value("${gonlink.public-method}")
    Set<String> PUBLIC_METHODS;

    @Value("${gonlink.thread.fix-pool}")
    int THREAD_FIX_POOL;
}
