package online.gonlink.grpc.impl.base;

import com.google.protobuf.Message;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import online.gonlink.BaseGrpc;
import online.gonlink.DayTrafficInRangeRequest;
import online.gonlink.DayTrafficInRangeResponse;
import online.gonlink.GeneralTrafficsSearchRequest;
import online.gonlink.GeneralTrafficsSearchResponse;
import online.gonlink.MonthTrafficGetAllResponse;
import online.gonlink.MonthTrafficsGetAllRequest;
import online.gonlink.PageInfo;
import online.gonlink.RealTimeTrafficRequest;
import online.gonlink.RealTimeTrafficResponse;
import online.gonlink.TrafficGrpc.TrafficImplBase;
import online.gonlink.common.CommonHandler;
import online.gonlink.dto.TrafficDataDto;
import online.gonlink.dto.TrafficDayDto;
import online.gonlink.entity.GeneralTraffic;
import online.gonlink.service.TrafficService;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class TrafficGrpcImpl extends TrafficImplBase implements CommonHandler {

    /** service */
    private final TrafficService trafficService;

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

        TrafficDayDto traffics = trafficService.getDayTrafficInRange(request);

        DayTrafficInRangeResponse response = DayTrafficInRangeResponse
                .newBuilder()
                .addAllTrafficData(
                        traffics.getTrafficDataDtoList().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getDate())
                                        .setData(o.getData())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllCities(
                        traffics.getCities().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllCountries(
                        traffics.getCountries().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllZoneIds(
                        traffics.getZoneIds().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllBrowsers(
                        traffics.getBrowsers().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllBrowserVersions(
                        traffics.getBrowserVersions().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllOperatingSystems(
                        traffics.getOperatingSystems().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
                                        .build()
                                )
                                .collect(Collectors.toList())
                ).addAllDeviceTypes(
                        traffics.getDeviceTypes().entrySet().stream()
                                .map(o -> online.gonlink.TrafficData
                                        .newBuilder()
                                        .setDate(o.getKey())
                                        .setData(o.getValue())
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
