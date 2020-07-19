package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Product;
import model.Salesperson;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import static clientController.ServerConnection.*;

public class AllAuctionsMenu implements Initializable {

    private GridPane cardsBase;
    private ArrayList<Parent> auctionCards;
    @FXML
    FontAwesomeIcon back;
    @FXML
    AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardsBase = new GridPane();
        auctionCards = new ArrayList<>();

        cardsBase.setHgap(5);
        cardsBase.setVgap(5);
        cardsBase.setLayoutY(65);
        cardsBase.setLayoutX(20);
        cardsBase.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));

        pane.getChildren().add(cardsBase);
        setCardsOnPaneAndMouseClicked();
    }

    private void handleClickOnAuction(Salesperson salesperson, Product product, Parent card) {
        MainMenuController.isInAllAuction = false;
        AuctionMenu auctionMenu = new AuctionMenu(salesperson, product, card);
        FXMLLoader loader = new FXMLLoader(AllAuctionsMenu.class.getResource("/fxml/auctionMenu.fxml"));
        loader.setController(auctionMenu);
        App.setRoot(loader);
    }

    private void setCardsOnPaneAndMouseClicked() {
        int index = 0;
        HashMap<Salesperson, ArrayList<Product>> allAuctions = getAllAuctions();
        System.out.println(allAuctions);
        for (Salesperson seller : allAuctions.keySet()) {
            for (Product product : allAuctions.get(seller)) {
                AuctionInList auctionInList = new AuctionInList(seller, product);
                FXMLLoader loader = new FXMLLoader(AllAuctionsMenu.class.getResource("/fxml/auctionInList.fxml"));
                loader.setController(auctionInList);
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert parent != null;
                auctionCards.add(parent);
                cardsBase.add(parent, index % 3, index / 3);
                Parent finalParent = parent;
                parent.setOnMouseClicked(event -> handleClickOnAuction(seller, product, finalParent));
                index++;
            }
        }
    }

    @FXML
    void back ( MouseEvent event ) {
        MainMenuController.isInAllAuction = false;
        App.setRoot ( "mainMenu" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }
}
