package fxmlController;

import controller.CartController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import client.view.App;

import java.io.IOException;

public class Pay {

    @FXML
    private TextField address;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField postalCode;

    @FXML
    private DatePicker deliveryDate;

    @FXML
    private Button purchaseButton;

    @FXML
    private FontAwesomeIcon back;

    @FXML
    void purchase(ActionEvent event) {
        purchase();
    }

    private void purchase(){
        if (address.getText().isEmpty()||phoneNumber.getText().isEmpty()||postalCode.getText().isEmpty())
            App.showAlert(Alert.AlertType.ERROR,App.currentStage,"Error","Fill blank fields!");
        else
            try {
                CartController.getInstance().purchase();
                App.showAlert ( Alert.AlertType.INFORMATION , App.currentStage , "Yay" , "You Bought It." );
                back();
            } catch (CartController.NoLoggedInPersonException e) {
                e.printStackTrace();
            } catch (CartController.AccountIsNotCustomerException e) {
                e.printStackTrace();
            } catch (CartController.NotEnoughCreditMoney notEnoughCreditMoney) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage,"Error","Your credit is not enough.");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML private void back() {
        App.setRoot ( "mainMenu" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }
}
