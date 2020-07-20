package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;

public class Pay implements Initializable {

    @FXML
    private ImageView bank;
    @FXML
    private PasswordField password;
    @FXML
    private ImageView wallet;
    @FXML
    private TextField username;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField postalCode;
    @FXML
    private DatePicker deliveryDate;
//    @FXML
//    private Button purchaseButton;
    @FXML
    private FontAwesomeIcon back;

    @FXML
    void purchase(ActionEvent event) {
        purchase();
    }

    private void purchase() {
//        if (address.getText().isEmpty() || phoneNumber.getText().isEmpty() || postalCode.getText().isEmpty()) {
//            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Error", "Fill blank fields!");
//            return;
//        }
//        try {
//            CartController.getInstance().purchase();
//            App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "Yay", "You Bought It.");
//            back();
//        } catch (CartController.NoLoggedInPersonException | CartController.AccountIsNotCustomerException | IOException e) {
//            e.printStackTrace();
//        } catch (CartController.NotEnoughCreditMoney notEnoughCreditMoney) {
//            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Error", "Your credit is not enough.");
//        }
    }

    @FXML
    private void back() {
        App.setRoot("mainMenu");
    }

    @FXML
    private void backSizeBig(MouseEvent mouseEvent) {
        back.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);");
    }

    @FXML
    private void backSizeSmall(MouseEvent mouseEvent) {
        back.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 1em");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wallet.setOnMouseClicked(event -> purchaseWithWallet());
        back.setOnMouseClicked(event -> purchaseWithBank());
    }

    private void purchaseWithWallet() {
        if (address.getText().isEmpty() || phoneNumber.getText().isEmpty() || postalCode.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Error", "Fill blank fields!");
            return;
        }
        String response = getWalletPurchase();
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "purchase", response);
        if (response.equals("successful"))
            back();
    }

    private void purchaseWithBank() {
        if (address.getText().isEmpty() || phoneNumber.getText().isEmpty() || postalCode.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Error", "Fill blank fields!");
            return;
        }
        String response = getBankPurchase(username.getText(), password.getText());
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "purchase", response);
        if (response.equals("successful"))
            back();
    }
}
