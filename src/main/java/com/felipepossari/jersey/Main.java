package com.felipepossari.jersey;

import com.felipepossari.jersey.configuration.AutoScanFeatureConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static final String BASE_URI = "http://localhost:8080/";
    public static final String BASE_PACKAGE = "com.felipepossari.jersey";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig();
        rc.packages(BASE_PACKAGE);
        rc.register(AutoScanFeatureConfig.class);
        rc.register(LoggingFeature.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args){
        final HttpServer server = startServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        log.info("Jersey app started with endpoints available at " + BASE_URI);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            log.severe("Thread join error. Exiting application");
            Thread.currentThread().interrupt();
        }
    }
}

