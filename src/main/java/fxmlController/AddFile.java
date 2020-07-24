package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.App;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;
public class AddFile implements Initializable {

    private String path = "";
    @FXML
    private TextField fileDescription;

    @FXML
    private TextField fileName;

    @FXML
    private Button submit;

    @FXML
    private TextField price;

    @FXML
    private Button chooseFile;

    @FXML
    private FontAwesomeIcon back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooseFile.setCursor(Cursor.HAND);
        submit.setCursor(Cursor.HAND);
        chooseFile.setOnMouseClicked(event -> choose());
        submit.setOnMouseClicked(event -> submit());
    }

    void choose() {
        Stage stage = new Stage();
        stage.setTitle("FileChooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Files");
        File file = fileChooser.showOpenDialog (stage);

        if (file != null) {
            path = file.getAbsolutePath();
        }
    }

    void submit() {
        if (fileDescription.getText().isEmpty() || fileName.getText().isEmpty() || price.getText().isEmpty() || path.isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "", "fill everything");
            return;
        }
        if (!price.getText().matches("^\\d*(\\.\\d+)?$")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "", "enter price correctly");
            return;
        }
        addFileRequest(fileName.getText(), fileDescription.getText(), price.getText(), path);
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "", "your request will be sent to manager.");
        App.setRoot("salespersonMenu");
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @FXML void back ( MouseEvent event ) {
        App.setRoot("salespersonMenu");
    }
}
