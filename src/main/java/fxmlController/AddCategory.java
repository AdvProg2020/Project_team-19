package fxmlController;

import controller.CategoryController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Category;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class AddCategory implements Initializable {
    enum requestMode {
        EDIT, ADD
    }

    public AddCategory() {
        this.mode = requestMode.ADD;
    }

    public AddCategory(Category category) {
        this.category = category;
        this.mode = requestMode.EDIT;
    }

    private Category category;
    private requestMode mode;
    @FXML private ChoiceBox<String> categories;
    @FXML private TextField name;
    @FXML private TextArea properties;
    @FXML private Button manageButton;


    private void manage() {
        if (mode.equals(requestMode.EDIT)){
          //  CategoryController.getInstance().editCategory(name.getText(),category,CategoryController.getInstance().getCategoryByName(categories.getValue(),CategoryController.rootCategories),properties,properties,false);
        } else {
            CategoryController.getInstance().addCategory(name.getText(),CategoryController.getInstance().getCategoryByName(categories.getValue(),CategoryController.rootCategories),new HashSet<>());
        }
    }

    private ArrayList<String> parentCategories = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(mode.equals(requestMode.EDIT)){
            manageButton.setText("Edit");
            name.setText(category.getName());
            categories.setValue(category.getParent().getName());
        }
        else {
            manageButton.setText("Add");
        }
        initializeCategories();
        manageButton.setOnAction(event -> manage());
    }

    public void initializeCategories(){
        parentCategories.add("root");
        CategoryController.getInstance().getParentCategories(parentCategories,CategoryController.rootCategories);
        categories.setItems(FXCollections.observableArrayList(parentCategories));
    }

}