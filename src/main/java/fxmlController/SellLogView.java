package fxmlController;

import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;

public class SellLogView implements Initializable {

    @FXML
    private TextArea date;

    @FXML
    private TextArea deliveredAmount;

    @FXML
    private TextArea discountAmount;

    @FXML
    private TextArea product;

    @FXML
    private TextArea count;

    @FXML
    private TextArea buyer;

    @FXML
    private TextArea transmitted;

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
        date.setStyle("-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; ");
        deliveredAmount.setStyle ( "-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; " );
        discountAmount.setStyle ( "-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; " );
        product.setStyle ( "-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; " );
        count.setStyle ( "-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; " );
        buyer.setStyle ( "-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; " );
        transmitted.setStyle ( "-fx-control-inner-background:#89b7ff; -fx-font-family: Consolas; -fx-highlight-fill: #000000; -fx-highlight-text-fill: #89b7ff; -fx-text-fill: #000000; " );
        date.setText (  Metadata.date );
        deliveredAmount.setText ( String.valueOf ( Metadata.deliveredAmount ) );
        discountAmount.setText ( String.valueOf ( Metadata.discountAmount ));
        product.setText ( Metadata.product );
        count.setText ( String.valueOf ( Metadata.count ) );
        buyer.setText ( Metadata.buyerUsername );
        transmitted.setText ( String.valueOf ( Metadata.transmitted ) );
    }

}
