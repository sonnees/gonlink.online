package online.gonlink.shortenservice.grpc;

import lombok.AllArgsConstructor;
import online.gonlink.*;
import online.gonlink.shortenservice.config.AccountServiceConfig;
import online.gonlink.shortenservice.dto.AuthConstants;
import online.gonlink.shortenservice.dto.ResponseGenerateShortCode;
import online.gonlink.shortenservice.exception.GrpcStatusException;
import online.gonlink.shortenservice.service.UrlShortenerService;
import online.gonlink.shortenservice.service.base.QRCodeService;
import online.gonlink.shortenservice.util.FormatLogMessage;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.UrlShortenerServiceGrpc.*;
import online.gonlink.shortenservice.util.HtmlSanitizer;

@GrpcService
@AllArgsConstructor
@Slf4j
public class UrlShortenerServiceGrpc extends UrlShortenerServiceImplBase {
    private final UrlShortenerService urlShortenerService;
    private final QRCodeService qrCodeService;
    private final HtmlSanitizer htmlSanitizer;
    private AccountServiceConfig config;

    @Override
    public void generateShortCode(GenerateShortCodeRequest request, StreamObserver<GenerateShortCodeResponse> responseObserver) {
        try {
            String originalUrl = htmlSanitizer.sanitizeStrict(request.getOriginalUrl());
            ResponseGenerateShortCode responseGenerateShortCode = urlShortenerService.generateShortCode(
                    originalUrl,
                    request.getTrafficDate(),
                    request.getZoneId()
            );
            String base64Image = qrCodeService.getStringBase64Image(config.getFRONTEND_DOMAIN() + responseGenerateShortCode.shortCode());

            GenerateShortCodeResponse response = GenerateShortCodeResponse
                    .newBuilder()
                    .setShortCode(responseGenerateShortCode.shortCode())
                    .setBase64Image(base64Image)
                    .setIsOwner(responseGenerateShortCode.isOwner())
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
                    "generateShortCode",
                    "Unexpected error: {}",
                    e
            ));
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error")));
        }
    }

    @Override
    public void generateShortCodeAccount(GenerateShortCodeAccountRequest request, StreamObserver<GenerateShortCodeResponse> responseObserver) {
        Context context = Context.current();
        try {
            String originalUrl = htmlSanitizer.sanitizeStrict(request.getOriginalUrl());
            ResponseGenerateShortCode responseGenerateShortCode = urlShortenerService.generateShortCode(
                    AuthConstants.USER_EMAIL.get(context),
                    originalUrl,
                    request.getTrafficDate(),
                    request.getZoneId()
            );
            String base64Image = qrCodeService.getStringBase64Image(config.getFRONTEND_DOMAIN() + responseGenerateShortCode.shortCode());

            GenerateShortCodeResponse response = GenerateShortCodeResponse
                    .newBuilder()
                    .setShortCode(responseGenerateShortCode.shortCode())
                    .setIsOwner(responseGenerateShortCode.isOwner())
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
                    "generateShortCodeAccount",
                    "Unexpected error: {}",
                    e
            ));
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error")));
        }
    }

    @Override
    public void getOriginalUrl(GetOriginalUrlRequest request, StreamObserver<GetOriginalUrlResponse> responseObserver) {
        try {
            String originalUrl = urlShortenerService.getOriginalUrl(request.getShortCode(), request.getClientTime(), request.getZoneId());
            GetOriginalUrlResponse response = GetOriginalUrlResponse
                    .newBuilder()
                    .setOriginalUrl(originalUrl)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (StatusRuntimeException e){
            responseObserver.onError(e);
        }
        catch (Exception e){
            log.error(FormatLogMessage.formatLogMessage(
                    this.getClass().getSimpleName(),
                    "getOriginalUrl",
                    "Unexpected error: {}",
                    e
            ));
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal Server Error")));
        }
    }
}
