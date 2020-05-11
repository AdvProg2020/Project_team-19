package model;

import controller.ProductController;

public class ProductStateInCart {
    int count;
    double price;
    Salesperson salesperson;
    boolean inDiscount;
    double priceAfterDiscount;
    public ProductStateInCart(int count, Salesperson salesperson,Product product) {
        this.count = count;
        this.salesperson = salesperson;
        price = salesperson.getProductPrice(product);
        if(salesperson.getOfferedProducts().get(product).isInDiscount()){
            inDiscount = true;
            priceAfterDiscount = salesperson.getDiscountPrice(product);
        }
    }

    public ProductStateInCart(ProductStateInCart productStateInCart){
        count = productStateInCart.count;
        price = productStateInCart.price;
        salesperson = productStateInCart.salesperson;
        if (productStateInCart.inDiscount)
        {
            inDiscount = true;
            priceAfterDiscount = productStateInCart.priceAfterDiscount;
        }
    }

    public boolean isInDiscount() {
        return inDiscount;
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public double getFinalPrice(){
        if(inDiscount)
            return count * priceAfterDiscount;
        return count * price;
    }

    public int getCount() {
        return count;
    }

    public Salesperson getSalesperson() {
        return salesperson;
    }

    public double getPrice() {
        return price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
