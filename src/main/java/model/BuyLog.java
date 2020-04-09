package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class BuyLog {
    private String logID;
    private String date;
    private double paymentAmount;
    private double discountCodeAmount;//by discount code
    private HashMap< Product,Integer > tradedProductList;
    private Salesperson seller;
    private boolean reachedBuyer;

    public BuyLog(String logID, String date, double paymentAmount, double discountCodeAmount, HashMap<Product,Integer> tradedProductList, Salesperson seller, boolean reachedBuyer) {
        this.logID = logID;
        this.date = date;
        this.paymentAmount = paymentAmount;
        this.discountCodeAmount = discountCodeAmount;
        this.tradedProductList = tradedProductList;
        this.seller = seller;
        this.reachedBuyer = reachedBuyer;
    }
}
