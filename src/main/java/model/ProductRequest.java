package model;

import controller.Database;
import controller.ProductController;

import java.io.IOException;
import java.util.HashMap;

import static controller.ProductController.*;
import static controller.RequestController.allRequests;

public class ProductRequest extends Request {
    private Salesperson salesperson;
    private Product product;
    private String category;
    private String name;
    private String brand;
    private HashMap<String, String> properties;
    private double price;
    private int amount;

    public ProductRequest(double price, int amount, Salesperson salesperson, Product product) {
        super(RequestState.ADD);
        this.price = price;
        this.amount = amount;
        this.salesperson = salesperson;
        this.product = product;
        save();
    }

    public ProductRequest(double price, int amount, Salesperson salesperson, String category, String name
            , String brand, HashMap<String, String> properties, Product product) {
        super(RequestState.EDIT);
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.salesperson = salesperson;
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.properties = new HashMap<>(properties);
        save();
    }

    public ProductRequest(Salesperson salesperson, Product product) {
        super(RequestState.DELETE);
        this.salesperson = salesperson;
        this.product = product;
        save();
    }

    private void save() {
        Database.saveToFile(this, Database.createPath("product_requests", this.getRequestId()));
        allRequests.add(this);
    }


    @Override
    public void doThis() {
        switch (getRequestState()) {
            case ADD:
                ProductController.getInstance().addProduct(product, salesperson, amount, price);
                changeState();
                break;
            case EDIT:
                ProductController.getInstance().editProduct(product, salesperson, amount, price, category, name, brand, properties);
                changeState();
                break;
            case DELETE:
                ProductController.getInstance().removeProduct(product, salesperson);
                break;
        }
    }

    @Override
    public String show() {
        if (getRequestState() == RequestState.DELETE) {
            return salesperson.getUsername() + " want to " + this.getRequestState() + " this product : " + product.getID();
        }
        return salesperson.getUsername() + " want to " + this.getRequestState() + " this product : " + product.getID() +
                "\nwith " + price + "$" + " and amount " + amount;
    }

    private void changeState() {
        salesperson.setProductState(product, ProductState.State.VERIFIED);
    }
}
