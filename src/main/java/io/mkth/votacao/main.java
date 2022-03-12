package io.mkth.votacao;

import com.sun.net.httpserver.HttpServer;
import io.mkth.votacao.infra.HandlerHTTP;
import io.mkth.votacao.infra.ServerHTTP;
import io.mkth.votacao.usecases.ProcessVotation;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        HttpServer server = ServerHTTP.getInstance();

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        ProcessVotation processVotation = new ProcessVotation();
        HandlerHTTP myHandler = new HandlerHTTP(processVotation);

        server.createContext("/test", myHandler);
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println(" Server started on port 8001");
    }

}