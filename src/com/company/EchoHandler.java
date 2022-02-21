package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EchoHandler implements HttpHandler {
    // URL:         https://sito.web.it/login?user=mimmo&passwords=123456789abc
    // URI:         /login?user=mimmo&passwords=123456789abc
    // Protocol:    https
    // Host:        sito.web.it
    // Route:       /login
    // Query:       ?user=mimmo&passwords=123456789abc

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = exchange.getRequestURI().toString();

        // Read parameter
        var parameters = QueryParameter.parse(response);
        System.out.println("Parameters");
        for (var parameter : parameters) {
            System.out.println(parameter);
        }

        // Read body
        var body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        var requestBody = Integer.toString(body.readLine().length());

        System.out.println("Body");
        System.out.println(requestBody);

        exchange.sendResponseHeaders(200, requestBody.length());
        exchange.getResponseBody().write(requestBody.getBytes());
        exchange.close();
    }
}
