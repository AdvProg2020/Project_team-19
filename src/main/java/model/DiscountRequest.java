package model;

import controller.Database;

import java.io.IOException;
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

    public DiscountRequest(String requestId,String discountID, ArrayList<Product> discountProductList,
                           ArrayList<Product> addProduct, ArrayList<Product> removeProduct, String startTime,
                           String endTime, double discountPercentage) throws IOException {
        super(requestId);
        this.discountID = discountID;
        this.discountProductList = discountProductList;
        this.addProduct = addProduct;
        this.removeProduct = removeProduct;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        Database.saveToFile(this, Database.createPath("discountRequests", requestId));
    }


//    public DiscountRequest(ArrayList<Product> discountProductList, String startTime, String endTime, double discountPercentage) {
//        this.discountProductList = discountProductList;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.discountPercentage = discountPercentage;
//    }

    @Override
    public void doThis() {
        if (requestState.name().equals("ADD")) {
            addProduct();
        }
        else
            editProduct();
    }

    @Override
    public String show() {
        //ba tavajoh be salighe avazesh konim
        return getRequestId();
    }

    private void addProduct () {
        //...
        System.out.println("add product");
    }

    private void editProduct () {
        //...
        System.out.println("edit product");
    }

    //a simple constructor for test
    public DiscountRequest(String requestId) {
        super(requestId);
    }
}
