package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import static clientController.ServerConnection.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllProductsForSeller implements Initializable { //todo
    private ArrayList<Parent> productCards;
    private Salesperson salesperson;
    private boolean verified;
    private GridPane cardBase;
    @FXML private AnchorPane basePane;
    @FXML private FontAwesomeIcon back;
    @FXML private Label title;
    @FXML private Button add;

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
        basePane.getChildren().add(cardBase);
        cardBase.setLayoutY(65);
        cardBase.setLayoutX(130);
        back.setOnMouseClicked ( event -> App.setRoot ( "salespersonMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );
        cardBase.setAlignment(Pos.CENTER);
        add.setOnAction(event -> add());
    }

    private void add() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        Button newProd = new Button("add from stock");
        Button fromStock = new Button("add new product");

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));


        newProd.getStylesheets().add("/fxml/button.css");
        newProd.getStyleClass().add("btn");

        fromStock.getStylesheets().add("/fxml/button.css");
        fromStock.getStyleClass().add("btn");

        newProd.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(AllProductsForSeller.class.getResource("/fxml/addRequestFromStock.fxml"));
            App.setRoot(loader);
            stage.close();
        });
        fromStock.setOnAction(event -> {
            Salesperson salesperson = getPersonByToken(Salesperson.class);
            ProductRequestFXML productRequestFXML = new ProductRequestFXML(Request.RequestState.ADD, salesperson);
            FXMLLoader loader = new FXMLLoader(AllProductsForSeller.class.getResource("/fxml/productRequest.fxml"));
            loader.setController(productRequestFXML);
            App.setRoot(loader);
            stage.close();
        });

        gridPane.add ( newProd , 0 , 0 );
        gridPane.add ( fromStock , 0 , 1 );
        Scene scene = new Scene(gridPane, 300, 200);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void setProductCards() {
        ArrayList<Product> products = verified ? getVerifiedSellerProducts() : getAllSellerProducts();
        for (Product product : products) {
//                if (!salesperson.getProductState(product).label.equals("Verified") || salesperson.getProductAmount(product) == 0)
//                    continue;

            ProductForSeller productForSeller = new ProductForSeller(product, salesperson, false);
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
