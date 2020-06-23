package fxmlController;


import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import view.App;

import java.io.IOException;

import static view.App.getFXMLLoader;

public class SalespersonMenuController {

    @FXML
    private FontAwesomeIcon back;

    @FXML
    void back ( MouseEvent event ) {
        App.currentScene = App.firstScene;
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML
    void availableProd ( ActionEvent event ) {

    }

    @FXML
    void category ( ActionEvent event ) {

    }

    @FXML
    void discounts ( ActionEvent event ) {

    }

    @FXML
    void logout ( ActionEvent event ) throws IOException {
        PersonController.getInstance().logOut();
        App.currentScene = new Scene ( getFXMLLoader ( "mainMenu" ).load () );
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML
    void personalInfo ( ActionEvent event ) {
        App.setRoot ( "personInfo" );
    }

    @FXML
    void prod ( ActionEvent event ) {

    }

    @FXML
    void sellLogs ( ActionEvent event ) {
        App.setRoot ( "sellBuyLogs" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

}
