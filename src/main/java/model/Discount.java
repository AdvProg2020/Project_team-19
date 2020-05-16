package model;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Discount {

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public enum DiscountState {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    private final String discountID;
    private ArrayList<Product> products;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;

    public Discount(LocalDateTime startTime, LocalDateTime endTime, double discountPercentage,
                    ArrayList<Product> products) {
        this.discountID = RandomStringUtils.random(4,true,true);
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.products = products;
    }

    public boolean checkDiscountEndTime(){
        return endTime.isAfter(LocalDateTime.now());
    }

    public boolean checkDiscountStartTime(){
        return startTime.isBefore(LocalDateTime.now());
    }

    public String getDiscountID() {
        return discountID;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getPriceAfterDiscount(double price) {
        return price * (1 - discountPercentage / 100);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public String toString () {
        StringBuilder string = new StringBuilder ( "Discount ID : " + discountID + "\n" +
                "Products : \n" );
        for (Product product : products) {
            string.append ( product ).append ( " | " );
        }
        string.append ( "\n" );
        string.append ( "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
                "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" +
                "\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014" );
        string.append ( "\n" );
        string.append ( "Start Time : " )
                .append ( startTime )
                .append ( "\n" )
                .append ( "End Time : " )
                .append ( endTime )
                .append ( "\n" )
                .append ( "Discount Percentage : " )
                .append ( discountPercentage );
        return string.toString ();
    }
}
