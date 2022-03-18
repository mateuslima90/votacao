package io.mkth.votacao.infra.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.mkth.votacao.domain.Voto;
import io.mkth.votacao.infra.repository.VotoRepositoryInterface;
import io.mkth.votacao.usecases.ProcessVotation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HandlerHTTP implements HttpHandler {

    private ProcessVotation processVotation;
    private VotoRepositoryInterface repository;
    private ObjectMapper mapper;

    public HandlerHTTP(ProcessVotation processVotation, VotoRepositoryInterface repository, ObjectMapper mapper) {
        this.processVotation = processVotation;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        if(he.getRequestMethod().equals("GET")) {
            handleGetRequest(he);
        }
        else if(he.getRequestMethod().equals("POST")) {
            handlePostRequest(he);
        }
        else {
            System.out.println("I don't understande your request");
        }
    }

    private void handleGetRequest(HttpExchange httpExchange) throws IOException {

        var resultado = repository.totalVotos();

        var mapResult = mapper.writeValueAsString(resultado);
        handleGetResponse(httpExchange, mapResult);
    }

    private void handlePostRequest(HttpExchange httpExchange) throws IOException {
        Voto voto = ToVoto(httpExchange.getRequestBody());

        if(voto == null) throw new RuntimeException("Failed to process");

        //Save in the database
        repository.createVoto(voto);

        //Return to response http
        handlePostResponse(httpExchange, FromVoto(voto));
    }

    private void handleGetResponse(HttpExchange httpExchange, String jsonObject) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.getResponseHeaders().put("Content-Type", new ArrayList<String>(Collections.singleton("application/json")));
        httpExchange.sendResponseHeaders(200, jsonObject.length());
        outputStream.write(jsonObject.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private void handlePostResponse(HttpExchange httpExchange, String jsonObject)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.getResponseHeaders().put("Content-Type", new ArrayList<String>(Collections.singleton("application/json")));
        httpExchange.sendResponseHeaders(200, jsonObject.length());
        outputStream.write(jsonObject.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private Voto ToVoto(InputStream requestBody) {
        try {
            return mapper.readValue(requestBody, Voto.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String FromVoto(Voto voto) {
        try {
            return mapper.writeValueAsString(voto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
