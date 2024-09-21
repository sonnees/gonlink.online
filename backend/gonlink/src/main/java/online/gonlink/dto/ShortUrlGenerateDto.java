package online.gonlink.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record ShortUrlGenerateDto(ZonedDateTime time, String shortCode, String originalUrl, String alias, String desc, String timeExpired, String password, long maxUsage) {
}
