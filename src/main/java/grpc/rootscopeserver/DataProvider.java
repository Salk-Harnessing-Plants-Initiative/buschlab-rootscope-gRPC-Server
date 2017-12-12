package grpc.rootscopeserver;

public interface DataProvider {
    byte[] getImgData1();
    String getCameraProperties();
    boolean autoScaleContrast();
}
