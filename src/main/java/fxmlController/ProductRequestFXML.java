package fxmlController;

import controller.CategoryController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.ProductRequest;
import model.Request;
import model.Salesperson;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductRequestFXML implements Initializable {
    Product product;
    Request.RequestState state;
    Salesperson salesperson;
    Image image;

//    public ProductRequestFXML(Product product, Request.RequestState state,Salesperson salesperson){
//        this.product = product;
//        this.state = state;
//        this.salesperson = salesperson;
//    }
//
//    public ProductRequestFXML( Request.RequestState state,Salesperson salesperson){
//        this.state = state;
//        this.salesperson = salesperson;
//    }

    @FXML
    private TextField name;

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
    private Button properties;

    @FXML
    private Button chooseFile;

    @FXML
    void chooseImage(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("FileChooser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        chooseFile.setOnAction(e -> {

            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                image = new Image(file.toURI().toString());
            }
        });
    }


    @FXML
    void arrangeProperties(ActionEvent event) {

        if (category.getText().length() == 0) {
            category.setText("Fill the field.");
        } else if (CategoryController.getInstance().getCategoryByName(category.getText(), CategoryController.rootCategories) == null && !category.getText().equals("Fill the field.")) {
            category.setText("There is no such category.");
        }
    }


    @FXML
    void action(ActionEvent event) {
        if (state.equals(Request.RequestState.EDIT)) {
            // ProductRequest request = new ProductRequest();
        } else if (state.equals(Request.RequestState.ADD)) {
            ProductRequest productRequest = new ProductRequest(Double.parseDouble(price.getText()), Integer.parseInt(amount.getText()), salesperson, product);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        if (state.equals(Request.RequestState.EDIT)){
//            initializeTextFields();
//            actionButton.setText("Edit");
//        }else if (state.equals(Request.RequestState.ADD)){
//            actionButton.setText("Add");
//        }
    }

    public void initializeTextFields() {
        name.setText(product.getName());
        brand.setText(product.getBrand());
        price.setText(String.valueOf(salesperson.getProductPrice(product)));
        category.setText(product.getCategory().getName());
    }
}
