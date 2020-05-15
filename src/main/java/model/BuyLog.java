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
    private HashMap<Product,Integer> productHashMap;
    private boolean reachedBuyer;

    public BuyLog( LocalDateTime date, double paymentAmount, double discountCodeAmount, HashMap<Product,HashMap<Salesperson,ProductStateInCart>> tradedProductList, boolean reachedBuyer) {
        this.logID = RandomStringUtils.random(4, true, true);
        this.date = date;
        this.paymentAmount = paymentAmount;
        this.discountCodeAmount = discountCodeAmount;
        this.reachedBuyer = reachedBuyer;
        products = new ArrayList<>();
        for (Product product : tradedProductList.keySet()) {
            productHashMap.put(product,null);
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

    public String getLogID() {
        return logID;
    }

    public boolean isProductScored(Product product){
        return productHashMap.get(product)!=null;
    }

    public void setScore(Product product,int score) {
        //TODO set score in product
        productHashMap.put(product, score);
    }
}