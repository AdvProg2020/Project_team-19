package fxmlController;

import controller.DiscountCodeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DiscountCodeHandler {
    @FXML
    private Label errorLabel;

    @FXML
    private ImageView tickImage;

    @FXML
    private TextField codeField;

    @FXML
    private Button confirmButton;

    @FXML
    void confirmOnClick(ActionEvent event) {
        if (codeField.getText().length()==0){
            errorLabel.setText("Please Enter The Discount Code.");
        }else
        if (DiscountCodeController.getInstance().isThereDiscountCodeByCode(codeField.getText())){
            errorLabel.setText("The Entered Code Is Wrong.");
        }else {
            errorLabel.setText("Successful.");
            errorLabel.setTextFill(Color.web("#79d2a6"));
            tickImage.setVisible(true);
        }
    }
}
