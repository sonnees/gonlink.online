package online.gonlink.common;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import io.grpc.Status;
import online.gonlink.BaseGrpc;
import online.gonlink.constant.CommonConstant;

import java.time.LocalDateTime;

public interface GrpcSuccessHandler {
    default BaseGrpc handleSuccess(Message resObj, long start) {
        long took = System.currentTimeMillis() - start;
        return BaseGrpc.newBuilder()
                .setStatus(Status.OK.hashCode())
                .setMessage(Status.OK.getCode().name())
                .setTitle(CommonConstant.GRPC_SUCCESS)
                .setTime(LocalDateTime.now().toString())
                .setTook(took)
                .setData(Any.pack(resObj))
                .build();
    }
}
