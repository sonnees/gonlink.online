package online.gonlink.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class TrafficDayDto {
    private List<TrafficDataDto> trafficDataDtoList;
    private Map<String, Long> cities;
    private Map<String, Long>  countries;
    private Map<String, Long>  zoneIds;
    private Map<String, Long>  browsers;
    private Map<String, Long>  browserVersions;
    private Map<String, Long>  operatingSystems;
    private Map<String, Long>  deviceTypes;
}
