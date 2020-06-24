package main.java;

import com.sun.net.httpserver.HttpExchange;
import io.netty.channel.Channel;
import main.java.netty.model.RequestData;
import main.java.netty.model.TestResponse;
import main.java.netty.model.TransactionRequest;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static main.java.AbstractController.sendError;
import static main.java.AbstractController.sendResponse;
import static main.java.WebServer.queryToMap;
import static main.java.netty.NettyClient.getNodeChannel;
import static main.java.netty.NettyClient.reopenChannel;

public class PerformTestingService {

    public static RequestData msg = new RequestData(1, new TransactionRequest("0357530FFE3A741492D4C2A24DE55A7F97367E81", "0357530FFE3A741492D4C2A24DE55A7F97367E81", 1));

    public static void sendTransactions(HttpExchange exchange) throws IOException {

        try {
            String query = exchange.getRequestURI().getQuery();
            if (query == null) {
                sendResponse(exchange, 400, "not this parameter has been passed to controller. Go read docs.");
                return;
            }

            sendResponse(exchange, 200, new JSONObject(performSending(initFilledItemList(Integer.parseInt(queryToMap(query).get("amount"))))).toString());
        } catch (Exception e) {
            e.printStackTrace();
            sendError(e, exchange);
        }
    }

    private static synchronized TestResponse performSending(List<Integer> listInt) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        Channel ctx = getNodeChannel();
        AtomicInteger ai = new AtomicInteger();
        listInt.parallelStream().forEach(i -> {


            try {
                if (ctx==null || !ctx.isOpen()) reopenChannel();
                else if (ctx.writeAndFlush(msg).await(15, TimeUnit.SECONDS)) ai.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getLocalizedMessage());
            }
        });

        System.out.println("Total send: " + ai.get() + " from: " + listInt.size());
        return new TestResponse(listInt.size(), ai.get(), startTime, System.currentTimeMillis());
    }

    private static List<Integer> initFilledItemList(int size) {
        List<Integer> listInt = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listInt.add(i);
        }
        return listInt;
    }
}
