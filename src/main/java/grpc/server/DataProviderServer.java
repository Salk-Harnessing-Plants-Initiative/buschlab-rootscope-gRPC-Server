package grpc.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DataProviderServer implements DataProvider{
    @Override
    public byte[] getImgData1() {
        File file = new File("/Users/alexander.bindeus/Desktop/bigpng.png");

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCameraProperties() {
        return "i don't know...";
    }

    @Override
    public String autoScaleContrast() {
        return "AutoScaleContrast";
    }
}
