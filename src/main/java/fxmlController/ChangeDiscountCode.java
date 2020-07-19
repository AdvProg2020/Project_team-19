package fxmlController;

import controller.Database;
import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;
import model.DiscountCode;
import model.Person;
import model.Salesperson;
import view.App;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static view.LoginMenu.PersonInfo.PASSWORD;

public class ChangeDiscountCode implements Initializable { //todo

    public ChoiceBox< Customer > customer;
    public FontAwesomeIcon add;
    public FontAwesomeIcon del;
    public Label amount;
    public Button doneBtn;
    @FXML private Button cancel;
    @FXML private ChoiceBox<String> field;
    @FXML private TextField text;

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        ObservableList <String> typeItems;
        typeItems = FXCollections.observableArrayList ( "End Date","Percentage","Max Value","Count","Customers" );
        field.setItems ( typeItems );
        field.setValue ( "End Date" );

        customer.setVisible ( false );
        add.setVisible ( false );
        del.setVisible ( false );
        amount.setVisible ( false );

        field.setOnAction ( event -> {
            if (field.getValue ().equals ( "Customers" )) {
                text.setVisible ( false );
                customer.setVisible ( true );
                add.setVisible ( true );
                del.setVisible ( true );
                amount.setVisible ( true );
            } else {
                text.setPromptText ( field.getValue () );
                text.setVisible ( true );
                customer.setVisible ( false );
                add.setVisible ( false );
                del.setVisible ( false );
                amount.setVisible ( false );
            }
        } );

        ArrayList <Customer> customerArrayList = new ArrayList <> (  );
        for (Person person : PersonController.allPersons) {
            if (person instanceof Customer)
                customerArrayList.add ( (Customer) person );
        }


        customer.setItems ( FXCollections.observableArrayList ( customerArrayList ) );

        customer.setValue ( customerArrayList.get ( 0 ) );
        String amountString = String.valueOf ( customer.getValue ().getDiscountCodes ().get ( Metadata.discountCode ) );
        if (amountString.equals ( "null" ))
            amountString = "0";
        amount.setText ( amountString );


        add.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );

        if (customer.getValue ().getDiscountCodes ().containsKey ( Metadata.discountCode ))
            del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
        else
            del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20" );

        customer.setOnAction ( event -> {

            if (customer.getValue ().getDiscountCodes ().containsKey ( Metadata.discountCode ))
                del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
            else
                del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20" );

            if (String.valueOf ( customer.getValue ().getDiscountCodes ().get ( Metadata.discountCode ) ).equals ( "null" ))
                amount.setText ( "0" );
            else
                amount.setText ( String.valueOf ( customer.getValue ().getDiscountCodes ().get ( Metadata.discountCode ) ) );

        } );

        del.setOnMouseClicked ( event1 -> {
            if (customer.getValue ().getDiscountCodes ().containsKey ( Metadata.discountCode )) {
                customer.getValue ().removeDiscountCode ( Metadata.discountCode );
                if (!customer.getValue ().getDiscountCodes ().containsKey ( Metadata.discountCode ))
                    del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20" );
                amount.setText ( "0" );
                Database.saveToFile(customer.getValue (),Database.createPath("customers",customer.getValue ().getUsername()));
            }
        } );

        add.setOnMouseClicked ( event1 -> {
            customer.getValue ().addDiscountCode ( Metadata.discountCode , 1 );
            del.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #0ac615,75,0,5,0);" );
            amount.setText ( String.valueOf ( customer.getValue ().getDiscountCodes ().get ( Metadata.discountCode ) ) );
            Database.saveToFile(customer.getValue (),Database.createPath("customers",customer.getValue ().getUsername()));
        } );

    }

    @FXML private void done () {
        String string = text.getText ();
        switch (field.getValue ( )) { //Start Time isn't possible
            case "End Date":
                if ( Pattern.compile ( "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}" ).matcher ( string ).matches ( ) ) {
                    Metadata.discountCode.setEndTime ( LocalDateTime.parse ( string ) );
                    Database.saveToFile(Metadata.discountCode, Database.createPath("discount_codes",Metadata.discountCode.getCode()));
                }
                else
                    App.error ( "Couldn't Edit End Date" );
                break;
            case "Percentage":
                if ( Pattern.compile ( "\\d+" ).matcher ( string ).matches ( ) ) {
                    Metadata.discountCode.setDiscountPercentage ( Double.parseDouble ( string ) );
                    Database.saveToFile(Metadata.discountCode, Database.createPath("discount_codes",Metadata.discountCode.getCode()));
                }
                else
                    App.error ( "Couldn't Edit Percentage" );
                break;
            case "Max Value":
                if ( Pattern.compile ( "\\d+" ).matcher ( string ).matches ( ) ) {
                    Metadata.discountCode.setMaxDiscount ( Double.parseDouble ( string ) );
                    Database.saveToFile(Metadata.discountCode, Database.createPath("discount_codes",Metadata.discountCode.getCode()));
                }
                else
                    App.error ( "Couldn't Edit Max Value" );
                break;
            case "Count":
                if ( Pattern.compile ( "\\d+" ).matcher ( string ).matches ( ) ) {
                    Metadata.discountCode.setUseCounter ( Integer.parseInt ( string ) );
                    Database.saveToFile(Metadata.discountCode, Database.createPath("discount_codes",Metadata.discountCode.getCode()));
                }
                else
                    App.error ( "Couldn't Edit Usage Counter" );
                break;
        }
        Metadata.discountCodesFXMLController.updateTable ();
        Stage stage = (Stage) doneBtn.getScene ( ).getWindow ( );
        stage.close ( );
    }

}
