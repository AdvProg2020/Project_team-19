package fxmlController;

import controller.DiscountCodeController;
import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.Person;
import view.App;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NewDiscountCode implements Initializable {

    @FXML
    private AnchorPane content;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextField maxDiscount;

    @FXML
    private TextField discountPercent;

    @FXML
    private TextField usageCounter;

    @FXML
    private ChoiceBox< Customer > customers;

    @FXML
    private Button doneBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private FontAwesomeIcon add;

    @FXML
    private FontAwesomeIcon del;

    private ArrayList<Customer> includedCustomers;

    @FXML
    void doneBtnAction() {
        try {
            checkIfEmpty ();
            checkValidity();
            DiscountCodeController.getInstance().addNewDiscountCodeGraphics (
                    LocalDateTime.parse ( startTime.getText () ) ,
                    LocalDateTime.parse ( endTime.getText () ),
                    Double.parseDouble ( discountPercent.getText () ),
                    Double.parseDouble ( maxDiscount.getText () ),
                    Integer.parseInt ( usageCounter.getText () ),
                    includedCustomers);
            Metadata.discountCodesFXMLController.updateTable ();
            cancelBtnAction ();
        } catch (Exception e) {
            App.error ( e.getMessage () );
        }
    }

    @FXML
    void cancelBtnAction() {
        Stage stage = (Stage) cancelBtn.getScene ( ).getWindow ( );
        stage.close ( );
    }

    private void checkIfEmpty () throws Exception {
        if (startTime.getText ().isEmpty () ||
                endTime.getText ().isEmpty () ||
                maxDiscount.getText ().isEmpty () ||
                discountPercent.getText ().isEmpty () ||
                usageCounter.getText ().isEmpty () ||
                includedCustomers.isEmpty ())
            throw new Exception ( "Fields should be filled!" );
    }

    private void checkValidity () throws Exception {
        if ( !Pattern.compile ( "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}" ).matcher ( startTime.getText () ).matches ( ) ) {
            throw new Exception ( "Invalid Start Time" );
        } else if ( !Pattern.compile ( "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}" ).matcher ( endTime.getText () ).matches ( ) ) {
            throw new Exception ( "Invalid End Time" );
        } else if ( !Pattern.compile ( "\\d+" ).matcher ( maxDiscount.getText () ).matches ( ) ) {
            throw new Exception ( "Invalid Maximum Discount" );
        } else if ( !Pattern.compile ( "\\d+" ).matcher ( discountPercent.getText () ).matches ( ) ) {
            throw new Exception ( "Invalid Discount Percentage" );
        } else if ( !Pattern.compile ( "\\d+" ).matcher ( usageCounter.getText () ).matches ( ) ) {
            throw new Exception ( "Invalid Usage Counter" );
        }
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        ArrayList <Customer> customerArrayList = new ArrayList <> (  );
        for (Person person : PersonController.allPersons) {
            if (person instanceof Customer)
                customerArrayList.add ( (Customer) person );
        }

        customers.setItems ( FXCollections.observableArrayList ( customerArrayList ) );

        customers.setValue ( customerArrayList.get ( 0 ) );

        includedCustomers = new ArrayList <> (  );

        del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
        add.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;" );

        add.setOnMouseClicked ( event -> {
            if (!includedCustomers.contains ( customers.getValue () )) {
                add.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
                del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;" );
                includedCustomers.add ( customers.getValue () );
            }
        } );

        del.setOnMouseClicked ( event -> {
            if (includedCustomers.contains ( customers.getValue () )) {
                del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
                add.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;" );
                includedCustomers.remove ( customers.getValue () );
            }
        } );

        customers.setOnAction ( event -> {
            if (includedCustomers.contains ( customers.getValue () )) {
                add.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
                del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;" );
            } else {
                del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
                add.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;" );
            }
        } );

    }
}
