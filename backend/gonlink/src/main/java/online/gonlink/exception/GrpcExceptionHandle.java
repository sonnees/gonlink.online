package online.gonlink.exception;

import io.grpc.Status;
import online.gonlink.BaseGrpc;
import online.gonlink.ErrorDetailGrpc;
import online.gonlink.constant.CommonConstant;

import java.time.LocalDateTime;
import java.util.List;

public interface GrpcExceptionHandle {
    default BaseGrpc handleException(List<ErrorDetailGrpc> resObj, long start) {
        long took = System.currentTimeMillis() - start;
        return BaseGrpc.newBuilder()
                .setStatus(Status.OK.hashCode())
                .setMessage(Status.OK.getCode().name())
                .setTitle(CommonConstant.GRPC_SUCCESS)
                .setTime(LocalDateTime.now().toString())
                .setTook(took)
                .addAllErrors(resObj)
                .build();
    }
}
