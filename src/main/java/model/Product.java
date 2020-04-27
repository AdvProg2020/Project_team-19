package model;

import controller.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Product {
    enum ProductState {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    ArrayList<Salesperson> owners;
    private HashMap<String, String> properties;
    private String productID;
    private int count;
    private String name;
    private String brand;
    private Category category;
    private String description;
    private double score;
    private ArrayList<Comment> comments;
    private ProductState productState;

    public static Product findProductByName(String name){
        return null;
    }

    public static Product findProductById(String id){
        return null;
    }

    public Product(String productID, String name, String brand, String sellerName, ProductState productState,Category category) throws IOException {
        this.productID = productID;
        this.name = name;
        this.brand = brand;
        this.productState = productState;
        this.count = 0;
        for (String property : category.getProperties()) {
            //setting properties
        }
        Database.saveToFile(this,Database.createPath("product",productID));
    }

    public boolean isAvailable() {
        return count > 0;
    }


}
