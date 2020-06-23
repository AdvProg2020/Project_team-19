package fxmlController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Product;
import model.Salesperson;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellerInList implements Initializable {
    private static String FONT = "Verdana";
    @FXML
    private Button addToCart;
    @FXML
    private Label price;
    @FXML
    private Label sellerName;
    @FXML
    private Label priceInDiscount;
    private Salesperson salesperson;
    private Product product;

    public SellerInList(Salesperson salesperson, Product product) {
        this.salesperson = salesperson;
        this.product = product;
    }

    private void editLabel() {
        if (salesperson.isInDiscount(product)) {
            price.setTextFill(Color.gray(0.4));
            price.setFont(Font.font(FONT, 12));
            priceInDiscount.setTextFill(Color.RED);
            priceInDiscount.setFont(Font.font(FONT, 18));
        } else {
            priceInDiscount.setVisible(false);
            price.setFont(Font.font(FONT, 18));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sellerName.setText(salesperson.getUsername());
        sellerName.setFont(Font.font(FONT, 18));
        price.setText(salesperson.getProductPrice(product) + "$");
        editLabel();
    }
}
