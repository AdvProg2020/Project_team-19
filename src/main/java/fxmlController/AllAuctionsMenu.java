package fxmlController;

import controller.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Product;
import model.Salesperson;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllAuctionsMenu implements Initializable {
    private GridPane cardsBase;
    private ArrayList<Parent> auctionCards;
    @FXML
    ImageView cart;
    @FXML
    ImageView wallet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardsBase = new GridPane();
        auctionCards = new ArrayList<>();

        cardsBase.setHgap(5);
        cardsBase.setVgap(5);
        cardsBase.setLayoutY(65);
        cardsBase.setLayoutX(20);
        cardsBase.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));

        setCardsOnPaneAndMouseClicked();
    }

    private void handleClickOnAuction(Salesperson salesperson, Product product) {
        AuctionMenu auctionMenu = new AuctionMenu(salesperson, product);
        FXMLLoader loader = new FXMLLoader(AllAuctionsMenu.class.getResource("/fxml/auctionMenu.fxml"));
        loader.setController(auctionMenu);
        App.setRoot(loader);
    }

    private void setCardsOnPaneAndMouseClicked() {
        int index = 0;
        for (Salesperson seller : ProductController.getInstance().getAllAuctions().keySet()) {
            for (Product product : seller.getAuctions().keySet()) {
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
                parent.setOnMouseClicked(event -> handleClickOnAuction(seller, product));
                index++;
            }
        }
    }

    @FXML
    void cartClicked(ActionEvent event) {
        CartMenuFXML cartMenuFXML = new CartMenuFXML("mainMenu");
        FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/cart.fxml"));
        loader.setController(cartMenuFXML);
        App.setRoot(loader);
    }

    @FXML
    void walletClicked(ActionEvent event) {
        //todo open wallet
    }
}
