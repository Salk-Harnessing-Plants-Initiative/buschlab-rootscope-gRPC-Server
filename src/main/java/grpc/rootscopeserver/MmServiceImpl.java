package grpc.rootscopeserver;

import at.ac.oeaw.gmi.busch.RootScopeService.*;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

class MmServiceImpl extends MmServiceGrpc.MmServiceImplBase {

    private DataProvider dataProvider;

    public MmServiceImpl() {
    }

    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public void getRoi(RoiRequest req, StreamObserver<RoiReply> responseObserver) {
        RoiReply.Builder builder = RoiReply.newBuilder();
        builder.setRoiX(42).setRoiY(21);
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCameraProperties(CameraPropertiesRequest req,
                                    StreamObserver<CameraPropertiesReply> responseObserver) {
        CameraPropertiesReply.Builder builder = CameraPropertiesReply.newBuilder();
        builder.setReply(dataProvider.getCameraProperties());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getImgData(ImgDataRequest req, StreamObserver<ImgDataReply> responseObserver) {

        ImgDataReply.Builder builder = ImgDataReply.newBuilder();
        builder.setImgData(ByteString.copyFrom(dataProvider.getImgData1()));
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
