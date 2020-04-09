package model;

import java.util.HashMap;

public class ProductRequest extends Request {
    private String productID;
    private String name;
    private String brand;
    private double price;
    RequestState requestState;
    private HashMap<String, String> properties;

    public ProductRequest(String productID, String name, String brand, double price, RequestState requestState, HashMap<String, String> properties) {
        this.productID = productID;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.requestState = requestState;
        this.properties = properties;
    }
}
