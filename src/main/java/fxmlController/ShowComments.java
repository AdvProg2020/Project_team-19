package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Customer;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShowComments implements Initializable {
    private ArrayList<Parent> commentCards;
    private Product product;
    @FXML
    private VBox cardBase;

    public ShowComments(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // masalan();
        setCommentCards();
        setCommentCardBase();
    }

    private void setCommentCards() {
        commentCards = new ArrayList<>();
        for (Comment comment : product.getComments()) {
            CommentCard commentCardCtrl = new CommentCard(comment);
            FXMLLoader loader = new FXMLLoader(ShowComments.class.getResource("/fxml/commentCard.fxml"));
            loader.setController(commentCardCtrl);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            commentCards.add(parent);
        }
    }

    private void masalan() {
        HashMap<String, String> info = new HashMap<>();
        info.put("username", "jalali");
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));

    }

    private void setCommentCardBase() {
        for (int i = 0; i < commentCards.size(); i++) {
            cardBase.getChildren().add(i, commentCards.get(i));
        }
    }
}
