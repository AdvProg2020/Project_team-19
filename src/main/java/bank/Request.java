package bank;

public class Request {
    private final PacketType requestType;
    private final String requestString;

    public Request(PacketType requestType, String requestString) {
        this.requestType = requestType;
        this.requestString = requestString;
    }

    public PacketType getRequestType() {
        return requestType;
    }

    public String getRequestString() {
        return requestString;
    }
}