package io.mkth.votacao.infra.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerHTTP {

    private static HttpServer myServer;

    private static HttpServer createServerHTTP() throws IOException {
        myServer = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        return myServer;
    }

    public static HttpServer getInstance() {

        if (myServer == null) {
            try {
                myServer = createServerHTTP();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myServer;
    }
}
