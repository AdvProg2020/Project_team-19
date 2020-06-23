package model;

import controller.CategoryController;
import controller.Database;
import controller.ProductController;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.CategoryController.*;
import static controller.ProductController.allProducts;
import static controller.ProductController.stock;

public class Product {

    private HashMap<String, String> properties;
    private String productID;
    private int seen;
    private int buyersNum;
    private int totalScore;
    private String name;
    private String brand;
    private String category;
    private String description;
    private String mediaPath = "";
    private double averageScore;
    private double averagePrice;
    private double leastPrice;
    private ArrayList<Comment> comments;

    public Product(String name, String brand, String category,
                   HashMap<String, String> properties) {

        this.productID = RandomStringUtils.random(4, true, true);
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.seen = 0;
        this.totalScore = 0;
        this.buyersNum = 0;
        this.properties = properties;
        this.comments = new ArrayList<>();
        allProducts.add(this);
        Database.saveToFile(this, Database.createPath("products", productID));
    }

    public boolean hasMedia() {
        return mediaPath.length() != 0;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String path) {
        mediaPath = path;
    }

    public void increaseBuyers() {
        buyersNum += 1;
    }

    public void increaseTotalScore(int score) {
        totalScore += score;
    }

    public String getDescription() {
        return description;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public void increaseSeen() {
        this.seen += 1;
    }

    public int getSeen() {
        return seen;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public String getName() {
        return name;
    }

    public double getAveragePrice() {
        return ProductController.getInstance().getAveragePrice(this);
    }

    public double getAverageScore() {
        return buyersNum != 0 ? (double)totalScore / buyersNum : 0;
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

    public double getLeastPrice() {
        double minPrice = stock.get(this).get(0).getProductPrice(this);
        for (Salesperson salesperson : stock.get(this)) {
            if (salesperson.getProductPrice(this) <= minPrice) {
                minPrice = salesperson.getProductPrice(this);
            }
        }
        return minPrice;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void edit(String name, String brand, String category, HashMap<String, String> properties) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.properties = new HashMap<>(properties);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() { //ToDo ino taqir ddm check knim hmeja ok e
        return name + " (ID: " + productID + ")";
    }

    public String printProduct() {
        return "Product : \n" +
                "Product ID : " + productID + "\n" +
                "Name : " + name + "\n" +
                "Brand : " + brand;
    }
}