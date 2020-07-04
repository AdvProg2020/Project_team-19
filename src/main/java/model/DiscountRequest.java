package model;

import controller.Database;
import controller.DiscountController;
import controller.PersonController;
import controller.ProductController;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static controller.RequestController.allRequests;

public class DiscountRequest extends Request {
    private String salespersonUsername;
    private String discountId;
    private ArrayList<String> productIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;

    public DiscountRequest(Discount discount, ArrayList<Product> products, LocalDateTime startTime,
                           LocalDateTime endTime, double discountPercentage, Salesperson salesperson) {
        super(RequestState.EDIT);
        this.discountId = discount.getDiscountID();
        productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getID());
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.salespersonUsername = salesperson.getUsername();
        save();
    }

    public DiscountRequest(Discount discount, Salesperson salesperson, RequestState requestState) {
        super(requestState);
        productIds = new ArrayList<>();
        for (Product product : discount.getProducts()) {
            productIds.add(product.getID());
        }
        this.discountId = discount.getDiscountID();
        this.salespersonUsername = salesperson.getUsername();
        this.startTime = discount.getStartTime();
        this.endTime = discount.getEndTime();
        this.discountPercentage = discount.getDiscountPercentage();
        save();
    }

    private void save() {
        Database.saveToFile(this, Database.createPath("discount_requests", this.getRequestId()));
        allRequests.add(this);
    }

    public ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        if(!productIds.isEmpty())
            for (String productId : productIds) {
                products.add(ProductController.getInstance().getProductById(productId));
            }
        return products;
    }


    @Override
    public void doThis() {
        switch (getRequestState()) {
            case ADD:
                DiscountController.getInstance().addDiscount(getSalesperson(), getDiscount());
                break;
            case EDIT:
                DiscountController.getInstance().editDiscount(discountPercentage, startTime, endTime, getProducts(), getDiscount(), getSalesperson());
                break;
            case DELETE:
                DiscountController.getInstance().removeDiscount(getSalesperson(), getDiscount());
                break;
        }
    }

    @Override
    public void decline() {
        Discount discount = getDiscount();
        Salesperson salesperson = getSalesperson();
        if (getRequestState().equals(RequestState.ADD)) {
            DiscountController.getInstance().removeDiscount(salesperson, discount);
        } else {
            DiscountController.getInstance().declineRequestDiscountForEditAndRemove(discount, salesperson);
        }
    }

    @Override
    public String show() {
        if (getRequestState().equals(RequestState.DELETE))
            return salespersonUsername + " want to delete " + discountId + " .";
        return salespersonUsername + " want to put " + getProducts() + " in discount " + "(" + discountPercentage + "%"
                + ")" + ": " + discountId + " \nfrom " + startTime + " to " + endTime + " .";

    }

    private Discount getDiscount() {
        return DiscountController.getInstance().getDiscountByIdFromAll(discountId);
    }

    private Salesperson getSalesperson() {
        return (Salesperson) PersonController.getInstance().getPersonByUsername(salespersonUsername);
    }
}
