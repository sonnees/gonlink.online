package online.gonlink.shortenservice.grpc;

import online.gonlink.*;
import online.gonlink.shortenservice.dto.AuthConstants;
import online.gonlink.shortenservice.exception.GrpcStatusException;
import online.gonlink.shortenservice.util.FormatLogMessage;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.shortenservice.service.impl.UrlShortenerServiceImpl;
import online.gonlink.UrlShortenerServiceGrpc.*;

@GrpcService
@Slf4j
public class UrlShortenerServiceGrpc extends UrlShortenerServiceImplBase {

    private final UrlShortenerServiceImpl urlShortenerServiceImpl;

    public UrlShortenerServiceGrpc(UrlShortenerServiceImpl urlShortenerServiceImpl) {
        this.urlShortenerServiceImpl = urlShortenerServiceImpl;
    }

    @Override
    public void generateShortCode(GenerateShortCodeRequest request, StreamObserver<GenerateShortCodeResponse> responseObserver) {
        try {
            String shortCode = urlShortenerServiceImpl.generateShortCode(request.getOriginalUrl());

            GenerateShortCodeResponse response = GenerateShortCodeResponse
                    .newBuilder()
                    .setShortCode(shortCode)
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
            String shortCode = urlShortenerServiceImpl.generateShortCode(AuthConstants.USER_EMAIL.get(context), request.getOriginalUrl());
            GenerateShortCodeResponse response = GenerateShortCodeResponse
                    .newBuilder()
                    .setShortCode(shortCode)
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
            String originalUrl = urlShortenerServiceImpl.getOriginalUrl(request.getShortCode(), request.getClientTime(), request.getZoneId());
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
