package grpc.rootscopeserver;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import java.io.IOException;
import java.util.logging.Logger;

public class MMServer {
    private static final Logger logger = Logger.getLogger(MMServer.class.getName());

    private Server server;
    private static MmServiceImpl service = new MmServiceImpl();

    public void SetServiceDataProvider(DataProvider dataProvider) {
        service.setDataProvider(dataProvider);
    }

    public void start(int port) throws IOException {

//        File serverCertFile = new File ("/Users/alexander.bindeus/certFiles/rootscopeserver.crt");
//        File serverKeyFile = new File ("/Users/alexander.bindeus/certFiles/server1.key");

//        SelfSignedCertificate ssc = new SelfSignedCertificate();

        server = NettyServerBuilder.forPort(port)
                //.useTransportSecurity(serverCertFile,serverKeyFile)
                //.sslContext(GrpcSslContexts.forServer(ssc.certificate(),ssc.privateKey()).build())
                .maxMessageSize(1048576000)
                .addService(service)
                .build()
                .start();

        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC rootscopeserver since JVM is shutting down");
            MMServer.this.stop();
            System.err.println("*** rootscopeserver shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
            System.out.println("rootscopeserver shut down");
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the rootscopeserver from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MMServer server = new MMServer();
        server.start(Integer.parseInt(args[0]));
        server.blockUntilShutdown();
    }
}
