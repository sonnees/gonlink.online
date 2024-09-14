package online.gonlink.grpc.impl.base;

import io.grpc.stub.StreamObserver;
import online.gonlink.BaseGrpc;
import online.gonlink.GetInfoAccountRequest;
import online.gonlink.GetInfoAccountResponse;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.RemoveUrlResponse;
import online.gonlink.common.CommonHandler;
import online.gonlink.constant.AuthConstant;
import online.gonlink.dto.UserInfoDto;
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
public class AccountGrpc extends AccountServiceImplBase implements CommonHandler {

    private final AccountService accountService;
    private final TrafficService trafficService;

    @Override
    public void getInfoAccount(GetInfoAccountRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        Account account = accountService.getInfoAccount(getUserInfo());
        GetInfoAccountResponse response = GetInfoAccountResponse
                .newBuilder()
                .setEmail(account.getEmail())
                .setName(account.getName())
                .setAvatar(account.getAvatar())
                .setRole(account.getRole())
                .setCreate(account.getCreate())
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUrl(RemoveUrlRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        trafficService.deleteTraffic(request.getShortCode());
        RemoveUrlResponse response = RemoveUrlResponse.newBuilder().build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    private static UserInfoDto getUserInfo() {
        Context context = Context.current();
        UserInfoDto userInfoDto = new UserInfoDto(
                AuthConstant.USER_EMAIL.get(context),
                AuthConstant.USER_NAME.get(context),
                AuthConstant.USER_AVATAR.get(context),
                AuthConstant.USER_ROLE.get(context)
        );
        return userInfoDto;
    }

}
