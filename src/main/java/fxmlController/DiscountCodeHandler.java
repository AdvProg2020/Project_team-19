package fxmlController;
import controller.CartController;
import controller.DiscountCodeController;
import controller.PersonController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Customer;
import model.DiscountCode;
import org.omg.CORBA.INITIALIZE;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscountCodeHandler implements Initializable {
    @FXML
    private Label errorLabel;

    @FXML
    private ImageView tickImage;

    @FXML
    private TextField codeField;

    @FXML
    private Button confirmButton;

    @FXML
    private ChoiceBox<String> codes;

    @FXML
    void confirmOnClick(ActionEvent event) {
        if (codes.getValue().length()==0){
            errorLabel.setText("Please Enter The Discount Code.");
        }
//        else if (!DiscountCodeController.getInstance().isThereDiscountCodeByCode(codes.getValue())){
//            errorLabel.setText("The Entered Code Is Wrong.");
//        }
        else {
            CartController.getInstance().manageDiscountCode(DiscountCode.findDiscountCodeByCode(codes.getValue()));
            errorLabel.setText("Successful.");
            errorLabel.setTextFill(Color.web("#79d2a6"));
            tickImage.setVisible(true);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (((Customer)PersonController.getInstance().getLoggedInPerson()).getDiscountCodesList().size()==0){
            showAlert(Alert.AlertType.ERROR, App.currentStage,"Bitch","You do not have any discount code.");
        }
        else
        codes.setItems(FXCollections.observableArrayList(((Customer)PersonController.getInstance().getLoggedInPerson()).getDiscountCodesList()));
    }

    public void showAlert(Alert.AlertType alertType, Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
