package model;

import static controller.RequestController.allRequests;

public abstract class Request {
    public enum RequestState {
        ADD, EDIT, DELETE
    }

    private String requestId;
    private RequestState requestState;

    public Request(String requestId, RequestState requestState) {
        this.requestId = requestId;
        this.requestState = requestState;
    }

    public RequestState getRequestState() {
        return requestState;
    }

    public String getRequestId() {
        return requestId;
    }

    public abstract void doThis();

    public abstract String show();

    public static Request getRequestById(String requestId) {
        for (Request request : allRequests) {
            if (request.getRequestId().equals(requestId))
                return request;
        }
        return null;
    }

}
