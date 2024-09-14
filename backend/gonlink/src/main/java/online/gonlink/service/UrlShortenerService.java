package online.gonlink.service;

import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordResponse;
import online.gonlink.ShortCodeCheckExistRequest;
import online.gonlink.ShortCodeCheckExistResponse;
import online.gonlink.dto.ResponseGenerateShortCode;

public interface UrlShortenerService {
    OriginalUrlCheckNeedPasswordResponse checkNeedPassword(OriginalUrlCheckNeedPasswordRequest request);
    boolean checkExistShortCode(String shortCode);
    ShortCodeCheckExistResponse checkExistShortCode(ShortCodeCheckExistRequest request);
    ResponseGenerateShortCode generateShortCode(GenerateShortCodeRequest request);
    ResponseGenerateShortCode generateShortCode(String email, GenerateShortCodeAccountRequest request);
    String getOriginalUrl(GetOriginalUrlRequest request);
}
