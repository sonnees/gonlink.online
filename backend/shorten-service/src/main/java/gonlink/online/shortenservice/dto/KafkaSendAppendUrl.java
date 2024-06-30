package gonlink.online.shortenservice.dto;

import java.io.Serializable;

public record KafkaSendAppendUrl(String email, String url) implements Serializable {
}
