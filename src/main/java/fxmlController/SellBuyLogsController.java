package fxmlController;

import controller.PersonController;
import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import model.*;
import view.App;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;

public class SellBuyLogsController implements Initializable { //todo

    public TableView<LogForTable> tableView;
    public TableColumn<LogForTable,String> dateColumn;
    public TableColumn<LogForTable,String> productColumn;
    public TableColumn < LogForTable, String > costColumn;

    @FXML private FontAwesomeIcon back;
    @FXML private Label label;

    Person person;
    ArrayList<LogForTable> scoreLogs;

    @FXML private void back () {
        App.goBack ();
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        //todo person = PersonController.getInstance ().getLoggedInPerson ();
        if (person instanceof Customer ) {
            buyLogs ( );
            label.setText ( "Buy Logs" );
        }
        else {
            sellLogs ( );
            label.setText ( "Sell Logs" );
        }

        dateColumn.setCellValueFactory ( new PropertyValueFactory<> ( "date" ) );
        productColumn.setCellValueFactory ( new PropertyValueFactory<> ( "products" ) );
        costColumn.setCellValueFactory ( new PropertyValueFactory<> ( "cost" ) );
//        tableView.setStyle ( "-fx-font-family: 'JetBrains Mono'" );
        tableView.setItems ( FXCollections.observableArrayList ( scoreLogs ) );

//        dateColumn.setCellFactory ( customCell () );
//        costColumn.setCellFactory ( customCell () );
//        productColumn.setCellFactory ( customCell () );


        if (person instanceof Customer ) {
            tableView.setRowFactory(tv -> {
                TableRow<LogForTable> row = new TableRow <> ();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        LogForTable rowData = row.getItem();
                        Metadata.date = row.getItem ().getDate ();
                        //todo BuyLog buyLog = ((Customer) PersonController.getInstance ().getLoggedInPerson ()).getBuyLogAtTime ( Metadata.date );
//                        StringBuilder stringBuilder = new StringBuilder (  );
//                        buyLog.getProducts ().forEach ( (key,value) -> stringBuilder.append ( ProductController.getInstance ( ).getProductById ( key ) ).append ( " :\n" ).append ( value ) );
//                        Metadata.product = stringBuilder.toString ();
//                        Metadata.deliveredAmount = buyLog.getPaymentAmount ();
//                        Metadata.discountAmount = buyLog.getDiscountCodeAmount ();
//                        Metadata.transmitted = buyLog.isReachedBuyer ();
                        App.setRoot ( "buyLogView" );
                    }
                });
                return row ;
            });
        } else {
            tableView.setRowFactory(tv -> {
                TableRow<LogForTable> row = new TableRow <> ();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        LogForTable rowData = row.getItem();
                        Metadata.date = row.getItem ().getDate ();
                        Metadata.product = row.getItem ().getProducts ();
//                        SellLog sellLog = ((Salesperson) PersonController.getInstance ().getLoggedInPerson ()).getSellLogAtTime ( Metadata.date );
//                        Metadata.deliveredAmount = sellLog.getDeliveredAmount ();
//                        Metadata.discountAmount = sellLog.getDiscountAmount ();
//                        Metadata.buyerUsername = sellLog.getBuyer ().getUsername ();
//                        Metadata.count = sellLog.getCount ();
//                        Metadata.transmitted = sellLog.isTransmitted ();
                        App.setRoot ( "sellLogView" );
                    }
                });
                return row ;
            });
        }

//        costColumn.setStyle ( "-fx-font-family: 'JetBrains Mono'; -fx-font-size: 15; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );
//        costColumn.setCellValueFactory ( getCustomCellFactory("red") );
    }

    Callback<TableColumn<LogForTable,String>, TableCell<LogForTable,String>> customCell () {
        return new Callback<TableColumn<LogForTable,String>, TableCell<LogForTable,String>>() {
            @Override
            public TableCell<LogForTable,String> call(TableColumn param) {
                return new TableCell<LogForTable, String>()
                {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if(isEmpty())
                        {
                            setText("");
                        }
                        else
                        {
//                            setStyle ( "-fx-font-family: 'JetBrains Mono'; -fx-font-size: 15; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );

                            setFont ( Font.font ( "Consolas",20 ) );
                            setText ( item );

//                            setTextFill(Color.RED);
//                            setFont(Font.font ("Verdana", 20));
//                            setText(item);
                        }
                    }
                };
            }
        };
    }

    private void buyLogs () {
        ArrayList< BuyLog > buyLogs = ((Customer) person).getBuyLogs ();
        scoreLogs = new ArrayList <> (  );
        for (BuyLog buyLog : buyLogs) {
            ArrayList<String> products = getProducts(buyLog.getProducts ( ).keySet ( ));
            scoreLogs.add ( new LogForTable ( buyLog.getDate ( ) , products , buyLog.getPaymentAmount ( ) ) );
        }
    }

    private ArrayList<String> getProducts ( Set<String> set ) {
        ArrayList<String> arrayList = new ArrayList <> (  );
        for (String s : set) {
            arrayList.add ( ProductController.getInstance ().getProductById ( s ).getName () );
        }
        return arrayList;
    }

    private void sellLogs () {
        ArrayList< SellLog > sellLogs = ((Salesperson) person).getSellLogs ();
        scoreLogs = new ArrayList <> (  );
        for (SellLog sellLog : sellLogs) {
            scoreLogs.add ( new LogForTable ( sellLog.getDate ( ) , new ArrayList <> ( Collections.singletonList ( sellLog.getProduct ( ).getName ( ) ) ) , sellLog.getDeliveredAmount ( ) ) );
        }
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }


}
