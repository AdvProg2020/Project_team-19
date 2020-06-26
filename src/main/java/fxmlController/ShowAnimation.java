package fxmlController;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShowAnimation extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        final ImageView imageView = new ImageView("/images/sprite/shopping-girl-walk-cycle-animation-sprite-sheet.jpg");
        imageView.setViewport(new Rectangle2D(5,5,50,200));

        Animation animation = new SpriteAnimation(imageView,9,18,8,8,50,200, Duration.seconds(6));
        animation.play();

        primaryStage.setScene(new Scene(new Group(imageView)));
        primaryStage.show();
    }
}
