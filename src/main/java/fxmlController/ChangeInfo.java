package fxmlController;

import clientController.ServerConnection;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

import static view.LoginMenu.PersonInfo.PASSWORD;

public class ChangeInfo implements Initializable {  //todo

    @FXML private Button cancel;
    @FXML private ChoiceBox<String> field;
    @FXML private TextField text;
    @FXML private PasswordField password;

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        ObservableList <String> typeItems;
        String type = ServerConnection.getPersonTypeByToken ();
        if ( type.equalsIgnoreCase ( "salesperson" ) )
            typeItems = FXCollections.observableArrayList ( "Password","First Name","Last Name","Email","Phone Number","Company","Dar Surate Vjud Sayere Moshakhsat" );
        else if ( type.equalsIgnoreCase ( "manager" ) )
            typeItems = FXCollections.observableArrayList ( "Password","First Name","Last Name","Email","Phone Number","Minimum Balance","Wage" );
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
        ServerConnection.changeInfo ( new ArrayList <String> () {{
            add ( field.getValue ().toLowerCase () );
            if (field.getValue ().equals ( "Password" ))
                add ( password.getText () );
            else
                add ( text.getText () );
        }} );
        Metadata.personInfoController.updateTable ();
        cancel();
    }

    @FXML private void cancel () {
        Stage stage = (Stage) cancel.getScene ( ).getWindow ( );
        stage.close ( );
    }
}
