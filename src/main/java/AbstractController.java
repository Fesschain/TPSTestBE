package main.java;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class AbstractController {
    public static void sendResponse(HttpExchange exchange, int status, String message) throws IOException {
        exchange.getResponseHeaders().add("Content-type", "application/json");

        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "token, Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        byte[] response = message.getBytes();
        exchange.sendResponseHeaders(status, response.length);
        exchange.getResponseBody().write(response);

        exchange.getResponseBody().close();
    }

    public static void sendError(Throwable e, HttpExchange httpExchange) throws IOException {
        String errorString = e.getClass()+" ["+e.getMessage()+"]";
        httpExchange.sendResponseHeaders(500, errorString.length());
        httpExchange.getResponseBody().write(errorString.getBytes());
        httpExchange.getResponseBody().close();
    }
}
