package online.gonlink.grpc.impl.base;

import io.grpc.stub.StreamObserver;
import online.gonlink.AccountGrpc.AccountImplBase;
import online.gonlink.BaseGrpc;
import online.gonlink.GetInfoAccountRequest;
import online.gonlink.common.CommonHandler;
import online.gonlink.service.AccountService;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class AccountGrpcImpl extends AccountImplBase implements CommonHandler {

    /** service */
    private final AccountService accountService;

    @Override
    public void getInfoAccount(GetInfoAccountRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(accountService.getInfoAccount(request), startTime));
        responseObserver.onCompleted();
    }

}
