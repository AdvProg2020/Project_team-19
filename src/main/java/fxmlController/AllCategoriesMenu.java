package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import model.Category;
import view.App;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;

public class AllCategoriesMenu implements Initializable {
    private GridPane gridPane;
    @FXML private GridPane basePane;
    @FXML private FontAwesomeIcon back;

    @FXML
    void addCategory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader ( AllCategoriesMenu.class.getResource ( "/fxml/addCategory.fxml"));
            AddCategory addCategory = new AddCategory();
            loader.setController(addCategory);
            App.currentScene = new Scene(loader.load());
            App.currentStage.setScene ( App.currentScene );
        } catch (Exception e) {
            App.error(e.getMessage());
        }
    }

    private void initializeCategories( ArrayList<Category> categories, int column, int row) {
        for (Category category : categories) {
            setCategoryCard(category, column, row++);
            if (category.getChildren().size() != 0) {
                initializeCategories(category.getChildren(), column + 1, row++);
                row++;
            }
        }
    }

    private void setCategoryCard(Category category, int column, int row) {
        CategoryCard categoryCard = new CategoryCard(category);
        FXMLLoader loader = new FXMLLoader(AllCategoriesMenu.class.getResource("/fxml/categoryCard.fxml"));
        loader.setController(categoryCard);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gridPane.add(parent, column, row);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridPane = new GridPane();
        initializeCategories(getRootCategories(), 0, 0);
        basePane.add(gridPane, 1, 2);
        back.setOnMouseClicked ( event -> App.setRoot ( "managerMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

    }
}