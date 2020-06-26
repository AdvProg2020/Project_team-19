package fxmlController;

import controller.PersonController;
import controller.RequestController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import model.Discount;
import model.Manager;
import model.Person;
import model.Salesperson;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;

import static view.App.getFXMLLoader;

public class DiscountCard implements Initializable {
    private Discount discount;
    public DiscountCard(Discount discount) {
        this.discount = discount;
    }

    @FXML
    private Label percentage;

    @FXML
    private Label startTime;

    @FXML
    private Label endTime;

    @FXML
    void edit(ActionEvent event) {
        try {
            FXMLLoader loader = getFXMLLoader("discountRequestHandler");
            DiscountRequestHandler handler = new DiscountRequestHandler(discount);
            loader.setController(handler);
            App.currentScene = new Scene(loader.load());
            App.currentStage.setScene ( App.currentScene );
        } catch (Exception e) {
            App.error(e.getMessage());
        }
    }

    @FXML
    void remove(ActionEvent event) {
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
        yes.setOnMouseClicked(evt -> {
            App.showAlert(Alert.AlertType.INFORMATION, App.currentStage,"delete discount", "your request will be sent to manager.");
            removeDiscount();
            stage.close();
        });

        no.setOnMouseClicked(evt -> stage.close());


        Scene scene = new Scene(gridPane, 300, 180);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void removeDiscount() {
        RequestController.getInstance().deleteDiscountRequest(discount, (Salesperson) PersonController.getInstance().getLoggedInPerson());
    }

    @FXML
    void showProducts(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        percentage.setText(String.valueOf(discount.getDiscountPercentage()));
        startTime.setText(discount.getStartTime().toString());
        endTime.setText(discount.getEndTime().toString());
    }
}
