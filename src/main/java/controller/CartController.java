package controller;
import model.*;

import java.util.HashMap;

public class CartController {
    private Customer customer;
    private HashMap<Product,ProductState> products;
    private static CartController single_instance = null;
    private double totalPrice;
    private double totalPriceAfterDiscount;

    public class ProductState{
        int count;
        double price;
        Salesperson salesperson;
        public ProductState(int count, Salesperson salesperson,Product product) {
            this.count = count;
            this.salesperson = salesperson;
            price = salesperson.getProductPrice(product);
        }
    }

    private CartController()
    {
       products = new HashMap<Product,ProductState>();
    }

    public static CartController getInstance()
    {
        if (single_instance == null)
            single_instance = new CartController();

        return single_instance;
    }

    public void addProduct(Product product,Salesperson salesperson){
        products.put(product,new ProductState(1,salesperson,product));
    }

    public void setProductCount(Product product,int count){
        products.get(product).count+=count;
    }

    public void calculateTotalPrice(){
       totalPrice =0;
        for (ProductState value : products.values()) {
            totalPrice+=value.price;
        }
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
            calculateTotalPrice();
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
