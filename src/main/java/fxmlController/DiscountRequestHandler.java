package fxmlController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Discount;
import model.DiscountRequest;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscountRequestHandler implements Initializable {

    Discount discount;

    enum requestMode {
        EDIT, ADD
    }

    public DiscountRequestHandler() {
        mode = requestMode.ADD;
    }

    public DiscountRequestHandler(Discount discount) {
        this.discount = discount;
        mode = requestMode.EDIT;
    }

    private requestMode mode;

    @FXML
    private TextField percentage;

    @FXML
    private DatePicker startTime;

    @FXML
    private DatePicker endTime;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (mode.equals(requestMode.EDIT)) {
            percentage.setText(String.valueOf(discount.getDiscountPercentage()));
        }
    }

    void send(ActionEvent event) {
        //DiscountRequest discountRequest = new DiscountRequest();
        if (mode.equals(requestMode.EDIT)) {

        } else {

        }
    }
}
