package model;

import java.io.IOException;
import java.util.ArrayList;

import static controller.DiscountController.*;
import static model.Discount.getDiscountByIdFromAll;

public class DiscountRequest extends Request {
    private Salesperson salesperson;
    private String discountID;
    private ArrayList<Product> products;
    private String startTime; //we can use "new java.util.Date()" that gives the exact time
    private String endTime;
    private double discountPercentage;

    public DiscountRequest(String requestId, String discountID, ArrayList<Product> products, String startTime,
                           String endTime, double discountPercentage, RequestState requestState, Salesperson salesperson) throws IOException {
        super(requestId, requestState);
        this.discountID = discountID;
        this.products = products;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.salesperson = salesperson;
    }

    @Override
    public void doThis() {
        switch (getRequestState()) {
            case ADD:
                addDiscount(salesperson, new Discount(discountID, startTime, endTime, discountPercentage, products));
                break;
            case EDIT:
                editDiscount();
                break;
            case DELETE:
                removeDiscount(salesperson, getDiscountByIdFromAll (discountID));
                break;
        }
    }

    @Override
    public String show() {
        //ba tavajoh be salighe avazesh konim
        return getRequestId();
    }


    private void editDiscount() {   //havaset bashe inja cizi be products add ya remove nemishe
        Discount discount = getDiscountByIdFromAll (discountID);
        assert discount != null;
        discount.setDiscountPercentage(discountPercentage);
        discount.setEndTime(endTime);
        discount.setStartTime(startTime);
        discount.setProducts(products);
        //...
        System.out.println("edit discount");
    }

    @Override
    public String toString() {
        return "DiscountRequest{" +
                "discountID='" + discountID + '\'' +
                '}';
    }
}
