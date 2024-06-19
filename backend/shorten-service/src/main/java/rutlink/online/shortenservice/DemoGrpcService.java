package rutlink.online.shortenservice;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import rutlink.online.DemoRequest;
import rutlink.online.DemoReturn;
import rutlink.online.DemoServiceGrpc;

@GrpcService
public class DemoGrpcService extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void sendMess(DemoRequest request, StreamObserver<DemoReturn> responseObserver) {
        DemoReturn.Builder builder = DemoReturn.newBuilder()
                .setMes(request.getMes());
        responseObserver.onNext(
                builder.build()
        );
        responseObserver.onCompleted();
    }
}
