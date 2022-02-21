package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class QueryParameter {
    String key;
    String value;

    QueryParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String toString() { return key + "=" + value; }

    public static QueryParameter[] parse(String URI) {
        var components = URI.split("\\?");
        if (components.length != 2) {
            return new QueryParameter[0];
        }
        return Arrays.stream(components[1].split("&"))
                .map(s -> s.split("="))
                .map(s -> new QueryParameter(s[0], s[1]))
                .toArray(QueryParameter[]::new);
    }

}

// ToDo | handle the game, printing the different results that the game could make
// ToDo | - Game over
// ToDo | - Winning player
// ToDo | - Wrong move
class TicTacToeHandler implements HttpHandler {
    // http://localhost:8001/ticTacToe?posI={qualcosa}&posJ={qualcosaltro}
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = exchange.getRequestURI().toString();

        var parameters = QueryParameter.parse(response);

        TicTacToeService.getGame().makeMove(
                Integer.parseInt(parameters[0].value), // <- i
                Integer.parseInt(parameters[1].value)  // <- j
        );

        var requestBody = TicTacToeService.getGame().toString();

        System.out.println("Body");
        System.out.println(requestBody);

        exchange.sendResponseHeaders(200, requestBody.length());
        exchange.getResponseBody().write(requestBody.getBytes());
        exchange.close();
    }
}

class HelloWorldHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Hello World!";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}

class EchoHandler implements HttpHandler {
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

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/", new HelloWorldHandler());
        server.createContext("/echo", new EchoHandler());
        server.createContext("/ticTacToe", new TicTacToeHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started");
    }
}
