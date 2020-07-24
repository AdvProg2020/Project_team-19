package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import model.Customer;
import view.App;

import static clientController.ServerConnection.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

public class CustomerMenuController implements Initializable {


    public Button supportChat;
    @FXML private TextField enterAmount;
    @FXML private FontAwesomeIcon increaseCredit;
    @FXML private FontAwesomeIcon back;
    @FXML private FontAwesomeIcon increaseCreditShowButton;
    @FXML private Label yourBalanceLabel;
    @FXML private ImageView cart;
    private AudioClip chaching;

    @FXML
    void back ( MouseEvent event ) {
        App.setRoot ( "mainMenu" );
    }

    @FXML
    void buyLogs ( ActionEvent event ) {
        App.setRoot ( "sellBuyLogs" );
    }

    @FXML
    void cart () {
        CartMenuFXML cartMenuFXML = new CartMenuFXML("customerMenu");
        FXMLLoader loader = new FXMLLoader(CustomerMenuController.class.getResource("/fxml/cart.fxml"));
        loader.setController(cartMenuFXML);
        App.setRoot(loader);
    }

    @FXML
    void discountCodes ( ActionEvent event ) {
        App.setRoot ( "customerDiscountCodes" );
    }

    @FXML
    void logout ( ActionEvent event ) throws IOException {
        sendLogout();
        App.currentScene = new Scene ( getFXMLLoader ( "mainMenu" ).load () );
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML
    void personalInfo ( ActionEvent event ) {
        App.setRoot ( "personInfo" );
    }

    @FXML
    void increaseCredit ( MouseEvent mouseEvent ) {
        double credit;
        try {
            credit = Double.parseDouble ( enterAmount.getText () );
            //todo PersonController.getInstance().increaseCustomerCredit( (Customer) PersonController.getInstance ().getLoggedInPerson () , credit);
            //yourBalanceLabel.setText ( "Your Balance : " + ((Customer) PersonController.getInstance ().getLoggedInPerson ()).getCredit () );
        } catch (Exception e) {
            App.error ( "Enter numbers only." );
        }
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @FXML private void plusSizeBig ( MouseEvent mouseEvent ) {
        increaseCredit.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #00ad48,75,0,5,0);" );
    }

    @FXML private void plusSizeSmall ( MouseEvent mouseEvent ) {
        increaseCredit.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em;-fx-effect: innershadow(gaussian, #00ad48,75,0,5,0);" );
    }

    @FXML private void plusShowSizeBig ( MouseEvent mouseEvent ) {
        increaseCreditShowButton.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @FXML private void plusShowSizeSmall ( MouseEvent mouseEvent ) {
        increaseCreditShowButton.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 0.8em" );
    }



    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        enterAmount.setVisible ( false );
        increaseCredit.setVisible ( false );
        increaseCredit.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20; -fx-effect: innershadow(gaussian, #00ad48,75,0,5,0);" );
        //todo yourBalanceLabel.setText ( "Your Balance : " + ((Customer) PersonController.getInstance ().getLoggedInPerson ()).getCredit () );
        cart.setOnMouseClicked ( event -> cart () );
    }

    @FXML private void increaseCreditShow ( MouseEvent mouseEvent ) {
        if (enterAmount.isVisible ()) {
//            increaseCreditShowButton.setStyle ( "-fx-font-family: FontAwesome;" );
            enterAmount.setVisible ( false );
            increaseCredit.setVisible ( false );
            enterAmount.setText ( "" );
        } else {
            enterAmount.setVisible ( true );
            increaseCredit.setVisible ( true );
        }
    }

    public void supportChatAction ( ActionEvent event ) {
        App.setRoot ( "supportChatClient" );
    }
}
