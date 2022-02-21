package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

// ToDo | handle the game, printing the different results that the game could make
// ToDo | - Game over
// ToDo | - Winning player
// ToDo | - Wrong move
public class TicTacToeHandler implements HttpHandler {
    // http://localhost:8001/ticTacToe?posI={qualcosa}&posJ={qualcosaltro}
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var parameters = QueryParameter.parse(exchange.getRequestURI().toString());

        TicTacToeService.getGame().makeMove(
                Integer.parseInt(parameters[0].value), // <- i
                Integer.parseInt(parameters[1].value)  // <- j
        );

        var response = TicTacToeService.getGame().toString();

        System.out.println("Body");
        System.out.println(response);

        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
