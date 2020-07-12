package fxmlController;

import controller.CategoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import client.view.App;

import java.net.URL;
import java.util.ResourceBundle;

import static client.view.App.getFXMLLoader;

public class CategoryCard implements Initializable {
    private Category category;
    @FXML private ImageView removeIcon;
    @FXML private ImageView editIcon;
    @FXML private Label categoryName;
    @FXML private ImageView childArrow;


    public CategoryCard(Category category) {
        this.category = category;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryName.setText(category.getName());
        categoryName.setFont(Font.font("Verdana", 10));
        if (category.getParent()!=null)
            childArrow.setVisible(true);
        editIcon.setOnMouseClicked(event -> editIcon());
        removeIcon.setOnMouseClicked(event -> removeIcon());
    }

    private void editIcon() {
        try {
            FXMLLoader loader = getFXMLLoader("addCategory");
            AddCategory addCategory = new AddCategory(category);
            loader.setController(addCategory);
            App.currentScene = new Scene(loader.load());
            App.currentStage.setScene ( App.currentScene );
        } catch (Exception e) {
            App.error(e.getMessage());
        }
    }

    private void removeIcon() {
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
            remove();
            stage.close();
        });

        no.setOnMouseClicked(event -> stage.close());


        Scene scene = new Scene(gridPane, 300, 180);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void remove() {
        CategoryController.getInstance().removeCategory(category.getParent(), category);
    }
}
