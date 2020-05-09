package model;

import controller.Database;

import java.io.IOException;
import java.util.HashMap;

public class ProductRequest extends Request {
    private String productID;
    private String name;
    private String brand;
    private double price;
    RequestState requestState;
    private HashMap<String, String> properties;

    public ProductRequest(String requestId,String productID, String name, String brand, double price, RequestState requestState, HashMap<String, String> properties) throws IOException {
        super(requestId);
        this.productID = productID;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.requestState = requestState;
        this.properties = properties;
        Database.saveToFile(this, Database.createPath("productRequests", requestId),false);
    }


    @Override
    public void doThis() {
        if (requestState.name().equals("ADD")) {
            addProduct();
        }
        else
            editProduct();
    }

    @Override
    public String show() {
        //ba tavajoh be salighe avazesh konim
        return getRequestId();
    }

    private void addProduct () {
        //...
        System.out.println("add product");
    }

    private void editProduct () {
        //...
        System.out.println("edit product");
    }

    //a simple constructor for test
    public ProductRequest (String requestId , RequestState requestState) {
        super(requestId);
        this.requestState = requestState;
    }
}
