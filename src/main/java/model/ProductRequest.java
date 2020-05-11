package model;

import controller.Database;

import java.io.IOException;

import static controller.ProductController.*;

public class ProductRequest extends Request {
    private Salesperson salesperson;
    private Product product;
    private double price;
    private int amount;

    public ProductRequest(String requestId, double price, int amount, RequestState requestState,
                          Salesperson salesperson, Product product) throws IOException {
        super(requestId, requestState);
        this.price = price;
        this.amount = amount;
        this.salesperson = salesperson;
        this.product = product;
        Database.saveToFile(this, Database.createPath("product_requests", requestId),false);
    }


    @Override
    public void doThis() {
        switch (getRequestState()) {
            case ADD:
                addProduct(product, salesperson, amount, price);
                changeState();
            case EDIT:
                editProduct(product, salesperson, amount, price);
                changeState();
            case DELETE:
                removeProduct(product, salesperson);
        }
    }

    @Override
    public String show() {
        //ba tavajoh be salighe avazesh konim
        return getRequestId();
    }

    private void changeState() {
        salesperson.setProductState(product, ProductState.State.VERIFIED);
    }
}
