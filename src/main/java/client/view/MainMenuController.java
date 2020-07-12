package client.view;

import controller.PersonController;
import fxmlController.MainProductsMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Manager;
import model.Person;
import model.Salesperson;
import client.view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static client.view.App.getFXMLLoader;

public class MainMenuController implements Initializable {
    @FXML private ImageView productIcon;
    @FXML private ImageView discountIcon;
    @FXML private ImageView userIcon;
//    @FXML Button productMenu;
//    @FXML Button discountMenu;
//    @FXML Button back;
//    @FXML Button help;

//    @FXML private Button userMenu;

    @FXML private void userMenuAction () throws IOException {
        App.akh.play ();
        App.firstScene = App.currentScene;
        if ( PersonController.getInstance ().isThereLoggedInPerson () ) {
            Person person = PersonController.getInstance ().getLoggedInPerson ();
            if ( person instanceof Manager ) {
                App.currentScene = new Scene ( getFXMLLoader ( "managerMenu" ).load () );
            } else if ( person instanceof Salesperson ) {
                App.currentScene = new Scene ( getFXMLLoader ( "salespersonMenu" ).load () );
            } else {
                App.currentScene = new Scene ( getFXMLLoader ( "customerMenu" ).load () );
            }
        } else {
            App.currentScene = new Scene ( getFXMLLoader ("userLoginMenu").load () );
        }
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML private void productAction () {
        MainProductsMenu.isDiscount = false;
        App.akh.play ();
        App.setRoot ( "mainProductsMenu" );
    }

    @FXML void discountAction() {
        MainProductsMenu.isDiscount = true;
        App.akh.play ();
        App.setRoot ( "mainProductsMenu" );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
//        help.setShape ( new Circle ( 1.5 ) );
    }

    @FXML private void userIconBig ( MouseEvent mouseEvent ) {
//        userIcon.setStyle ( "-fx-pref-width: 250; -fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
        userIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
//        userIcon.setFitWidth ( 75 );

    }

    @FXML private void userIconSmall ( MouseEvent mouseEvent ) {
        userIcon.setStyle ( "" );
//        userIcon.setFitWidth ( 72 );
    }

    @FXML private void productIconBig ( MouseEvent mouseEvent ) {
        productIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
//        productIcon.setFitWidth ( 3000 );
    }

    @FXML private void productIconSmall ( MouseEvent mouseEvent ) {
        productIcon.setStyle ( "" );
//        productIcon.setFitWidth ( 200 );
    }

    @FXML private void discountIconBig ( MouseEvent mouseEvent ) {
        discountIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
//        discountIcon.setFitWidth ( 220 );
    }

    @FXML private void discountIconSmall ( MouseEvent mouseEvent ) {
        discountIcon.setStyle ( "" );
//        discountIcon.setFitWidth ( 200 );
    }
}
