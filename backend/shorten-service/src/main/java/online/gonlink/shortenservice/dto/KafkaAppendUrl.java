package online.gonlink.shortenservice.dto;

import java.io.Serializable;

public record KafkaAppendUrl(String email, String shortCode, String originalUrl) implements Serializable {
}
