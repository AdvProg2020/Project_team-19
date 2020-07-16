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
    public static Scene firstScene; //ToDo feilan injow mizarimesh
    private double xOffset, yOffset;

    public static AudioClip akh = new AudioClip ( new File ( "src/main/resources/akh.mp3" ).toURI ().toString () );
    public static AudioClip bop = new AudioClip ( new File ( "src/main/resources/bop.mp3" ).toURI ().toString () );
    public static AudioClip click = new AudioClip ( new File ( "src/main/resources/mouseClick.mp3" ).toURI ().toString () );
    public static AudioClip chaChing = new AudioClip ( new File ( "src/main/resources/chaChing.mp3" ).toURI ().toString () );


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ServerConnection.run();
        App.currentStage = primaryStage;

        AnchorPane root = getFXMLLoader("mainMenu").load();

        currentScene = new Scene(root);
        primaryStage.setTitle("Bruh");
        primaryStage.setScene(currentScene);

        AudioClip background = new AudioClip ( new File ( "src/main/resources/Soul_and_Mind.mp3" ).toURI ().toString () );
        background.setCycleCount ( AudioClip.INDEFINITE );
        background.play ();

        primaryStage.show();

        mainRun();
    }

    private void mainRun() {
        initializer();
        App.manageDiscountCodeTimer();
        App.manageDiscountTimer();
        App.manageAuctionTimer();
        mainMenu = new MainMenu(null);
        userMenu = new UserMenu(mainMenu);
    }

    public static void manageDiscountCodeTimer() {
        Timer timer = new Timer();
        TimerTask task = new DiscountCodeTimer();
        timer.schedule(task, new Date(), 60000);
    }

    public static void manageDiscountTimer() {
        Timer timer = new Timer();
        TimerTask task = new DiscountTimer();
        timer.schedule(task, new Date(), 60000);
    }

    public static void manageAuctionTimer() {
        Timer timer = new Timer();
        TimerTask timerTask = new AuctionTimer();
        timer.schedule(timerTask, new Date(), 60000);
    }

    public static void initializer() {
        Database.createDatabase();
        Database.initializeAddress();
        ProductController.getInstance().initializeProducts();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance().initializePersons();
        ProductController.getInstance().initializeStock();
        RequestController.getInstance().initializeRequests();
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
        Person person = PersonController.getInstance().getLoggedInPerson();
        if (person instanceof Manager) {
            App.setRoot("managerMenu");
        } else if (person instanceof Salesperson) {
            App.setRoot("salespersonMenu");
        } else {
            App.setRoot("customerMenu");
        }
    }

}
