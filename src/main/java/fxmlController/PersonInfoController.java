package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Manager;
import model.Person;
import model.Salesperson;
import model.Support;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static view.App.getFXMLLoader;
import static view.LoginMenu.PersonInfo.*;

public class PersonInfoController implements Initializable {

    @FXML private ImageView profile;

    @FXML private TableView<String[]> tableView;

    @FXML private TableColumn<String[],String> fieldColumn;
    @FXML private TableColumn<String[],String> valueColumn;

    @FXML private FontAwesomeIcon back;


    @FXML private void back () {
        App.goBack ();
    }

    @Override
    public void initialize ( URL url , ResourceBundle resourceBundle ) {
        Metadata.personInfoController = this;
        updateTable ();
    }

    public void updateTable () {
        HashMap<String,String> personInfo = PersonController.getInstance ().getLoggedInPerson ().getPersonInfo ();

        if (personInfo.get ( PROFILE.label ) != null)
            profile.setImage ( new Image ( personInfo.get ( PROFILE.label ) ) );

        String[][] data = new String[6][2];
        data[0] = new String[]{"Username",personInfo.get ( USERNAME.label )};
        data[1] = new String[]{"Real Name",personInfo.get ( FIRST_NAME.label )+" "+personInfo.get ( LAST_NAME.label )};
        data[2] = new String[]{"Email",personInfo.get ( EMAIL.label )};
        data[3] = new String[]{"Phone Number",personInfo.get ( PHONE.label )};
        if (personInfo.get ( "type" ).equalsIgnoreCase ( "salesperson" )) {
            data[4] = new String[]{"Company",personInfo.get ( COMPANY.label )};
            data[5] = new String[]{"Dar Surate Vjud Sayere Moshakhasat",personInfo.get ( SAYERE_MOSHAKHASAT.label ) };
        } else {
            data[4] = new String[]{"",""};
            data[5] = new String[]{"",""};
        }

        fieldColumn.setCellValueFactory( p -> {
            String[] x = p.getValue();
            if (x != null && x.length>0) {
                return new SimpleStringProperty(x[0]);
            } else {
                return new SimpleStringProperty("<no name>");
            }
        } );

        valueColumn.setCellValueFactory( p -> {
            String[] x = p.getValue();
            if (x != null && x.length>1) {
                return new SimpleStringProperty(x[1]);
            } else {
                return new SimpleStringProperty("<no value>");
            }
        } );

        fieldColumn.setStyle ( "-fx-alignment: center;-fx-font-family: 'Consolas'; -fx-font-size: 20; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );
        valueColumn.setStyle ( "-fx-alignment: center;-fx-font-family: 'Consolas'; -fx-font-size: 20; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );


        tableView.getItems().setAll(data);
    }

    @FXML private void changeInfo () throws IOException {
        Parent root = getFXMLLoader ( "changeInfo" ).load ();
        Stage window = new Stage ( );
        window.setTitle ( "Change Info" );
        Scene scene = new Scene ( root , 600 , 300 );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.centerOnScreen ();
        window.show ();
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }
}
