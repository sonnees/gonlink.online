package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.gonlink.util.DateUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "month-traffic")
public class MonthTraffic implements Traffic{
    @Id
    private TrafficID id;
    private short[] trafficDays;

    public MonthTraffic(String shortCode, String trafficDate) {
        this.id = new TrafficID(shortCode, trafficDate);
        this.trafficDays = new short[DateUtil.getDaysInDate(trafficDate)];
    }

}
