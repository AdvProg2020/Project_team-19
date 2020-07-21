package controller;

import model.*;

import java.io.IOException;
import java.time.LocalDateTime;

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
            customer.getCart().addProduct(product, salesperson);
            saveToFile(customer, createPath("customers", customer.getUsername()));
    }

    public void setProductCount(Product product, int count, Salesperson salesperson,Customer customer) {
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
            saveToFile(customer, createPath("customers", customer.getUsername()));
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
