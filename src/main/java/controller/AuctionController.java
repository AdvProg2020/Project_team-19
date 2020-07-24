package controller;

import model.*;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Database.*;

public class AuctionController {
    private static AuctionController single_instance = null;
    private static ArrayList<Auction> allAuctions = new ArrayList<>();


    private AuctionController() {
    }

    public static AuctionController getInstance() {
        if (single_instance == null)
            single_instance = new AuctionController();

        return single_instance;
    }

    public void initializeAuctions() {
        for (File file : returnListOfFiles(Database.address.get("auctions"))) {
            allAuctions.add((Auction) read(Auction.class, file.getAbsolutePath()));
        }
    }

    public void addBuyer(Auction auction, Customer buyer, double price) {
        auction.addCustomer(buyer, price);
        buyer.getWallet().setBlocked(price + WalletController.MIN_BALANCE);
        saveToFile(auction, createPath("auctions", auction.getId()));
        //save customer
    }

    public Customer getHighestOfferPriceCustomer(Auction auction) {
        double high = 0;
        Customer customer = null;
        for (String username : auction.getCustomers().keySet()) {
            if (auction.getCustomers().get(username) >= high) {
                customer = (Customer) PersonController.getInstance().getPersonByUsername(username);
                high = auction.getCustomers().get(username);
            }
        }
        return customer;
    }

    public void addAuction(Salesperson salesperson, Product product, LocalDateTime endTime) {
        Auction auction = new Auction(salesperson, product, endTime);
        allAuctions.add(auction);
        salesperson.addAuction(auction.getId());
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
        saveToFile(auction, createPath("auctions", auction.getId()));
    }

    public Auction getAuctionById(String auctionId) {
        for (Auction auction : allAuctions) {
            if (auction.getId().equals(auctionId))
                return auction;
        }
        return null;
    }

    public void purchase(Auction auction) {
        Customer customer = getHighestOfferPriceCustomer(auction);
        double amount = auction.getCustomers().get(customer.getUsername());
        for (String username : auction.getCustomers().keySet()) {
            Person person = PersonController.getInstance().getPersonByUsername(username);
            ((Customer) person).getWallet().decreaseBlocked(auction.getCustomers().get(username));
        }
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(auction.getSellerName());
        Product product = ProductController.getInstance().getProductById(auction.getProductId());

        BuyLog buyLog = new BuyLog(LocalDateTime.now(), amount, amount, getProduct(product, salesperson, amount), false);
        customer.addToBuyLogs(buyLog);
        customer.getWallet().decreaseBalance(amount);

        double sellerAmount = amount * (100 - WalletController.WAGE) / 100;
        SellLog sellLog = new SellLog(LocalDateTime.now(), sellerAmount, sellerAmount, product, customer, true, 1);
        salesperson.getSellLogs().add(sellLog);

        removeAuction(auction);
        allAuctions.remove(auction);
        ProductController.stock.get(product).remove(salesperson);

        salesperson.removeFromOfferedProducts(product);
        salesperson.getWallet().increaseBalance(sellerAmount);
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
        saveToFile(customer, createPath("customers", customer.getUsername()));
    }

    private HashMap<Product, HashMap<Salesperson, ProductStateInCart>> getProduct (Product product, Salesperson salesperson,double price) {
        ProductStateInCart productStateInCart = new ProductStateInCart(1, salesperson.getUsername(), product.getID(), price, price, product.getName());
        HashMap<Salesperson, ProductStateInCart> middle = new HashMap<>();
        middle.put(salesperson, productStateInCart);

        HashMap<Product, HashMap<Salesperson, ProductStateInCart>> total = new HashMap<>();
        total.put(product, middle);

        return total;
    }

    public ArrayList<Product> getSellerAvailableProductsForAuction(Salesperson salesperson){
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : salesperson.getOfferedProducts().keySet()) {
            if (!salesperson.getProductState(product).label.equals("Verified") || salesperson.getProductAmount(product) != 1
                    || isInAuction(salesperson, product))
                continue;
            products.add(product);
        }
        return products;
    }

    public boolean isInAuction(Salesperson salesperson, Product product) {
        return salesperson.getAuctions().containsKey(product);
    }

    public ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public void removeAuction(Auction auction) {
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(auction.getSellerName());
        salesperson.removeAuction(auction.getId());
        //purchase(auction);
        try {
            deleteFile(createPath("auctions", auction.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveToFile(salesperson, createPath("salespersons", salesperson.getUsername()));
    }

    public void checkAuctionTime() {
        ArrayList<Auction> remove = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (auction.getEndTime().isBefore(LocalDateTime.now())) {
                remove.add(auction);
                removeAuction(auction);
            }
        }
        allAuctions.removeAll(remove);
    }
}
