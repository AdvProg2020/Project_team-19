package model;

import controller.Database;
import controller.DiscountCodeController;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Person {
    private ArrayList<BuyLog> buyLogs;
    private HashMap<String, Integer> discountCodes;
    private HashMap<Product, Integer> productsWithScore;
    private double credit;
    private Cart cart;


    public Customer(HashMap<String, String> personInfo) {
        super(personInfo);
        discountCodes = new HashMap<>();
        buyLogs = new ArrayList<>();
        productsWithScore = new HashMap<>();
        Database.saveToFile(this, Database.createPath("customers", personInfo.get("username")));
        cart = new Cart();
    }

    public void setCartAfterLogin(Cart cart) {
        cart.setCartAfterLogIn(cart);
    }

    public boolean isThereDiscountCodeByCode(String code) {
        for (String discountCode : discountCodes.keySet()) {
            if (discountCode.equals(code))
                return true;
        }
        return false;
    }

    public Cart getCart() {
        return cart;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        discountCodes.remove(discountCode.getCode());
    }

    public void useDiscountCode(DiscountCode discountCode) {
        cart.useDiscountCode(discountCode);
        discountCodes.put(discountCode.getCode(), discountCodes.get(discountCode.getCode()) - 1);
        if (discountCodes.get(discountCode.getCode()) == 0) {
            discountCodes.remove(discountCode.getCode());
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
        if (discountCodes.containsKey(discountCode.getCode())) {
            discountCodes.put(discountCode.getCode(), discountCodes.get(discountCode.getCode()) + counter);
        } else {
            discountCodes.put(discountCode.getCode(), counter);
        }
    }

    public void increaseCredit(double amount) {
        credit += amount;
    }

    public HashMap<DiscountCode, Integer> getDiscountCodes() {
        HashMap<DiscountCode, Integer> codes = new HashMap<>();
        for (String s : discountCodes.keySet()) {
            codes.put(DiscountCodeController.getInstance().findDiscountCodeByCode(s),discountCodes.get(s));
        }
        return codes;
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