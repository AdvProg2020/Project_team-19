package model;

import java.util.ArrayList;
import java.util.UUID;

public class Product {
    UUID productID;
    int[] productState;
    ArrayList<Salesperson> owners;

    String name;
    String brand;
    double price;
    String sellerName;
    boolean available;

    Category category;
    String description;
    double averageScore; //is related to Model.Score Class
    ArrayList<Comment> comments;
}
