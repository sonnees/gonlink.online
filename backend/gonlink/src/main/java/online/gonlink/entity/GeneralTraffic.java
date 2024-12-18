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
@Document(collection = "general-traffic")
public class GeneralTraffic implements Traffic {
    @Id
    private String shortCode;
    private String owner;
    private String originalUrl;
    private String trafficDate; // createDate
    private long traffic;

    public GeneralTraffic(String shortCode, String owner, String originalUrl, String trafficDate) {
        this.shortCode = shortCode;
        this.owner = owner;
        this.originalUrl = originalUrl;
        this.trafficDate = trafficDate;
        this.traffic = 0;
    }
}
