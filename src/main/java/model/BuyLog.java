package model;

import controller.Database;
import org.apache.commons.lang3.RandomStringUtils;

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
    private ArrayList<OwnedProduct> products;
    private boolean reachedBuyer;

    public BuyLog( LocalDateTime date, double paymentAmount, double discountCodeAmount, HashMap<Product,HashMap<Salesperson,ProductStateInCart>> tradedProductList, boolean reachedBuyer) {
        this.logID = RandomStringUtils.random(4, true, true);
        this.date = date;
        this.paymentAmount = paymentAmount;
        this.discountCodeAmount = discountCodeAmount;
        this.reachedBuyer = reachedBuyer;
        products = new ArrayList<>();
        for (Product product : tradedProductList.keySet()) {
            for (ProductStateInCart value : tradedProductList.get(product).values()) {
                products.add(new OwnedProduct(value,product));
            }
        }
    }

    public boolean isThereProduct(Product product){
        for (OwnedProduct ownedProduct : products) {
            if(product.equals(ownedProduct.getProduct()))
                return true;
        }
        return false;
    }

    public String getLogID () {
        return logID;
    }

    public String getEverythingString () {
        return "Log ID : " + logID + "\n" +
                "Date : " + date + "\n" +
                "Payment Amount : " + paymentAmount + "\n" +
                "Discount Code Amount : " + discountCodeAmount + "\n" +
                "Products : " + products + "\n" +
                "Reached Buyer : " + reachedBuyer;
    }

    @Override
    public String toString () {
        return "Log ID : " + logID + "\n" +
                "Date : " + date;
    }
}