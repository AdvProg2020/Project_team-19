package fxmlController;

import controller.ProductController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductInList implements Initializable {
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private HBox starRate;
    @FXML private ImageView finished;

    private Product product;

    public ProductInList(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editLabel(productNameLabel, product.getName());
        int index = ("" + product.getAveragePrice()).indexOf(".");
        editLabel(priceLabel, ("" + product.getAveragePrice()).substring(0, index + 2) + "$");
        setScoreRate();
        checkFinished();
    }

    private void checkFinished() {
        if (ProductController.getInstance().isProductAvailable(product)) {
            finished.setVisible(false);
        }
    }

    private void setScoreRate() {
        StarRate starRate = new StarRate(product);
        FXMLLoader loader = new FXMLLoader(ProductMenu.class.getResource("/fxml/starRate.fxml"));
        loader.setController(starRate);
        try {
            this.starRate.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editLabel(Label label, String text) {
        label.setText(text);
        label.setFont(Font.font("Verdana", 10));
    }
}
