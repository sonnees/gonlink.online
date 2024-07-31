package online.gonlink.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "day-traffic")
public class DayTraffic implements Traffic{
    @Id
    private TrafficID id;
    private Integer[] trafficHours;

    public DayTraffic(String shortCode, String trafficDate) {
        this.id = new TrafficID(shortCode, trafficDate);
        this.trafficHours = new Integer[24];
        Arrays.fill(trafficHours, 0);
    }

}
