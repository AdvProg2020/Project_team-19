package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Category;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductsInCategory implements Initializable {
    private ArrayList<Parent> productCards;
    private HashMap<Parent, Product> links;
    private Category category;
    @FXML
    private GridPane productCardsBase;

    @FXML
    private Label categoryName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProductCards();
        addProductCardsToPane();
        editCategoryLabel();
    }

    public HashMap<Parent, Product> getProductLinks() {
        return links;
    }

    public ProductsInCategory(Category category) {
        this.category = category;
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
