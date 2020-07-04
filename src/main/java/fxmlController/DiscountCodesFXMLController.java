package fxmlController;

import controller.DiscountCodeController;
import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DiscountCode;
import model.Person;
import model.Salesperson;
import model.SellLog;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

public class DiscountCodesFXMLController implements Initializable {

    public TableView < DiscountCode > tableView;
    public TableColumn < DiscountCode,String > codeColumn;
    public TableColumn< DiscountCode,String > startColumn;
    public TableColumn< DiscountCode,String > endColumn;
    public TableColumn< DiscountCode,Double > percentColumn;
    public TableColumn< DiscountCode,Double > maxColumn;
    public TableColumn< DiscountCode,Integer > countColumn;
    public TableColumn < DiscountCode, String > editColumn;

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
        Metadata.discountCodesFXMLController = this;
        updateTable();

    }

    public void updateTable() {
        ArrayList < DiscountCode > users = new ArrayList <> ( DiscountCodeController.getInstance ( ).getAllDiscountCodes ( ) );


        codeColumn.setCellValueFactory ( new PropertyValueFactory<> ( "code" ) );
        startColumn.setCellValueFactory ( new PropertyValueFactory<> ( "startTime" ) );
        endColumn.setCellValueFactory ( new PropertyValueFactory<> ( "endTime" ) );
        percentColumn.setCellValueFactory ( new PropertyValueFactory<> ( "discountPercentage" ) );
        maxColumn.setCellValueFactory ( new PropertyValueFactory<> ( "maxDiscount" ) );
        countColumn.setCellValueFactory ( new PropertyValueFactory<> ( "useCounter" ) );
        editColumn.setCellValueFactory( p -> {
            DiscountCode discountCode = p.getValue ();
            if (discountCode != null) {
                return new SimpleStringProperty ("+");
            } else {
                return new SimpleStringProperty("<no name>");
            }
        } );
        editColumn.setStyle ( "-fx-alignment: center; -fx-font-family: 'Consolas'; -fx-font-size: 20; -fx-font-color: white; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );

                editColumn.setCellFactory(tc -> {
            TableCell <DiscountCode, String> cell = new TableCell<DiscountCode, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !cell.isEmpty()) {
                    Metadata.discountCode = (DiscountCode) cell.getTableRow ().getItem ();
                    Parent root = null;
                    try {
                        root = getFXMLLoader ( "changeDiscountCode" ).load ();
                    } catch (IOException ioException) {
                        ioException.printStackTrace ( );
                    }
                    Stage window = new Stage ( );
                    window.setTitle ( "Change Discount Code" );
                    assert root != null;
                    Scene scene = new Scene ( root , 600 , 300 );
                    window.setScene ( scene );
                    window.initModality ( Modality.APPLICATION_MODAL );
                    window.centerOnScreen ();
                    window.show ();
                }
            });
            return cell ;
        });


        tableView.setItems ( FXCollections.observableArrayList ( users ) );
    }

    public void newOne ( ActionEvent event ) {
        Parent root = null;
        try {
            root = getFXMLLoader ( "newDiscountCode" ).load ();
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
        Stage window = new Stage ( );
        window.setTitle ( "New Discount Code" );
        assert root != null;
        Scene scene = new Scene ( root , 600 , 300 );
        window.setScene ( scene );
        window.initModality ( Modality.APPLICATION_MODAL );
        window.centerOnScreen ();
        window.show ();
    }

}
