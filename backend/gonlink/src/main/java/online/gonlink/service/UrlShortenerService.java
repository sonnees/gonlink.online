package online.gonlink.service;

import online.gonlink.dto.ResponseGenerateShortCode;

public interface UrlShortenerService {
    ResponseGenerateShortCode generateShortCode(String originalUrl, String trafficDate, String zoneId);
    ResponseGenerateShortCode generateShortCode(String email, String originalUrl, String trafficDate, String zoneId);
    String getOriginalUrl(String shortCode, String clientTime, String zoneId);
}
