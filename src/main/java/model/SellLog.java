package model;

import controller.Database;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SellLog {
    private String logID;
    private LocalDateTime date;
    private double deliveredAmount;
    private double discountAmount;
    private Product product;
    private int count;
    private Customer buyer;
    private boolean transmitted;

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
        return product;
    }

    public int getCount() {
        return count;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public boolean isTransmitted() {
        return transmitted;
    }

    public SellLog( LocalDateTime date, double deliveredAmount, double discountAmount, Product product, Customer buyer, boolean transmitted, int count) throws IOException {
        this.logID = RandomStringUtils.random(4, true, true);
        this.date = date;
        this.deliveredAmount = deliveredAmount;
        this.discountAmount = discountAmount;
        this.product = product;
        this.buyer = buyer;
        this.count = count;
        this.transmitted = transmitted;
    }
}
