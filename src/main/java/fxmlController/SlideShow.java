package fxmlController;


import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;

public class SlideShow {
    private AnchorPane anchorPane;
    static StackPane stackPane;
    private ArrayList<Image> images;
    static int i = 0;

    public SlideShow(ArrayList<Image> images) {
        stackPane = new StackPane();
        this.images = images;
        initialize();
    }

    static void doSlideInFromRight(Image image) {
        Node paneToRemove = stackPane.getChildren().get(0);
        Node paneToAdd = makeSectionPane(image);

        paneToAdd.translateXProperty().set(stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);

        KeyValue keyValue = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(5), keyValue);
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
        imageView.setFitHeight(120);
        imageView.setFitWidth(200);
        borderPane.getChildren().add(imageView);
        return borderPane;
    }

    public void initialize() {
        anchorPane = new AnchorPane(stackPane);
        AnchorPane.setTopAnchor(stackPane, 0.0);
        AnchorPane.setBottomAnchor(stackPane, 0.0);
        AnchorPane.setLeftAnchor(stackPane, 300.0);
        AnchorPane.setRightAnchor(stackPane, 0.0);


        stackPane.getChildren().add(
                makeSectionPane(images.get(0))
        );

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    i++;
                    doSlideInFromRight(images.get(i % images.size()));
                }
                ));
        timeline.setCycleCount(10);
        timeline.play();
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}

