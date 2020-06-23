package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Comment;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentCard implements Initializable {
    private Comment comment;

    @FXML
    private Label commenterName;

    @FXML
    private ImageView commenterImage; //todo

    @FXML
    private Label commentLabel;

    @FXML
    private Label titleLabel;

    public CommentCard(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commentLabel.setText(comment.getCommentString());
        commenterName.setText(comment.getCommenterUsername());
        titleLabel.setText(comment.getTitle());
    }
}
