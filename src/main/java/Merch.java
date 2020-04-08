import java.util.ArrayList;
import java.util.UUID;

public class Merch {
    UUID productID;
    int[] merchState;

    String name;
    String brand;
    double price;
    String sellerName;
    boolean available;

    Category category;
    String description;
    double averageScore; //is related to Score Class
    ArrayList<Comment> comments;
    // can there be only 1 merch but with 2 sellers?
    // or sellers add one to the merch number and then they would be able to sell it?
}
