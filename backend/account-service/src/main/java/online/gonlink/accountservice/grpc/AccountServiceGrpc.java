package online.gonlink.accountservice.grpc;

import io.grpc.stub.StreamObserver;
import online.gonlink.*;
import online.gonlink.accountservice.dto.AuthConstants;
import online.gonlink.accountservice.dto.UserInfo;
import online.gonlink.accountservice.entity.Account;
import online.gonlink.accountservice.exception.GrpcStatusException;
import online.gonlink.accountservice.service.AccountService;
import online.gonlink.accountservice.service.TrafficService;
import online.gonlink.accountservice.util.FormatLogMessage;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.AccountServiceGrpc.AccountServiceImplBase;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
@Slf4j
public class AccountServiceGrpc extends AccountServiceImplBase {

    private final AccountService accountService;
    private final TrafficService trafficService;

    @Override
    public void getInfoAccount(GetInfoAccountRequest request, StreamObserver<GetInfoAccountResponse> responseObserver) {
        Context context = Context.current();

        try {
            UserInfo userInfo = new UserInfo(
                    AuthConstants.USER_EMAIL.get(context),
                    AuthConstants.USER_NAME.get(context),
                    AuthConstants.USER_AVATAR.get(context),
                    AuthConstants.USER_ROLE.get(context)
            );
            Account account = accountService.getInfoAccount(userInfo);

            GetInfoAccountResponse response = GetInfoAccountResponse
                    .newBuilder()
                    .setEmail(account.getEmail())
                    .setName(account.getName())
                    .setAvatar(account.getAvatar())
                    .setRole(account.getRole())
                    .setCreate(account.getCreate())
                    .addAllUrls(
                            account.getUrls()
                                    .stream()
                                    .map(shortUrl ->
                                        ShortUrl.newBuilder()
                                                .setShortCode(shortUrl.getShortCode())
                                                .setOriginalUrl(shortUrl.getOriginalUrl())
                                                .build()
                                    ).collect(Collectors.toList())
                            )
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
                    "getInfoAccount",
                    "Unexpected error: {}",
                    e
            ));
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error")));
        }
    }

    @Override
    @Transactional
    public void removeUrl(RemoveUrlRequest request, StreamObserver<RemoveUrlResponse> responseObserver) {
        Context context = Context.current();
        try {
            removeUrl_AccountService(AuthConstants.USER_EMAIL.get(context), request.getShortCode());
            RemoveUrlResponse response = RemoveUrlResponse.newBuilder().build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (GrpcStatusException e){
            responseObserver.onError(e.getStatusException());
        }
        catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "getInfoAccount",
                    "Unexpected error: {}",
                    e
            ));
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error")));
        }
    }

    @Transactional
    public void removeUrl_AccountService(String email, String shortCode){
        accountService.removeUrl(email, shortCode);
        removeUrl_TrafficService(shortCode);
    }

    @Transactional
    public void removeUrl_TrafficService(String shortCode){
        trafficService.deleteTraffic(shortCode);
    }
}
