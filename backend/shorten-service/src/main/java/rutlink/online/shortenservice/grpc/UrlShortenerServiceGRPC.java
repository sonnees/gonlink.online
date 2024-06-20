package rutlink.online.shortenservice.grpc;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import rutlink.online.*;
import rutlink.online.shortenservice.dto.LogObj;
import rutlink.online.shortenservice.service.JsonConverter;
import rutlink.online.shortenservice.service.UrlShortenerService;

@GrpcService
@Slf4j
public class UrlShortenerServiceGRPC extends UrlShortenerServiceGrpc.UrlShortenerServiceImplBase {

    private final UrlShortenerService urlShortenerService;
    private final JsonConverter jsonConverter;

    public UrlShortenerServiceGRPC(UrlShortenerService urlShortenerService, JsonConverter jsonConverter) {
        this.urlShortenerService = urlShortenerService;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public void generateShortCode(GenerateShortCodeRequest request, StreamObserver<GenerateShortCodeResponse> responseObserver) {
        try {
            String shortCode = urlShortenerService.generateShortCode(request.getOriginalUrl());

            GenerateShortCodeResponse response = GenerateShortCodeResponse
                    .newBuilder()
                    .setShortCode(shortCode)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e){
            LogObj<? extends UrlShortenerServiceGRPC, Exception> logObj = new LogObj<>(
                    this.getClass(),
                    "generateShortCode",
                    e
            );
            log.error("@ {}", jsonConverter.objToString(logObj));
            responseObserver.onError(e);
        }
    }

    @Override
    public void getOriginalUrl(GetOriginalUrlRequest request, StreamObserver<GetOriginalUrlResponse> responseObserver) {
        try {
            String originalUrl = urlShortenerService.getOriginalUrl(request.getShortCode());

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
            LogObj<? extends UrlShortenerServiceGRPC, Exception> logObj = new LogObj<>(
                    this.getClass(),
                    "getOriginalUrl",
                    e
            );
            log.error("@ {}", jsonConverter.objToString(logObj));
            responseObserver.onError(e);
        }
    }
}
