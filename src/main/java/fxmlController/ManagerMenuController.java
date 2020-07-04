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

public class ManagerMenuController {

    @FXML
    private FontAwesomeIcon back;

    @FXML
    void back ( MouseEvent event ) {
        App.currentScene = App.firstScene;
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML
    void categories ( ActionEvent event ) {
        try {
            App.currentScene = new Scene(getFXMLLoader("AllCategoriesMenu").load());
            App.currentStage.setScene(App.currentScene);
        } catch (Exception e) {
            App.error(e.getMessage());
        }
    }

    @FXML
    void discountCodes ( ActionEvent event ) {
        //todo alireza
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
    void reqs ( ActionEvent event ) {
        App.setRoot("AllRequests");
    }

    @FXML
    void users ( ActionEvent event ) {
        App.setRoot ( "users" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

}
