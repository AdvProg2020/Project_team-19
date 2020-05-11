package model;

import controller.Database;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class BuyLog {
    private String logID;
    private LocalDateTime date;
    private double paymentAmount;
    private double discountCodeAmount;
    private HashMap< Product,ProductStateInCart > tradedProductList;
    private boolean reachedBuyer;

    public BuyLog(String logID, LocalDateTime date, double paymentAmount, double discountCodeAmount, HashMap<Product,ProductStateInCart> tradedProductList, boolean reachedBuyer) throws IOException {
        this.logID = logID;
        this.date = date;
        this.paymentAmount = paymentAmount;
        this.discountCodeAmount = discountCodeAmount;
        this.tradedProductList = tradedProductList;
        this.reachedBuyer = reachedBuyer;
    }

    public boolean isThereProduct(Product product){
        return tradedProductList.containsKey(product);
    }
}
