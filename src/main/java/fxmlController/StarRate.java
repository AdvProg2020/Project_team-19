package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class StarRate implements Initializable {
    final String FUL_STAR = "/images/star.png";
    final String HALF_STAR = "/images/half-star.png";
    private ImageView[] scoreViews = new ImageView[5];
    private Product product;
    @FXML private Label scoreLabel;
    @FXML private ImageView star1;
    @FXML private ImageView star2;
    @FXML private ImageView star3;
    @FXML private ImageView star4;
    @FXML private ImageView star5;

    public StarRate(Product product) {
        this.product = product;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scoreViews[0] = star1;
        scoreViews[1] = star2;
        scoreViews[2] = star3;
        scoreViews[3] = star4;
        scoreViews[4] = star5;
        arrangeScoreHBox(product.getAverageScore());
        editLabel(scoreLabel, String.valueOf(product.getAverageScore()));
    }

    private void arrangeScoreHBox(double score) {
        for (int i = 1; i <= 5; i++) {
            if (i <= score) {
                setImage(FUL_STAR, scoreViews[i - 1]);
                scoreViews[i - 1].setStyle("-fx-border-color:red");
                scoreViews[i - 1].setVisible(true);
            } else if (i == Math.ceil(score)) {
                setImage(HALF_STAR, scoreViews[i - 1]);
                scoreViews[i - 1].setVisible(true);
            } else {
                scoreViews[i - 1].setVisible(false);
            }
            scoreViews[i - 1].setFitHeight(15);
            scoreViews[i - 1].setFitWidth(15);
        }
    }

    private void editLabel(Label label, String text) {
        label.setText(text);
        label.setFont(Font.font("Verdana", 10));
    }

    private void setImage(String address, ImageView view) {
        Image image = new Image(address);
        view.setImage(image);
    }
}
