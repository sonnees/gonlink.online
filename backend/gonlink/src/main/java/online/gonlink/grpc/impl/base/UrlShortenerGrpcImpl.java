package online.gonlink.grpc.impl.base;

import com.google.protobuf.Message;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.BaseGrpc;
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
import online.gonlink.OriginalUrlCheckNeedPasswordRequest;
import online.gonlink.PageInfo;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.RealTimeTrafficResponse;
import online.gonlink.ShortCodeCheckExistRequest;
import online.gonlink.common.CommonHandler;
import online.gonlink.config.GlobalValue;
import online.gonlink.constant.AuthConstant;
import online.gonlink.dto.ResponseGenerateShortCode;
import online.gonlink.dto.TrafficDataDto;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.service.TrafficService;
import online.gonlink.service.UrlShortenerService;
import online.gonlink.service.base.QRCodeService;
import online.gonlink.util.HtmlSanitizer;
import online.gonlink.UrlShortenerServiceGrpc.UrlShortenerServiceImplBase;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class UrlShortenerGrpcImpl extends UrlShortenerServiceImplBase implements CommonHandler {
    private final GlobalValue config;
    private final HtmlSanitizer htmlSanitizer;

    /** service */
    private final UrlShortenerService urlShortenerService;
    private final QRCodeService qrCodeService;
    private final TrafficService trafficService;

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
        htmlSanitizer.sanitizeStrict(request.getOriginalUrl());
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
        htmlSanitizer.sanitizeStrict(request.getOriginalUrl());
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
        long startTime = System.currentTimeMillis();
        String originalUrl = urlShortenerService.getOriginalUrl(request);
        GetOriginalUrlResponse response = GetOriginalUrlResponse
                .newBuilder()
                .setOriginalUrl(originalUrl)
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void searchGeneralTraffics(GeneralTrafficsSearchRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        Page<GeneralTraffic> traffics = trafficService.searchGeneralTraffics(request);
        GeneralTrafficsSearchResponse response = GeneralTrafficsSearchResponse
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
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllMonthTraffics(MonthTrafficsGetAllRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        List<TrafficDataDto> traffics = trafficService.getAllMonthTraffic(request);
        MonthTrafficGetAllResponse response = MonthTrafficGetAllResponse
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
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void getDayTrafficInRange(DayTrafficInRangeRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        List<TrafficDataDto> traffics = trafficService.getDayTrafficInRange(request);
        MonthTrafficGetAllResponse response = MonthTrafficGetAllResponse
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
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public void getRealTimeTraffic(RealTimeTrafficRequest request, StreamObserver<BaseGrpc> responseObserver) {
        long startTime = System.currentTimeMillis();
        short[] realTimeTraffic = trafficService.getRealTimeTraffic(request);
        List<Integer> realTimeTrafficAsIntegers = new ArrayList<>();

        for (short value : realTimeTraffic) {
            realTimeTrafficAsIntegers.add((int) value);
        }
        RealTimeTrafficResponse response = RealTimeTrafficResponse
                .newBuilder()
                .addAllData(realTimeTrafficAsIntegers)
                .build();
        responseObserver.onNext(this.handleSuccess(response, startTime));
        responseObserver.onCompleted();
    }

    @Override
    public BaseGrpc handleSuccess(Message resObj, long start) {
        return CommonHandler.super.handleSuccess(resObj, start);
    }
}
