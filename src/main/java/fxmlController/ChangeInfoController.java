package fxmlController;

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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;
import static view.LoginMenu.PersonInfo.PASSWORD;
import static view.LoginMenu.PersonInfo.PROFILE;

public class ChangeInfoController implements Initializable { //todo

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
        if ( getPersonTypeByToken().equals("salesperson") )
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
        //todo
//        if (field.getValue ().equals ( "Password" ) && !password.getText ().isEmpty ()) {
//            PersonController.getInstance ().getLoggedInPerson ().setField ( PASSWORD.label , password.getText () );
//        } else if (!text.getText ().isEmpty ()) {
//            System.out.println ( field.getValue ( ).toLowerCase ( ) );
//            PersonController.getInstance ( ).getLoggedInPerson ( ).setField ( field.getValue ( ).toLowerCase ( ) , text.getText ( ) );
//        }
//        PersonController.getInstance ().getLoggedInPerson ().setField ( PROFILE.label , profileFileString );
//        Metadata.personInfoController.updateTable ();
//        cancel();
    }

    @FXML private void cancel () {
        Stage stage = (Stage) cancel.getScene ( ).getWindow ( );
        stage.close ( );
    }
}
