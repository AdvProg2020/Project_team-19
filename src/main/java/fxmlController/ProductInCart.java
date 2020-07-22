package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ProductStateInCart;

import java.net.URL;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;

public class ProductInCart implements Initializable {

    private ProductStateInCart productStateInCart;

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

    public ProductInCart(ProductStateInCart productStateInCart) {
        this.productStateInCart = productStateInCart;
        productIsInCart = true;
    }

    private void decreaseOnClick() {
        if (productStateInCart.getCount() == 1) {
            productIsInCart = false;
        }
        if (productStateInCart.getCount()-1 == 1) {
            Image image = new Image("/images/filled-trash.png");
            decrease.setImage(image);
        }
        //todo CartController.getInstance().setProductCount(productStateInCart.getProduct(), -1, productStateInCart.getSalesperson());
        countLabel.setText(String.valueOf(productStateInCart.getCount()));
    }

    private void increaseOnClick() {
        Image image = new Image("/images/icons8-minus-30.png");
        decrease.setImage(image);
        //todo CartController.getInstance().setProductCount(productStateInCart.getProduct(), 1, productStateInCart.getSalesperson());
        countLabel.setText(String.valueOf(productStateInCart.getCount()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productNameLabel.setText(productStateInCart.getProduct().getName());
        sellerNameLabel.setText(productStateInCart.getSalesperson().getUsername());
        totalPriceLabel.setText(String.valueOf(productStateInCart.getSalesperson().getProductPrice(productStateInCart.getProduct())));
        if (productStateInCart.getCount() == 1) {
            decrease.setImage(new Image("/images/filled-trash.png"));
        }
        countLabel.setText(String.valueOf(productStateInCart.getCount()));
        decrease.setOnMouseClicked(event -> decreaseOnClick());
        increase.setOnMouseClicked(event -> increaseOnClick());
        if (productStateInCart.getProduct().getImageURI() != null) {
            productImage.setImage(new Image(productStateInCart.getProduct().getImageURI()));
        }
    }

    public boolean isProductInCart() {
        return productIsInCart;
    }
}
