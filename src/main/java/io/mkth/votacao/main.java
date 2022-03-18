package io.mkth.votacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import io.mkth.votacao.infra.http.HandlerHTTP;
import io.mkth.votacao.infra.http.ServerHTTP;
import io.mkth.votacao.infra.repository.VotoFileRepository;
import io.mkth.votacao.usecases.ProcessVotation;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        HttpServer server = ServerHTTP.getInstance();

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        ProcessVotation processVotation = new ProcessVotation();
        ObjectMapper mapper = new ObjectMapper();
        VotoFileRepository repository = new VotoFileRepository();
        HandlerHTTP myHandler = new HandlerHTTP(processVotation, repository, mapper);

        server.createContext("/votos", myHandler);
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println(" Server started on port 8001");
    }

}