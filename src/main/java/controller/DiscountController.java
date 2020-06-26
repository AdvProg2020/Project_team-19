package controller;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static controller.Database.*;
import static controller.ProductController.stock;

public class DiscountController {
    private static DiscountController single_instance = null;
    private static ArrayList<Discount> allDiscounts = new ArrayList <> (  );

    private DiscountController() {
    }

    public static DiscountController getInstance() {
        if (single_instance == null)
            single_instance = new DiscountController();

        return single_instance;
    }

    public enum EditType {
        ADD, REMOVE
    }

    public static ArrayList < Discount > getAllDiscounts () {
        for (Person person : PersonController.getInstance().filterByRoll(Salesperson.class)) {
            Salesperson salesperson = (Salesperson) person;
            allDiscounts.addAll ( salesperson.getDiscounts ( ) );
        }
        return allDiscounts;
    }

    public void declineRequestDiscountForEditAndRemove(Discount discount, Salesperson salesperson) {
        discount.setDiscountState(Discount.DiscountState.VERIFIED);
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }

    public void removeDiscount(Salesperson salesperson, Discount discount) {
        salesperson.removeFromDiscounts(discount);
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }


    public void addDiscount(Salesperson salesperson, Discount discount) {
        discount.setDiscountState(Discount.DiscountState.VERIFIED);
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }

    public void editDiscount(double discountPercentage, LocalDateTime startTime, LocalDateTime endTime, ArrayList<Product> products, Discount discount, Salesperson salesperson) {
        discount.setDiscountState(Discount.DiscountState.VERIFIED);
        discount.setDiscountPercentage(discountPercentage);
        discount.setEndTime(endTime);
        discount.setStartTime(startTime);
        discount.setProducts(products);
        for (Product product : products) {
            salesperson.setProductDiscountState(product, discount);
        }
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }

    public void handleProductsInDiscount(ArrayList<Product> products, ArrayList<Product> chosenProducts, EditType editType) {
        switch (editType) {
            case ADD:
                products.addAll(chosenProducts);
                break;
            case REMOVE:
                products.removeAll(chosenProducts);
                break;
        }
    }

    public Discount getDiscountByIdFromAll ( String id ) {
        for (Person person : PersonController.getInstance().filterByRoll(Salesperson.class)) {
            Salesperson salesperson = (Salesperson) person;
            if (salesperson.getDiscountWithIdSpecificSalesperson(id) != null) {
                return salesperson.getDiscountWithIdSpecificSalesperson(id);
            }
        }
        return null;
    }

    public void checkDiscountTime() {
        for (Person person : PersonController.getInstance().filterByRoll(Salesperson.class)) {
            Salesperson salesperson = (Salesperson) person;
            for (Discount discount : salesperson.getDiscounts()) {
                if (discount.checkDiscountEndTime()&&discount.checkDiscountStartTime()){
                   salesperson.setProductsDiscountState(discount,true);
                } else{
                    salesperson.setProductsDiscountState(discount,false);
                    if (!discount.checkDiscountEndTime()) {
                    removeDiscount(salesperson, discount);
                }
            }}
        }
    }
}
