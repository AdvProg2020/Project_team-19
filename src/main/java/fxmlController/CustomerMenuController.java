package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import model.Customer;
import view.App;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

public class CustomerMenuController implements Initializable {


    @FXML private TextField enterAmount;
    @FXML private FontAwesomeIcon increaseCredit;
    @FXML private FontAwesomeIcon back;
    @FXML private FontAwesomeIcon increaseCreditShowButton;
    private AudioClip chaching;

    @FXML
    void back ( MouseEvent event ) {
//        App.currentScene = App.firstScene;
//        App.currentStage.setScene ( App.currentScene );
        App.setRoot ( "mainMenu" );
    }

    @FXML
    void buyLogs ( ActionEvent event ) {
        App.setRoot ( "sellBuyLogs" );
    }

    @FXML
    void cart ( ActionEvent event ) {

    }

    @FXML
    void discountCodes ( ActionEvent event ) {
        App.setRoot ( "discountCodes" );
    }

    @FXML
    void logout ( ActionEvent event ) throws IOException {
        PersonController.getInstance().logOut();
        App.currentScene = new Scene ( getFXMLLoader ( "mainMenu" ).load () );
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML
    void personalInfo ( ActionEvent event ) {
        App.setRoot ( "personInfo" );
    }

    @FXML
    void purchase ( ActionEvent event ) {

    }

    @FXML
    void increaseCredit ( MouseEvent mouseEvent ) {
        double credit;
        try {
            credit = Double.parseDouble ( enterAmount.getText () );
//            chaching.play ();
            PersonController.getInstance().increaseCustomerCredit( (Customer) PersonController.getInstance ().getLoggedInPerson () , credit);
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

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        enterAmount.setVisible ( false );
        increaseCredit.setVisible ( false );
        increaseCredit.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 2em; -fx-effect: innershadow(gaussian, #00ad48,75,0,5,0);" );
//        chaching = new AudioClip ( new File ( "ChaChing.mp3" ).toURI ( ).toString ( ) );
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
}
