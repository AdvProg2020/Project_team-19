package model;

import java.util.LinkedList;

public abstract class Request {
    public static LinkedList<Request> allRequests=new LinkedList<Request>();
    enum RequestState{
        ADD,EDIT
    }
}
