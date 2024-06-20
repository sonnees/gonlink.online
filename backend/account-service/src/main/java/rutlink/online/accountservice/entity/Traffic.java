package rutlink.online.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "traffic")
public class Traffic {
    @Id
    private String shortCode;
    private ZonedDateTime trafficDate;
    private Integer[] trafficHours;

    public Traffic(String shortCode) {
        this.shortCode = shortCode;
        this.trafficDate = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        this.trafficHours = new Integer[24];
        Arrays.fill(trafficHours, 0);
    }
}
