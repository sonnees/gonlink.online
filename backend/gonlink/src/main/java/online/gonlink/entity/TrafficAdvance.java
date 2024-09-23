package online.gonlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrafficAdvance {
    private List<TrafficData> cities = new ArrayList<>();
    private List<TrafficData> countries = new ArrayList<>();
    private List<TrafficData> zoneIds = new ArrayList<>();
    private List<TrafficData> browsers = new ArrayList<>();
    private List<TrafficData> browserVersions = new ArrayList<>();
    private List<TrafficData> operatingSystems = new ArrayList<>();
    private List<TrafficData> osVersions = new ArrayList<>();
    private List<TrafficData> deviceTypes = new ArrayList<>();
    private List<TrafficData> deviceManufacturers = new ArrayList<>();
    private List<TrafficData> deviceNames = new ArrayList<>();
}
