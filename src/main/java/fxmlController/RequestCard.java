package fxmlController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Request;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestCard implements Initializable {
    Request request;

    public RequestCard(Request request){
        this.request = request;
    }
    @FXML
    private Label requestText;

    @FXML
    private Button acceptButton;

    @FXML
    private Button declineButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestText.setText(request.show());
        acceptButton.setOnAction(e -> {
            request.doThis();
        });
        declineButton.setOnAction(e -> {
            request.decline();
        });
    }
}
