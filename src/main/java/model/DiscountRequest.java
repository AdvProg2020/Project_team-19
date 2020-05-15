package model;

import controller.Database;
import controller.DiscountController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static controller.RequestController.allRequests;

public class DiscountRequest extends Request {
    private Salesperson salesperson;
    private Discount discount;
    private ArrayList<Product> products;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;

    public DiscountRequest(Discount discount, ArrayList<Product> products, LocalDateTime startTime,
                           LocalDateTime endTime, double discountPercentage, Salesperson salesperson) {
        super(RequestState.EDIT);
        this.discount = discount;
        this.products = products;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.salesperson = salesperson;
        save();
    }

    public DiscountRequest(Discount discount, Salesperson salesperson) {
        super(RequestState.DELETE);
        this.discount = discount;
        this.salesperson = salesperson;
        save();
    }

    public DiscountRequest(ArrayList<Product> products, LocalDateTime startTime, LocalDateTime endTime, double discountPercentage, Salesperson salesperson) {
        super(RequestState.ADD);
        this.products = products;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.salesperson = salesperson;
        save();
    }

    private void save() {
        Database.saveToFile(this, Database.createPath("discount_requests", this.getRequestId()));
        allRequests.add(this);
    }

    @Override
    public void doThis() {
        switch (getRequestState()) {
            case ADD:
                DiscountController.getInstance().addDiscount(salesperson, new Discount(startTime, endTime, discountPercentage, products));
                break;
            case EDIT:
                DiscountController.getInstance().editDiscount(discountPercentage, startTime, endTime, products, discount, salesperson);
                break;
            case DELETE:
                DiscountController.getInstance().removeDiscount(salesperson, discount);
                break;
        }
    }

    @Override
    public String show() {
        return salesperson.getUsername() + " want to put " + products + " in discount " + "(" + discountPercentage + "%"
                + ")" + ": " + discount.getDiscountID() + " from " + startTime + " to" + endTime;
    }
}
