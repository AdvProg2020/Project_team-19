package fxmlController;

import controller.RequestController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.DiscountRequest;
import model.ProductRequest;
import model.Request;
import model.SalespersonRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.Database.createPath;
import static controller.Database.deleteFile;

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
            RequestController.getInstance ().acceptRequest ( request );
            Metadata.allRequests.updateTable ();
        });
        declineButton.setOnAction(e -> {
            RequestController.getInstance ().declineRequest ( request );
            Metadata.allRequests.updateTable ();
        });
    }
}
