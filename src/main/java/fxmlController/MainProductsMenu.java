package fxmlController;

import controller.CategoryController;
import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import model.Category;
import model.Product;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainProductsMenu implements Initializable {
    private ArrayList<Parent> categoryCards;
    private ArrayList<Category> leafCategories;
    private LinkedList<Product> filterProducts;
    private HashMap<Parent, Product> productLinks;
    private String selectedFilter = "";
    private String field;
    private Popup popup = new Popup();
    @FXML private Pagination categoryCardsBase;
    @FXML private GridPane basePane;
    @FXML private ComboBox<String> filter;
    @FXML private TextField propertyField;
   // @FXML private FontAwesomeIcon back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLeafCategories();
        setCategoryCards();
        setCardsInPagination();
        handleClickedOnProducts();
        setComboFilters();
        setComboOnAction();
        setTextFieldAction();
//        back.setOnMouseClicked ( event -> App.setRoot ( "mainMenu" ) );

//        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

 //       back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );
    }

    private void setTextFieldAction() {
        propertyField.textProperty().addListener(event -> {
            field = propertyField.getText();
            if (field.isEmpty())
                popup.hide();
            setFilteredProducts();
            addProductsAfterListener();
            if (!field.isEmpty())
                popup.show(App.currentStage);
        });
    }

    private void setFilteredProducts() {
        LinkedList<Product> allProducts = new LinkedList<>(ProductController.allProducts);
        switch (selectedFilter) {
            case "name" :
                filterProducts = ProductController.getInstance().filterByName(field, allProducts);
                break;
            case "price" :
              //todo filterProducts = ProductController.getInstance().filterByPrice(0, 0, allProducts);
                break;
            case "brand" :
                filterProducts = ProductController.getInstance().filterByBrand(field, allProducts);
                break;
        }
    }

    private void addProductsAfterListener() {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);
        for (int i = 0; i < filterProducts.size(); i++) {
            ProductInList productInList = new ProductInList(filterProducts.get(i));
            FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/productInList.fxml"));
            loader.setController(productInList);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert parent != null;
            gridPane.add(parent, i % 4, i / 4);
            handleClickedOnProduct(parent, filterProducts.get(i));
        }
        gridPane.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));
        gridPane.setGridLinesVisible(true);
        scrollPane.setPrefSize(600, 300);
        popup.getContent().add(scrollPane);
    }

    private void setComboOnAction() {
        filter.setOnAction(event -> {
            selectedFilter = filter.getValue();
            propertyField.setDisable(false);
        });
    }

    private void setComboFilters() {
        filter.getItems().add(0, "name");
        filter.getItems().add(1, "brand");
        filter.getItems().add(2, "price");
    }

    private void handleClickedOnProduct(Parent parent, Product product) {
        parent.setOnMouseClicked(event -> {
            popup.hide();
            ProductMenu productMenu = new ProductMenu(product);
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

    private void handleClickedOnProducts() {
        for (Parent parent : productLinks.keySet()) {
            handleClickedOnProduct(parent, productLinks.get(parent));
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
