package online.gonlink.grpc.impl.base;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.BaseGrpc;
import online.gonlink.GetStringBase64ImageRequest;
import online.gonlink.GetStringBase64ImageResponse;
import online.gonlink.QRCodeServiceGrpc.QRCodeServiceImplBase;
import online.gonlink.common.CommonHandler;
import online.gonlink.service.base.QRCodeService;

@GrpcService
@AllArgsConstructor
public class QRCodeGrpcImpl extends QRCodeServiceImplBase implements CommonHandler {
    private final QRCodeService qrCodeService;

    @Override
    public void getStringBase64Image(GetStringBase64ImageRequest request,
                                     StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        String base64Image = qrCodeService.getStringBase64Image(request.getContent(), request.getWidth(), request.getHeight());
        GetStringBase64ImageResponse response = GetStringBase64ImageResponse
                .newBuilder()
                .setBase64Image(base64Image)
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }
}
