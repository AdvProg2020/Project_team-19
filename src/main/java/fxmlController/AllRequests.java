package fxmlController;

import controller.RequestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import model.Request;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllRequests implements Initializable {

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Request request : RequestController.getAllRequests()) {
            RequestCard card = new RequestCard(request);
            FXMLLoader loader = new FXMLLoader(AllRequests.class.getResource("/fxml/requestCard.fxml"));
            loader.setController(card);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            vBox.getChildren().add(parent);
        }
    }
}
