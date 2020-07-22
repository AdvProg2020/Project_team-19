package fxmlController;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;
import static clientController.ServerConnection.*;
public class UserMenuController implements Initializable {

    @FXML private Button sellBuy;
    @FXML private Label type;
    @FXML private AnchorPane page;

    @FXML private void back () {
        System.out.println("back in usermenu");
//        App.currentScene = App.firstScene;
//        App.currentStage.setScene ( App.currentScene );
    }

    @FXML private void logout () throws IOException {
        sendLogout();
        App.currentScene = new Scene ( getFXMLLoader ( "mainMenu" ).load () , 600 , 400);
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML private void personalInfo () {
        App.setRoot ( "personInfo" );
    }

    @FXML private void sellBuyAction () {
        App.setRoot ( "sellBuyLogs" );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {

    }
}
