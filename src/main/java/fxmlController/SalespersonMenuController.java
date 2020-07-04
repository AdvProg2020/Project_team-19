package fxmlController;


import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import model.Salesperson;
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
        Salesperson salesperson = (Salesperson)PersonController.getInstance().getLoggedInPerson();
        AllProductsForSeller allProductsForSeller = new AllProductsForSeller(salesperson, true);
        FXMLLoader loader = new FXMLLoader(SalespersonMenuController.class.getResource("/fxml/allProductsForSellerMenu.fxml"));
        loader.setController(allProductsForSeller);
        App.setRoot(loader);
    }

    @FXML
    void discounts ( ActionEvent event ) {
        FXMLLoader loader = new FXMLLoader(SalespersonMenuController.class.getResource("/fxml/allDiscounts.fxml"));
        App.setRoot(loader);
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
        Salesperson salesperson = (Salesperson)PersonController.getInstance().getLoggedInPerson();
        AllProductsForSeller allProductsForSeller = new AllProductsForSeller(salesperson, false);
        FXMLLoader loader = new FXMLLoader(SalespersonMenuController.class.getResource("/fxml/allProductsForSellerMenu.fxml"));
        loader.setController(allProductsForSeller);
        App.setRoot(loader);
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
