package view;

import clientController.ServerConnection;
import controller.*;
import fxmlController.AllCategoriesMenu;
import fxmlController.ProductsInCategory;
import fxmlController.ProductMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static view.Menu.mainMenu;
import static view.Menu.userMenu;

public class App extends Application {

    public static Scene currentScene;
    public static Stage currentStage;
    public static Scene firstScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ServerConnection.run();
        mainRun();
        App.currentStage = primaryStage;

        AnchorPane root = getFXMLLoader("mainMenu").load();

        currentScene = new Scene(root);
        primaryStage.setTitle("Bruh");
        primaryStage.setScene(currentScene);

        primaryStage.show();

        primaryStage.setOnCloseRequest ( event -> {
            ServerConnection.sendLogout ();
            ServerConnection.exit ();
        } );

        System.out.println ( "Hello To Terminal" );

    }

    private void mainRun() {
        mainMenu = new MainMenu(null);
        userMenu = new UserMenu(mainMenu);
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
    }

    public static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAlert(Alert.AlertType alertType, Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void setRoot(String fxml) {
        try {
            currentScene.setRoot(getFXMLLoader(fxml).load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(FXMLLoader fxmlLoader) {
        try {
            currentScene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goBack() {
        String person = ServerConnection.getPersonTypeByToken();
        switch (person) {
            case "manager":
                App.setRoot("managerMenu");
                break;
            case "salesperson":
                App.setRoot("salespersonMenu");
                break;
            case "support":
                App.setRoot("supportMenu");
                break;
            case "customer":
                App.setRoot("customerMenu");
                break;
        }
    }

}
