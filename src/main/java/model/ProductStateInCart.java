package model;

public class ProductStateInCart {
    int count;
    double price;
    Salesperson salesperson;
    public ProductStateInCart(int count, Salesperson salesperson,Product product) {
        this.count = count;
        this.salesperson = salesperson;
        price = salesperson.getProductPrice(product);
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
