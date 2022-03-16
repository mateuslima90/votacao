package io.mkth.votacao.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.mkth.votacao.domain.Voto;
import io.mkth.votacao.usecases.ProcessVotation;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HandlerHTTP implements HttpHandler {

    private ProcessVotation processVotation;
    private ObjectMapper mapper;

    public HandlerHTTP(ProcessVotation processVotation, ObjectMapper mapper) {
        this.processVotation = processVotation;
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue=null;
        if("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
            handleResponse(httpExchange,requestParamValue);
        }else if("POST".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handlePostRequest(httpExchange);
            handlePostResponse(httpExchange, requestParamValue);
        }
    }

    private String handleGetRequest(HttpExchange httpExchange) {
       httpExchange.getRequestURI().getQuery();
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        //String body = httpExchange.getRequestBody().toString();
        Voto voto = null;
        try {
            //voto = mapper.readValue(httpExchange.getRequestBody().readAllBytes(), Voto.class);
            voto = mapper.readValue(httpExchange.getRequestBody(), Voto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(voto);

        try {
            return mapper.writeValueAsString(voto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Hello ")
                .append(requestParamValue)
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        // encode HTML content
        String htmlResponse = htmlBuilder.toString();
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
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
}
