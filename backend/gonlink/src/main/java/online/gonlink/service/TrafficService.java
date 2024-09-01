package online.gonlink.service;

public interface TrafficService {
    boolean increaseTraffic(String shortCode, String trafficDate, String zoneId);
    void deleteTraffic(String shortCode);
}
