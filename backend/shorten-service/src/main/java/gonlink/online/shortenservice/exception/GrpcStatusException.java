package gonlink.online.shortenservice.exception;

import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GrpcStatusException extends RuntimeException {
    private StatusRuntimeException statusException;
}