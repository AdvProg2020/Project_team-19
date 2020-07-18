package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.App;

import java.util.HashMap;

import static view.LoginMenu.PersonInfo.*;
import static view.LoginMenu.usernamePattern;
import static clientController.ServerConnection.*;

public class AddManager {

    @FXML private Button cancelBtn;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phone;

    @FXML private Button registerBtn;

    private void checkIfEmpty () throws Exception {
        if (username.getText ().isEmpty () ||
                password.getText ().isEmpty () ||
                firstName.getText ().isEmpty () ||
                lastName.getText ().isEmpty () ||
                email.getText ().isEmpty () ||
                phone.getText ().isEmpty ()
                )
            throw new Exception ( "Fields should be filled!" );
    }

    private void checkIfExist () throws Exception {
        String response = getPersonByUsername(username.getText());
        if ( !response.equalsIgnoreCase("invalid username."))
            throw new Exception ( "This Dude Already Exists." );
    }

    private void checkValidity () throws Exception {
        if (username.getText ().equalsIgnoreCase ( "nigger" ))
            throw new Exception ( "Username Can't Be Nigger." );
        if ( !usernamePattern.matcher ( username.getText () ).matches ( ) )
            throw new Exception ( "Username Should Contain More Than 3 Characters And Not Contain Spaces." );
    }

    @FXML private void registerBtnAction () {
        try {
            checkIfEmpty ();
            checkIfExist();
            checkValidity ();
            HashMap < String, String > personInfo = new HashMap<>();
            personInfo.put ( USERNAME.label , username.getText () );
            personInfo.put ( PASSWORD.label , password.getText () );
            personInfo.put ( "type" , "manager" );
            personInfo.put ( FIRST_NAME.label , firstName.getText () );
            personInfo.put ( LAST_NAME.label , lastName.getText () );
            personInfo.put ( EMAIL.label , email.getText () );
            personInfo.put ( PHONE.label , phone.getText () );
            String response = sendRegisterRequest(personInfo);
            ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
                    response,
                    ok,
                    cancel);
            alert.showAndWait ();
            cancelBtnAction ();
        } catch (Exception e) {
            App.error ( e.getMessage () );
        }
    }

    @FXML private void cancelBtnAction () {
        Stage stage = (Stage) cancelBtn.getScene ( ).getWindow ( );
        stage.close ( );
    }
}
