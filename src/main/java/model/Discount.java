package model;

import java.util.ArrayList;

import static controller.DiscountController.allDiscounts;

public class Discount {

    public enum DiscountState {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    private String discountID;
    private ArrayList<Product> products;
    private String startTime; //we can use "new java.util.Date()" that gives the exact time
    private String endTime;
    private double discountPercentage;

    public Discount(String discountID, String startTime, String endTime, double discountPercentage,
                    ArrayList<Product> products) {
        this.discountID = discountID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.products = products;
    }

    public String getDiscountID() {
        return discountID;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public static Discount getDiscountById(String discountID) {
        for (Discount discount : allDiscounts) {
            if (discount.getDiscountID().equals(discountID))
                return discount;
        }
        return null;
    }

}
