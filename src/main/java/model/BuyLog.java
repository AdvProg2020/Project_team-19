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
    private HashMap<String,ArrayList<String>> products;
    private boolean reachedBuyer;

    public BuyLog( LocalDateTime date, double paymentAmount, double discountCodeAmount, HashMap<Product,HashMap<Salesperson,ProductStateInCart>> tradedProductList, boolean reachedBuyer) {
        this.logID = RandomStringUtils.random(4, true, true);
        this.date = date;
        this.paymentAmount = paymentAmount;
        this.discountCodeAmount = discountCodeAmount;
        this.reachedBuyer = reachedBuyer;
        products = new HashMap<>();
        for (Product product : tradedProductList.keySet()) {
            ArrayList<String> strings= new ArrayList<>();
            String state ;
            for (ProductStateInCart value : tradedProductList.get(product).values()) {
                state = "seller:"+value.getSalesperson().getUsername()+"\n"
                        +"count: "+value.count+"\n"
                        +"price: "+value.getPrice()+"\n";
                if(value.isInDiscount())
                    state += "price after discount: "+value.getPriceAfterDiscount()+"\n";
                state+= "total: "+value.getTotalPrice();
                strings.add(state);
            }
            products.put(product.getID(),strings);
        }
    }

    public boolean isThereProduct(Product product){
        for (String productId: products.keySet()) {
            if(product.getID().equals(productId))
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
                "Reached Buyer : " + reachedBuyer;
    }

    @Override
    public String toString () {
        return "Log ID : " + logID + "\n" +
                "Date : " + date;
    }
}