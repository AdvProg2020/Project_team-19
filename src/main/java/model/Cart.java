package model;

import controller.PersonController;
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

    public void addProduct(Product product, Salesperson salesperson) {
        if (products.containsKey(product.getID())) {
            if (products.get(product.getID()).containsKey(salesperson.getUsername())) {
                setProductCount(product, salesperson,  1);
            } else {
                products.get(product.getID()).put(salesperson.getUsername(), new ProductStateInCart(1, salesperson, product));
            }
        } else {
            HashMap<String , ProductStateInCart> temp = new HashMap<>();
            temp.put(salesperson.getUsername(), new ProductStateInCart(1, salesperson, product));
            products.put(product.getID(), temp);
        }
    }

    public void setCartAfterLogIn(Cart cart) {
        for (String productId : cart.products.keySet()) {
            Product product = ProductController.getInstance().getProductById(productId);
            for (String sellerName : cart.products.get(productId).keySet()) {
                Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerName);
                if (products.containsKey(productId)) {
                    if (cart.products.get(productId).containsKey(sellerName)) {
                        setProductCount(product, salesperson, cart.products.get(productId).get(sellerName).count + products.get(productId).get(sellerName).count);
                    } else {
                        products.get(productId).put(sellerName, new ProductStateInCart(cart.products.get(productId).get(sellerName).count, salesperson, product));
                    }
                } else {
                    HashMap<String , ProductStateInCart> temp = new HashMap<>();
                    temp.put(sellerName, new ProductStateInCart(cart.products.get(productId).get(sellerName).count, salesperson, product));
                    products.put(productId, temp);
                }
            }
        }
    }

    public void useDiscountCode(DiscountCode discountCode) {
        totalPriceAfterDiscountCode = discountCode.getPriceAfterDiscountCode(calculateTotalPrice());
    }

    public void setProductCount(Product product, Salesperson salesperson, int count) {
        products.get(product.getID()).get(salesperson.getUsername()).count += count;
        if (products.get(product.getID()).get(salesperson.getUsername()).getCount()==0){
            products.get(product.getID()).remove(salesperson.getUsername());
            if (products.get(product.getID()).size()==0)
                products.remove(product.getID());
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
        BuyLog buyLog = new BuyLog(LocalDateTime.now(), customer.getCart().totalPrice, customer.getCart().getTotalPriceAfterDiscountCode(), customer.getCart().getProducts(), false);
        customer.addToBuyLogs(buyLog);
        customer.getCart().purchaseForSalesperson();
        customer.getCart().cleanAfterPurchase();
    }

    public void purchaseForSalesperson(){
        for (String productId : products.keySet()) {
            Product product = ProductController.getInstance().getProductById(productId);
            for (String sellerName : products.get(productId).keySet()) {
                Salesperson salesperson = (Salesperson)PersonController.getInstance().getPersonByUsername(sellerName);
                int count = products.get(productId).get(sellerName).count;
                salesperson.addSellLogAndPurchase(new SellLog(LocalDateTime.now(),
                        salesperson.getProductPrice(product) * count,
                        salesperson.discountAmount(product) * count, product,
                        (Customer) PersonController.getInstance().getLoggedInPerson(), true, count));

            }
        }
    }

    public HashMap<Product, HashMap<Salesperson, ProductStateInCart>> getProducts() {
        HashMap<Product, HashMap<Salesperson, ProductStateInCart>> productsInCart = new HashMap<>();
        for (String productId : products.keySet()) {
            Product product = ProductController.getInstance().getProductById(productId);
            productsInCart.put(product, new HashMap<>());
            for (String sellerName : products.get(productId).keySet()) {
                Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerName);
                ProductStateInCart productStateInCart = this.products.get(productId).get(sellerName);
                productsInCart.get(product).put(salesperson, productStateInCart);
            }
        }
        return productsInCart;
    }

    public double getTotalPriceAfterDiscountCode() {
        return totalPriceAfterDiscountCode;
    }

    public void setTotalPriceAfterDiscountCode(double totalPriceAfterDiscountCode) {
        this.totalPriceAfterDiscountCode = totalPriceAfterDiscountCode;
    }
}