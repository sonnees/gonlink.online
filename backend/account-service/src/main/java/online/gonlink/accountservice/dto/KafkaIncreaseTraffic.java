package online.gonlink.accountservice.dto;

import java.io.Serializable;

public record KafkaIncreaseTraffic(String shortCode, String trafficDate, String zoneId) implements Serializable {
}
