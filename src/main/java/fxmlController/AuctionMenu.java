package fxmlController;

import controller.AuctionController;
import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Person;
import model.Product;
import model.Salesperson;
import view.App;
import view.MainMenu;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static fxmlController.MainMenuController.isInAllAuction;

public class AuctionMenu implements Initializable {
    private Salesperson salesperson;
    private Product product;
    private Parent productCard;
    @FXML
    private TextField offerPrice;
    @FXML
    private ImageView submitPrice;
    @FXML
    private FontAwesomeIcon back;
    @FXML
    private AnchorPane pane;

    public AuctionMenu(Salesperson salesperson, Product product, Parent productCard) {
        this.salesperson = salesperson;
        this.product = product;
        this.productCard = productCard;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitPrice.setOnMouseClicked(event -> submitPrice());

        pane.getChildren().add(productCard);
        productCard.setLayoutX(50);
        productCard.setLayoutY(100);

        back.setOnMouseClicked ( event -> {
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
            timer.schedule(timerTask, new Date(), 2000);
        } );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

    }

    private void submitPrice() {
        if (offerPrice.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "enter price", "Enter price");
            return;
        }
        if (!offerPrice.getText().matches("^\\d*(\\.\\d+)?$")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "enter price", "Enter price (like 20.10)");
            return;
        }
        //todo send server req Wallet wallet = ((Customer)PersonController.getInstance().getLoggedInPerson()).getBalance();
        //todo getResponse
        double balance = -1;
        if (Double.parseDouble(offerPrice.getText()) > balance) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "faghiri", "Enter price less than your balance in wallet");
            return;
        }
        Person person = PersonController.getInstance().getLoggedInPerson();
        AuctionController.getInstance().addBuyer(product.getID(), salesperson.getUsername(), person.getUsername(), Double.parseDouble(offerPrice.getText()));
    }

}
