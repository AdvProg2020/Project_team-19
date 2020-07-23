package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;

public class GetBankInfo implements Initializable {
    private Stage stage;
    @FXML public TextField password;
    @FXML public TextField username;
    @FXML private Button submit;

    public GetBankInfo(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setOnAction(event -> submit());
    }

    void submit() {
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "", "fill everything.");
            return;
        }
        FileCard.username = username.getText();
        FileCard.password = password.getText();
        stage.close();
    }
}
