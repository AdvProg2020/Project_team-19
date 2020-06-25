package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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

public class AllProductsForSeller implements Initializable {
    private ArrayList<Parent> productCards;
    private Salesperson salesperson;
    private boolean verified;
    private GridPane cardBase;
    @FXML private GridPane basePane;
    @FXML private FontAwesomeIcon back;
    @FXML private Label title;

    public AllProductsForSeller(Salesperson salesperson, boolean verified) {
        this.salesperson = salesperson;
        this.verified = verified;
        productCards = new ArrayList<>();
        cardBase = new GridPane();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!verified)
            title.setText("Your products");
        setProductCards();
        addProductCardsToPane();
        cardBase.setBackground((new Background(new BackgroundFill(Color.rgb(153, 221, 255), CornerRadii.EMPTY, Insets.EMPTY))));
        basePane.add(cardBase, 1, 2);
        back.setOnMouseClicked ( event -> App.setRoot ( "salespersonMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );
        cardBase.setAlignment(Pos.CENTER);
    }

    private void setProductCards() {
        for (Product product : salesperson.getOfferedProducts().keySet()) {
            if (verified) {
                if (!salesperson.getProductState(product).label.equals("Verified") && salesperson.getProductAmount(product) == 0)
                    continue;
            }
            ProductForSeller productForSeller = new ProductForSeller(product, salesperson);
            FXMLLoader loader = new FXMLLoader(AllProductsForSeller.class.getResource("/fxml/productForSellerCard.fxml"));
            loader.setController(productForSeller);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            productCards.add(parent);
        }
    }

    private void addProductCardsToPane() {
        for (int i = 0; i < productCards.size(); i++) {
            cardBase.add(productCards.get(i), i % 3, i / 3);
        }
    }
}
