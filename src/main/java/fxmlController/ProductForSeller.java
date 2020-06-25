package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Product;
import model.Salesperson;

import javafx.scene.shape.Line;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductForSeller implements Initializable {
    private Product product;
    private Salesperson salesperson;
    @FXML private Label amount;
    @FXML private ImageView productImage;
    @FXML private Line priceLine;
    @FXML private Label price;
    @FXML private Label priceInDiscount;
    @FXML private Label name;
    @FXML private StackPane pricePane;
    @FXML private Label state;
    @FXML private Label off;
    @FXML private ImageView inDiscount;
    @FXML private ImageView remove;
    @FXML private ImageView edit;

    public ProductForSeller(Product product, Salesperson salesperson) {
        this.product = product;
        this.salesperson = salesperson;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (salesperson.isInDiscount(product)) {
            inDiscount.setVisible(true);
            off.setText(salesperson.getDiscountPercentage(product) + "%");
            priceInDiscount.setText(salesperson.getDiscountPrice(product) + "$");
        } else {
            priceLine.setVisible(false);
            //pricePane.getChildren().remove(priceLine);
        }
        amount.setText("amount : " + salesperson.getProductAmount(product));
        state.setText(salesperson.getProductState(product).label + "");
        price.setText(salesperson.getProductPrice(product) + " $");
        name.setText(product.getName());
        edit.setOnMouseClicked(event -> edit());
        remove.setOnMouseClicked(event -> remove());
    }

    private void edit() {

    }

    private void remove() {

    }
}
