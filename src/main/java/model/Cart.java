package model;

import controller.CartController;

import java.util.HashMap;

public class Cart {
    private HashMap<Product, ProductState> products;
    private double totalPrice;
    private double totalPriceAfterDiscount;

    public class ProductState{
        int count;
        double price;
        Salesperson salesperson;
        public ProductState(int count, Salesperson salesperson,Product product) {
            this.count = count;
            this.salesperson = salesperson;
            price = salesperson.getProductPrice(product);
        }
    }

    public void addProduct(Product product,Salesperson salesperson){
        products.put(product,new ProductState(1,salesperson,product));
    }

    public void setProductCount(Product product,int count){
        products.get(product).count+=count;
    }

    public void calculateTotalPrice(){
        totalPrice =0;
        for (ProductState value : products.values()) {
            totalPrice+=value.price;
        }
    }
}