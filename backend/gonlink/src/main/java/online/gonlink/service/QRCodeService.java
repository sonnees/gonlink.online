package online.gonlink.service;

import online.gonlink.GetStringBase64ImageRequest;
import online.gonlink.GetStringBase64ImageResponse;

public interface QRCodeService {
    GetStringBase64ImageResponse getStringBase64Image(GetStringBase64ImageRequest request);
    String getStringBase64Image(String content);
}
