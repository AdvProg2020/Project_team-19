package fxmlController;

import controller.CartController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Cart;
import model.Product;
import model.Salesperson;
import client.view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.view.App.getFXMLLoader;

public class CartMenuFXML implements Initializable {

    private String backPageName;
    private GridPane ordersBox;
    @FXML private Label totalPrice;
    @FXML private Label discountCodeAmount;
    @FXML private GridPane basePane;
    @FXML private FontAwesomeIcon back;
    @FXML private AnchorPane anchorPane;

    public CartMenuFXML(String backPageName) {
        this.backPageName = backPageName;
    }

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
        basePane.add(scrollPane, 0 , 1);
        totalPrice.setText(String.valueOf(CartController.getInstance().getCart().calculateTotalPrice()));
        System.out.println(backPageName);
        back.setOnMouseClicked ( event -> App.setRoot ( backPageName ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

        discountCodeAmount.setText(String.valueOf(CartController.getInstance().getCart().calculateTotalPrice () - CartController.getInstance ().getCart ().getTotalPriceAfterDiscountCode ()));
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

    @FXML
    void purchase(ActionEvent event) {
        try {
            CartController.getInstance().purchase(true);
            App.setRoot("pay");
        } catch (CartController.NoLoggedInPersonException e) {
            App.showAlert(Alert.AlertType.ERROR,App.currentStage,"Error","You need to login.");
        } catch (CartController.AccountIsNotCustomerException e) {
            App.showAlert(Alert.AlertType.ERROR,App.currentStage,"Error","You need to login with customer account.");
        }
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
