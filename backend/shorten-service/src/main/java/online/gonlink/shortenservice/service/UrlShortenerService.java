package online.gonlink.shortenservice.service;

import online.gonlink.shortenservice.dto.ResponseGenerateShortCode;

public interface UrlShortenerService {
    ResponseGenerateShortCode generateShortCode(String originalUrl);
    ResponseGenerateShortCode generateShortCode(String email, String originalUrl);
    String getOriginalUrl(String shortCode, String clientTime, String zoneId);
}
