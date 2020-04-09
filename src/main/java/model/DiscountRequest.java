package model;

import java.util.ArrayList;

public class DiscountRequest extends Request {
    private RequestState requestState;
    private String discountID;
    private ArrayList< Product > discountProductList;
    private ArrayList<Product> addProduct;
    private ArrayList<Product> removeProduct;
    private String startTime; //we can use "new java.util.Date()" that gives the exact time
    private String endTime;
    private double discountPercentage;

    public DiscountRequest(String discountID, ArrayList<Product> discountProductList, ArrayList<Product> addProduct, ArrayList<Product> removeProduct, String startTime, String endTime, double discountPercentage) {
        this.discountID = discountID;
        this.discountProductList = discountProductList;
        this.addProduct = addProduct;
        this.removeProduct = removeProduct;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
    }

    public DiscountRequest(ArrayList<Product> discountProductList, String startTime, String endTime, double discountPercentage) {
        this.discountProductList = discountProductList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
    }
}
