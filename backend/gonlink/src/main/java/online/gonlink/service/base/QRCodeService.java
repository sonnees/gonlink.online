package online.gonlink.service.base;

public interface QRCodeService {
    String getStringBase64Image(String content, int optionalWidth, int optionalHeight);
    String getStringBase64Image(String content);
}
