package model;

import java.util.ArrayList;
import java.util.HashMap;


public class Product {
    enum ProductState {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    ArrayList<Salesperson> owners;
    private HashMap<String, String> properties;
    private String productID;
    private double price;
    private int count;
    private String name;
    private String brand;
    private String sellerName;
    private Category category;
    private String description;
    private double averageScore; //is related to Model.Score Class
    private ArrayList<Comment> comments;
    private ProductState productState;

    public static Product findProductByName(String name){
        return null;
    }

    public static Product findProductById(String id){
        return null;
    }

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


}
