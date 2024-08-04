package online.gonlink.shortenservice.dto;

public record KafkaAppendUrl(String email, String shortCode, String originalUrl, String trafficDate, String zoneId) {
}
