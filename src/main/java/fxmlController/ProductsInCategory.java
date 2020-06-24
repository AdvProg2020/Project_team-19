package fxmlController;

import controller.ProductController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

public class ProductsInCategory implements Initializable {
    private ArrayList<Parent> productCards;
    private HashMap<Parent, Product> links;
    private ArrayList<Product> filterProducts;
    private Category category;
    private String selectedFilter;
    private String property;
    private Popup popup = new Popup();
    @FXML private GridPane productCardsBase;
    @FXML private Label categoryName;
    @FXML private ComboBox<String> filterBox;
    @FXML private TextField propertyField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProductCards();
        addProductCardsToPane();
        editCategoryLabel();
        setComboFilters();
        setOnComboAction();
        setTextFieldAction();
    }

    public HashMap<Parent, Product> getProductLinks() {
        return links;
    }

    public ProductsInCategory(Category category) {
        this.category = category;
    }

    private void setTextFieldAction() {
        propertyField.textProperty().addListener(event -> {
            property = propertyField.getText();
            if (property.isEmpty())
                popup.hide();
            setFilteredProducts();
            addProductsAfterListener();
            if (!property.isEmpty())
                popup.show(App.currentStage);
        });
    }

    private void setFilteredProducts() {
        filterProducts = ProductController.getInstance().filterByField(selectedFilter, property, category.getProductList());
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
            handleClickedOProduct(parent, filterProducts.get(i));
        }
        gridPane.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));
        gridPane.setGridLinesVisible(true);
        scrollPane.setPrefSize(600, 300);
        popup.getContent().add(scrollPane);
    }

    private void handleClickedOProduct(Parent parent, Product product) {
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

    private void setOnComboAction() {
        filterBox.setOnAction(event -> {
            selectedFilter = filterBox.getValue();
            propertyField.setDisable(false);
        });
    }

    private void setComboFilters() {
        for (String field : category.getPropertyFields()) {
            filterBox.getItems().add(field);
        }
    }

    private void editCategoryLabel() {
        categoryName.setText(category.getName());
        categoryName.setFont(Font.font("Verdana", 16));
        categoryName.setTextFill(Color.rgb(23,132,180));
    }

    private void setProductCards() {
        productCards = new ArrayList<>();
        links = new HashMap<>();
        for (Product product : category.getProductList()) {
            ProductInList productInList = new ProductInList(product);
            FXMLLoader loader = new FXMLLoader(ProductsInCategory.class.getResource("/fxml/productInList.fxml"));
            loader.setController(productInList);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            productCards.add(parent);
            links.put(parent, product);
        }
    }

    private void addProductCardsToPane() {
        for (int i = 0; i < productCards.size(); i++) {
            productCardsBase.add(productCards.get(i), i % 3, i / 3 + 3);
        }
    }
}
