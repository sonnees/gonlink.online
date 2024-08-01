package online.gonlink.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "day-traffic")
public class DayTraffic implements Traffic{
    @Id
    private TrafficID id;
    private int[] trafficHours;

    public DayTraffic(String shortCode, String trafficDate) {
        this.id = new TrafficID(shortCode, trafficDate);
        this.trafficHours = new int[24];
    }

}
