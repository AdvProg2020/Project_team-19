package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Product {
    enum ProductState {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    ArrayList<Salesperson> owners;
    private HashMap<String, String> properties;

    private double price;
    private int count;
    private String productID;
    private String name;
    private String brand;
    private String sellerName;

    public Product(String productID, String name, String brand, double price, String sellerName, ProductState productState,Category category) {
        this.productID = productID;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.sellerName = sellerName;
        this.productState = productState;
        this.count = 0;
        for (String property : category.getProperties()) {
            //setting properties
        }
    }

    public boolean isAvailable() {
        return count > 0;
    }

    private ProductState productState;

    Category category;
    String description;
    double averageScore; //is related to Model.Score Class
    ArrayList<Comment> comments;
}
