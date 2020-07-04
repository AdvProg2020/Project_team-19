package fxmlController;

import controller.CategoryController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Category;
import view.App;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    @FXML
    private ChoiceBox<String> categories;
    @FXML
    private TextField name;
    @FXML
    private TextArea properties;
    @FXML
    private Button manageButton;
    @FXML
    private FontAwesomeIcon back;

    @FXML
    void back() {
        App.setRoot ( "managerMenu" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }


    private void manage() {
        String[] propertiesArray = properties.getText().split("-");
        HashSet<String> set = new HashSet<>(Arrays.asList(propertiesArray));
            if (mode.equals(requestMode.EDIT)) {
                CategoryController.getInstance().editCategory(name.getText(), category, CategoryController.getInstance().getCategoryByName(categories.getValue(), CategoryController.rootCategories), set, false);
                Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
                alert.setContentText ( "Successfully Edited!" );
                alert.showAndWait ();
            } else {
                if (name.getText().length() == 0 || categories.getValue().length() == 0 || propertiesArray.length == 0) {
                    App.error("Please fill the fields.");
                } else {
                    CategoryController.getInstance().addCategory(name.getText(), CategoryController.getInstance().getCategoryByName(categories.getValue(), CategoryController.rootCategories), set);
                    Alert alert = new Alert ( Alert.AlertType.CONFIRMATION );
                    alert.setContentText ( "Successfully Added!" );
                    alert.showAndWait ();
                }
            }
        }

        private ArrayList<String> parentCategories = new ArrayList<>();

        @Override
        public void initialize (URL location, ResourceBundle resources){
            if (mode.equals(requestMode.EDIT)) {
                manageButton.setText("Edit");
                name.setText(category.getName());
                categories.setValue(category.getParent().getName());
                StringBuilder p=new StringBuilder();
                for (String propertyField : category.getPropertyFields()) {
                    p.append(propertyField).append("-");
                }
                p.deleteCharAt(p.length()-1);
                properties.setText(p.toString());
            } else {
                manageButton.setText("Add");
            }
            initializeCategories();
            manageButton.setOnAction(event -> manage());
        }

        public void initializeCategories () {
            parentCategories.add("root");
            CategoryController.getInstance().getParentCategories(parentCategories, CategoryController.rootCategories);
            categories.setItems(FXCollections.observableArrayList(parentCategories));
        }

    }
