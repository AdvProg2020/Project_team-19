package fxmlController;

import clientController.ServerConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Salesperson;
import view.App;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static clientController.ServerConnection.*;
import static view.LoginMenu.PersonInfo.PASSWORD;
import static view.LoginMenu.PersonInfo.PROFILE;

public class ChangeInfoController implements Initializable {

    @FXML private ImageView profile;
    private File profileFile;
    private String profileFileString;
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
            typeItems = FXCollections.observableArrayList ( "Password","First Name","Last Name","Email","Phone Number","Min_balance","Wage" );
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

        profileFileString =  getPersonInfoByToken().get(PROFILE.label);

        if (profileFileString != null)
            profile.setImage ( new Image ( profileFileString ) );

        Stage stage = new Stage();
        stage.setTitle("FileChooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        profile.setOnMouseClicked ( event -> {
            if (event.getClickCount () == 2) {
                profileFile = fileChooser.showOpenDialog ( stage );

                if (profileFile != null) {
                    profile.setImage ( new Image (profileFile.toURI().toString()) );
                    profileFileString = profileFile.toURI ().toString ();
                }
            }
        } );

    }

    @FXML private void done () {
//        if (field.getValue ().equals ( "Password" ) && !password.getText ().isEmpty ()) {
//            PersonController.getInstance ().getLoggedInPerson ().setField ( PASSWORD.label , password.getText () );
//        } else if (!text.getText ().isEmpty ()) {
//            System.out.println ( field.getValue ( ).toLowerCase ( ) );
//            PersonController.getInstance ( ).getLoggedInPerson ( ).setField ( field.getValue ( ).toLowerCase ( ) , text.getText ( ) );
//        }
//        PersonController.getInstance ().getLoggedInPerson ().setField ( PROFILE.label , profileFileString );
        ArrayList<String> info = new ArrayList <> ();
        info.add ( field.getValue ().toLowerCase () );
        try {
            if ( (field.getValue ( ).equals ( "Password" )) ) {
                if ( !password.getText ( ).isEmpty ( ) ) {
                    info.add ( password.getText ( ) );
                    ServerConnection.changeInfo ( info );
                }
            } else {
                if ( !text.getText ( ).isEmpty ( ) ) {
                    if ( field.getValue ( ).equals ( "Wage" ) ) {
                        if ( text.getText ( ).matches ( "^\\d*(\\.\\d+)?$" ) && Integer.parseInt ( text.getText ( ) ) < 100 ) {
                            info.add ( text.getText ( ) );
                            ServerConnection.changeInfo ( info );
                        } else App.error ( "error" );
                    } else if ( field.getValue ( ).equals ( "Min_balance" ) ) {
                        if ( text.getText ( ).matches ( "^\\d*(\\.\\d+)?$" ) ) {
                            info.add ( text.getText ( ) );
                            ServerConnection.changeInfo ( info );
                        } else App.error ( "error" );
                    } else {
                        info.add ( text.getText ( ) );
                        ServerConnection.changeInfo ( info );
                    }
                }
            }
        } catch (Exception e) {
            App.error ( e.getMessage () );
        }
        Metadata.personInfoController.updateTable ();
        cancel();
    }

    @FXML private void cancel () {
        Stage stage = (Stage) cancel.getScene ( ).getWindow ( );
        stage.close ( );
    }
}
