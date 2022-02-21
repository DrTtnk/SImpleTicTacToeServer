package com.company;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

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
