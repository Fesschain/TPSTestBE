package main.java.netty.model;

import lombok.Data;
import org.json.JSONObject;

@Data
public class TestResponse {
    private int requestAmount;
    private long nodeTransactionChange;
    private double tps;
    private double avg;
    private long startTime;
    private long endTime;

    public TestResponse(int requestAmount, long nodeTransactionChange, long startTime, long endTime) {
        this.requestAmount = requestAmount;
        this.nodeTransactionChange = nodeTransactionChange;
        this.startTime = startTime;
        this.endTime = endTime;


        long performMilliss = endTime - startTime;




        this.avg = performMilliss/(requestAmount*1d);
        this.tps = 1000*requestAmount/(performMilliss*1d);
    }

    public String toJSON(){
        JSONObject json = new JSONObject();
        json.put("requestAmount", requestAmount);
        json.put("nodeTransactionChange", nodeTransactionChange);
        json.put("tps", tps);
        json.put("startTime", startTime);
        json.put("endTime", endTime);
        json.put("avg", avg);

        return json.toString();
    }
}
