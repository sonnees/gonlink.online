package online.gonlink.dto;

import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.Setter;
import online.gonlink.GenerateShortCodeResponse;
import online.gonlink.GetInfoAccountResponse;
import online.gonlink.GetOriginalUrlResponse;
import online.gonlink.GetStringBase64ImageResponse;
import online.gonlink.RemoveUrlResponse;

@AllArgsConstructor
public enum Standard {
    ACCOUNT_NOT_FOUND(Status.NOT_FOUND, "KHÔNG TÌM THẤY", null),
    ACCOUNT_ME_SUCCESS(Status.OK, "THÀNH CÔNG", GetInfoAccountResponse.newBuilder()),
    ACCOUNT_REMOVE_URL_SUCCESS(Status.OK, "THÀNH CÔNG", RemoveUrlResponse.newBuilder()),
    ACCOUNT_REMOVE_URL_FAIL(Status.CANCELLED, "THẤT BẠI", null),
    ACCOUNT_APPEND_URL(Status.CANCELLED, "THẤT BẠI", null),

    SHORTEN_GENERATE_SUCCESS(Status.OK, "THÀNH CÔNG", GenerateShortCodeResponse.newBuilder()),
    SHORTEN_GENERATE_FAIL(Status.CANCELLED, "THẤT BẠI", null),
    SHORTEN_GET_ORIGINAL_URL_SUCCESS(Status.OK, "THÀNH CÔNG", GetOriginalUrlResponse.newBuilder()),
    SHORTEN_GET_ORIGINAL_URL_FAIL(Status.CANCELLED, "THẤT BẠI", null),

    QR_CODE_SUCCESS(Status.OK, "THÀNH CÔNG", GetStringBase64ImageResponse.newBuilder()),
    QR_CODE_FAIL(Status.CANCELLED, "THẤT BẠI", null),

    UNAUTHENTICATED(Status.UNAUTHENTICATED, "KHÔNG ĐƯỢC PHÉP", null),
    UNAUTHORIZED(Status.PERMISSION_DENIED, "KHÔNG CÓ QUYỀN", null),



    ;
    Status status;
    String message;
    Object data;

    public void setData(Object data) {
        this.data = data;
    }

}
