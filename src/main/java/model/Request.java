package model;

import controller.Database;

import java.io.IOException;

import static controller.RequestController.allRequests;

public abstract class Request {
    public enum RequestState {
        ADD, EDIT, DELETE
    }

    private String requestId;
    private RequestState requestState;

    public Request(String requestId, RequestState requestState) throws IOException {
        this.requestId = requestId;
        this.requestState = requestState;
        Database.saveToFile(this, Database.createPath("requests", requestId));
        allRequests.add(this);
    }

    public RequestState getRequestState() {
        return requestState;
    }

    public String getRequestId() {
        return requestId;
    }

    public abstract void doThis() throws IOException;

    public abstract String show();

    public static Request getRequestById(String requestId) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId))
                return request;
        }
        return null;
    }

    public abstract String toString();
   // @Override
//    public String toString() {
//        return requestId;
//    }
}
