package model;

import controller.PersonController;
import controller.ProductController;

import java.time.LocalDateTime;

public class AuctionRequest extends Request{
    private String sellerId;
    private String productId;
    private LocalDateTime endTime;

    public AuctionRequest(Salesperson salesperson, Product product, LocalDateTime endTime) {
        super(RequestState.ADD);
        this.sellerId = salesperson.getUsername();
        this.productId = product.getID();
        this.endTime = endTime;
    }


    @Override
    public void doThis() {
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getPersonByUsername(sellerId);
        Product product = ProductController.getInstance().getProductById(productId);
        ProductController.getInstance().addAuction(salesperson, product, endTime);
    }

    @Override
    public void decline() {
        //do nothing
        //just delete auction request which is done before
    }

    @Override
    public String show() {
        return sellerId + " want to put this product in auction\nwith end time " + endTime +
                " : " + productId;
    }
}
