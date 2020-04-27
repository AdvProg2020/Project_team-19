package controller;
import model.*;

import java.util.HashMap;

public class CartController {
    private Customer customer;
    private static CartController single_instance = null;
    private double totalPrice;
    private double totalPriceAfterDiscount;

    private CartController()
    {

    }

    public static CartController getInstance()
    {
        if (single_instance == null)
            single_instance = new CartController();

        return single_instance;
    }


    public void purchase() throws NoLoggedInPersonException, AccountIsNotCustomerException{
        if (!PersonController.isThereLoggedInPerson()){
            throw new NoLoggedInPersonException();
        }else if(!PersonController.isLoggedInPersonCustomer()){
            throw new AccountIsNotCustomerException();
        }else {
            customer = (Customer)PersonController.getLoggedInPerson();
        }
    }

    public void manageDiscountCode(String discountCode) throws  WrongDiscountCode{
        if(!customer.isThereDiscountCodeByCode(discountCode)){
            throw new WrongDiscountCode();
        }else {
            DiscountCode thisDiscountCode = customer.findDiscountCodeByCode(discountCode);
            customer.getCart().calculateTotalPrice();
            totalPriceAfterDiscount = totalPrice*(thisDiscountCode.getDiscountPercentage()) ;
            if(totalPriceAfterDiscount>thisDiscountCode.getMaxDiscount()){
                totalPriceAfterDiscount = thisDiscountCode.getMaxDiscount();
            }
            customer.useDiscountCode(thisDiscountCode);
        }
    }

    public static class NoLoggedInPersonException extends Exception{
        String message;
    }

    public static class AccountIsNotCustomerException extends Exception{
        String message;
    }

    public static class WrongDiscountCode extends Exception{
        String message;
    }
}
