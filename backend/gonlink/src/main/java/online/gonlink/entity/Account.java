package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "account")
public class Account {
    @Id
    private String email;
    private String name;
    private String avatar;
    private String role;
    private LocalDate create = LocalDate.now();

    private int totalShortURL = 0;
    private long totalClick = 0;

    private List<TrafficData> cities = new ArrayList<>();
    private List<TrafficData> countries = new ArrayList<>();
//    private List<TrafficData> longitudes = new ArrayList<>();
//    private List<TrafficData> latitudes = new ArrayList<>();
    private List<TrafficData> timezones = new ArrayList<>();

    private List<TrafficData> browsers = new ArrayList<>();
    private List<TrafficData> browserVersions = new ArrayList<>();
    private List<TrafficData> operatingSystems = new ArrayList<>();
    private List<TrafficData> osVersions = new ArrayList<>();
    private List<TrafficData> deviceTypes = new ArrayList<>();
    private List<TrafficData> deviceManufacturers = new ArrayList<>();
    private List<TrafficData> deviceNames = new ArrayList<>();

    public String getCreate() {
        return this.create.toString();
    }
}
