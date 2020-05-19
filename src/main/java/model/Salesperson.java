package model;


import controller.Database;
import controller.DiscountController;
import controller.ProductController;

import java.util.ArrayList;
import java.util.HashMap;

public class Salesperson extends Person {
    private ArrayList<SellLog> sellLogs;
    private HashMap<String, ProductState> offeredProducts;
    private ArrayList<Discount> discounts;
    private double credit;


    public Salesperson(HashMap<String, String> personInfo) {
        super(personInfo);
        sellLogs = new ArrayList<>();
        offeredProducts = new HashMap<>();
        discounts = new ArrayList<>();
        Database.saveToFile(this, Database.createPath("salespersons", personInfo.get("username")));
    }


    public void addSellLogAndPurchase(SellLog sellLog) {
        offeredProducts.get(sellLog.getProduct().getID()).setAmount(offeredProducts.get(sellLog.getProduct().getID()).getAmount() - sellLog.getCount());
        setCredit(credit + sellLog.getDeliveredAmount());
        sellLogs.add(sellLog);
    }

    public double getDiscountPrice(Product product) {
        return offeredProducts.get(product.getID()).getDiscount().getPriceAfterDiscount(offeredProducts.get(product.getID()).getPrice());
    }

    public void setProductState(Product product, ProductState.State state) {
        offeredProducts.get(product.getID()).setState(state);
    }

    public boolean isInDiscount(Product product) {
        return offeredProducts.get(product.getID()).isInDiscount();
    }

    public boolean hasProduct(Product offeredProduct) {
        return offeredProducts.containsKey(offeredProduct.getID());
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<SellLog> getSellLogs() {
        return sellLogs;
    }

    public Discount getDiscountWithIdSpecificSalesperson(String id) {
        for (Discount discount : discounts) {
            if (discount.getDiscountID().equals(id))
                return discount;
        }
        return null;
    }

    public void setProductDiscountState(Product product, Discount discount) {
        offeredProducts.get(product.getID()).setDiscount(discount);
    }

    public void addToOfferedProducts(Product offeredProduct, int amount, double price) {
        offeredProducts.put(offeredProduct.getID(), new ProductState(amount, price));
    }

    public void removeFromDiscounts(Discount discount) {
        for (Product product : discount.getProducts()) {
            offeredProducts.get(product.getID()).removeFromDiscount();
        }
        discounts.remove(discount);
    }

    public void setProductsDiscountState(Discount discount, boolean state) {
        for (Product product : discount.getProducts()) {
            offeredProducts.get(product.getID()).setInDiscount(state);
        }
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void addToDiscounts(Discount discount) { //TODO check here
        discounts.add(discount);
    }

    public void removeFromOfferedProducts(Product offeredProduct) {
        if (offeredProducts.get(offeredProduct.getID()).isInDiscount()) {
            offeredProducts.get(offeredProduct.getID()).getDiscount().removeProduct(offeredProduct);
        }
        offeredProducts.remove(offeredProduct.getID());
    }

    public HashMap<Product, ProductState> getOfferedProducts() {
        HashMap<Product, ProductState> products = new HashMap<>();
        for (String id : offeredProducts.keySet()) {
            products.put(ProductController.getInstance().getProductById(id), offeredProducts.get(id));
        }
        return products;
    }

    public double getProductPrice(Product product) {
        return offeredProducts.get(product.getID()).getPrice();
    }

    public void setProductPrice(Product product, double price) {
        offeredProducts.get(product.getID()).setPrice(price);
    }

    public void setProductAmount(Product product, int amount) {
        offeredProducts.get(product.getID()).setAmount(amount);
    }

    public void editProduct(Product product, double price, int amount) {
        setProductPrice(product, price);
        setProductAmount(product, amount);
    }

    public void setBuildInProgress(Product product) {
        offeredProducts.get(product.getID()).setState(ProductState.State.BUILD_IN_PROGRESS);
    }

    public void setEditInProgress(Product product) {
        offeredProducts.get(product.getID()).setState(ProductState.State.EDIT_IN_PROGRESS);
    }

    public void setVerified(Product product) {
        offeredProducts.get(product.getID()).setState(ProductState.State.VERIFIED);
    }

    public double discountAmount(Product product) {
        if (offeredProducts.get(product.getID()).isInDiscount()) { //toDo get DiscountFromALL null mide
            return offeredProducts.get(product.getID()).getDiscount().getDiscountPercentage() * getProductPrice(product);
        }
        return 0;
    }

    public int getProductAmount(Product product) {
        return offeredProducts.get(product.getID()).getAmount();
    }



    public ProductState.State getProductState(Product product) {
        return offeredProducts.get(product.getID()).getProductState();
    }

    public String getProductStateForShow (Product product) {
        return offeredProducts.get ( product.getID () ).getProductState ().label;
    }

}

class ProductState {
    public enum State {
        BUILD_IN_PROGRESS ("Build In Progress"),
        EDIT_IN_PROGRESS ("Edit In Progress"),
        VERIFIED ("Verified");

        public final String label;

        State (String label) {
            this.label = label;
        }
    }

    private String discountId;
    private boolean inDiscount;
    private int amount;
    private double price;
    private State productState;

    public ProductState(int amount, double price) {
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
        return DiscountController.getInstance().getDiscountByIdFromAll(discountId);
    }

    public void setDiscount(Discount discount) {
        this.discountId = discount.getDiscountID();
        this.inDiscount = true;
    }

    public void removeFromDiscount() {
        discountId = null;
        inDiscount = false;
    }

    public void setInDiscount(boolean inDiscount) {
        this.inDiscount = inDiscount;
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

    public void setProductState(State productState) {
        this.productState = productState;
    }


}

