package controller;

import model.Person;
import model.Product;
import model.Salesperson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Database.createPath;
import static controller.Database.saveToFile;

public class AuctionController {
    private static AuctionController single_instance = null;
    private static HashMap<String, HashMap<String, Double>> customers = new HashMap<>(); //product:seller -> buyer & offeredPrice

    private AuctionController() {
    }

    public static AuctionController getInstance() {
        if (single_instance == null)
            single_instance = new AuctionController();

        return single_instance;
    }

    public void addBuyer(String productId, String sellerUsername, String buyerUsername, double price) {
        String ownedProduct = productId + ":" + sellerUsername;
        if (customers.containsKey(ownedProduct)) {
            customers.get(ownedProduct).put(buyerUsername, price);
        } else {
            HashMap<String, Double> buyer = new HashMap<>();
            customers.put(ownedProduct, buyer);
        }
    }

    public double getHighestPrice(String productId, String seller) {
        String ownedProduct = productId + ":" + seller;
        double high = 0;
        for (String username : customers.get(ownedProduct).keySet()) {
            if (customers.get(ownedProduct).get(username) > high) {
                high = customers.get(ownedProduct).get(username);
            }
        }
        return high;
    }

    public void addAuction(Salesperson salesperson, Product product, LocalDateTime endTime) {
        salesperson.addAuction(product, endTime);
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }

    public HashMap<Salesperson, ArrayList<Product>> getAllAuctions() {
        HashMap<Salesperson, ArrayList<Product>> auctions = new HashMap<>();

        ArrayList<Person> sellers = PersonController.getInstance().filterByRoll(Salesperson.class);
        for (Person seller : sellers) {
            ArrayList<Product> products = new ArrayList<>(((Salesperson) seller).getAuctions().keySet());
            auctions.put((Salesperson) seller, products);
        }

        return auctions;
    }

    public void checkAuctionTime() {
        HashMap<Salesperson, ArrayList<Product>> allAuctions = getAllAuctions();
        ArrayList<Product> remove = new ArrayList<>();
        for (Salesperson salesperson : allAuctions.keySet()) {
            remove.clear();
            for (Product product : salesperson.getAuctions().keySet()) {
                if (salesperson.checkEndTimeAuction(product)) {
                    remove.add(product);
                }
            }
            for (Product removeProd : remove) {
                salesperson.removeAuction(removeProd);
            }
            saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
        }
    }
}
