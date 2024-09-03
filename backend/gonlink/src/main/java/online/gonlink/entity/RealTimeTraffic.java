package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "real-time-traffic")
public class RealTimeTraffic implements Traffic{
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
