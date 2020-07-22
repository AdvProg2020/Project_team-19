package model;

import controller.Database;
import controller.PersonController;
import controller.ProductController;

import java.util.HashMap;

import static controller.RequestController.allRequests;

public class ProductRequest extends Request {
    private String salespersonUsername;
    private String productId;
    private String category;
    private String name;
    private String brand;
    private String imageURI;
    private String mediaURI;
    private HashMap<String, String> properties;
    private double price;
    private int amount;

    public ProductRequest(double price, int amount, Salesperson salesperson, Product product) {
        super(RequestState.ADD);
        this.price = price;
        this.amount = amount;
        this.salespersonUsername = salesperson.getUsername();
        this.productId = product.getID();
        this.name = product.getName();
        this.category = product.getCategory().getName();
        save();
    }

    public ProductRequest(double price, int amount, Salesperson salesperson, Product product, String imageURI, String mediaURI) {
        super(RequestState.ADD);
        this.imageURI = imageURI;
        this.mediaURI = mediaURI;
        this.price = price;
        this.amount = amount;
        this.salespersonUsername = salesperson.getUsername();
        this.productId = product.getID();
        this.name = product.getName();
        this.category = product.getCategory().getName();
        save();
    }

    public ProductRequest(double price, int amount, Salesperson salesperson, String category, String name
            , String brand, HashMap<String, String> properties, Product product) {
        super(RequestState.EDIT);
        this.productId = product.getID();
        this.price = price;
        this.amount = amount;
        this.salespersonUsername = salesperson.getUsername();
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.properties = new HashMap<>(properties);
        save();
    }

    public ProductRequest(double price, int amount, Salesperson salesperson, HashMap<String, String> properties, Product product) {
        super(RequestState.EDIT);
        this.productId = product.getID();
        this.price = price;
        this.amount = amount;
        this.name = product.getName();
        this.category = product.getCategory().getName();
        this.salespersonUsername = salesperson.getUsername();
        this.properties = new HashMap<>(properties);
        save();
    }

    public ProductRequest(Salesperson salesperson, Product product) {
        super(RequestState.DELETE);
        this.salespersonUsername = salesperson.getUsername();
        this.productId = product.getID();
        save();
    }

    private void save() {
        Database.saveToFile(this, Database.createPath("product_requests", this.getRequestId()));
        allRequests.add(this);
    }


    @Override
    public void doThis() {
        Product product = ProductController.getInstance().getProductById(productId);
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(salespersonUsername);
        switch (getRequestState()) {
            case ADD:
                ProductController.getInstance().addProduct(product, salesperson);
                break;
            case EDIT:
                ProductController.getInstance().editProduct(product, salesperson, amount, price, category, name, brand, properties, imageURI, mediaURI);
                break;
            case DELETE:
                ProductController.getInstance().removeProduct(product, salesperson);
                break;
        }
    }

    @Override
    public void decline() {
        Product product = ProductController.getInstance().getProductById(productId);
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(salespersonUsername);
        if (getRequestState() == RequestState.ADD) {
            ProductController.getInstance().declineProductRequestForAdd(product, salesperson);
        } else {
            ProductController.getInstance().declineRequestForEditAndRemove(product, salesperson);
        }
    }

    @Override
    public String show() {
        if (getRequestState().equals(RequestState.DELETE)) {
            return salespersonUsername + " want to " + this.getRequestState() + " this product : " + productId +
                    "\nname :" + name + "\ncategory :" + category;
        }
        return salespersonUsername + " want to "
                + this.getRequestState() + " this product : "
                + productId +
                "\nwith " + price + "$" + " and amount " + amount +
                "\nname :" + name + "\ncategory :" + category;
    }
}
