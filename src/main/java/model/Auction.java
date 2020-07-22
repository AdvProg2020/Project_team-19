package model;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Auction {
    private String auctionId;
    private String sellerName;
    private String productId;
    private String productName;
    private String productImageURL;
    private String sellerImageURL;
    private double basePrice;
    private LocalDateTime endTime;
    private HashMap<String, Double> customers = new HashMap<>();

    public Auction(Salesperson salesperson, Product product, LocalDateTime endTime) {
        this.sellerName = salesperson.getUsername();
        this.productId = product.getID();
        this.productImageURL = product.getImageURI();
        this.sellerImageURL = salesperson.getImage();
        this.productName = product.getName();
        this.endTime = endTime;
        this.basePrice = salesperson.getProductPrice(product);
        this.auctionId = RandomStringUtils.random(4, true, true);
    }

    public String getProductId() {
        return productId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getId() {
        return auctionId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public String getSellerImageURL() {
        return sellerImageURL;
    }

    public String getProductName() {
        return productName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void addCustomer(Customer customer, double price) {
        customers.put(customer.getUsername(), price);
    }

    public HashMap<String, Double> getCustomers() {
        return customers;
    }
}
