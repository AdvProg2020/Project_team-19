package controller;

import model.*;

import java.util.HashMap;

public class CartController {

    private HashMap<Product, ProductStateInCart> products;
    private static CartController single_instance = null;
    private double totalPrice;
    private double totalPriceAfterDiscount;
    private ProductStateInCart productState;


    private CartController() {
        products = new HashMap<Product, ProductStateInCart>();
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
        products.put(product, new ProductStateInCart(1, salesperson, product));
    }

    public void setProductCount(Product product, int count) {
        if(PersonController.isThereLoggedInPerson()){
            ( (Customer)PersonController.getLoggedInPerson()).getCart().setProductCount(product,count);
        }else
            products.get(product).setCount(products.get(product).getCount() + count);
    }

    public void setLoggedInPerson(){
        ( (Customer)PersonController.getLoggedInPerson()).getCart().setProducts(products);
    }

    public void calculateTotalPrice() {
        totalPrice = 0;
        for (ProductStateInCart value : products.values()) {
            totalPrice += value.getPrice();
        }
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
