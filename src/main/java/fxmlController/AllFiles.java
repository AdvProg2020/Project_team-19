package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.FileProduct;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static clientController.ServerConnection.*;

public class AllFiles implements Initializable {
    private final GridPane gridPane = new GridPane();

    @FXML
    AnchorPane base;

    @FXML
    FontAwesomeIcon back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCardsOnPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setLayoutX(80);
        gridPane.setLayoutY(65);
        gridPane.setAlignment(Pos.CENTER);
        base.getChildren().add(gridPane);
    }

    private void setCardsOnPane() {
        int row = 0;
        for (FileProduct file : getAllFiles()) {
            FileCard fileCard = new FileCard(file);
            FXMLLoader loader = new FXMLLoader(AllFiles.class.getResource("/fxml/fileCard.fxml"));
            loader.setController(fileCard);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gridPane.add(parent, 0, row);
            row++;
        }
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @FXML void back ( MouseEvent event ) {
        App.setRoot("mainMenu");
    }
}
