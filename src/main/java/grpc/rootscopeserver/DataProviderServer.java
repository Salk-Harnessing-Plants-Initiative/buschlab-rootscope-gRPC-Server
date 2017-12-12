package grpc.rootscopeserver;

// dummy implementation !!!

public class DataProviderServer implements DataProvider{
    @Override
    public byte[] getImgData1() {
        return new byte[0];
    }

    @Override
    public String getCameraProperties() {
        return "dummy implementation...";
    }

    @Override
    public boolean autoScaleContrast() {
        return true;
    }
}
