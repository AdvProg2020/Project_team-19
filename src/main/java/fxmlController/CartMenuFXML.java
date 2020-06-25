package fxmlController;

import controller.CartController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cart;
import model.Product;
import model.Salesperson;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

public class CartMenuFXML implements Initializable {

    private GridPane ordersBox;
    @FXML private Label totalPrice;
    @FXML private Label discountCodeAmount;
    @FXML private GridPane basePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScrollPane scrollPane = new ScrollPane();
        ordersBox = new GridPane();
        ordersBox.setAlignment(Pos.CENTER);
        setCartsOnPane();
        ordersBox.setPadding(new Insets(10));
        ordersBox.setVgap(5);
        ordersBox.setHgap(5);
        scrollPane.setContent(ordersBox);
        basePane.add(ordersBox, 0 , 1);
        totalPrice.setText(String.valueOf(CartController.getInstance().getCart().calculateTotalPrice()));
//        discountCodeAmount.setText(String.valueOf(CartController.getInstance().getCart().));
    }

    @FXML
    void discountCode(ActionEvent event) {
        Stage stage = new Stage();
        Parent fxml = null;
        try {
            fxml = getFXMLLoader ("discountCodeHandler").load ();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fxml != null;
        stage.setScene(new Scene(fxml,300,200));
        stage.show();
    }

    private void setCartsOnPane() {
        Cart cart = CartController.getInstance().getCart();
        int rowIndex = 0;
        for (Product product : cart.getProducts().keySet()) {
            for (Salesperson salesperson : cart.getProducts().get(product).keySet()){
                ProductInCart productInCart = new ProductInCart(cart.getProducts().get(product).get(salesperson));
                FXMLLoader loader = new FXMLLoader(CartMenuFXML.class.getResource("/fxml/productInCart.fxml"));
                loader.setController(productInCart);
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent finalParent = parent;
                finalParent.setOnMouseClicked(e -> {
                    if (!productInCart.isProductInCart()){
                        ordersBox.getChildren().remove(finalParent);
                    }
                });
                ordersBox.add(parent, rowIndex % 2, rowIndex / 2);
                rowIndex++;
            }
        }
    }
}
