package online.gonlink.grpc.impl.base;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.BaseGrpc;
import online.gonlink.GetStringBase64ImageRequest;
import online.gonlink.QRCodeGrpc.QRCodeImplBase;
import online.gonlink.common.CommonHandler;
import online.gonlink.service.QRCodeService;

@GrpcService
@AllArgsConstructor
public class QRCodeGrpcImpl extends QRCodeImplBase implements CommonHandler {

    /** service */
    private final QRCodeService qrCodeService;

    @Override
    public void getStringBase64Image(GetStringBase64ImageRequest request,
                                     StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(qrCodeService.getStringBase64Image(request), startTime));
        responseObserver.onCompleted();
    }
}
