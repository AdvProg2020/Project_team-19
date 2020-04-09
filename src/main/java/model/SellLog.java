package model;

import java.util.ArrayList;

public class SellLog {
    private String logID;
    private String date;
    private double deliveredAmount;
    private double discountAmount;//by discount
    private ArrayList< Product > tradedProductList;
    private Customer buyer;
    private boolean transmitted;

    public SellLog(String logID, String date, double deliveredAmount, double discountAmount, ArrayList<Product> tradedProductList, Customer buyer, boolean transmitted) {
        this.logID = logID;
        this.date = date;
        this.deliveredAmount = deliveredAmount;
        this.discountAmount = discountAmount;
        this.tradedProductList = tradedProductList;
        this.buyer = buyer;
        this.transmitted = transmitted;
    }
}
