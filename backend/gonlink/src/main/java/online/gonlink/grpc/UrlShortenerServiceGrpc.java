package online.gonlink.grpc;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GenerateShortCodeResponse;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.GetOriginalUrlResponse;
import online.gonlink.StandardResponse;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.AuthConstants;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.dto.Standard;
import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.service.UrlShortenerService;
import online.gonlink.service.base.QRCodeService;
import online.gonlink.util.HtmlSanitizer;
import online.gonlink.UrlShortenerServiceGrpc.UrlShortenerServiceImplBase;

@GrpcService
@AllArgsConstructor
public class UrlShortenerServiceGrpc extends UrlShortenerServiceImplBase {
    private final UrlShortenerService urlShortenerService;
    private final QRCodeService qrCodeService;
    private final HtmlSanitizer htmlSanitizer;
    private GlobalValue config;
    private StandardResponseGrpc standardResponseGrpc;

    @Override
    public void generateShortCode(GenerateShortCodeRequest request, StreamObserver<StandardResponse> responseObserver) {
        String originalUrl = htmlSanitizer.sanitizeStrict(request.getOriginalUrl());
        ResponseGenerateShortCode responseGenerateShortCode = urlShortenerService.generateShortCode(
                originalUrl,
                request.getTrafficDate(),
                request.getZoneId()
        );
        String base64Image = qrCodeService.getStringBase64Image(config.getFRONTEND_DOMAIN() + responseGenerateShortCode.shortCode());

        Standard standard = Standard.SHORTEN_GENERATE_SUCCESS;
        standard.setData(
                GenerateShortCodeResponse
                        .newBuilder()
                        .setShortCode(responseGenerateShortCode.shortCode())
                        .setBase64Image(base64Image)
                        .setIsOwner(responseGenerateShortCode.isOwner())
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

    @Override
    public void generateShortCodeAccount(GenerateShortCodeAccountRequest request, StreamObserver<StandardResponse> responseObserver) {
        Context context = Context.current();
        String originalUrl = htmlSanitizer.sanitizeStrict(request.getOriginalUrl());
        ResponseGenerateShortCode responseGenerateShortCode = urlShortenerService.generateShortCode(
                AuthConstants.USER_EMAIL.get(context),
                originalUrl,
                request.getTrafficDate(),
                request.getZoneId()
        );
        String base64Image = qrCodeService.getStringBase64Image(config.getFRONTEND_DOMAIN() + responseGenerateShortCode.shortCode());

        Standard standard = Standard.SHORTEN_GENERATE_SUCCESS;
        standard.setData(
                GenerateShortCodeResponse
                        .newBuilder()
                        .setShortCode(responseGenerateShortCode.shortCode())
                        .setIsOwner(responseGenerateShortCode.isOwner())
                        .setBase64Image(base64Image)
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

    @Override
    public void getOriginalUrl(GetOriginalUrlRequest request, StreamObserver<StandardResponse> responseObserver) {
        String originalUrl = urlShortenerService.getOriginalUrl(request.getShortCode(), request.getClientTime(), request.getZoneId());

        Standard standard = Standard.SHORTEN_GET_ORIGINAL_URL_SUCCESS;
        standard.setData(
                GetOriginalUrlResponse
                        .newBuilder()
                        .setOriginalUrl(originalUrl)
                        .build()
        );

        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }
}
