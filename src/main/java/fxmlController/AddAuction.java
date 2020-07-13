package fxmlController;

import controller.ProductController;
import controller.RequestController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import model.Product;
import model.Salesperson;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class AddAuction implements Initializable {
    private Salesperson salesperson;
    private Product product;

    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> chooseCombo;
    @FXML
    private TextField endTime;
    @FXML
    private Button submit;
    @FXML
    private FontAwesomeIcon back;

    public AddAuction(Salesperson salesperson) {
        this.salesperson = salesperson;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCombo();
        setProductOnBase();

        submit.setOnAction(event -> submit());

        back.setOnMouseClicked ( event -> App.setRoot ( "salespersonMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

    }

    private void setCombo() {
        for (Product product : salesperson.getOfferedProducts().keySet()) {
            if (!salesperson.getProductState(product).label.equals("Verified") && salesperson.getProductAmount(product) == 0)
                continue;
            chooseCombo.getItems().add(product.getID());
        }
    }

    private void setProductOnBase() {
        AtomicReference<Pane> productPane = new AtomicReference<>(new Pane());
        chooseCombo.setOnMouseClicked(event -> {
            productPane.get().setVisible(false);
            if (chooseCombo.getValue() != null) {
                productPane.set(new Pane());
                product = ProductController.getInstance().getProductById(chooseCombo.getValue());
                ProductForSeller productForSeller = new ProductForSeller(product, salesperson, true);
                FXMLLoader loader = new FXMLLoader(AddAuction.class.getResource("/fxml/productForSellerCard.fxml"));
                loader.setController(productForSeller);
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                productPane.get().getChildren().add(parent);
                productPane.get().setLayoutX(70);
                productPane.get().setLayoutY(230);
                pane.getChildren().add(productPane.get());
            }
        });
    }

    private void submit() {
        if (endTime.getText().isEmpty() || !endTime.getText().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Fill all", "Enter end time(correctly)");
            return;
        }
        if (chooseCombo.getValue().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Fill all", "Choose product");
            return;
        }

        RequestController.getInstance().addAuctionRequest(salesperson.getUsername(), product.getID(), endTime.getText());
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "successful", "Your request will be sent to manager");
        App.setRoot("salespersonMenu");
    }

}
