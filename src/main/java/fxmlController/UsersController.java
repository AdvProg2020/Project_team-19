package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Person;
import view.App;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    public TableView < UserForTable > tableView;
    public TableColumn < UserForTable,String > nameColumn;
    public TableColumn< UserForTable,String > typeColumn;

    @FXML
    private FontAwesomeIcon back;

    @FXML private void back () {
        App.goBack ();
    }

    @FXML
    private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        ArrayList<UserForTable> users = new ArrayList <> (  );
        for (Person person : PersonController.allPersons) {
            users.add ( new UserForTable ( person.getUsername () , person.getType () ) );
        }
        nameColumn.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
        typeColumn.setCellValueFactory ( new PropertyValueFactory <> ( "type" ) );

        tableView.setItems ( FXCollections.observableArrayList ( users ) );
    }

}