package fxmlController;


import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

    public class SlideShow extends Application{
        static StackPane stackPane;
        static ArrayList<Image> images = new ArrayList<>();
        static int i = 0;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            stackPane = new StackPane();
            Image image = new Image("/images/icons8-minus-30.png");
            images.add(image);
            image = new Image("/images/filled-trash.png");
            images.add(image);

            AnchorPane anchorPane = new AnchorPane(stackPane);

            AnchorPane.setTopAnchor(stackPane, 0.0);
            AnchorPane.setBottomAnchor(stackPane, 0.0);
            AnchorPane.setLeftAnchor(stackPane, 0.0);
            AnchorPane.setRightAnchor(stackPane, 0.0);


            stackPane.getChildren().add(
                    makeSectionPane(images.get(0))
            );

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(5), e -> {
                        i++;
                        doSlideInFromRight(images.get(i%2));
                    }
            ));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            primaryStage.setScene(new Scene(anchorPane));
            primaryStage.show();
        }

        static void doSlideInFromRight(Image image) {
            Node paneToRemove = stackPane.getChildren().get(0);
            Node paneToAdd = makeSectionPane(image);

            paneToAdd.translateXProperty().set(stackPane.getWidth());
            stackPane.getChildren().add(paneToAdd);

            KeyValue keyValue = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), keyValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.setOnFinished(evt -> {
                stackPane.getChildren().remove(paneToRemove);
                timeline.stop();
            });
            timeline.play();
        }

        private static Node makeSectionPane(Image image) {
            BorderPane borderPane = new BorderPane();
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            borderPane.getChildren().add(imageView);
            return borderPane;
        }
    }

