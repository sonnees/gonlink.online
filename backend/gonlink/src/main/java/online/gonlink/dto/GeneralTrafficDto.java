package online.gonlink.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.gonlink.entity.GeneralTraffic;

@Setter
@Getter
@NoArgsConstructor
public class GeneralTrafficDto{
    private String shortCode;
    private String originalUrl;
    private String trafficDate;
    private long traffic;
    private boolean active;
    private String alias;
    private String desc;
    private String timeExpired;
    private boolean isUsingPassword;
    private long maxUsage;

    public GeneralTrafficDto(GeneralTraffic generalTraffic) {
        this.shortCode = generalTraffic.getShortCode();
        this.originalUrl = generalTraffic.getOriginalUrl();
        this.trafficDate = generalTraffic.getTrafficDate();
        this.traffic = generalTraffic.getTraffic();
    }
}

