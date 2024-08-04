package online.gonlink.shortenservice.dto;

import java.io.Serializable;

public record KafkaMessage(ActionCode actionCode, Object obj) implements Serializable {
}
