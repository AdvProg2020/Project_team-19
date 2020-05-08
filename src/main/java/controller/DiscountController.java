package controller;

import model.Discount;
import model.Product;
import model.Salesperson;

import java.util.ArrayList;
import java.util.Collection;

public class DiscountController {
    public static ArrayList<Discount> allDiscounts = new ArrayList<>();

    public enum EditType { //edit add product bashe ya emove
        ADD, REMOVE
    }

    public static void removeDiscount(Salesperson salesperson, Discount discount) {
        salesperson.removeFromDiscounts(discount);
        allDiscounts.remove(discount);

        //az file ham hazf she
    }

    public static void addDiscount(Salesperson salesperson, Discount discount) {
        salesperson.addToDiscounts(discount);
        allDiscounts.add(discount);

        //file
    }

    public static void setProductsInDiscount (Discount discount, Collection<Product> chosenProducts, EditType editType) {
        switch (editType) {
            case ADD:
                discount.getProducts().addAll(chosenProducts);
            case REMOVE:
                discount.getProducts().removeAll(chosenProducts);
        }
    }
}
