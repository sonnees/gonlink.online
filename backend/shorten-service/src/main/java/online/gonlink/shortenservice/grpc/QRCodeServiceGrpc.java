package online.gonlink.shortenservice.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.GetStringBase64ImageRequest;
import online.gonlink.GetStringBase64ImageResponse;
import online.gonlink.QRCodeServiceGrpc.*;
import online.gonlink.shortenservice.exception.GrpcStatusException;
import online.gonlink.shortenservice.service.base.QRCodeService;
import online.gonlink.shortenservice.util.FormatLogMessage;

@GrpcService
@AllArgsConstructor
@Slf4j
public class QRCodeServiceGrpc extends QRCodeServiceImplBase {
    private final QRCodeService qrCodeService;

    @Override
    public void getStringBase64Image(GetStringBase64ImageRequest request, StreamObserver<GetStringBase64ImageResponse> responseObserver) {
        try {
            String base64Image = qrCodeService.getStringBase64Image(request.getContent(), request.getWidth(), request.getHeight());

            GetStringBase64ImageResponse response = GetStringBase64ImageResponse
                    .newBuilder()
                    .setBase64Image(base64Image)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (GrpcStatusException e){
            responseObserver.onError(e.getStatusException());
        }
        catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "getStringBase64Image",
                    "Unexpected error: {}",
                    e
            ));
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error")));
        }
    }
}
