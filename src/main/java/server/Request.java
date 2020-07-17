package server;

import model.Product;

import java.util.ArrayList;
import java.util.UUID;

public class Request {
    private PacketType requestType;
    private ArrayList<String> json;
    private String token;

    public Request(PacketType requestType, String json,String token) {
        this.requestType = requestType;
        this.json = new ArrayList<>();
        this.json.add(json);
        this.token = token;
    }

    public PacketType getRequestType() {
        return requestType;
    }

    public void setRequestType(PacketType requestType) {
        this.requestType = requestType;
    }

    public ArrayList<String> getJson() {
        return json;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addToJson(String json){
        this.json.add(json);
    }
}
