package model;

import controller.Database;
import controller.PersonController;
import controller.ProductController;

import java.time.LocalDateTime;

import static controller.RequestController.allRequests;

public class AuctionRequest extends Request{
    private String sellerId;
    private String productId;
    private LocalDateTime endTime;

    public AuctionRequest(Salesperson salesperson, Product product, LocalDateTime endTime) {
        super(RequestState.ADD);
        this.sellerId = salesperson.getUsername();
        this.productId = product.getID();
        this.endTime = endTime;
        save();
    }

    private void save() {
        Database.saveToFile(this, Database.createPath("auction_requests", this.getRequestId()));
        allRequests.add(this);
    }

    @Override
    public void doThis() {
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerId);
        Product product = ProductController.getInstance().getProductById(productId);
        ProductController.getInstance().addAuction(salesperson, product, endTime);
    }

    @Override
    public void decline() {
        allRequests.remove(this);
    }

    @Override
    public String show() {
        return sellerId + " want to put this product in auction\nwith end time " + endTime +
                " : " + productId;
    }
}
