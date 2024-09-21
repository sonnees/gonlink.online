package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account extends TrafficAdvance {
    @Id
    private String email;
    private String name;
    private String avatar;
    private String role;
    private String create = LocalDate.now().toString();
    private int totalShortURL = 0;
    private long totalClick = 0;
}
