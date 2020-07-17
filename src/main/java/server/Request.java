package server;
import java.util.ArrayList;

public class Request {
    private PacketType requestType;
    private ArrayList<String> json;
    private String token;

    public Request(PacketType requestType, ArrayList<String> json, String token) {
        this.requestType = requestType;
        this.json = json;
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
