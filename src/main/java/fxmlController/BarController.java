package fxmlController;

import controller.PersonController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.Person;
import view.App;

import java.io.IOException;

import static view.App.getFXMLLoader;

public class BarController {  //todo
    @FXML private Button userMenu;

    @FXML private void userMenuAction () throws IOException {
        App.firstScene = App.currentScene;
        if ( PersonController.getInstance ().isThereLoggedInPerson () ) {
            Person person = PersonController.getInstance ().getLoggedInPerson ();
            App.currentScene = new Scene ( getFXMLLoader ( "userMenu" ).load () );
        } else {
            App.currentScene = new Scene ( getFXMLLoader ("userLoginMenu").load () );
        }
        App.currentStage.setScene ( App.currentScene );
    }
}
