package model;

import controller.PersonController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Cart {
    private HashMap<Product,HashMap<Salesperson,ProductStateInCart> > products;
    private double totalPrice;
    private double discountAmount;
    private double totalPriceAfterDiscountCode;

    public void setProducts(HashMap<Product,HashMap<Salesperson,ProductStateInCart> > products) {
        this.products = products;
    }

    public void addProduct(Product product, Salesperson salesperson){
        if(products.containsKey(product)){
            if(products.containsKey(salesperson)){
                setProductCount(product,salesperson,products.get(product).get(salesperson).count+1);
            }else {
                products.get(product).put(salesperson,new ProductStateInCart(1,salesperson,product));
            }        }else {
            HashMap<Salesperson,ProductStateInCart> temp = new HashMap<>();
            temp.put(salesperson,new ProductStateInCart(1,salesperson,product));
            products.put(product,temp);
        }
    }

    public void setCartAfterLogIn(Cart cart){
        for (Product product : cart.products.keySet()) {
            for (Salesperson salesperson : cart.products.get(product).keySet()) {
                if(products.containsKey(product)){
                    if(cart.products.containsKey(salesperson)){
                        setProductCount(product,salesperson,cart.products.get(product).get(salesperson).count+products.get(product).get(salesperson).count);
                    }else {
                        products.get(product).put(salesperson,new ProductStateInCart(cart.products.get(product).get(salesperson).count,salesperson,product));
                    }
                }
                else {
                    HashMap<Salesperson,ProductStateInCart> temp = new HashMap<>();
                    temp.put(salesperson,new ProductStateInCart(cart.products.get(product).get(salesperson).count,salesperson,product));
                    products.put(product,temp);
                }
            }

        }
    }

    public void useDiscountCode(DiscountCode discountCode){
        totalPriceAfterDiscountCode = discountCode.getPriceAfterDiscountCode(calculateTotalPrice());
    }

    public void setProductCount(Product product,Salesperson salesperson,int count){
        products.get(product).get(salesperson).count+=count;
    }

    public Cart() {
        products = new HashMap<Product,HashMap<Salesperson, ProductStateInCart>>();
    }

    public double calculateTotalPrice(){
        totalPrice =0;
        for (Product product : products.keySet()) {
            for (Salesperson salesperson : products.get(product).keySet()) {
                totalPrice += products.get(product).get(salesperson).getTotalPrice();
            }
        }
        return totalPrice;
    }

    public void cleanAfterPurchase(){
        products.clear();
        calculateTotalPrice();
        totalPriceAfterDiscountCode = 0;
    }

    public static void purchase(Customer customer) throws IOException {
        customer.setCredit(customer.getCredit()-customer.getCart().totalPrice);
        BuyLog buyLog = new BuyLog( LocalDateTime.now(),customer.getCart().totalPrice,customer.getCart().discountAmount,customer.getCart().getProducts(),false);
        customer.addToBuyLogs(buyLog);
        customer.getCart().purchaseForSalesperson();
        customer.getCart().cleanAfterPurchase();
    }

    public void purchaseForSalesperson() throws IOException {
        for (Product product : products.keySet()) {
                           for (Salesperson salesperson : products.get(product).keySet()) {
            int count = products.get(product).get(salesperson).count;
            salesperson.addSellLogAndPurchase(new SellLog(LocalDateTime.now(),salesperson.getProductPrice(product)*count,salesperson.discountAmount(product)*count,product, (Customer)PersonController.getLoggedInPerson(),true,count));

                }
        }
    }

    public HashMap<Product,HashMap<Salesperson,ProductStateInCart> > getProducts() {
        return products;
    }

    public double getTotalPriceAfterDiscountCode() {
        return totalPriceAfterDiscountCode;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPriceAfterDiscountCode(double totalPriceAfterDiscountCode) {
        this.totalPriceAfterDiscountCode = totalPriceAfterDiscountCode;
    }

}