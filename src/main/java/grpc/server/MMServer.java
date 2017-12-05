package grpc.server;

import io.grpc.Server;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

public class MMServer {
    private static final Logger logger = Logger.getLogger(MMServer.class.getName());

    private Server server;
    private static MmServiceImpl service;

    public static void SetServiceDataProvider(DataProvider dataProvider) {
        service = new MmServiceImpl();
        service.setDataProvider(dataProvider);
    }

    private void start(int port) throws IOException {
        try {
            SelfSignedCertificate ssc = new SelfSignedCertificate();

            server = NettyServerBuilder.forPort(port)
                    .maxMessageSize(1048576000)
                    .addService(service)
                    .sslContext(GrpcSslContexts.forServer(ssc.certificate(),ssc.privateKey()).build())
                    .build()
                    .start();

        } catch (CertificateException e) {
            e.printStackTrace();
        }

        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            MMServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MMServer server = new MMServer();
        server.start(Integer.parseInt(args[0]));
        server.blockUntilShutdown();
    }
}
