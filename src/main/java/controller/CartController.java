package controller;

import model.*;

import java.io.IOException;

public class CartController {

    //private HashMap<Product, ProductStateInCart> products;
    private static CartController single_instance = null;
    private Cart tempCart;


    private CartController()
    {
        tempCart = new Cart();
    }

    public static CartController getInstance() {
        if (single_instance == null)
            single_instance = new CartController();

        return single_instance;
    }

    public void addProduct(Product product, Salesperson salesperson) {
        if (PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            ((Customer)PersonController.getLoggedInPerson()).getCart().addProduct(product,salesperson);
        } else
            tempCart.addProduct(product,salesperson);
    }

    public void setProductCount(Product product, int count,Salesperson salesperson) {
        if(PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            ( (Customer)PersonController.getLoggedInPerson()).getCart().setProductCount(product,salesperson,count);
        }else
            tempCart.setProductCount(product,salesperson,count);
    }

    public void setLoggedInPersonCart(){
        ( (Customer)PersonController.getLoggedInPerson()).setCartAfterLogin(tempCart);
    }

    public double calculateTotalPrice() {
        if(PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            return ( (Customer)PersonController.getLoggedInPerson()).getCart().calculateTotalPrice();
        }
        return tempCart.calculateTotalPrice();
    }

    public void purchase() throws NoLoggedInPersonException, AccountIsNotCustomerException, NotEnoughCreditMoney, IOException {
        if (!PersonController.isThereLoggedInPerson()) {
            throw new NoLoggedInPersonException("You are not logged in."+"\n"+"Please login to continue.");
        } else if (!PersonController.isLoggedInPersonCustomer()) {
            throw new AccountIsNotCustomerException("Please login with customer account.");
        } else {
            Customer customer = (Customer) PersonController.getLoggedInPerson();
            if(!customer.checkCredit(customer.getCart().calculateTotalPrice())){
                throw new NotEnoughCreditMoney("Your balance is not enough"+"\n"+"Please increase your credit.");
            }else {
                Cart.purchase(customer);
            }
        }
    }

    public void manageDiscountCode(DiscountCode discountCode) {
        Customer customer= (Customer)PersonController.getLoggedInPerson();
            customer.useDiscountCode(discountCode);
    }

    public int itemNumber(){
        if(PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            return ( (Customer)PersonController.getLoggedInPerson()).getCart().getProducts().size();
        }
        return tempCart.getProducts().size();
    }

    public Cart getCart() {
        if(PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            return ( (Customer)PersonController.getLoggedInPerson()).getCart();
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

    public static class WrongDiscountCode extends Exception {
        public WrongDiscountCode(String message) {
            super(message);
        }
    }

    public static class NotEnoughCreditMoney extends Exception{
        public NotEnoughCreditMoney(String message) {
            super(message);
        }
    }
}
