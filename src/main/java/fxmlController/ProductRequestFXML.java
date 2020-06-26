package fxmlController;

import controller.CategoryController;
import controller.RequestController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;
import model.Request;
import model.Salesperson;
import view.App;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductRequestFXML implements Initializable {
    Product product;
    Request.RequestState state;
    Salesperson salesperson;
    String image;
    String media;

    public ProductRequestFXML(Product product, Request.RequestState state, Salesperson salesperson) {
        this.product = product;
        this.state = state;
        this.salesperson = salesperson;
    }

    public ProductRequestFXML(Request.RequestState state, Salesperson salesperson) {
        this.state = state;
        this.salesperson = salesperson;
    }
    private HashMap<String, String> properties = new HashMap<>();

    @FXML
    private TextField name;

    @FXML
    private FontAwesomeIcon back;

    @FXML
    private TextField brand;

    @FXML
    private TextField category;

    @FXML
    private TextField price;

    @FXML
    private TextField amount;

    @FXML
    private Button actionButton;

    @FXML
    private Button property;

    @FXML
    private Button chooseFile;

    @FXML
    private Button chooseFileMedia;

    @FXML
    private TextField description;


    private void chooseImage() {
        Stage stage = new Stage();
        stage.setTitle("FileChooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            image = file.getAbsolutePath();
        }
    }

    private void chooseMedia() {
        Stage stage = new Stage();
        stage.setTitle("FileChooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Medias");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            media = file.getAbsolutePath();
        }
    }

    private boolean arrangeProperties() {
        if (category.getText().length() == 0) {
            category.setText("Fill the field.");
            return false;
        } else if (CategoryController.getInstance().getCategoryByName(category.getText(), CategoryController.rootCategories) == null && !category.getText().equals("Fill the field.")) {
            category.setText("There is no such category.");
            return false;
        }
        return true;
    }

    private void action() {
        if (state.equals(Request.RequestState.EDIT)) {
            RequestController.getInstance().editProductRequest(price.getText(), amount.getText(), salesperson, product.getID(),
                    category.getText(), name.getText(), brand.getText(), properties, image, media);
        } else if (state.equals(Request.RequestState.ADD)) {
            RequestController.getInstance().addProductRequest(Double.parseDouble(price.getText()), Integer.parseInt(amount.getText()),
                    salesperson, category.getText(), name.getText(), brand.getText(), properties, image, media);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (state.equals(Request.RequestState.EDIT)) {
            initializeTextFields();
            actionButton.setText("Edit");
        } else if (state.equals(Request.RequestState.ADD)) {
            actionButton.setText("Add");
        }
        chooseFile.setOnAction(event -> chooseImage());
        chooseFileMedia.setOnAction(event -> chooseMedia());
        property.setOnAction(event -> getProperty());
        actionButton.setOnAction(event -> setActionButton());

        back.setOnMouseClicked ( event -> App.setRoot ( "salespersonMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

    }

    private void getProperty() {
        if (arrangeProperties()) {
            Stage stage = new Stage();
            GridPane gridPane = new GridPane();
            Button submit = new Button("submit");
            submit.setDisable(true);

            int rowIndex = 0;
            HashMap<String, TextField> textFields = new HashMap<>();
            for (String field : CategoryController.getInstance().getCategoryByName(category.getText(), CategoryController.rootCategories).getPropertyFields()) {
                Label label = new Label("new " + field + " : ");
                label.setTextFill(Color.rgb(8, 181, 255));
                label.setFont(Font.font("Verdana", 14));
                TextField textField = new TextField();
                textFields.put(field, textField);
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
            }

            gridPane.setAlignment(Pos.CENTER);
            gridPane.setPadding(new Insets(20));


            submit.getStylesheets().add("/fxml/button.css");
            submit.getStyleClass().add("btn");
            submit.setCursor(Cursor.HAND);
            gridPane.add(submit, 0, rowIndex + 2, 2, 1);
            submit.setOnAction(event -> {
                for (String field : textFields.keySet()) {
                    if (textFields.get(field).getText().isEmpty()) {
                        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "error", "Fill every thing");
                        return;
                    }
                    properties.put(field, textFields.get(field).getText());
                }
                stage.close();
            });


            Scene scene = new Scene(gridPane, 400, 250);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    public void initializeTextFields() {
        name.setPromptText(product.getName());
        brand.setPromptText(product.getBrand());
        price.setPromptText(String.valueOf(salesperson.getProductPrice(product)));
        amount.setPromptText(salesperson.getProductAmount(product) + "");
        category.setPromptText(product.getCategory().getName());
        description.setPromptText(product.getDescription());
    }

    private void setActionButton() {
        if (state.equals(Request.RequestState.ADD)) {
            if (name.getText().isEmpty()) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
                return;
            }
            if (brand.getText().isEmpty()) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
                return;
            }
            if (price.getText().isEmpty()) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
                return;
            }
            if (category.getText().isEmpty()) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
                return;
            }
            if (amount.getText().isEmpty()) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Fill every thing");
                return;
            }
            if (price.getText().matches("\\d+(.\\d+)?")) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Enter number for price");
                return;
            }
            if (amount.getText().matches("\\d+")) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "error", "Enter number for amount");
                return;
            }
        }
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "product request", "your request will be sent to manager");
        action();
    }
}
