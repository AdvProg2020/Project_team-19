package controller;

import model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static controller.Database.*;

public class CartController {

    private static CartController single_instance = null;


    private CartController() {

    }

    public static CartController getInstance() {
        if (single_instance == null)
            single_instance = new CartController();

        return single_instance;
    }

    public void removeProduct(Product product){
        for (Person person : PersonController.getInstance().filterByRoll(Customer.class)) {
            Customer customer = (Customer) person;
            customer.getCart().getProducts().remove(product);
            Database.saveToFile(customer,Database.createPath("customers",customer.getUsername()));
        }
    }

    public void removeProduct(Product product,Salesperson salesperson){
        for (Person person : PersonController.getInstance().filterByRoll(Customer.class)) {
            Customer customer = (Customer) person;
            if(customer.getCart().getProducts().containsKey(product)){
                customer.getCart().getProducts().get(product).remove(salesperson);
                Database.saveToFile(customer,Database.createPath("customers",customer.getUsername()));
            }
        }
    }

    public void removeSeller(Salesperson salesperson){
        for (Product product : salesperson.getOfferedProducts().keySet()) {
            removeProduct(product,salesperson);
        }
    }

    public void addProduct(Product product, Salesperson salesperson,Customer customer) {
            customer.getCart().addProduct(product.getID(), salesperson.getUsername(),salesperson.getProductPrice(product),salesperson.getDiscountPrice(product));
            saveToFile(customer, createPath("customers", customer.getUsername()));
    }

    public void setProductCount(String product, int count, String salesperson,Customer customer) {
            customer.getCart().setProductCount(product, salesperson, count);
            saveToFile(customer, createPath("customers", customer.getUsername()));

    }

    public double calculateTotalPrice(Customer customer) {
            return (customer.getCart().calculateTotalPrice());

    }



    public void purchaseWallet(Customer customer) throws NotEnoughCreditMoney {
        if (!customer.checkCredit(customer.getCart().calculateTotalPrice())) {
            throw new NotEnoughCreditMoney("Your balance is not enough" + "\n" + "Please increase your credit.");
        } else {
            if(customer.getCart().calculateTotalPrice()>1000000){
                System.out.println("We got that you are rich. Take this discount and show off less.");
                discountCodeForGoodCustomer(customer);
            }
            Cart.purchase(customer);
            purchaseForSalesperson(customer);
            saveToFile(customer, createPath("customers", customer.getUsername()));
        }

    }

    public void purchaseBank(Customer customer, String bankUsername, String bankPassword) throws Exception {
        String response;
        if (!(response = WalletController.getInstance().moveFromCustomerToBank(customer.getCart().calculateTotalPrice(), bankUsername, bankPassword, customer.getWallet().getBankId())).equals("successfully paid!")) {
            throw new Exception(response);
        } else {
            if(customer.getCart().calculateTotalPrice()>1000000){
                System.out.println("We got that you are rich. Take this discount and show off less.");
                discountCodeForGoodCustomer(customer);
            }
            Cart.purchaseBank(customer);
            purchaseForSalesperson(customer);
            saveToFile(customer, createPath("customers", customer.getUsername()));
        }

    }

    public HashMap<Product, HashMap<Salesperson, ProductStateInCart>> getProducts(Customer customer) {
        HashMap<String, HashMap<String, ProductStateInCart>> products = customer.getCart().getProducts();
        HashMap<Product, HashMap<Salesperson, ProductStateInCart>> productsInCart = new HashMap<>();
        for (String productId : products.keySet()) {
            Product product = ProductController.getInstance().getProductById(productId);
            productsInCart.put(product, new HashMap<>());
            for (String sellerName : products.get(productId).keySet()) {
                Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerName);
                ProductStateInCart productStateInCart = products.get(productId).get(sellerName);
                productsInCart.get(product).put(salesperson, productStateInCart);
            }
        }
        return productsInCart;
    }

    public void purchaseForSalesperson(Customer customer){
        HashMap<String, HashMap<String, ProductStateInCart>> products = customer.getCart().getProducts();
        for (String productId : products.keySet()) {
            Product product = ProductController.getInstance().getProductById(productId);
            for (String sellerName : products.get(productId).keySet()) {
                Salesperson salesperson = (Salesperson)PersonController.getInstance().getPersonByUsername(sellerName);
                int count = products.get(productId).get(sellerName).getCount();
                double deliverAmount = count * salesperson.getProductPrice(product) * (WalletController.WAGE / 100);
                WalletController.getInstance().increaseShopBalance(deliverAmount);
                salesperson.addSellLogAndPurchase(new SellLog(LocalDateTime.now(),
                        salesperson.getProductPrice(product) * count * (1 - WalletController.WAGE / 100),
                        salesperson.discountAmount(product) * count, product,
                        customer, true, count));

            }
        }
        BuyLog buyLog = new BuyLog(LocalDateTime.now(), customer.getCart().calculateTotalPrice(), customer.getCart().getTotalPriceAfterDiscountCode(), getProducts(customer), false);
        customer.addToBuyLogs(buyLog);
    }

    public void setCartAfterLogIn(Cart cart,Customer customer) {
        double price;
        double priceAfterDiscount;
        HashMap<String, HashMap<String, ProductStateInCart>> products = customer.getCart().getProducts();
        for (String productId : cart.getProducts().keySet()) {
            for (String sellerName : cart.getProducts().get(productId).keySet()) {
                Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerName);
                Product product = ProductController.getInstance().getProductById(productId);
                price = salesperson.getProductPrice(product);
                priceAfterDiscount = salesperson.getDiscountPrice(product);
                if (products.containsKey(productId)) {
                    if (cart.getProducts().get(productId).containsKey(sellerName)) {
                        setProductCount(productId, cart.getProducts().get(productId).get(sellerName).getCount() + products.get(productId).get(sellerName).getCount(), sellerName,customer);
                    } else {
                        products.get(productId).put(sellerName, new ProductStateInCart(cart.getProducts().get(productId).get(sellerName).getCount(), sellerName, productId,price,priceAfterDiscount,product.getName()));
                    }
                } else {
                    HashMap<String , ProductStateInCart> temp = new HashMap<>();
                    temp.put(sellerName, new ProductStateInCart(cart.getProducts().get(productId).get(sellerName).getCount(), sellerName, productId,price,priceAfterDiscount,product.getName()));
                    products.put(productId, temp);
                }
            }
        }
    }


    public void discountCodeForGoodCustomer(Customer customer){
        DiscountCode discountCode = new DiscountCode(LocalDateTime.MIN,LocalDateTime.MAX,10,Double.POSITIVE_INFINITY,1);
        manageDiscountCode(discountCode,customer);
        DiscountCodeController.getInstance().removeDiscountCode(discountCode);
    }


    public void manageDiscountCode(DiscountCode discountCode,Customer customer) {
        customer.useDiscountCode(discountCode);
        customer.getCart().setTotalPriceAfterDiscountCode(discountCode.getPriceAfterDiscountCode(customer.getCart().calculateTotalPrice()));
        saveToFile(customer, createPath("customers", customer.getUsername()));
    }

    public int itemNumber(Customer customer) {
            return customer.getCart().getProducts().size();

    }


    public Cart getCart(Customer customer) {
            return customer.getCart();

    }

    public static class NoLoggedInPersonException extends Exception {
        public NoLoggedInPersonException(String message) {
            super(message);
        }
    }

    public static class AccountIsNotCustomerException extends Exception {
        public AccountIsNotCustomerException(String message) {
            super(message);
        }
    }


    public static class NotEnoughCreditMoney extends Exception {
        public NotEnoughCreditMoney(String message) {
            super(message);
        }
    }
}
