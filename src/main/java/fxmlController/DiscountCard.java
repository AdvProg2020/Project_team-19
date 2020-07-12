package fxmlController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.Discount;
import client.view.App;

import java.net.URL;
import java.util.ResourceBundle;

import static client.view.App.getFXMLLoader;

public class DiscountCard implements Initializable {
    private Discount discount;
    public DiscountCard(Discount discount) {
        this.discount = discount;
    }

    @FXML
    private Label percentage;

    @FXML
    private Label startTime;

    @FXML
    private Label endTime;

    @FXML
    void edit(ActionEvent event) {
        try {
            FXMLLoader loader = getFXMLLoader("discountRequest");
            DiscountRequestHandler handler = new DiscountRequestHandler(discount);
            loader.setController(handler);
            App.currentScene = new Scene(loader.load());
            App.currentStage.setScene ( App.currentScene );
        } catch (Exception e) {
            App.error(e.getMessage());
        }
    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void showProducts(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        percentage.setText(String.valueOf(discount.getDiscountPercentage()));
        startTime.setText(discount.getStartTime().toString());
        endTime.setText(discount.getEndTime().toString());
    }
}
