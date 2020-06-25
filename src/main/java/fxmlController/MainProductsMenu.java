package fxmlController;

import controller.CategoryController;
import controller.ProductController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Category;
import model.Product;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainProductsMenu implements Initializable {
    private ArrayList<Parent> categoryCards;
    private ArrayList<Category> leafCategories;
    private LinkedList<Product> filterProducts;
    private HashMap<Parent, Product> productLinks;
    private LinkedHashMap<String, String> filters;
    private String selectedFilter = "";
    private String property;
    private Popup popup = new Popup();
    public static boolean isDiscount;
    @FXML
    private Pagination categoryCardsBase;
    @FXML
    private GridPane basePane;
    @FXML
    private ComboBox<String> filterCombo;
    @FXML
    private TextField propertyField;
    @FXML
    private ImageView switchIcon;
    @FXML
    private ImageView cartIcon;
    // @FXML private FontAwesomeIcon back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filters = new LinkedHashMap<>();
        filterProducts = new LinkedList<>(ProductController.allProducts);
        setLeafCategories();
        setCategoryCards();
        setCardsInPagination();
        handleClickedOnProducts();
        setComboFilters();
        setComboOnAction();
        setTextFieldAction();
        popup.setHideOnEscape(true);
        switchIcon.setOnMouseClicked(event -> switchIconHandler());
        if (isDiscount)
            switchIcon.setImage(new Image("/images/product.png"));
        else
            switchIcon.setImage(new Image("/images/discount.png"));
        basePane.add(slides(), 0, 1);


//        back.setOnMouseClicked ( event -> App.setRoot ( "mainMenu" ) );

//        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        //       back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );
    }

    private void switchIconHandler() {
        isDiscount = !isDiscount;
        Parent parent = null;
        try {
            parent = FXMLLoader.load(App.class.getResource("/fxml/mainProductsMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        App.currentScene = new Scene(parent, 800, 500);
        App.currentStage.setScene(App.currentScene);
        App.currentStage.show();
    }

    private void setTextFieldAction() {
        propertyField.textProperty().addListener(event -> {
            property = propertyField.getText();
            filters.remove(selectedFilter);
            filters.put(selectedFilter, property);
            if (property.isEmpty()) {
                //popup.hide();
                if (selectedFilter.equals("price")) {
                    propertyField.setPromptText("least price-most price");
                } else {
                    propertyField.setPromptText("Enter Property");
                }
            } else {
                setFilteredProducts();
                addProductsAfterListener();
                popup.show(propertyField, 350, 150);
            }
        });
    }

    private void setFilteredProducts() {
        LinkedList<Product> products = ProductController.allProducts.stream()
                .filter(product -> {
                    if (isDiscount)
                        return product.isInDiscountInTotal();
                    else
                        return true;
                })
                .collect(Collectors.toCollection(LinkedList::new));

        switch (selectedFilter) {
            case "name":
                filterProducts = ProductController.getInstance().filterByName(property, filterByOtherFields(products));
                break;
            case "price":
                if (property.matches("\\d+-\\d+")) {
                    String leastPrice = property.split("-")[0];
                    String mostPrice = property.split("-")[1];

                    filterProducts = ProductController.getInstance().filterByPrice(Integer.parseInt(leastPrice), Integer.parseInt(mostPrice), filterByOtherFields(products));
                    filterCombo.setDisable(false);
                } else {
                    //propertyField.setText("invalid format");
                    filterCombo.setDisable(true);
                }
                break;
            case "brand":
                filterProducts = ProductController.getInstance().filterByBrand(property, filterByOtherFields(products));
                break;
        }
    }

    private LinkedList<Product> filterByOtherFields(LinkedList<Product> allProducts) {
        LinkedList<Product> filteredProducts = new LinkedList<>(allProducts);
        for (String filterField : filters.keySet()) {
            if (!filters.get(filterField).isEmpty())
                switch (filterField) {
                    case "name":
                        filteredProducts = ProductController.getInstance().filterByName(filters.get(filterField), filteredProducts);
                        break;
                    case "price":
                        String price = filters.get(filterField);
                        String leastPrice = price.split("-")[0];
                        String mostPrice = price.split("-")[1];
                        filteredProducts = ProductController.getInstance().filterByPrice(Integer.parseInt(leastPrice), Integer.parseInt(mostPrice), filteredProducts);
                        break;
                    case "brand":
                        filteredProducts = ProductController.getInstance().filterByBrand(filters.get(filterField), filteredProducts);
                        break;
                }
        }
        return filteredProducts;
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
        gridPane.setBackground((new Background(new BackgroundFill(Color.rgb(153, 221, 255), CornerRadii.EMPTY, Insets.EMPTY))));
        gridPane.setGridLinesVisible(true);
        scrollPane.setPrefSize(600, 300);
        popup.getContent().add(scrollPane);
    }

    private void setComboOnAction() {
        filterCombo.setOnAction(event -> {
            selectedFilter = filterCombo.getValue();
            propertyField.setDisable(false);
            propertyField.setText(filters.get(selectedFilter));
        });
    }

    private void setComboFilters() {
        filterCombo.getItems().add(0, "name");
        filterCombo.getItems().add(1, "brand");
        filterCombo.getItems().add(2, "price");
        filters.put("name", "");
        filters.put("brand", "");
        filters.put("price", "");
    }

    private void handleClickedOnProduct(Parent parent, Product product) {
        parent.setOnMouseClicked(event -> {
            popup.hide();
            ProductMenu productMenu = new ProductMenu(product, isDiscount);
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
            ProductsInCategory cardCtrl = new ProductsInCategory(category, isDiscount);
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

    public AnchorPane slides() {
        ArrayList<Image> images = new ArrayList<>();
        Image image = new Image("/images/slides/pic2.jpg");
        images.add(image);
        Image image1 = new Image("/images/slides/pic3.jpg");
        images.add(image1);
        Image image2 = new Image("/images/slides/pic1.jpg");
        images.add(image2);
        Image image3 = new Image("/images/slides/pic4.jpg");
        images.add(image3);
        Image image4 = new Image("/images/slides/pic5.jpg");
        images.add(image4);
        SlideShow slideShow = new SlideShow(images);
        return slideShow.getAnchorPane();
    }

    private void setLeafCategories() {
        leafCategories = new ArrayList<>();
        CategoryController.getInstance().getNodeCategories(leafCategories, CategoryController.rootCategories);
    }
}
