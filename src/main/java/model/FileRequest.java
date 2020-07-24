package model;

import controller.Database;
import controller.PersonController;
import controller.ProductController;

import static controller.RequestController.allRequests;

public class FileRequest extends Request{
    private String sellerId;
    private String fileName;
    private String description;
    private double price;
    private String address;

    public FileRequest(String sellerId, String fileName, String description, double price, String address) {
        super(RequestState.ADD);
        this.fileName = fileName;
        this.sellerId = sellerId;
        this.description = description;
        this.price = price;
        this.address = address;
        save();
    }

    private void save() {
        Database.saveToFile(this, Database.createPath("file_requests", this.getRequestId()));
        allRequests.add(this);
    }

    @Override
    public void doThis() {
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerId);
        ProductController.getInstance().addFile(salesperson, fileName, description, price, address);
    }

    @Override
    public void decline() {
        allRequests.remove(this);
    }

    @Override
    public String show() {
        return sellerId + " want to add this file\nwith description " + description +
                " : " + fileName + " and price :" + price;
    }
}
