package model;

import controller.Database;
import controller.PersonController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Person {
    private ArrayList<BuyLog> buyLogs;
    private HashMap<DiscountCode,Integer> discountCodes;
    private HashMap<Product,Integer> productsWithScore;
    private double credit;
    private Cart cart;


    public Customer(HashMap<String, String> personInfo) {
        super(personInfo);
        discountCodes = new HashMap<DiscountCode, Integer>();
        buyLogs = new ArrayList<BuyLog>();
        productsWithScore = new HashMap <> (  );
        Database.saveToFile(this, Database.createPath("customers", personInfo.get("username")));
        cart = new Cart();
    }

    public DiscountCode findDiscountCodeByCode(String code) {
        for (DiscountCode discountCode : discountCodes.keySet()) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public void setCartAfterLogin(Cart cart) {
        cart.setCartAfterLogIn(cart);
    }

    public boolean isThereDiscountCodeByCode(String code) {
        return findDiscountCodeByCode(code) != null;
    }

    public Cart getCart() {
        return cart;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        discountCodes.remove(discountCode);
    }


    public void useDiscountCode(DiscountCode discountCode) {
        cart.useDiscountCode(discountCode);
        discountCodes.put(discountCode, discountCodes.get(discountCode) - 1);
        if (discountCodes.get(discountCode) == 0) {
            discountCodes.remove(discountCode);
        }
    }

    public boolean checkCredit (double price) {
        return price <= credit;
    }

    public void setCredit ( double credit ) {
        this.credit = credit;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isProductBought(Product product){
        for (BuyLog buyLog : buyLogs) {
            if(buyLog.isThereProduct(product))
                return true;
        }
        return false;
    }

    public double getCredit () {
        return credit;
    }

    public void addToBuyLogs(BuyLog buyLog) {
        buyLogs.add(buyLog);
    }

    public void addToProductWithScore(Product product, Integer score) {
        productsWithScore.put ( product , score );
    }

    public ArrayList < BuyLog > getBuyLogs () {
        return buyLogs;
    }

    public void updateHistory() {

    }

    public void addDiscountCode(DiscountCode discountCode, int counter) {
        if (discountCodes.containsKey(discountCode)) {
            discountCodes.put(discountCode, discountCodes.get(discountCode) + counter);
        } else {
            discountCodes.put(discountCode, counter);
        }
    }

    public void increaseCredit(double amount) {
        credit += amount;
    }

    public HashMap<DiscountCode, Integer> getDiscountCodes() {
        return discountCodes;
    }

    public BuyLog findBuyLogById(String id) {
        for (BuyLog buyLog : buyLogs) {
            if (buyLog.getLogID().equals(id))
                return buyLog;
        }
        return null;
    }

    public void setScore(Product product,int score){ //ToDo GIJ SHODM
        //TODO set score in product
//        int n = product.getAmountOfScores ();
//        product.setAmountOfScores ( n+1 );
//        product.setAverageScore (  );
//        productsWithScore.put ( product , (int) ((n * product.getAverageScore () + score)/(n+1)) );
    }


}