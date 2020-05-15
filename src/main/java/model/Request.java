package model;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;


public abstract class Request {
    public enum RequestState {
        ADD, EDIT, DELETE
    }

    private String requestId;
    private RequestState requestState;

    public Request(RequestState requestState) {
        this.requestId = RandomStringUtils.random(4, true, true);
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

    @Override
    public String toString() {
        return requestId;
    }
}
