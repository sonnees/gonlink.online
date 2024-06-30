package gonlink.online.shortenservice.dto;

import java.io.Serializable;

public record KafkaMessage(String actionCode, Object obj) implements Serializable {
}
