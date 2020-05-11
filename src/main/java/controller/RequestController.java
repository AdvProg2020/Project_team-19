package controller;

import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static controller.Database.*;
import static model.Product.getProductById;

public class RequestController {
    public static ArrayList<Request> allRequests = new ArrayList<>();

    public enum FilterType {
        ALL, SALESPERSON, PRODUCT, DISCOUNT
    }

    public static void initializeRequests() throws FileNotFoundException {
        for (File file : Database.returnListOfFiles(Database.address.get("requests"))) {
            allRequests.add((DiscountRequest) Database.read(DiscountRequest.class, file.getAbsolutePath()));
        }
    }

    public static void acceptRequest(Request request) throws IOException {
        request.doThis();
        allRequests.remove(request);
        deleteFile(createPath("requests", request.getRequestId()));
    }

    public static void declineRequest(Request request) throws IOException {
        allRequests.remove(request);
        deleteFile(createPath("requests", request.getRequestId()));
    }

    public static void addSalesPerson(HashMap<String, String> personInfo) {
        try {
            Salesperson salesperson = new Salesperson(personInfo);
            PersonController.addPerson(salesperson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Request> filterByState(Request.RequestState requestState) {
        return allRequests.stream().filter(request -> request.getRequestState().
                equals(requestState)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static <T> ArrayList<Request> getSpecificTypeOfRequests(Class<T> requestType) {
        ArrayList<Request> requests = allRequests;
        return requests.stream().filter(requestType::isInstance).collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean editIsValid(String productId, Salesperson salesperson) {
        return salesperson.hasProduct(getProductById(productId));
    }

    public static String getDetailsOf(String requestId) {
        Request request = Request.getRequestById(requestId);
        return request.show();
    }

    public static ArrayList<Request> processGetSpecificRequests( FilterType type) {
        switch (type) {
            case SALESPERSON:
                return getSpecificTypeOfRequests(SalespersonRequest.class);
            case PRODUCT:
                return getSpecificTypeOfRequests(ProductRequest.class);
            case DISCOUNT:
                return getSpecificTypeOfRequests(DiscountRequest.class);
            default:
                return getSpecificTypeOfRequests(Request.class);
        }
    }

}
