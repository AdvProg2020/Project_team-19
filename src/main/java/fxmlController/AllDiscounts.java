package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Discount;
import model.Salesperson;
import client.view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllDiscounts implements Initializable {
    @FXML
    private AnchorPane basePane;
    @FXML
    private FontAwesomeIcon back;

    @FXML
    void newDiscount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader ( AllCategoriesMenu.class.getResource ( "/fxml/discountRequest.fxml"));
            DiscountRequestHandler requestHandler = new DiscountRequestHandler();
            loader.setController(requestHandler);
            App.currentScene = new Scene(loader.load());
            App.currentStage.setScene ( App.currentScene );
        } catch (Exception e) {
            App.error(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GridPane gridpane = new GridPane();
        int i=1;
        Salesperson salesperson = (Salesperson) PersonController.getInstance().getLoggedInPerson();
        for (Discount discount : salesperson.getDiscounts()) {
            DiscountCard discountCard = new DiscountCard(discount);
            FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/discountCard.fxml"));
            loader.setController(discountCard);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert parent != null;
            gridpane.add(parent,0,i++);
        }
        basePane.getChildren().add(gridpane);
        gridpane.setLayoutY(60);
        gridpane.setLayoutX(10);

        back.setOnMouseClicked ( event -> App.setRoot ( "salespersonMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

    }
}
