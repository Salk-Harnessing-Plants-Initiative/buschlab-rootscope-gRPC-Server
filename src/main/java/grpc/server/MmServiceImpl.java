package grpc.server;

import at.ac.oeaw.gmi.busch.RootScopeService.*;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class MmServiceImpl extends MmServiceGrpc.MmServiceImplBase {

    int xRoi = 10;
    int yRoi = 20;

    DataProvider dataProvider;

    public MmServiceImpl() {
        System.out.println(" constructor !!!");
    }

    public void setDataProvider(DataProvider dataProvider) {
        System.out.println("provider set here !!!!!!!!!!! " + dataProvider.getClass().toString());
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
    public void getImgData(ImgDataRequest req, StreamObserver<ImgDataReply> responseObserver) {

        ImgDataReply.Builder builder = ImgDataReply.newBuilder();

        builder.setImgData(ByteString.copyFrom(dataProvider.getImgData1()));

//        File file = new File("/Users/alexander.bindeus/Desktop/bigpng.png");
//
//        try {
//            byte[] bytes = Files.readAllBytes(file.toPath());
//            builder.setImgData(ByteString.copyFrom(bytes));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
