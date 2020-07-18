package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;
import model.Salesperson;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionInList implements Initializable {
    private Salesperson salesperson;
    private Product product;

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

    public AuctionInList(Salesperson salesperson, Product product) {
        this.salesperson = salesperson;
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellerName.setText(salesperson.getUsername());
        profilePic.setImage(new Image(salesperson.getImage()));

        productName.setText(product.getName());
        productPic.setImage(new Image(product.getImageURI()));

        endTime.setText(fancyEndTime());

    }

    private String fancyEndTime() {
        String uglyEndTime = String.valueOf(salesperson.getAuctionEndTime(product));
        uglyEndTime = uglyEndTime.replace("-", "/");
        uglyEndTime = uglyEndTime.replace("T", "  ");
        return uglyEndTime;
    }
}
