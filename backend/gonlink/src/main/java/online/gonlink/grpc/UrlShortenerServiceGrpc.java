package online.gonlink.grpc;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.GeneralTrafficsSearchResponse;
import online.gonlink.GenerateShortCodeAccountRequest;
import online.gonlink.GenerateShortCodeRequest;
import online.gonlink.GenerateShortCodeResponse;
import online.gonlink.GetOriginalUrlRequest;
import online.gonlink.GetOriginalUrlResponse;
import online.gonlink.MonthTrafficGetAllResponse;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.PageInfo;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.RealTimeTrafficResponse;
import online.gonlink.StandardResponse;
import online.gonlink.config.GlobalValue;
import online.gonlink.dto.AuthConstants;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.dto.Standard;
import online.gonlink.dto.StandardResponseGrpc;
import online.gonlink.dto.TrafficData;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.service.TrafficService;
import online.gonlink.service.UrlShortenerService;
import online.gonlink.service.base.QRCodeService;
import online.gonlink.util.HtmlSanitizer;
import online.gonlink.UrlShortenerServiceGrpc.UrlShortenerServiceImplBase;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class UrlShortenerServiceGrpc extends UrlShortenerServiceImplBase {
    private final UrlShortenerService urlShortenerService;
    private final QRCodeService qrCodeService;
    private final HtmlSanitizer htmlSanitizer;
    private GlobalValue config;
    private StandardResponseGrpc standardResponseGrpc;
    private TrafficService trafficService;

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

    @Override
    public void searchGeneralTraffics(GeneralTrafficsSearchRequest request, StreamObserver<StandardResponse> responseObserver) {

        Page<GeneralTraffic> traffics = trafficService.searchGeneralTraffics(request);
        Standard standard = Standard.SUCCESS;
        standard.setData(
                GeneralTrafficsSearchResponse
                        .newBuilder()
                        .addAllGeneralTraffic(
                                traffics.getContent()
                                        .stream()
                                        .map(o -> online.gonlink.GeneralTraffic
                                                .newBuilder()
                                                .setShortCode(o.getShortCode())
                                                .setOriginalUrl(o.getOriginalUrl())
                                                .setTrafficDate(o.getTrafficDate())
                                                .setTraffic(o.getTraffic())
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                        )
                        .setPageInfo(PageInfo.newBuilder()
                                .setCurrentPage(traffics.getNumber())
                                .setTotalPages(traffics.getTotalPages())
                                .setTotalElements(traffics.getTotalElements())
                                .setPageSize(traffics.getSize())
                                .build())
                        .build()
        );

        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllMonthTraffics(MonthTrafficsGetAllRequest request, StreamObserver<StandardResponse> responseObserver) {
        List<TrafficData> traffics = trafficService.getAllMonthTraffic(request);
        Standard standard = Standard.SUCCESS;
        standard.setData(
                MonthTrafficGetAllResponse
                        .newBuilder()
                        .addAllTrafficData(
                                traffics.stream()
                                        .map(o -> online.gonlink.TrafficData
                                                .newBuilder()
                                                .setDate(o.getDate())
                                                .setData(o.getData())
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                        )
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

    @Override
    public void getDayTrafficInRange(DayTrafficInRangeRequest request, StreamObserver<StandardResponse> responseObserver) {
        List<TrafficData> traffics = trafficService.getDayTrafficInRange(request);
        Standard standard = Standard.SUCCESS;
        standard.setData(
                MonthTrafficGetAllResponse
                        .newBuilder()
                        .addAllTrafficData(
                                traffics.stream()
                                        .map(o -> online.gonlink.TrafficData
                                                .newBuilder()
                                                .setDate(o.getDate())
                                                .setData(o.getData())
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                        )
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }

    @Override
    public void getRealTimeTraffic(RealTimeTrafficRequest request, StreamObserver<StandardResponse> responseObserver) {
        short[] realTimeTraffic = trafficService.getRealTimeTraffic(request);
        List<Integer> realTimeTrafficAsIntegers = new ArrayList<>();

        for (short value : realTimeTraffic) {
            realTimeTrafficAsIntegers.add((int) value);
        }
        Standard standard = Standard.SUCCESS;
        standard.setData(
                RealTimeTrafficResponse
                        .newBuilder()
                        .addAllData(realTimeTrafficAsIntegers)
                        .build()
        );
        responseObserver.onNext(standardResponseGrpc.standardResponse(standard));
        responseObserver.onCompleted();
    }
}
