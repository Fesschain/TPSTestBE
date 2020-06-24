package main.java.netty.model;

import lombok.Data;

@Data
public class TransactionRequest {
    private final String from;
    private final String to;
    private final long amount;

    public TransactionRequest(String from, String to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
