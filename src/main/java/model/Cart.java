package model;

import bank.BankAPI;
import controller.ProductController;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Cart {
    private HashMap<String, HashMap<String, ProductStateInCart>> products;
    private double totalPrice;
    private double discountAmount;
    private double totalPriceAfterDiscountCode;

    public void setProducts(HashMap<Product, HashMap<Salesperson, ProductStateInCart>> products) {
        for (Product product : products.keySet()) {
            this.products.put(product.getID(),new HashMap<>());
            for (Salesperson salesperson : products.get(product).keySet()) {
                this.products.get(product.getID()).put(salesperson.getUsername(), products.get(product).get(salesperson));
            }
        }
    }

    public void addProduct(String product, String salesperson,double price,double priceAfterDiscount) {
        if (products.containsKey(product)) {
            if (products.get(product).containsKey(salesperson)) {
                setProductCount(product, salesperson,  1);
            } else {
                products.get(product).put(salesperson, new ProductStateInCart(1, salesperson, product,price,priceAfterDiscount, ProductController.getInstance().getProductById(product).getName()));
            }
        } else {
            HashMap<String , ProductStateInCart> temp = new HashMap<>();
            temp.put(salesperson, new ProductStateInCart(1, salesperson, product,price,priceAfterDiscount,ProductController.getInstance().getProductById(product).getName()));
            products.put(product, temp);
        }
    }


    public void useDiscountCode(DiscountCode discountCode) {
        totalPriceAfterDiscountCode = discountCode.getPriceAfterDiscountCode(calculateTotalPrice());
    }

    public void setProductCount(String product, String salesperson, int count) {
        products.get(product).get(salesperson).count += count;
        if (products.get(product).get(salesperson).getCount()==0){
            products.get(product).remove(salesperson);
            if (products.get(product).size()==0)
                products.remove(product);
        }
    }

    public Cart() {
        products = new HashMap<>();
    }

    public double calculateTotalPrice() {
        totalPrice = 0;
        for (String product : products.keySet()) {
            for (String salesperson : products.get(product).keySet()) {
                totalPrice += products.get(product).get(salesperson).getTotalPrice();
            }
        }
        totalPriceAfterDiscountCode = totalPrice;
        return totalPrice;
    }

    public void cleanAfterPurchase() {
        products.clear();
        calculateTotalPrice();
        totalPriceAfterDiscountCode = 0;
    }

    public static void purchase(Customer customer) {
        customer.setCredit(customer.getCredit() - customer.getCart().totalPrice);
        customer.getCart().cleanAfterPurchase();
    }

    public static void purchaseBank(Customer customer) {
        customer.getCart().cleanAfterPurchase();
    }


    public HashMap<String, HashMap<String, ProductStateInCart>> getProducts() {
        return products;
    }

    public HashMap<String , HashMap<String, ProductStateInCart>> getProductsForClient() {
        return products;
    }

    public double getTotalPriceAfterDiscountCode() {
        return totalPriceAfterDiscountCode;
    }

    public void setTotalPriceAfterDiscountCode(double totalPriceAfterDiscountCode) {
        this.totalPriceAfterDiscountCode = totalPriceAfterDiscountCode;
    }
}