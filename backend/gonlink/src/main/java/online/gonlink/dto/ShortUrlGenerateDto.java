package online.gonlink.dto;

import java.time.LocalDateTime;

public record ShortUrlGenerateDto(String dateTime, String zoneId, String shortCode, String originalUrl, String alias, String desc, LocalDateTime timeExpired, String password, long maxUsage) {
}
