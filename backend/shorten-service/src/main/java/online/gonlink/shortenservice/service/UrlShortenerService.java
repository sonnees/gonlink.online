package online.gonlink.shortenservice.service;

public interface UrlShortenerService {
    String generateShortCode(String originalUrl);
    String generateShortCode(String email, String originalUrl);
    String getOriginalUrl(String shortCode);
}
