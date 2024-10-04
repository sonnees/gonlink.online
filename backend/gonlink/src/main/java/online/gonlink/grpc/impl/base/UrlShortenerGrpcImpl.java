package online.gonlink.grpc.impl.base;

import com.google.protobuf.Message;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.BaseGrpc;
import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GenerateShortCodeResponse;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.OriginalUrlCheckNeedPasswordRequest;
import online.gonlink.RemoveUrlRequest;
import online.gonlink.ShortCodeCheckExistRequest;
import online.gonlink.ShortCodeUpdateRequest;
import online.gonlink.UrlShortenerGrpc.UrlShortenerImplBase;
import online.gonlink.common.CommonHandler;
import online.gonlink.config.GlobalValue;
import online.gonlink.constant.AuthConstant;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.service.UrlShortenerService;
import online.gonlink.service.QRCodeService;
import online.gonlink.service.base.impl.HtmlSanitizerServiceImpl;
import online.gonlink.service.base.impl.IPInfoServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@AllArgsConstructor
public class UrlShortenerGrpcImpl extends UrlShortenerImplBase implements CommonHandler {
    /** config */
    private final GlobalValue config;

    /** service */
    private final UrlShortenerService urlShortenerService;
    private final QRCodeService qrCodeService;
    private final HtmlSanitizerServiceImpl htmlSanitizerServiceImpl;
    private final IPInfoServiceImpl ipGeolocationService;

    @Override
    public void checkExistShortCode(ShortCodeCheckExistRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(urlShortenerService.checkExistShortCode(request), startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void checkNeedPasswordGetOriginalUrl(OriginalUrlCheckNeedPasswordRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(urlShortenerService.checkNeedPassword(request), startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void generateShortCode(GenerateShortCodeRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        htmlSanitizerServiceImpl.sanitize(request.getOriginalUrl());
        ResponseGenerateShortCode responseGenerateShortCode = urlShortenerService.generateShortCode(request);

        String base64Image = qrCodeService.getStringBase64Image(config.getFRONTEND_DOMAIN() + responseGenerateShortCode.shortCode());
        GenerateShortCodeResponse response = GenerateShortCodeResponse
                .newBuilder()
                .setShortCode(responseGenerateShortCode.shortCode())
                .setIsOwner(responseGenerateShortCode.isOwner())
                .setBase64Image(base64Image)
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void generateShortCodeAccount(GenerateShortCodeAccountRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        Context context = Context.current();
        String email = AuthConstant.USER_EMAIL.get(context);
        htmlSanitizerServiceImpl.sanitize(request.getOriginalUrl());
        ResponseGenerateShortCode responseGenerateShortCode = urlShortenerService.generateShortCode(email, request);

        String base64Image = qrCodeService.getStringBase64Image(config.getFRONTEND_DOMAIN() + responseGenerateShortCode.shortCode());
        GenerateShortCodeResponse response = GenerateShortCodeResponse
                .newBuilder()
                .setShortCode(responseGenerateShortCode.shortCode())
                .setIsOwner(responseGenerateShortCode.isOwner())
                .setBase64Image(base64Image)
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void getOriginalUrl(GetOriginalUrlRequest request, StreamObserver<BaseGrpc> responseObserver) {
        Context context = Context.current();
        GetOriginalUrlRequest requestBuilder = GetOriginalUrlRequest.newBuilder()
                .setShortCode(request.getShortCode())
                .setZoneId((request.getZoneId()))
                .setPassword(request.getPassword())
                .setIp(AuthConstant.IP.get(context))
                .setHostname(AuthConstant.HOST_NAME.get(context))
                .setCity(AuthConstant.CITY.get(context))
                .setRegion(AuthConstant.REGION.get(context))
                .setCountry(AuthConstant.COUNTRY.get(context))
                .setLoc(AuthConstant.LOC.get(context))
                .setOrg(AuthConstant.ORG.get(context))
                .setPostal(AuthConstant.POSTAL.get(context))
                .setTimezone(AuthConstant.TIMEZONE.get(context))
                .setBrowser(request.getBrowser())
                .setBrowserVersion(request.getBrowserVersion())
                .setOperatingSystem(request.getOperatingSystem())
                .setDeviceType(request.getDeviceType())
                .build();

        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(urlShortenerService.getOriginalUrl(requestBuilder), startTime));
        responseObserver.onCompleted();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByID(RemoveUrlRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(urlShortenerService.removeByShortCode(request), startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void updateByID(ShortCodeUpdateRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        responseObserver.onNext(this.handleSuccess(urlShortenerService.updateByID(request), startTime));
        responseObserver.onCompleted();
    }

    @Override
    public BaseGrpc handleSuccess(Message resObj, long start) {
        return CommonHandler.super.handleSuccess(resObj, start);
    }
}
