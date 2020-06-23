package view;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Customer;
import model.Manager;
import model.Person;
import model.Salesperson;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static view.Menu.mainMenu;
import static view.Menu.userMenu;

public class App extends Application {

    public static Scene currentScene;
    public static Stage currentStage;

    public static Scene firstScene; //ToDo feilan injow mizarimesh

    private double xOffset,yOffset;

    public static void main ( String[] args ) {
        launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws IOException {
        App.currentStage = primaryStage ;

        AnchorPane root = getFXMLLoader ("mainMenu").load ();
        root.setOnMouseClicked ( event -> System.out.println ( event.getX () + " " + event.getY () ) );

        currentScene = new Scene ( root );
        primaryStage.setTitle ( "Bruh" );
        primaryStage.setScene( currentScene );
//        primaryStage.initStyle( StageStyle.UNDECORATED);
//        primaryStage.setResizable ( false ); //felan
//        root.setOnMousePressed( event -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        } );
//        root.setOnMouseDragged( event -> {
//            primaryStage.setX(event.getScreenX() - xOffset);
//            primaryStage.setY(event.getScreenY() - yOffset);
//        } );

        primaryStage.show();

        mainRun ();
    }

    private void mainRun () {
        initializer ();
        App.manageDiscountCodeTimer ();
        App.manageDiscountTimer ();
        mainMenu = new MainMenu ( null );
        userMenu =  new UserMenu(mainMenu);
    }

    public static void manageDiscountCodeTimer(){
        Timer timer = new Timer();
        TimerTask task = new DiscountCodeTimer ();
        timer.schedule(task,60000);
    }

    public static void manageDiscountTimer(){
        Timer timer = new Timer();
        TimerTask task = new DiscountTimer ();
        timer.schedule(task,60000);
    }

    public static void initializer(){
        Database.createDatabase ();
        Database.initializeAddress ( );
        ProductController.getInstance ().initializeProducts ();
        CategoryController.getInstance().initializeRootCategories();
        PersonController.getInstance ().initializePersons ();
        ProductController.getInstance ().initializeStock ();
        RequestController.getInstance ().initializeRequests ();
    }

    public static FXMLLoader getFXMLLoader ( String fxml ) {
        return new FXMLLoader ( App.class.getResource ( "/fxml/" + fxml + ".fxml"));
    }

    public static void error (String message) {
        Alert alert = new Alert ( Alert.AlertType.ERROR );
        alert.setContentText ( message );
        alert.showAndWait ();
    }

    public static void setRoot(String fxml) {
        try {
            currentScene.setRoot ( getFXMLLoader ( fxml ).load () );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
    public static void setRoot(FXMLLoader fxmlLoader) {
        try {
            currentScene.setRoot ( fxmlLoader.load () );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public static void goBack () {
        Person person = PersonController.getInstance ().getLoggedInPerson ();
        if (person instanceof Manager ) {
            App.setRoot ( "managerMenu" );
        } else if (person instanceof Salesperson ) {
            App.setRoot ( "salespersonMenu" );
        } else {
            App.setRoot ( "customerMenu" );
        }
    }


}
