package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.gonlink.factory.Traffic;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "real-time-traffic")
public class RealTimeTraffic implements Traffic {
    @Id
    private String shortCode;
    private String updateAt;
    private short[] trafficMinute;

    public RealTimeTraffic(String shortCode, String updateAt) {
        this.shortCode = shortCode;
        this.trafficMinute = new short[60];
        this.updateAt = updateAt;
    }
}
