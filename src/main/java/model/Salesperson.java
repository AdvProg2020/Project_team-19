package model;


import controller.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private ArrayList<SellLog> sellLogs;
    private HashMap<Product, ProductState> offeredProducts;
    private ArrayList<Discount> discounts;
    private double credit;


    public Salesperson(HashMap<String, String> personInfo) throws IOException {
        super(personInfo);
        sellLogs = new ArrayList<>();
        offeredProducts = new HashMap<>();
        discounts = new ArrayList<>();
        Database.saveToFile(this, Database.createPath("salespersons", personInfo.get("username")));
    }


    public void addSellLogAndPurchase(SellLog sellLog){
        offeredProducts.get(sellLog.getProduct()).setAmount(offeredProducts.get(sellLog.getProduct()).getAmount()-sellLog.getCount());
        setCredit(credit+sellLog.getDeliveredAmount());
        sellLogs.add(sellLog);
    }

    public double getDiscountPrice(Product product){
        return offeredProducts.get(product).getDiscount().getPriceAfterDiscount(offeredProducts.get(product).getPrice());
    }

    public void setProductState(Product product, ProductState.State state) {
        offeredProducts.get(product).setState(state);
    }

    public boolean isInDiscount(Product product){
        return offeredProducts.get(product).isInDiscount();
    }

    public boolean hasProduct(Product offeredProduct) {
        return offeredProducts.containsKey(offeredProduct);
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getCredit () {
        return credit;
    }

    public ArrayList < SellLog > getSellLogs () {
        return sellLogs;
    }

    public Discount getDiscountWithIdSpecificSalesperson (String id) {
        for (Discount discount : discounts) {
            if (discount.getDiscountID ().equals ( id ))
                return discount;
        }
        return null;
    }

    public void setProductDiscountState( Product product, Discount discount) {
        offeredProducts.get(product).setDiscount(discount);
    }

    public void addToOfferedProducts(Product offeredProduct, int amount, double price) {
        offeredProducts.put(offeredProduct, new ProductState( amount, price));
    }

    public void removeFromDiscounts(Discount discount) {
        for (Product product : discount.getProducts()) {
            offeredProducts.get(product).removeFromDiscount();
        }
        discounts.remove(discount);
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void addToDiscounts(Discount discount) { //TODO check here
        discounts.add(discount);
    }

    public void removeFromOfferedProducts(Product offeredProduct) {
        offeredProducts.remove(offeredProduct);
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

    public double discountAmount(Product product){
       // return offeredProducts.get(product).discount
        return 1;
    }

    public int getProductAmount(Product product) {
        return offeredProducts.get(product).getAmount();
    }
}

class ProductState {
    public enum State {
        BUILD_IN_PROGRESS, EDIT_IN_PROGRESS, VERIFIED
    }

    private Discount discount;
    private boolean inDiscount;
    private int amount;
    private double price;
    private State productState;

    public ProductState(  int amount, double price) {
        this.inDiscount = false;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        this.inDiscount = true;
    }

    public void removeFromDiscount(){
        discount = null;
        inDiscount = false;
    }

    public boolean isInDiscount() {
        return inDiscount;
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

