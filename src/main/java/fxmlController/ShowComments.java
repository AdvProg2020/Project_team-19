package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import model.Comment;
import model.Customer;
import model.Product;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShowComments implements Initializable {
    private ArrayList<Parent> commentCards;
    private Product product;
    private boolean isDiscount;
    @FXML
    private VBox cardBase;
    @FXML
    private FontAwesomeIcon back;

    public ShowComments(Product product, boolean isDiscount) {
        this.isDiscount = isDiscount;
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCommentCards();
        setCommentCardBase();
        ProductMenu productMenu = new ProductMenu(product, isDiscount);
        FXMLLoader loader = new FXMLLoader(ShowComments.class.getResource("/fxml/singleProduct.fxml"));
        loader.setController(productMenu);
        back.setOnMouseClicked(event -> App.setRoot(loader));

        back.setOnMousePressed(event -> back.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);"));

        back.setOnMouseReleased(event -> back.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 1em"));

    }

    private void setCommentCards() {
        commentCards = new ArrayList<>();
        for (Comment comment : product.getComments()) {
            CommentCard commentCardCtrl = new CommentCard(comment, product);
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
        info.put("type", "customer");
        Comment comment = new Comment(false, new Customer(info), "salam", "khobi");
        product.addComment(comment);
        comment.setReply("yalda", "alaki");
        comment.setReply("yalda1", "replye");
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
        product.addComment(new Comment(false, new Customer(info), "salam", "khobi"));
    }

    private void setCommentCardBase() {
        for (int i = 0; i < commentCards.size(); i++) {  //todo ba back az i + 1 shoru mikonom
            cardBase.getChildren().add(i, commentCards.get(i));
        }
    }
}
