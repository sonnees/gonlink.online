package online.gonlink.dto;

import com.google.protobuf.Message;
import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.gonlink.MessageEmpty;

@Getter
@AllArgsConstructor
public enum Standard {
    ACCOUNT_NOT_FOUND(Status.NOT_FOUND, "KHÔNG TÌM THẤY", MessageEmpty.newBuilder().build()),
    ACCOUNT_ME_SUCCESS(Status.OK, "THÀNH CÔNG", MessageEmpty.newBuilder().build()),
    ACCOUNT_REMOVE_URL_SUCCESS(Status.OK, "THÀNH CÔNG", MessageEmpty.newBuilder().build()),
    ACCOUNT_REMOVE_URL_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),
    ACCOUNT_APPEND_URL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),

    SHORTEN_GENERATE_SUCCESS(Status.OK, "THÀNH CÔNG", MessageEmpty.newBuilder().build()),
    SHORTEN_GENERATE_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),
    SHORTEN_GENERATE_ALREADY_EXISTS(Status.ALREADY_EXISTS, "ĐÃ TỒN TẠI", MessageEmpty.newBuilder().build()),
    SHORTEN_GET_ORIGINAL_URL_SUCCESS(Status.OK, "THÀNH CÔNG", MessageEmpty.newBuilder().build()),
    SHORTEN_GET_ORIGINAL_URL_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),
    REALTIME_TRAFFIC_INCREASE_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),
    DAY_TRAFFIC_INCREASE_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),
    MONTH_TRAFFIC_INCREASE_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),
    GENERAL_TRAFFIC_INCREASE_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),

    QR_CODE_SUCCESS(Status.OK, "THÀNH CÔNG", MessageEmpty.newBuilder().build()),
    QR_CODE_FAIL(Status.CANCELLED, "THẤT BẠI", MessageEmpty.newBuilder().build()),

    SUCCESS(Status.OK, "THÀNH CÔNG", MessageEmpty.newBuilder().build()),
    UNAUTHENTICATED(Status.UNAUTHENTICATED, "KHÔNG ĐƯỢC PHÉP", MessageEmpty.newBuilder().build()),
    UNAUTHORIZED(Status.PERMISSION_DENIED, "KHÔNG CÓ QUYỀN", MessageEmpty.newBuilder().build()),
    FORBIDDEN_URL(Status.PERMISSION_DENIED, "KHÔNG ĐƯỢC PHÉP RÚT GỌN", MessageEmpty.newBuilder().build()),
    NOT_FOUND_URL(Status.NOT_FOUND, "URL KHÔNG THỂ XÁC ĐỊNH", MessageEmpty.newBuilder().build()),
    INTERNAL(Status.INTERNAL, "SERVER ĐANG GẶP LỖI", MessageEmpty.newBuilder().build()),


    ;
    Status status;
    String message;
    Message data;

    public void setData(Message data) {

        this.data = data;
    }

}
