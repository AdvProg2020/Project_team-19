package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Manager;
import model.Person;
import model.Salesperson;
import view.App;

import java.io.IOException;

import static view.App.getFXMLLoader;
import static view.LoginMenu.usernamePattern;

public class UserLoginMenuController {

    @FXML
    private AnchorPane content;

    @FXML
    private FontAwesomeIcon back;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private void checkIfEmpty () throws Exception {
        if (username.getText ().isEmpty () ||
                password.getText ().isEmpty ())
            throw new Exception ( "Fields Should Be Filled!" );
    }

    private void checkIfExistNot () throws Exception {
        if (!PersonController.getInstance ( ).isTherePersonByUsername ( username.getText () ))
            throw new Exception ( "You Don't Exist. Go Make Yourself." );
    }

    private void checkValidity () throws Exception {
        if (username.getText ().equalsIgnoreCase ( "nigger" ))
            throw new Exception ( "Username Can't Be Nigger." );
        if ( !usernamePattern.matcher ( username.getText () ).matches ( ) )
            throw new Exception ( "Username Should Contain More Than 3 Characters And Not Contain Spaces." );
    }

    @FXML
    void loginBtnAction() {
        try {
            checkIfEmpty ();
            checkIfExistNot ();
            checkValidity ();
            PersonController.getInstance ( ).checkPassword ( password.getText () , username.getText () );
            PersonController.getInstance ().login ( username.getText () );
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
                    "Successfully Logged In! Ok?",
                    ok,
                    cancel);
            alert.showAndWait ();
//            back ();
            App.firstScene = new Scene ( getFXMLLoader ( "mainMenu" ).load () );
            Person person = PersonController.getInstance ().getLoggedInPerson ();
            if ( person instanceof Manager ) {
                App.currentScene = new Scene ( getFXMLLoader ( "managerMenu" ).load () );
            } else if ( person instanceof Salesperson ){
                App.currentScene = new Scene ( getFXMLLoader ( "salespersonMenu" ).load () );
            } else {
                App.currentScene = new Scene ( getFXMLLoader ( "customerMenu" ).load () );
            }
            App.currentStage.setScene ( App.currentScene );
        } catch (Exception e) {
            App.error ( e.getMessage () );
        }
    }

    @FXML
    void registerBtnAction() throws IOException {
        Parent fxml = getFXMLLoader ("registerMenu").load ();
        content.getChildren ().removeAll ();
        content.getChildren ().setAll ( fxml );
    }

    @FXML private void back () {
        App.currentScene = App.firstScene;
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML
    void closeApp () {
        System.exit ( 0 );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }
}

