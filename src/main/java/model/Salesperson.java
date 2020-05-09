package model;


import controller.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private ArrayList<SellLog> sellLogs;
    private HashMap<Product, ProductState> offeredProducts;
    private ArrayList<Discount> discountProducts;
    private ArrayList<DiscountCode> discountCodes;
    private int credit;


    public Salesperson(HashMap<String, String> personInfo) throws IOException {
        super(personInfo);
        sellLogs = new ArrayList<>();
        offeredProducts = new HashMap<>();
        discountProducts = new ArrayList<>();
        discountCodes = new ArrayList<>();
        Database.saveToFile(this, Database.createPath("salesperson", personInfo.get("username")));
    }

    class ProductState {
        boolean inDiscount;
        int amount;
        double price;

        public ProductState(boolean inDiscount, int amount, double price) {
            this.inDiscount = inDiscount;
            this.amount = amount;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public void setInDiscount(boolean inDiscount) {
            this.inDiscount = inDiscount;
        }

        public void setAmount(int amount) {
            this.amount += amount;
        }
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

    public boolean findProduct(Product offeredProduct) {
        return offeredProducts.containsKey(offeredProduct);
    }

    public void addToOfferedProducts(Product offeredProduct, int amount, double price) {
        offeredProducts.put(offeredProduct, new ProductState(false, amount, price));
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

    @Override
    public String toString() {
        return "Salesperson{" +
                "offeredProducts=" + offeredProducts +
                '}';
    }
}

