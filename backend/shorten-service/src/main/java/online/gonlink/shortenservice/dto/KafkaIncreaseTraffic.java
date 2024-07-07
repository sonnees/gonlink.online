package online.gonlink.shortenservice.dto;

import java.io.Serializable;

public record KafkaIncreaseTraffic(String shortCode, String trafficDate, String zoneId) implements Serializable {
}
