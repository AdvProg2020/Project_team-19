package fxmlController;

import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;

public class SellLogView implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Label deliveredAmount;

    @FXML
    private Label discountAmount;

    @FXML
    private Label product;

    @FXML
    private Label count;

    @FXML
    private Label buyer;

    @FXML
    private Label transmitted;

    @FXML
    private FontAwesomeIcon back;

    @FXML
    void back() {
        App.setRoot ( "sellBuyLogs" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        date.setText (  Metadata.date );
        deliveredAmount.setText ( String.valueOf ( Metadata.deliveredAmount ) );
        discountAmount.setText ( String.valueOf ( Metadata.discountAmount ));
        product.setText ( Metadata.product );
        count.setText ( String.valueOf ( Metadata.count ) );
        buyer.setText ( Metadata.buyerUsername );
        transmitted.setText ( String.valueOf ( Metadata.transmitted ) );
    }

}
