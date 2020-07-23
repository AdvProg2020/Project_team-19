package model;

import org.apache.commons.lang3.RandomStringUtils;

public class FileProduct {
    private String name;
    private String description;
    private String id;
    private double price;
    private String sellerId;
    private String address;

    public FileProduct(Salesperson salesperson, String name, String description, double price, String address) {
        this.name = name;
        this.description = description;
        this.sellerId = salesperson.getUsername();
        this.price = price;
        this.address = address;
        this.id = RandomStringUtils.random(4, true, true);
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
