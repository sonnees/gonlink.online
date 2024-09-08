package online.gonlink.grpc;

import io.grpc.stub.StreamObserver;
import online.gonlink.GetInfoAccountRequest;
import online.gonlink.GetInfoAccountResponse;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.RemoveUrlResponse;
import online.gonlink.StandardResponse;
import online.gonlink.dto.Standard;
import online.gonlink.dto.AuthConstants;
import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.dto.UserInfo;
import online.gonlink.entity.Account;
import online.gonlink.service.AccountService;
import online.gonlink.service.TrafficService;
import online.gonlink.AccountServiceGrpc.AccountServiceImplBase;
import io.grpc.Context;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@AllArgsConstructor
public class AccountServiceGrpc extends AccountServiceImplBase {

    private final AccountService accountService;
    private final TrafficService trafficService;
    private final StandardResponseGrpc standardResponseGrpc;

    @Override
    public void getInfoAccount(GetInfoAccountRequest request, StreamObserver<StandardResponse> responseObserver) {
        Context context = Context.current();

        UserInfo userInfo = new UserInfo(
                AuthConstants.USER_EMAIL.get(context),
                AuthConstants.USER_NAME.get(context),
                AuthConstants.USER_AVATAR.get(context),
                AuthConstants.USER_ROLE.get(context)
        );
        Account account = accountService.getInfoAccount(userInfo);

        Standard standard = Standard.ACCOUNT_ME_SUCCESS;
        standard.setData(
                GetInfoAccountResponse
                        .newBuilder()
                        .setEmail(account.getEmail())
                        .setName(account.getName())
                        .setAvatar(account.getAvatar())
                        .setRole(account.getRole())
                        .setCreate(account.getCreate())
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUrl(RemoveUrlRequest request, StreamObserver<StandardResponse> responseObserver) {
        trafficService.deleteTraffic(request.getShortCode());
        Standard standard = Standard.ACCOUNT_REMOVE_URL_SUCCESS;
        standard.setData(
                RemoveUrlResponse
                        .newBuilder()
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

}
