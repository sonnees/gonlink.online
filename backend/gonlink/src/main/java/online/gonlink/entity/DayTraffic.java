package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.gonlink.factory.Traffic;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "day-traffic")
public class DayTraffic extends TrafficAdvance implements Traffic {
    @Id
    private TrafficID id;
    private short[] trafficHours;

    public DayTraffic(String shortCode, String trafficDate) {
        this.id = new TrafficID(shortCode, trafficDate);
        this.trafficHours = new short[24];
    }

}
