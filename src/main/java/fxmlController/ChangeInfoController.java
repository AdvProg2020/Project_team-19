package fxmlController;

import controller.PersonController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Salesperson;

import java.net.URL;
import java.util.ResourceBundle;

import static view.LoginMenu.PersonInfo.PASSWORD;

public class ChangeInfoController implements Initializable {

    @FXML private Button cancel;
    @FXML private ChoiceBox<String> field;
    @FXML private TextField text;
    @FXML private PasswordField password;

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        ObservableList <String> typeItems;
        if ( PersonController.getInstance ().getLoggedInPerson () instanceof Salesperson )
            typeItems = FXCollections.observableArrayList ( "Password","First Name","Last Name","Email","Phone Number","Company","Dar Surate Vjud Sayere Moshakhsat" );
        else
            typeItems = FXCollections.observableArrayList ( "Password","First Name","Last Name","Email","Phone Number" );
        field.setItems ( typeItems );
        field.setValue ( "Password" );
        text.setVisible ( false );

        field.setOnAction ( event -> {
            if (field.getValue ().equals ( "Password" )) {
                password.setVisible ( true );
                password.setText ( "" );
                text.setVisible ( false );
            } else {
                password.setVisible ( false );
                text.setVisible ( true );
                text.setPromptText ( "" );
                text.setPromptText ( field.getValue () );
            }
        } );
    }

    @FXML private void done () {
        if (field.getValue ().equals ( "Password" )) {
            PersonController.getInstance ().getLoggedInPerson ().setField ( PASSWORD.label , password.getText () );
        } else {
            System.out.println ( field.getValue ().toLowerCase () );
            PersonController.getInstance ().getLoggedInPerson ().setField ( field.getValue ().toLowerCase () , text.getText () );
        }
        Metadata.personInfoController.updateTable ();
        cancel();
    }

    @FXML private void cancel () {
        Stage stage = (Stage) cancel.getScene ( ).getWindow ( );
        stage.close ( );
    }
}
