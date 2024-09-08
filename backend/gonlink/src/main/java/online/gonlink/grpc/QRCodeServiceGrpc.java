package online.gonlink.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.GetStringBase64ImageRequest;
import online.gonlink.GetStringBase64ImageResponse;
import online.gonlink.QRCodeServiceGrpc.QRCodeServiceImplBase;
import online.gonlink.StandardResponse;
import online.gonlink.dto.Standard;
import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.service.base.QRCodeService;

@GrpcService
@AllArgsConstructor
public class QRCodeServiceGrpc extends QRCodeServiceImplBase {
    private final QRCodeService qrCodeService;
    private final StandardResponseGrpc standardResponseGrpc;

    @Override
    public void getStringBase64Image(GetStringBase64ImageRequest request, StreamObserver<StandardResponse> responseObserver) {
        String base64Image = qrCodeService.getStringBase64Image(request.getContent(), request.getWidth(), request.getHeight());

        Standard standard = Standard.QR_CODE_SUCCESS;

        standard.setData(
                GetStringBase64ImageResponse
                        .newBuilder()
                        .setBase64Image(base64Image)
                        .build()
        );

        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }
}
