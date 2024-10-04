package online.gonlink.service;

import com.google.protobuf.Message;
import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GenerateShortCodeResponse;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.GetOriginalUrlResponse;
import online.gonlink.OriginalUrlCheckNeedPasswordRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordResponse;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.RemoveUrlResponse;
import online.gonlink.ShortCodeCheckExistRequest;
import online.gonlink.ShortCodeCheckExistResponse;
import online.gonlink.ShortCodeUpdateRequest;
import online.gonlink.ShortCodeUpdateResponse;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.entity.ShortUrl;

public interface UrlShortenerService {
    OriginalUrlCheckNeedPasswordResponse checkNeedPassword(OriginalUrlCheckNeedPasswordRequest request);
    boolean checkExistShortCode(String shortCode);
    ShortCodeCheckExistResponse checkExistShortCode(ShortCodeCheckExistRequest request);
    ResponseGenerateShortCode generateShortCode(GenerateShortCodeRequest request);
    ResponseGenerateShortCode generateShortCode(String email, GenerateShortCodeAccountRequest request);
    GetOriginalUrlResponse getOriginalUrl(GetOriginalUrlRequest request);
    ShortUrl search(String shortCode);
    RemoveUrlResponse removeByShortCode(RemoveUrlRequest request);
    ShortCodeUpdateResponse updateByID(ShortCodeUpdateRequest request);
}
