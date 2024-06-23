package gonlink.online.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "traffic")
public class Traffic {
    @Id // uni
    private String shortCode;
    @Id
    private String trafficDate;
    private Integer[] trafficHours;

    public Traffic(String shortCode, String trafficDate) {
        this.shortCode = shortCode;
        this.trafficDate = trafficDate;
        this.trafficHours = new Integer[24];
        Arrays.fill(trafficHours, 0);
    }

}
