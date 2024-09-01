package online.gonlink.exception;

import com.mongodb.DuplicateKeyException;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Slf4j
@Component
public class GrpcExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
        return new ExceptionHandlingServerCallListener<>(listener, call, headers);
    }

    private class ExceptionHandlingServerCallListener<ReqT, RespT>
            extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {
        private ServerCall<ReqT, RespT> serverCall;
        private Metadata metadata;

        ExceptionHandlingServerCallListener(ServerCall.Listener<ReqT> listener,
                                            ServerCall<ReqT, RespT> serverCall,
                                            Metadata metadata) {
            super(listener);
            this.serverCall = serverCall;
            this.metadata = metadata;
        }

        @Override
        public void onHalfClose() {
            try {
                super.onHalfClose();
            } catch (RuntimeException ex) {
                handleException(ex, serverCall, metadata);
                throw ex;
            }
        }

        @Override
        public void onReady() {
            try {
                super.onReady();
            } catch (RuntimeException ex) {
                handleException(ex, serverCall, metadata);
                throw ex;
            }
        }

//        DuplicateKeyException
//        MalformedURLException
        private void handleException(RuntimeException ex, ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
            Status status = Status.INTERNAL.withDescription("An internal error occurred");
            if (ex instanceof IllegalArgumentException) {
                status = Status.INVALID_ARGUMENT.withDescription(ex.getMessage());
            } else if (ex instanceof ResourceNotFoundException) {
                status = Status.NOT_FOUND.withDescription(ex.getMessage());
            }

            serverCall.close(status, metadata);
        }
    }
}
