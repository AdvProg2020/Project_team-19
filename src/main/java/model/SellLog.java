package model;

import controller.PersonController;
import controller.ProductController;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class SellLog {
    private String logID;
    private LocalDateTime date;
    private double deliveredAmount;
    private double discountAmount;
    private String productId;
    private int count;
    private String buyerUsername;
    private boolean transmitted;

    public SellLog(LocalDateTime date, double deliveredAmount, double discountAmount, Product product, Customer buyer, boolean transmitted, int count) {
        this.logID = RandomStringUtils.random(4, true, true);
        this.date = date;
        this.deliveredAmount = deliveredAmount;
        this.discountAmount = discountAmount;
        this.productId = product.getID();
        this.buyerUsername = buyer.getUsername();
        this.count = count;
        this.transmitted = transmitted;
    }

    public double getDeliveredAmount() {
        return deliveredAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public String getLogID() {
        return logID;
    }

    public Product getProduct() {
        return ProductController.getInstance().getProductById(productId);
    }

    public int getCount() {
        return count;
    }

    public Customer getBuyer() {
        return (Customer) PersonController.getInstance().getPersonByUsername(buyerUsername);
    }

    public LocalDateTime getDate () {
        return date;
    }

    public boolean isTransmitted() {
        return transmitted;
    }

    public String getEverythingString() {
        return "Log ID : " + logID + "\n" +
                "Date : " + date + "\n" +
                "Delivered Amount : " + deliveredAmount + "\n" +
                "Discount Amount : " + discountAmount + "\n" +
                "Product : " + getProduct().getName() + "\n" +
                "Count : " + count + "\n" +
                "Buyer : " + getBuyer().getUsername() + "\n" +
                "Transmitted(T/F) : " + transmitted;
    }

    @Override
    public String toString() {
        return "Log ID : " + logID + "\n" +
                "Date : " + date;
    }
}