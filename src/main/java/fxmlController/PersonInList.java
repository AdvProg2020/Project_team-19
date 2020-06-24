package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonInList implements Initializable {
    private Person person;
    @FXML private Label role;
    @FXML private ImageView profilePic;
    @FXML private Label username;

    public PersonInList(Person person) {
        this.person = person;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {  //Todo type ro hatman tu hashmap bezarim
        role.setText(person.getType());
        //setimageview
        username.setText(person.getUsername());
    }
}
