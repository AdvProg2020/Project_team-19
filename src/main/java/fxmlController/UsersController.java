package fxmlController;

import clientController.ServerConnection;
import controller.CartController;
import controller.PersonController;
import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
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
import java.util.*;

import static view.App.getFXMLLoader;

public class UsersController implements Initializable {

    public TableView < UserForTable > tableView;
    public TableColumn < UserForTable,String > nameColumn;
    public TableColumn< UserForTable,String > typeColumn;
    public TableColumn < UserForTable, String > statusColumn;
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
                            System.out.println ( ServerConnection.removeUser( new ArrayList<String> () {{
                                add ( userForTable.getName () );
                            }} ) );
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
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ArrayList<UserForTable> users = new ArrayList <> (  );
                    HashMap <String,String> allPersons = ServerConnection.getAllPersonInfo ();
                    allPersons.forEach ( (k,v) -> users.add ( new UserForTable ( k , v ) ) );

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

                    statusColumn.setCellValueFactory ( param -> {
                        UserForTable user = param.getValue ();
                        if (user != null) {
                            if (ServerConnection.isOnline ( new ArrayList<String>() {{
                                add ( user.getName () );
                            }} )) {
                                return new SimpleStringProperty ( "■" );
                            } else return new SimpleStringProperty ( "□" );
                        } else return new SimpleStringProperty ("<no name>");
                    } );

                    statusColumn.setStyle ( "-fx-alignment: center; -fx-font-family: 'Consolas'; -fx-font-size: 20; -fx-font-color: white; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );


                    tableView.setItems ( FXCollections.observableArrayList ( users ) );
                });
                Platform.setImplicitExit(false);
            }
        };
        timer.schedule(timerTask, new Date(), 1000);
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


    public void newOneSupport ( ActionEvent event ) {
        Parent root = null;
        try {
            root = getFXMLLoader ( "addSupport" ).load ();
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
        Stage window = new Stage ( );
        window.setTitle ( "Add A Support" );
        assert root != null;
        Scene scene = new Scene ( root , 600 , 300 );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.centerOnScreen ();
        window.showAndWait ();
        updateTable ();
    }
}