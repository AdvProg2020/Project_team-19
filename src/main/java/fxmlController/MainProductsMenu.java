package fxmlController;

import controller.CategoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Category;
import model.Product;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainProductsMenu implements Initializable {
    private ArrayList<Parent> categoryCards;
    private ArrayList<Category> leafCategories;
    private HashMap<Parent, Product> productLinks;
    @FXML
    private Pagination categoryCardsBase;

    @FXML
    private GridPane basePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLeafCategories();
        setCategoryCards();
        setCardsInPagination();
        handleClickedOnProduct();
    }

    private void handleClickedOnProduct() {
        for (Parent parent : productLinks.keySet()) {
            parent.setOnMouseClicked(event -> {
                ProductMenu productMenu = new ProductMenu(productLinks.get(parent));
                FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/singleProduct.fxml"));
                loader.setController(productMenu);
                Parent base = null;
                try {
                    base = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert base != null;
                App.currentScene = new Scene(base, 800, 500);
                App.currentStage.setScene(App.currentScene);
                App.currentStage.show();
            });
        }
    }

    private void setCardsInPagination() {
        categoryCardsBase.setPageCount(leafCategories.size());
        categoryCardsBase.setCurrentPageIndex(0);
        categoryCardsBase.setMaxPageIndicatorCount(3);
        categoryCardsBase.setPageFactory(param -> {
            GridPane gridPane = new GridPane();
            gridPane.add(categoryCards.get(param), 0, 0);
            return gridPane;
        });
    }

    private void setCategoryCards() {
        categoryCards = new ArrayList<>();
        productLinks = new HashMap<>();
        for (Category category : leafCategories) {
            ProductsInCategory cardCtrl = new ProductsInCategory(category);
            FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/productsInCategory.fxml"));
            loader.setController(cardCtrl);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            categoryCards.add(parent);
            productLinks.putAll(cardCtrl.getProductLinks());
        }
    }

    private void setLeafCategories() {
        leafCategories = new ArrayList<>();
        CategoryController.getInstance().getNodeCategories(leafCategories, CategoryController.rootCategories);
    }
}
