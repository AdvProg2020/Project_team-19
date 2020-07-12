package server;

import model.Product;

import java.util.ArrayList;

public class Request {
    private PacketType requestType;
    private String json;

    public Request(PacketType requestType, String json) {
        this.requestType = requestType;
        this.json = json;
    }

    public PacketType getRequestType() {
        return requestType;
    }

    public void setRequestType(PacketType requestType) {
        this.requestType = requestType;
    }

    public String getJson() {
        return json;
    }
}
