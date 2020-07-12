package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.DiscountCode;
import client.view.App;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiscountCodes implements Initializable {

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
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #d90582,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        ArrayList <DiscountCode> users = new ArrayList <> (  );

//        for (Person person : PersonController.allPersons) {
//            users.add ( new UserForTable ( person.getUsername () , person.getType () ) );
//        }

        codeColumn.setCellValueFactory ( new PropertyValueFactory<> ( "code" ) );
        startColumn.setCellValueFactory ( new PropertyValueFactory<> ( "startTime" ) );
        endColumn.setCellValueFactory ( new PropertyValueFactory<> ( "endTime" ) );
        percentColumn.setCellValueFactory ( new PropertyValueFactory<> ( "discountPercentage" ) );
        maxColumn.setCellValueFactory ( new PropertyValueFactory<> ( "maxDiscount" ) );
        countColumn.setCellValueFactory ( new PropertyValueFactory<> ( "useCounter" ) );

        tableView.setItems ( FXCollections.observableArrayList ( users ) );
    }

}
