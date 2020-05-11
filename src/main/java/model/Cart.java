package model;

import controller.CartController;
import controller.PersonController;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Cart {
    private HashMap<Product, ProductStateInCart> products;
    private double totalPrice;
    private double discountAmount;
    private double totalPriceAfterDiscount;

    public void setProducts(HashMap<Product, ProductStateInCart> products) {
        this.products = products;
    }

    public void addProduct(Product product, Salesperson salesperson){
        products.put(product,new ProductStateInCart(1,salesperson,product));
    }

    public void setProductCount(Product product,int count){
        products.get(product).count+=count;
    }

    public double calculateTotalPrice(){
        totalPrice = 0;
        for (ProductStateInCart value : products.values()) {
            totalPrice+=value.price;
        }
        return totalPrice;
    }

    public void cleanAfterPurchase(){
        products.clear();
        calculateTotalPrice();
        totalPriceAfterDiscount = 0;
    }

    public static void purchase(Customer customer) throws IOException {
        customer.setCredit(customer.getCredit()-customer.getCart().totalPrice);
        //TODO set id and date
        BuyLog buyLog = new BuyLog("", LocalDateTime.now(),customer.getCart().totalPrice,customer.getCart().discountAmount,customer.getCart().getProducts(),false);
        customer.addToBuyLogs(buyLog);
        customer.getCart().purchaseForSalesperson();
        customer.getCart().cleanAfterPurchase();
    }

    public void purchaseForSalesperson() throws IOException {
        for (Product product : products.keySet()) {
            Salesperson salesperson = products.get(product).salesperson;
            int count = products.get(product).count;
            salesperson.addSellLogAndPurchase(new SellLog("",LocalDateTime.now(),salesperson.getProductPrice(product)*count,
                    salesperson.discountAmount(product)*count,product, (Customer)PersonController.getLoggedInPerson()
                    ,true,count));
        }
    }

    public HashMap<Product, ProductStateInCart> getProducts() {
        return products;
    }

    public double getTotalPriceAfterDiscount() {
        return totalPriceAfterDiscount;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPriceAfterDiscount(double totalPriceAfterDiscount) {
        this.totalPriceAfterDiscount = totalPriceAfterDiscount;
    }
}