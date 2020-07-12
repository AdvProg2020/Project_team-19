package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import client.view.App;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerDiscountCodesFXMLController implements Initializable {

    public TableView < DiscountCode > tableView;
    public TableColumn < DiscountCode,String > codeColumn;
    public TableColumn< DiscountCode,String > startColumn;
    public TableColumn< DiscountCode,String > endColumn;
    public TableColumn< DiscountCode,Double > percentColumn;
    public TableColumn< DiscountCode,Double > maxColumn;
    public TableColumn< DiscountCode,Integer > countColumn;

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

        updateTable();

    }

    public void updateTable() {
        ArrayList < DiscountCode > users = new ArrayList <> ( ((Customer) PersonController.getInstance ().getLoggedInPerson ()).getDiscountCodes ().keySet () );

        codeColumn.setCellValueFactory ( new PropertyValueFactory<> ( "code" ) );
        startColumn.setCellValueFactory ( new PropertyValueFactory<> ( "startTime" ) );
        endColumn.setCellValueFactory ( new PropertyValueFactory<> ( "endTime" ) );
        percentColumn.setCellValueFactory ( new PropertyValueFactory<> ( "discountPercentage" ) );
        maxColumn.setCellValueFactory ( new PropertyValueFactory<> ( "maxDiscount" ) );
        countColumn.setCellValueFactory( p -> {
            DiscountCode discountCode = p.getValue ();
            if (discountCode != null) {
                return new SimpleIntegerProperty (((Customer) PersonController.getInstance ().getLoggedInPerson ()).getDiscountCodes ().get ( discountCode )).asObject ();
            } else {
                return new SimpleIntegerProperty(0).asObject ();
            }
        } );

        tableView.setItems ( FXCollections.observableArrayList ( users ) );
    }


}
