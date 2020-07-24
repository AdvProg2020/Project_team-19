package model;

import controller.PersonController;
import controller.ProductController;

public class ProductStateInCart {
    int count;
    String salespersonUsername;
    boolean inDiscount;
    private String productId;
    double price;
    double priceAfterDiscount;
    String productName;

    public ProductStateInCart(int count, String salesperson,String product,double price,double priceAfterDiscount,String productName) {
        this.productId = product;
        this.count = count;
        this.salespersonUsername = salesperson;
        this.price = price;
        this.priceAfterDiscount = priceAfterDiscount;
        this.productName = productName;
    }

    public double getTotalPrice() {
        if (inDiscount) {
            return getPriceAfterDiscount() * count;
        } else return getPrice() * count;
    }

    public boolean isInDiscount() {
        return inDiscount;
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public String getProductName() {
        return productName;
    }

    public double getFinalPrice() {
        if (inDiscount)
            return count * getPriceAfterDiscount();
        return count * getPrice();
    }

    public int getCount() {
        return count;
    }

    public String getSalesperson() {
        return salespersonUsername;
    }

    public String getProduct() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
