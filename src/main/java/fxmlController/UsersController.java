package fxmlController;

import controller.CartController;
import controller.PersonController;
import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DiscountCode;
import model.Person;
import model.Salesperson;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

public class UsersController implements Initializable {

    public TableView < UserForTable > tableView;
    public TableColumn < UserForTable,String > nameColumn;
    public TableColumn< UserForTable,String > typeColumn;
    public TableColumn < UserForTable, String > removeColumn;

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
        updateTable ();

        removeColumn.setCellFactory(tc -> {
            TableCell <UserForTable, String> cell = new TableCell<UserForTable, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !cell.isEmpty()) {
                    UserForTable userForTable = (UserForTable) cell.getTableRow ().getItem ();
                    ButtonType ok = new ButtonType("Yeah", ButtonBar.ButtonData.OK_DONE);
                    ButtonType cancel = new ButtonType("Nope", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert( Alert.AlertType.CONFIRMATION,
                            "You sure you wanna delete " + userForTable.getName () + " ?",
                            ok,
                            cancel);
                    alert.setTitle("Life And Death Situation");
                    Optional < ButtonType > result = alert.showAndWait();
                    if (result.orElse(cancel) == ok) {
                        Person person = PersonController.getInstance ().getPersonByUsername ( userForTable.getName () );
                        try {
                            if (person == PersonController.getInstance ().getLoggedInPerson ())
                                throw new Exception ( "Don't Commit Suicide" );
                            if (person instanceof Salesperson ) {
                                CartController.getInstance ( ).removeSeller ( (Salesperson) person );
                                ProductController.getInstance ().removeSellerInStock ( (Salesperson) person );
                            }
                            PersonController.getInstance ().removePersonFromAllPersons ( person );
                            System.out.println ( "Removed successfully." );
                        } catch (Exception exception) {
                            App.error ( exception.getMessage () );
                        }
                    } else {
                        System.out.println ( "Cancelled" );
                    }
                    updateTable ();
                }
            });
            return cell ;
        });

    }

    private void updateTable () {
        ArrayList<UserForTable> users = new ArrayList <> (  );
        for (Person person : PersonController.allPersons) {
            users.add ( new UserForTable ( person.getUsername () , person.getType () ) );
        }
        nameColumn.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
        typeColumn.setCellValueFactory ( new PropertyValueFactory <> ( "type" ) );
        removeColumn.setCellValueFactory( p -> {
            UserForTable user = p.getValue ();
            if (user != null) {
                return new SimpleStringProperty ("X");
            } else {
                return new SimpleStringProperty("<no name>");
            }
        } );
        removeColumn.setStyle ( "-fx-alignment: center; -fx-font-family: 'Consolas'; -fx-font-size: 20; -fx-font-color: white; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );

        tableView.setItems ( FXCollections.observableArrayList ( users ) );
    }

    public void newOne () {
        Parent root = null;
        try {
            root = getFXMLLoader ( "addManager" ).load ();
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
        Stage window = new Stage ( );
        window.setTitle ( "Add A Manager" );
        assert root != null;
        Scene scene = new Scene ( root , 600 , 300 );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.centerOnScreen ();
        window.showAndWait ();
        updateTable ();
    }
}