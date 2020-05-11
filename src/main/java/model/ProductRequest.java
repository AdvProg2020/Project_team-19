package model;

import controller.Database;

import java.io.IOException;

import static controller.ProductController.*;
import static controller.RequestController.allRequests;

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
    }


    @Override
    public void doThis() throws IOException {
        switch (getRequestState()) {
            case ADD:
                addProduct(product, salesperson, amount, price);
                changeState();
                break;
            case EDIT:
                editProduct(product, salesperson, amount, price);
                changeState();
                break;
            case DELETE:
                removeProduct(product, salesperson);
                break;
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
