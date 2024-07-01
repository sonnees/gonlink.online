package online.gonlink.shortenservice.dto;

import java.io.Serializable;

public record KafkaMessage(String actionCode, Object obj) implements Serializable {
}
