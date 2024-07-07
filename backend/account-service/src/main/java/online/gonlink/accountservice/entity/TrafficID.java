package online.gonlink.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrafficID {
    private String shortCode;
    private String trafficDate;
}
