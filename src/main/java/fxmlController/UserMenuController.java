package fxmlController;

import controller.PersonController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Manager;
import model.Salesperson;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

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
        PersonController.getInstance().logOut();
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
