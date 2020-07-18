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
import model.Manager;
import model.Product;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductInList implements Initializable {
    @FXML private ImageView productImage;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private HBox starRate;
    @FXML private ImageView finished;
    @FXML private ImageView deleteProduct;


    private Product product;

    public ProductInList(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (PersonController.getInstance().getLoggedInPerson() != null && PersonController.getInstance().getLoggedInPerson() instanceof Manager) {
            deleteProduct.setVisible(true);
            deleteProduct.setCursor(Cursor.HAND);
            deleteProduct.setOnMouseClicked(event -> {
                deleteProduct();
            });
        }
        editLabel(productNameLabel, product.getName());
        int index = ("" + product.getAveragePrice()).indexOf(".");
        editLabel(priceLabel, ("" + product.getAveragePrice()).substring(0, index + 2) + "$");
        setScoreRate();
        checkFinished();
        if (product.getImageURI() != null) {
            productImage.setImage(new Image(product.getImageURI()));
        }
    }

    private void deleteProduct() {
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
            stage.close();
            removeProduct();
        });

        no.setOnMouseClicked(event -> stage.close());


        Scene scene = new Scene(gridPane, 300, 180);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void removeProduct() {
        ProductController.getInstance().removeProductForManager(product);
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage,"remove product", "removed product successfully");
    }

    private void checkFinished() {
        if (ProductController.getInstance().isProductAvailable(product)) {
            finished.setVisible(false);
        }
    }

    private void setScoreRate() {
        StarRate starRate = new StarRate(product);
        FXMLLoader loader = new FXMLLoader(ProductMenu.class.getResource("/fxml/starRate.fxml"));
        loader.setController(starRate);
        try {
            this.starRate.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editLabel(Label label, String text) {
        label.setText(text);
        label.setFont(Font.font("Verdana", 10));
    }
}
