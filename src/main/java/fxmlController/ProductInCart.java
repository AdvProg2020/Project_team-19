package fxmlController;

import clientController.ServerConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ProductStateInCart;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInCart implements Initializable {
    @FXML private ImageView productImage;
    @FXML private Label totalPriceLabel;
    @FXML private Label discountPriceLabel;
    @FXML private Label productNameLabel;
    @FXML private Label sellerNameLabel;
    @FXML private ImageView increase;
    @FXML private Label countLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView decrease;
    private boolean productIsInCart;
    private ProductStateInCart productStateInCart;

    public ProductInCart(ProductStateInCart productStateInCart) {
        this.productStateInCart = productStateInCart;
        productIsInCart = true;
    }

    private void decreaseOnClick() {
        int count = ServerConnection.getCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson());
        if (count == 1) {
            productIsInCart = false;
        }
        if (count-1 == 1) {
            Image image = new Image("/images/filled-trash.png");
            decrease.setImage(image);
        }
        ServerConnection.setCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson(),-1);
        countLabel.setText(String.valueOf(ServerConnection.getCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson())));
    }

    private void increaseOnClick() {
        Image image = new Image("/images/icons8-minus-30.png");
        decrease.setImage(image);
        ServerConnection.setCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson(),1);
        countLabel.setText(String.valueOf(ServerConnection.getCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson())));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productNameLabel.setText(productStateInCart.getProductName());
        sellerNameLabel.setText(productStateInCart.getSalesperson());
        totalPriceLabel.setText(String.valueOf(productStateInCart.getPrice()));
        if (ServerConnection.getCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson()) == 1) {
            decrease.setImage(new Image("/images/filled-trash.png"));
        }
        countLabel.setText(String.valueOf(ServerConnection.getCountInCart(productStateInCart.getProduct(),productStateInCart.getSalesperson())));
        decrease.setOnMouseClicked(event -> decreaseOnClick());
        increase.setOnMouseClicked(event -> increaseOnClick());
//        if (productStateInCart.getProduct().getImageURI() != null) {
//            productImage.setImage(new Image(productStateInCart.getProduct().getImageURI()));
//        }
    }

    public boolean isProductInCart() {
        return productIsInCart;
    }
}
