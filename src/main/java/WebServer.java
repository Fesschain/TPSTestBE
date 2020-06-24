package main.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import main.java.netty.NettyClient;
import main.java.netty.RequestHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static main.resources.Resources.HOST;


public class WebServer {

    public static HttpServer nodeServer = null;

    public static void startServer() {
        try {

            nodeServer = HttpServer.create(new InetSocketAddress(9000), 80);
            initNodeWebContext(nodeServer);

            nodeServer.start();
        } catch (Exception e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static void initNodeWebContext(HttpServer httpServer){
        httpServer.createContext("/", PerformTestingService::sendTransactions);
        httpServer.createContext("/lastTransactions", WebServer::lastTransactions);
    }

    public static void lastTransactions(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();
        try {
            AbstractController.sendResponse(exchange, 200, RequestHelper.GET_Request("http://"+HOST+":31883/lastTransactions"
                    +(query==null?"":"?startTime="+queryToMap(query).get("startTime"))));
        } catch (Exception e) {
            AbstractController.sendError(e, exchange);
        }
    }

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
