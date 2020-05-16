package controller;

import model.*;

import java.io.IOException;

import static controller.Database.*;

public class CartController {

    private static CartController single_instance = null;
    private Cart tempCart;


    private CartController() {
        tempCart = new Cart();
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
        }
    }

    public void removeProduct(Product product,Salesperson salesperson){
        for (Person person : PersonController.getInstance().filterByRoll(Customer.class)) {
            Customer customer = (Customer) person;
            if(customer.getCart().getProducts().containsKey(product)){
                customer.getCart().getProducts().get(product).remove(salesperson);
            }
        }
    }

    public void addProduct(Product product, Salesperson salesperson) {
        if (PersonController.getInstance().isThereLoggedInPerson() && PersonController.getInstance().isLoggedInPersonCustomer()) {
            Customer customer = (Customer) PersonController.getInstance().getLoggedInPerson();
            customer.getCart().addProduct(product, salesperson);
            saveToFile(customer, createPath("customer", customer.getUsername()));
        } else
            tempCart.addProduct(product, salesperson);
    }

    public void setProductCount(Product product, int count, Salesperson salesperson) {
        if (PersonController.getInstance().isThereLoggedInPerson() && PersonController.getInstance().isLoggedInPersonCustomer()) {
            Customer customer = (Customer) PersonController.getInstance().getLoggedInPerson();
            customer.getCart().setProductCount(product, salesperson, count);
            saveToFile(customer, createPath("customer", customer.getUsername()));
        } else
            tempCart.setProductCount(product, salesperson, count);
    }

    public void setLoggedInPersonCart() {
        ((Customer) PersonController.getInstance().getLoggedInPerson()).setCartAfterLogin(tempCart);
    }

    public double calculateTotalPrice() {
        if (PersonController.getInstance().isThereLoggedInPerson() && PersonController.getInstance().isLoggedInPersonCustomer()) {
            return ((Customer) PersonController.getInstance().getLoggedInPerson()).getCart().calculateTotalPrice();
        }
        return tempCart.calculateTotalPrice();
    }

    public void purchase() throws NoLoggedInPersonException, AccountIsNotCustomerException, NotEnoughCreditMoney, IOException {
        if (!PersonController.getInstance().isThereLoggedInPerson()) {
            throw new NoLoggedInPersonException("You are not logged in." + "\n" + "Please login to continue.");
        } else if (!PersonController.getInstance().isLoggedInPersonCustomer()) {
            throw new AccountIsNotCustomerException("Please login with customer account.");
        } else {
            Customer customer = (Customer) PersonController.getInstance().getLoggedInPerson();
            if (!customer.checkCredit(customer.getCart().calculateTotalPrice())) {
                throw new NotEnoughCreditMoney("Your balance is not enough" + "\n" + "Please increase your credit.");
            } else {
                Cart.purchase(customer);
                saveToFile(customer, createPath("customer", customer.getUsername()));
            }
        }
    }

    public void removeProduct(Product product){
        for (Person person : PersonController.getInstance().filterByRoll(Customer.class)) {
            Customer customer = (Customer) person;
            customer.getCart().getProducts().remove(product);
        }
    }

    public void removeProduct(Product product,Salesperson salesperson){
        for (Person person : PersonController.getInstance().filterByRoll(Customer.class)) {
            Customer customer = (Customer) person;
            if(customer.getCart().getProducts().containsKey(product)){
                customer.getCart().getProducts().get(product).remove(salesperson);
            }
        }
    }

    public void manageDiscountCode(DiscountCode discountCode) {
        Customer customer = (Customer) PersonController.getInstance().getLoggedInPerson();
        customer.useDiscountCode(discountCode);
        saveToFile(customer, createPath("customer", customer.getUsername()));
    }

    public int itemNumber() {
        if (PersonController.getInstance().isThereLoggedInPerson() && PersonController.getInstance().isLoggedInPersonCustomer()) {
            return ((Customer) PersonController.getInstance().getLoggedInPerson()).getCart().getProducts().size();
        }
        return tempCart.getProducts().size();
    }

    public Cart getCart() {
        if (PersonController.getInstance().isThereLoggedInPerson() && PersonController.getInstance().isLoggedInPersonCustomer()) {
            return ((Customer) PersonController.getInstance().getLoggedInPerson()).getCart();
        }
        return tempCart;
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
