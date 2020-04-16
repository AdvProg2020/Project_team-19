package model;

public abstract class Request {
    private String requestId;

    public enum RequestState {
        ADD, EDIT
    }

    public Request(String requestId) {
        this.requestId = requestId;
    }


    public String getRequestId() {
        return requestId;
    }

    public abstract void doThis();

    public abstract String show();


}
