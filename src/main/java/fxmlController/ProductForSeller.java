package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;
import model.Request;
import model.Salesperson;

import javafx.scene.shape.Line;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;

public class ProductForSeller implements Initializable {
    private Product product;
    private Salesperson salesperson;
    private boolean inAuction;
    @FXML private Label amount;
    @FXML private ImageView productImage;
    @FXML private Line priceLine;
    @FXML private Label price;
    @FXML private Label priceInDiscount;
    @FXML private Label name;
    @FXML private StackPane pricePane;
    @FXML private Label state;
    @FXML private Label off;
    @FXML private ImageView inDiscount;
    @FXML private ImageView remove;
    @FXML private ImageView edit;

    public ProductForSeller(Product product, Salesperson salesperson, boolean inAuction) {
        this.product = product;
        this.salesperson = salesperson;
        this.inAuction = inAuction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (inAuction) {
            edit.setVisible(false);
            remove.setVisible(false);
        }
        if (salesperson.isInDiscount(product)) {
            inDiscount.setVisible(true);
            //todo
            off.setText(salesperson.getDiscountPercentage(product) + "%");
            priceInDiscount.setText(salesperson.getDiscountPrice(product) + "$");
        } else {
            priceLine.setVisible(false);
        }
        amount.setText("amount : " + salesperson.getProductAmount(product));
        state.setText(salesperson.getProductState(product).label + "");
        price.setText(salesperson.getProductPrice(product) + " $");
        name.setText(product.getName() + "(" + product.getID() + ")");
        if (!inAuction) {
            edit.setVisible(true);
            remove.setVisible(true);
            edit.setOnMouseClicked(event -> edit());
            remove.setOnMouseClicked(event -> remove());
        }
        if (product.getImageURI() != null) {
            productImage.setImage(new Image(product.getImageURI()));
        }
    }

    private void edit() {
        ProductRequestFXML productRequestFXML = new ProductRequestFXML(product, Request.RequestState.EDIT, salesperson);
        FXMLLoader loader = new FXMLLoader(AllProductsForSeller.class.getResource("/fxml/productRequest.fxml"));
        loader.setController(productRequestFXML);
        App.setRoot(loader);
    }

    private void remove() {
        Stage stage = new Stage();

        GridPane gridPane = new GridPane();

        Label sureness = new Label("Are you sure?");
        sureness.setFont(Font.font("Verdana", 18));

        ImageView yes = new ImageView(new Image("/images/yes.png"));
        ImageView no = new ImageView(new Image("/images/no.png"));

        yes.setFitHeight(30);
        yes.setFitWidth(30);
        no.setFitHeight(30);
        no.setFitWidth(30);

        HBox hBox = new HBox();

        gridPane.add(sureness, 0, 0);
        hBox.getChildren().add(0, yes);
        hBox.getChildren().add(1, no);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        gridPane.add(hBox, 0, 1);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);

        gridPane.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));

        yes.setCursor(Cursor.HAND);
        no.setCursor(Cursor.HAND);
        yes.setOnMouseClicked(event -> {
            App.showAlert(Alert.AlertType.INFORMATION, App.currentStage,  "request for delete product", "Your request will be sent to manager.");
            removeProduct();
            stage.close();
        });

        no.setOnMouseClicked(event -> stage.close());


        Scene scene = new Scene(gridPane, 300, 180);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void removeProduct() {
        deleteProductRequest(product.getID());
    }
}
