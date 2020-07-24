package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static view.App.getFXMLLoader;
import static clientController.ServerConnection.*;

public class MainMenuController implements Initializable {
    public static boolean isInAllAuction = true;
    private String type = "null";
    @FXML private ImageView productIcon;
    @FXML private ImageView discountIcon;
    @FXML private ImageView userIcon;
    @FXML private ImageView auctionIcon;
    @FXML private ImageView walletIcon;


//    @FXML Button productMenu;
//    @FXML Button discountMenu;
//    @FXML Button back;
//    @FXML Button help;

//    @FXML private Button userMenu;

    @FXML private void userMenuAction () throws IOException {
        App.akh.play ();
        App.firstScene = App.currentScene;
        type = getPersonTypeByToken();
        if ( !type.equals("null") && !type.equals("invalid token.") ) {
            switch (type) {
                case "manager":
                    App.currentScene = new Scene(getFXMLLoader("managerMenu").load());
                    break;
                case "salesperson":
                    App.currentScene = new Scene(getFXMLLoader("salespersonMenu").load());
                    break;
                case "customer":
                    App.currentScene = new Scene(getFXMLLoader("customerMenu").load());
                    break;
                case "support":
                    App.currentScene = new Scene (getFXMLLoader("supportMenu").load ());
            }
        } else {
            App.currentScene = new Scene ( getFXMLLoader ("userLoginMenu").load () );
        }
        App.currentStage.setScene ( App.currentScene );
    }

    @FXML private void productAction () {
        MainProductsMenu.isDiscount = false;
        App.akh.play ();
        App.setRoot ( "mainProductsMenu" );
    }

    @FXML void discountAction() {
        MainProductsMenu.isDiscount = true;
        App.akh.play ();
        App.setRoot ( "mainProductsMenu" );
    }

    @FXML void allAuction() {
        isInAllAuction = true;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isInAllAuction) {
                    App.setRoot("allAuctionsMenu");
                    System.out.println("load");
                } else
                    timer.cancel();
            }
        };
        timer.schedule(timerTask, new Date(), 60000);
    }

    @FXML void walletClicked() {
        App.setRoot("walletMenu");
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        type = getPersonTypeByToken();
        if (type.equals("customer") || type.equals("salesperson")) {
            walletIcon.setDisable(false);
            walletIcon.setVisible(true);
        }
        if (type.equals("customer")) {
            auctionIcon.setDisable(false);
            auctionIcon.setVisible(true);
        }
    }

    @FXML private void userIconBig ( MouseEvent mouseEvent ) {
//        userIcon.setStyle ( "-fx-pref-width: 250; -fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
        userIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
//        userIcon.setFitWidth ( 75 );

    }

    @FXML private void userIconSmall ( MouseEvent mouseEvent ) {
        userIcon.setStyle ( "" );
//        userIcon.setFitWidth ( 72 );
    }

    @FXML private void productIconBig ( MouseEvent mouseEvent ) {
        productIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
//        productIcon.setFitWidth ( 3000 );
    }

    @FXML private void productIconSmall ( MouseEvent mouseEvent ) {
        productIcon.setStyle ( "" );
//        productIcon.setFitWidth ( 200 );
    }

    @FXML private void auctionIconBig ( MouseEvent mouseEvent ) {
        auctionIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
    }

    @FXML private void auctionIconSmall ( MouseEvent mouseEvent ) {
        auctionIcon.setStyle ( "" );
    }

    @FXML private void discountIconBig ( MouseEvent mouseEvent ) {
        discountIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
//        discountIcon.setFitWidth ( 220 );
    }

    @FXML private void discountIconSmall ( MouseEvent mouseEvent ) {
        discountIcon.setStyle ( "" );
//        discountIcon.setFitWidth ( 200 );
    }

    @FXML private void walletIconBig() {
        walletIcon.setStyle ( "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" );
    }

    @FXML private void walletIconSmall() {
        walletIcon.setStyle ( "" );
    }
}
