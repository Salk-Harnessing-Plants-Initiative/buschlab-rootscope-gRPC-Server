package grpc.server;

public interface DataProvider {
    byte[] getImgData1();
    String getCameraProperties();
    String autoScaleContrast();
}
