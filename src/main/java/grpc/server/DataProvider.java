package grpc.server;

public interface DataProvider {
    byte[] getImgData1();
    String getCameraProperties();
    boolean autoScaleContrast();
}
