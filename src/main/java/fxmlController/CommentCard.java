package fxmlController;

import controller.PersonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Comment;
import model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommentCard implements Initializable {
    private Comment comment;
    private TextField replyTextField = new TextField();
    private Stage commentStage;
    private Button submit;
    private Person person;
    @FXML private Label commenterName;
    @FXML private ImageView commenterImage; //todo
    @FXML private Label commentLabel;
    @FXML private Label titleLabel;
    @FXML private Label isBoughtLabel;
    @FXML private ImageView reply;
    @FXML private VBox commentBase;

    public CommentCard(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        person = PersonController.getInstance().getLoggedInPerson();
        commentLabel.setText(comment.getCommentString());
        commenterName.setText(comment.getCommenterUsername());
        titleLabel.setText(comment.getTitle());
        isBoughtLabel.setOpacity(0.5);
        if (comment.isBought()) {
            isBoughtLabel.setText("This product is bought by this person.");
        } else {
            isBoughtLabel.setText("The product hasn't been bought yet!");
        }
        if (person == null ||  person.getUsername().equals(comment.getCommenterUsername())) {
            reply.setOpacity(0.3);
        } else {
            reply.setCursor(Cursor.HAND);
            reply.setOnMouseClicked(event -> addComment());
        }
        if (!comment.getReply().isEmpty()) {
            int index = 1;
            for(String username : comment.getReply().keySet()) {
                setReply(username, comment.getReply().get(username), index);
                index += 2;
            }
        }
    }

    private void setReply(String username, String reply, int index) {
        PersonInList personInList = new PersonInList(PersonController.getInstance().getPersonByUsername(username));
        FXMLLoader loader = new FXMLLoader(CommentCard.class.getResource("/fxml/personInList.fxml"));
        loader.setController(personInList);
        Parent profile = null;
        try {
            profile = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox replyBox = new HBox();

        replyBox.getChildren().add(0, profile);

        Label replyComment = new Label();
        editLabel(replyComment, reply);
        replyBox.getChildren().add(1, replyComment);


        replyBox.setSpacing(10);
        replyBox.setPadding(new Insets(6,0,6,100));
        replyBox.setAlignment(Pos.CENTER_LEFT);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);

        commentBase.getChildren().add(index, separator);
        commentBase.getChildren().add(index + 1, replyBox);
    }

    private void editLabel(Label label, String text) {
        label.setText(text);
        label.setFont(Font.font("Verdana", 13));
    }

    private void addComment() {
        GridPane gridPane = new GridPane();
        Label label = new Label("Add your comment");
        replyTextField = new TextField();
        submit = new Button("Submit");
        replyTextField.setPromptText("reply");
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(label, 0, 0);
        gridPane.add(replyTextField, 0, 2);
        gridPane.add(submit, 0, 3);

        commentStage = new Stage();
        Scene scene = new Scene(gridPane, 300, 180);

        commentStage.setScene(scene);
        commentStage.initModality(Modality.APPLICATION_MODAL);
        commentStage.show();
        submit.setOnAction(event -> {
            submitComment();
            commentStage.close();
        });
    }

    private void submitComment() {
        if (replyTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, commentStage, "Error", "Fill the comment!");
            return;
        }

        comment.setReply(person.getUsername(), replyTextField.getText());
        commentStage.close();
    }

    public void showAlert(Alert.AlertType alertType,Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
