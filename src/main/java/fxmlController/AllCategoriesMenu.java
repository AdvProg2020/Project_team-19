package fxmlController;

import controller.CategoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import model.Category;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllCategoriesMenu implements Initializable {
    @FXML private GridPane gridPane;


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
        initializeCategories(CategoryController.rootCategories, 0, 0);
    }
}