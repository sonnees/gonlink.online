package gonlink.online.shortenservice.service;

public interface UrlShortenerService {
    String generateShortCode(String originalUrl);
    String getOriginalUrl(String shortCode);
}
