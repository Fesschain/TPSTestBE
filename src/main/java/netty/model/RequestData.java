package main.java.netty.model;

import org.json.JSONObject;

public class RequestData {
    private int intValue;
    private TransactionRequest req;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return new JSONObject(req).toString();
    }

    public void setReq(TransactionRequest tr){
        this.req = tr;
    }
    public void setStringValue(String stringValue) {
        System.out.println("In set str str value: "+stringValue);
        JSONObject jsonObject = new JSONObject(stringValue);

        this.req = new TransactionRequest(jsonObject.getString("from"), jsonObject.getString("to"), jsonObject.getLong("amount"));
    }

    @Override
    public String toString() {
        return "RequestData{" + "intValue=" + intValue + ", req='" + req + '\'' + '}';
    }

    public RequestData(int intValue, TransactionRequest req) {
        this.intValue = intValue;
        this.req = req;
    }
}
