package online.gonlink.dto;

import java.time.ZonedDateTime;

public record TrafficCreateDto(String shortCode, String owner, String originalUrl, ZonedDateTime time) {
}
