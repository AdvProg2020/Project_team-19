package fxmlController;

import controller.CategoryController;
import controller.RequestController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;
import model.Request;
import model.Salesperson;

import javafx.scene.shape.Line;
import view.App;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductForSeller implements Initializable {
    private Product product;
    private Salesperson salesperson;
    @FXML private Label amount;
    @FXML private ImageView productImage;
    @FXML private Line priceLine;
    @FXML private Label price;
    @FXML private Label priceInDiscount;
    @FXML private Label name;
    @FXML private StackPane pricePane;
    @FXML private Label state;
    @FXML private Label off;
    @FXML private ImageView inDiscount;
    @FXML private ImageView remove;
    @FXML private ImageView edit;

    public ProductForSeller(Product product, Salesperson salesperson) {
        this.product = product;
        this.salesperson = salesperson;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (salesperson.isInDiscount(product)) {
            inDiscount.setVisible(true);
            off.setText(salesperson.getDiscountPercentage(product) + "%");
            priceInDiscount.setText(salesperson.getDiscountPrice(product) + "$");
        } else {
            priceLine.setVisible(false);
        }
        amount.setText("amount : " + salesperson.getProductAmount(product));
        state.setText(salesperson.getProductState(product).label + "");
        price.setText(salesperson.getProductPrice(product) + " $");
        name.setText(product.getName() + "(" + product.getID() + ")");
        edit.setOnMouseClicked(event -> edit());
        remove.setOnMouseClicked(event -> remove());

//        if (product.getImageURI().length () != 0) {
//            productImage.setImage(new Image(product.getImageURI()));
//        }
    }

    private void edit() {
        ProductRequestFXML productRequestFXML = new ProductRequestFXML(product, Request.RequestState.EDIT, salesperson);
        FXMLLoader loader = new FXMLLoader(AllProductsForSeller.class.getResource("/fxml/productRequest.fxml"));
        loader.setController(productRequestFXML);
        App.setRoot(loader);
    }

    private void edit1() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        Button submit = new Button("submit");
        submit.setDisable(true);

        //name
        Label name = new Label("new name : ");
        name.setTextFill(Color.rgb(8,181,255));
        name.setFont(Font.font("Verdana", 14));
        TextField nameField = new TextField();
        nameField.setPromptText(product.getName());
        nameField.getStylesheets().add("/fxml/textField.css");
        nameField.setDisable(true);
        name.setCursor(Cursor.HAND);
        name.setOnMouseClicked(event -> {
            submit.setDisable(false);
            nameField.setDisable(false);
        });
        gridPane.add(name, 0, 0);
        gridPane.add(nameField, 1, 0);

        //brand
        Label brand = new Label("new brand : ");
        brand.setTextFill(Color.rgb(8,181,255));
        brand.setFont(Font.font("Verdana", 14));
        TextField brandField = new TextField();
        brandField.setPromptText(product.getBrand());
        brandField.getStylesheets().add("/fxml/textField.css");
        brandField.setDisable(true);
        brand.setCursor(Cursor.HAND);
        brand.setOnMouseClicked(event -> {
            submit.setDisable(false);
            brandField.setDisable(false);
        });
        gridPane.add(brand, 0 ,1);
        gridPane.add(brandField, 1, 1);

        //category
        Label category = new Label("new category : ");
        category.setTextFill(Color.rgb(8,181,255));
        category.setFont(Font.font("Verdana", 14));
        TextField categoryField = new TextField();
        categoryField.setPromptText(product.getCategory().getName());
        categoryField.getStylesheets().add("/fxml/textField.css");
        categoryField.setDisable(true);
        category.setCursor(Cursor.HAND);
        category.setOnMouseClicked(event -> {
            submit.setDisable(false);
            categoryField.setDisable(false);
        });
        gridPane.add(category, 0, 2);
        gridPane.add(categoryField, 1, 2);

        //properties
        HashMap<String, String> properties = new HashMap<>();
        int rowIndex = 3;
        for (String field : product.getCategory().getPropertyFields()) {
            Label label = new Label("new " + field + " : ");
            label.setTextFill(Color.rgb(8,181,255));
            label.setFont(Font.font("Verdana", 14));
            TextField textField = new TextField();
            textField.setPromptText(product.getProperties().get(field));
            textField.getStylesheets().add("/fxml/textField.css");
            textField.setDisable(true);
            label.setCursor(Cursor.HAND);
            label.setOnMouseClicked(event -> {
                submit.setDisable(false);
                textField.setDisable(false);
            });
            gridPane.add(label, 0, rowIndex);
            gridPane.add(textField, 1, rowIndex++);
            rowIndex++;
            properties.put(field, textField.getText());
        }

        //price
        Label price = new Label("new price : ");
        price.setTextFill(Color.rgb(8,181,255));
        price.setFont(Font.font("Verdana", 14));
        TextField priceField = new TextField();
        priceField.setPromptText(salesperson.getProductPrice(product) + "$");
        priceField.getStylesheets().add("/fxml/textField.css");
        priceField.setDisable(true);
        price.setCursor(Cursor.HAND);
        price.setOnMouseClicked(event -> {
            priceField.setDisable(false);
            submit.setDisable(false);
        });
        gridPane.add(price, 0 , rowIndex);
        gridPane.add(priceField, 1, rowIndex);

        //amount
        Label amount = new Label("new amount : ");
        amount.setTextFill(Color.rgb(8,181,255));
        amount.setFont(Font.font("Verdana", 14));
        TextField amountField = new TextField();
        amountField.setPromptText(salesperson.getProductAmount(product) + "$");
        amountField.getStylesheets().add("/fxml/textField.css");
        amountField.setDisable(true);
        amount.setCursor(Cursor.HAND);
        amount.setOnMouseClicked(event -> {
            submit.setDisable(false);
            amountField.setDisable(false);
        });
        gridPane.add(amount, 0 ,rowIndex + 1);
        gridPane.add(amountField, 1 ,rowIndex + 1);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));


        submit.getStylesheets().add("/fxml/button.css");
        submit.getStyleClass().add("btn");
        submit.setCursor(Cursor.HAND);
        submit.setOnAction(event -> {
            if (CategoryController.getInstance().getCategoryByName(categoryField.getText(), CategoryController.rootCategories) == null) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "wrong category", "Enter another category name.");
                return;
            }
            App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "edit product", "your request will be sent to manager");
            RequestController.getInstance().editProductRequest(priceField.getText(), amountField.getText(), salesperson, product.getID(),
                    categoryField.getText(), nameField.getText(), brandField.getText(), properties);
        });
        gridPane.add(submit, 0, rowIndex + 2, 2, 1);

        Scene scene = new Scene(gridPane, 400, 250);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    private void remove() {
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
            App.showAlert(Alert.AlertType.INFORMATION, App.currentStage,  "request for delete product", "Your request will be sent to manager.");
            removeProduct();
            stage.close();
        });

        no.setOnMouseClicked(event -> stage.close());


        Scene scene = new Scene(gridPane, 300, 180);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void removeProduct() {
        RequestController.getInstance().deleteProductRequest(product.getID(), salesperson);
    }
}
