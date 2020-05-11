package model;

import controller.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.ProductController.allProducts;

public class Product {
    public static HashMap<Product, ArrayList<Salesperson>> stock = new HashMap<>();

    private HashMap<String, String> properties;
    private String productID;
    private int count;
    private String name;
    private String brand;
    private String category;
    private String description;
    private double averageScore;
    private ArrayList<Comment> comments;

    public Product(String productID, String name, String brand, String category,
                   HashMap<String, String> properties) throws IOException {

        this.productID = productID;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.count = 0;
        this.properties = properties;
        Database.saveToFile(this, Database.createPath("products", productID));
        allProducts.add(this);
    }


    public boolean isAvailable() {
        return count > 0;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public String getBrand() {
        return brand;
    }

    public String getID() {
        return productID;
    }

    public static Product getProductById(String productID) {
        for (Product product : allProducts) {
            if (product.getID().equals(productID))
                return product;
        }
        return null;
    }

    public void edit(String name, String brand, String category, HashMap<String, String> properties) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.properties = properties;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return productID;

    public String printProduct() {
        return "Product : " +
                ", productID='" + productID + '\'' +
                ", count=" + count +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}