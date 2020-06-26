package model;

import controller.PersonController;
import controller.ProductController;

public class ProductStateInCart {
    int count;
    String salespersonUsername;
    boolean inDiscount;
    private String productId;

    public ProductStateInCart(int count, Salesperson salesperson, Product product) {
        this.productId = product.getID();
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
        return ((Salesperson)PersonController.getInstance().getPersonByUsername(salespersonUsername)).getDiscountPrice(getProduct());
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

    public Product getProduct() {
        return ProductController.getInstance().getProductById(productId);
    }

    public double getPrice() {
        return ((Salesperson)PersonController.getInstance().getPersonByUsername(salespersonUsername)).getProductPrice(getProduct());
    }

    public void setCount(int count) {
        this.count = count;
    }
}
