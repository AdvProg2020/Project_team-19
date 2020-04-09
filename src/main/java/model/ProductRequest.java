package model;

import java.util.HashMap;

public class ProductRequest extends Request {
    private String productID;
    private String name;
    private String brand;
    private double price;
    RequestState requestState;
    private HashMap<String, String> properties;
}
