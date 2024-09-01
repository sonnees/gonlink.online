package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "general-traffic")
public class GeneralTraffic implements Traffic{
    @Id
    private String shortCode;
    private String trafficDate;
    private long traffic;

    public GeneralTraffic(String shortCode, String trafficDate) {
        this.shortCode = shortCode;
        this.trafficDate = trafficDate;
        this.traffic = 0;
    }
}
