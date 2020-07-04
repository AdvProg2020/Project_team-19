package fxmlController;

import model.Product;
import model.Salesperson;

public class AuctionMenu {
    private Salesperson salesperson;
    private Product product;

    public AuctionMenu(Salesperson salesperson, Product product) {
        this.salesperson = salesperson;
        this.product = product;
    }
}
