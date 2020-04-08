package Model;

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
    // can there be only 1 merch but with 2 sellers?
    // or sellers add one to the merch number and then they would be able to sell it?
}
