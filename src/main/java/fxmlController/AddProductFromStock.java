package fxmlController;

import controller.PersonController;
import controller.ProductController;
import controller.RequestController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Product;
import model.Salesperson;
import view.App;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductFromStock implements Initializable {
    @FXML private TextField price;
    @FXML private TextField amount;
    @FXML private ChoiceBox<String> products;
    @FXML private Button actionButton;
    @FXML private FontAwesomeIcon back;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> ids = new ArrayList<>();
        for (Product product : ProductController.allProducts) {
            ids.add(product.getID());
        }
        products.setItems(FXCollections.observableArrayList(ids));
        actionButton.setOnAction(e->add());

    }

    private void add(){
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getLoggedInPerson();
        if (price.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
            return;
        }
        if (amount.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
            return;
        }
        if (!price.getText().matches("\\d+(.\\d+)?")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Enter number for price");
            return;
        }
        if (!amount.getText().matches("\\d+")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Enter number for amount");
            return;
        }
        if (products.getValue().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
            return;
        }
        RequestController.getInstance().addProductRequest(Double.parseDouble(price.getText()),Integer.parseInt(amount.getText()),salesperson,ProductController.getInstance().getProductById(products.getValue()));
    }

    @FXML
    void back() {
        App.setRoot ( "salespersonMenu" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }
}

