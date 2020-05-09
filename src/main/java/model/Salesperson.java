package model;


import controller.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private ArrayList<SellLog> sellLogs;
    private HashMap<Product, ProductState> offeredProducts;
    private ArrayList<Discount> discounts;
    private int credit;


    public Salesperson(HashMap<String, String> personInfo) throws IOException {
        super(personInfo);
        sellLogs = new ArrayList<>();
        offeredProducts = new HashMap<>();
        discounts = new ArrayList<>();
        Database.saveToFile(this, Database.createPath("salesperson", personInfo.get("username")));
    }


    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void changeProductAmount(Product offeredProduct, int amount) { //amount can be negative
        offeredProducts.get(offeredProduct).setAmount(amount);
    }

    public void setProductDiscountState(Product product, boolean state) {
        offeredProducts.get(product).setInDiscount(state);
    }

    public void setProductState(Product product, ProductState.State state) {
        offeredProducts.get(product).setState(state);
    }

    public boolean hasProduct(Product offeredProduct) {
        return offeredProducts.containsKey(offeredProduct);
    }

    public void addToOfferedProducts(Product offeredProduct, int amount, double price) {
        offeredProducts.put(offeredProduct, new ProductState(false, amount, price));
    }

    public void removeFromOfferedProducts(Product offeredProduct) {
        offeredProducts.remove(offeredProduct);
    }

    public void addToDiscounts(Discount discount){
        discounts.add(discount);
    }

    public void removeFromDiscounts(Discount discount) {
        discounts.remove(discount);
    }

    public HashMap<Product, ProductState> getOfferedProducts() {
        return offeredProducts;
    }

    public double getProductPrice(Product product) {
        return offeredProducts.get(product).getPrice();
    }

    public void setProductPrice(Product product, double price) {
        offeredProducts.get(product).setPrice(price);
    }

    public void setProductAmount(Product product, int amount) {
        offeredProducts.get(product).setAmount(amount);
    }

    public void editProduct(Product product, double price, int amount) {
        setProductPrice(product, price);
        setProductAmount(product, amount);
    }

    @Override
    public String toString() {
        return "Salesperson{" +
                "offeredProducts=" + offeredProducts +
                '}';
    }
}

class ProductState {
    public enum State {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    private boolean inDiscount;
    private int amount;
    private double price;
    private State productState;

    public ProductState( boolean inDiscount, int amount, double price) {
        this.inDiscount = inDiscount;
        this.amount = amount;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public State getProductState() {
        return productState;
    }

    public void setInDiscount(boolean inDiscount) {
        this.inDiscount = inDiscount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setState(State productState) {
        this.productState = productState;
    }
}