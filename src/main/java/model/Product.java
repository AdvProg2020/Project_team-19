package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    public static HashMap<Product, ArrayList<Salesperson>> stock = new HashMap<>();

    public enum ProductState {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    private HashMap<String, String> properties;
    private String productID;
    private int count;
    private String name;
    private String brand;
    private String category;
    private String description;
    private double averageScore; //is related to Model.Score Class
    private ArrayList<Comment> comments;
    private ProductState productState;

    public Product(String productID, String name, String brand, ProductState productState,
                   String category, HashMap<String, String> properties) {
        this.productID = productID;
        this.name = name;
        this.brand = brand;
        this.productState = productState;
        this.category = category;
        this.count = 0;
        this.properties = properties;
    }


    public boolean isAvailable() {
        return count > 0;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "Product : " +
                ", productID='" + productID + '\'' +
                ", count=" + count +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
