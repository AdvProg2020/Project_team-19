package model;

import controller.CategoryController;
import controller.Database;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.CategoryController.*;
import static controller.ProductController.allProducts;

public class Product {

    private HashMap<String, String> properties;
    private String productID;
    private int count;
    private int seen;
    private String name;
    private String brand;
    private String category;
    private String description;
    private double averageScore;
    private double averagePrice;
    private double leastPrice;
    private ArrayList<Comment> comments;

    public Product(String name, String brand, String category,
                   HashMap<String, String> properties, boolean temp) {

        this.productID = RandomStringUtils.random(4, true, true);
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.count = 0;
        this.seen = 0;
        this.properties = properties;
        if (!temp) {
            Database.saveToFile(this, Database.createPath("products", productID));
            allProducts.add(this);
        }
    }


    public void increaseSeen() {
        this.seen += 1;
    }

    public int getSeen() {
        return seen;
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

    public Category getCategory() {
        return CategoryController.getInstance().getCategoryByName(category, rootCategories);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public double getLeastPrice() {
        return leastPrice;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public static Product getProductById(String productID) {
        for (Product product : allProducts) {
            if (product.getID().equals(productID))
                return product;
        }
        return null;
    }

    public void edit(String name, String brand, String category, HashMap<String, String> properties) {
        getCategory().removeProduct(this);
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.properties = new HashMap<>(properties);
        getCategory().addProduct(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void changeCount(int count) {
        this.count += count;
    }

    @Override
    public String toString() { //ToDo ino taqir ddm check knim hmeja ok e
        return name + " (ID: " + productID + ")";
    }

    public String printProduct() {
        return "Product : \n" +
                "Product ID : " + productID + "\n" +
                "Count : " + count + "\n" +
                "Name : " + name + "\n" +
                "Brand : " + brand;
    }
}