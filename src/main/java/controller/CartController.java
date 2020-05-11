package controller;

import model.*;

import java.util.HashMap;

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
        if(PersonController.isThereLoggedInPerson()){
            ( (Customer)PersonController.getLoggedInPerson()).getCart().addProduct(product,salesperson);
        }else
            tempCart.addProduct(product,salesperson);
    }

    public void setProductCount(Product product, int count) {
        if(PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            ( (Customer)PersonController.getLoggedInPerson()).getCart().setProductCount(product,count);
        }else
            tempCart.setProductCount(product,count);
    }

    public void setLoggedInPersonCart(){
       // ( (Customer)PersonController.getLoggedInPerson()).getCart().setProducts(products);
    }

    public double calculateTotalPrice() {
        if(PersonController.isThereLoggedInPerson()&&PersonController.isLoggedInPersonCustomer()){
            return ( (Customer)PersonController.getLoggedInPerson()).getCart().calculateTotalPrice();
        }
        return tempCart.calculateTotalPrice();
    }

    public void purchase() throws NoLoggedInPersonException, AccountIsNotCustomerException,NotEnoughCreditMoney {
        if (!PersonController.isThereLoggedInPerson()) {
            throw new NoLoggedInPersonException();
        } else if (!PersonController.isLoggedInPersonCustomer()) {
            throw new AccountIsNotCustomerException();
        } else {
            Customer customer = (Customer) PersonController.getLoggedInPerson();
            if(customer.checkCredit(customer.getCart().calculateTotalPrice())){
                throw new NotEnoughCreditMoney();
            }
        }
    }

    public void manageDiscountCode(String discountCode) throws WrongDiscountCode {
        Customer customer= (Customer)PersonController.getLoggedInPerson();
        if (!customer.isThereDiscountCodeByCode(discountCode)) {
            throw new WrongDiscountCode();
        } else {
            DiscountCode thisDiscountCode = customer.findDiscountCodeByCode(discountCode);
            double tempPrice = customer.getCart().calculateTotalPrice();
             double tempTotalPriceAfterDiscount = tempPrice * (thisDiscountCode.getDiscountPercentage());
            if (tempTotalPriceAfterDiscount > thisDiscountCode.getMaxDiscount()) {
                tempTotalPriceAfterDiscount = thisDiscountCode.getMaxDiscount();
            }
            customer.getCart().setTotalPriceAfterDiscount(tempTotalPriceAfterDiscount);
            customer.useDiscountCode(thisDiscountCode);
        }
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
        String message;
    }

    public static class AccountIsNotCustomerException extends Exception {
        String message;
    }

    public static class WrongDiscountCode extends Exception {
        String message;
    }

    public static class NotEnoughCreditMoney extends Exception{
        String message;
    }
}
