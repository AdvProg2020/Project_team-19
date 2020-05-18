package model;

import controller.PersonController;

public class ProductStateInCart {
    int count;
    String salespersonUsername;
    boolean inDiscount;
    private Product product;

    public ProductStateInCart(int count, Salesperson salesperson, Product product) {
        this.product = product;
        this.count = count;
        this.salespersonUsername = salesperson.getUsername();
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
        return ((Salesperson)PersonController.getInstance().getPersonByUsername(salespersonUsername)).getDiscountPrice(product);
    }

    public double getFinalPrice() {
        if (inDiscount)
            return count * getPriceAfterDiscount();
        return count * getPrice();
    }

    public int getCount() {
        return count;
    }

    public Salesperson getSalesperson() {
        return (Salesperson)PersonController.getInstance().getPersonByUsername(salespersonUsername);
    }

    public double getPrice() {
        return ((Salesperson)PersonController.getInstance().getPersonByUsername(salespersonUsername)).getProductPrice(product);
    }

    public void setCount(int count) {
        this.count = count;
    }
}
