package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Auction;
import model.Product;
import model.Salesperson;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionInList implements Initializable {
    private Auction auction;

    @FXML
    private Label sellerName;

    @FXML
    private Label endTime;

    @FXML
    private Label productName;

    @FXML
    private ImageView profilePic;

    @FXML
    private ImageView productPic;

    public AuctionInList(Auction auction) {
        this.auction = auction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellerName.setText(auction.getSellerName());
        profilePic.setImage(new Image(auction.getSellerImageURL()));

        productName.setText(auction.getProductName());
        productPic.setImage(new Image(auction.getProductImageURL()));

        endTime.setText(fancyEndTime());

    }

    private String fancyEndTime() {
        String uglyEndTime = String.valueOf(auction.getEndTime());
        uglyEndTime = uglyEndTime.replace("-", "/");
        uglyEndTime = uglyEndTime.replace("T", "  ");
        return uglyEndTime;
    }
}
