package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestController {
    public enum FilterType {
        ALL, SALESPERSON, PRODUCT, DISCOUNT
    }

    public Request getRequestById (String requestId) {
        for (Request request : Database.allRequests) {
            if (request.getRequestId().equals(requestId))
                return request;
        }
        return null;
    }

    public void acceptRequest (Request request) {
        request.doThis();
    }

    public void declineRequest (Request request) {
        //Database.removeFromAllRequest(request);
    }

    public static <T> ArrayList<Request> getSpecificTypeOfRequests(Class<T> requestKind) {
        ArrayList<Request> requests = Database.allRequests;
        return requests.stream().filter(requestKind::isInstance).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getDetailsOf (String requestId) {
        Request request = getRequestById(requestId);
        return request.show();
    }

    public static ArrayList<Request> processGetSpecificRequests (FilterType type) {
        switch (type.name()) {
            case "SALESPERSON":
                return getSpecificTypeOfRequests(SalespersonRequest.class);
            case "PRODUCT":
                return getSpecificTypeOfRequests(ProductRequest.class);
            case "DISCOUNT":
                return getSpecificTypeOfRequests(DiscountRequest.class);
            default:
                return getSpecificTypeOfRequests(Request.class);
        }
    }

}
