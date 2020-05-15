package model;

public class ProductStateInCart {
    int count;
    Salesperson salesperson;
    boolean inDiscount;
    private Product product;

    public ProductStateInCart(int count, Salesperson salesperson, Product product) {
        this.product = product;
        this.count = count;
        this.salesperson = salesperson;
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
        return salesperson.getDiscountPrice(product);
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
        return salesperson;
    }

    public double getPrice() {
        return salesperson.getProductPrice(product);
    }

    public void setCount(int count) {
        this.count = count;
    }
}
