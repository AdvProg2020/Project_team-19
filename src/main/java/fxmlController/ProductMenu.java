package fxmlController;

import controller.Database;
import controller.PersonController;
import controller.ProductController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.*;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductMenu implements Initializable {
    private static final String STAR = "/images/empty-star.png";
    private static final String FUL_STAR = "/images/star.png";
    private ArrayList<Parent> sellerCards;
    private ArrayList<Product> similarProducts;
    private ImageView[] stars = new ImageView[5];
    private Product product;
    private TextField comment;
    private TextField title;
    private Button submit;
    private Parent currentCard;
    private Stage commentStage;
    private Stage rateStage;
    private Person person;
    private int score = 0;
    private boolean isBought = false;
    private Popup similarProductsPopup;
    private Popup mediaPopup;
    private boolean isDiscount;
    @FXML private ImageView showSimilarProducts;
    @FXML private ImageView productImage;
    @FXML private GridPane sellerCardBase;
    @FXML private ImageView forthButton;
    @FXML private ImageView backButton;
    @FXML private Button addComment;
    @FXML private Label productName;
    @FXML private Label properties;
    @FXML private Button showComments;
    @FXML private ImageView notAvailable;
    @FXML private ScrollPane basePane;
    @FXML private Pane starRate;
    @FXML private ImageView rate;
    @FXML private ImageView playMedia;
    @FXML private FontAwesomeIcon back;
    @FXML private ImageView cart;

    public ProductMenu(Product product, boolean isDiscount) {
        this.product = product;
        this.isDiscount = isDiscount;
        sellerCards = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        person = PersonController.getInstance().getLoggedInPerson();
        rate.setOpacity(0.3);
        addComment.setDisable(true);
        setSellerCards();
        if (!sellerCards.isEmpty()) {
            currentCard = sellerCards.get(0);
            sellerCardBase.getChildren().add(currentCard);
        }
        backButton.setOpacity(0.2);
        if (sellerCards.size() <= 1)
            forthButton.setOpacity(0.2);
        setProductInfo();
        checkBackAndForth();
        showComments.setOnAction(showCommentEvent());
        zoomImage();
        checkAvailable();
        setStarRate();
        checkBought();
        if (person instanceof Customer) {
            addComment.setDisable(false);
            addComment.setOnAction(addCommentEvent());
        }
        if (isBought) {
            rate.setOpacity(1);
            rate.setCursor(Cursor.HAND);
            rate.setOnMouseClicked(event -> rate());
        }
        product.increaseSeen();
        setSimilarProducts();
        showSimilarProducts.setCursor(Cursor.HAND);
        showSimilarProducts.setOnMouseClicked(event -> {
            setSimilarProductsAction();
            similarProductsPopup.show(App.currentStage);
        });
        checkMedia();
        if (product.getImageURI() != null) {
            productImage.setImage(new Image(product.getImageURI()));
        }
        back.setOnMouseClicked ( event -> App.setRoot ( "mainProductsMenu" ) );

        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );

        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );

    }

    private void checkMedia() {
        if (product.getMediaURI() != null) {
            playMedia.setCursor(Cursor.HAND);
            playMedia.setOpacity(1);
            playMedia.setOnMouseClicked(event -> playMedia());
        } else {
            playMedia.setOpacity(0.3);
        }
    }

    private void playMedia() {
        mediaPopup = new Popup();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(product.getMediaURI()));
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setFitHeight(100);
        mediaView.setFitHeight(100);
        mediaPopup.getContent().add(mediaView);
        mediaPopup.show(playMedia, 800, 150);
        playMedia.setOnMouseExited(event -> {
            mediaPopup.hide();
            mediaPlayer.stop();
        });
    }

    private void setSimilarProducts() {
        similarProducts = new ArrayList<>(ProductController.getInstance().getSimilarProducts(product));
    }

    private void setSimilarProductsAction() {
        similarProductsPopup = new Popup();
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);
        for (int i = 0; i < similarProducts.size(); i++) {
            ProductInList productInList = new ProductInList(similarProducts.get(i));
            FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/productInList.fxml"));
            loader.setController(productInList);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert parent != null;
            gridPane.add(parent, i % 4, i / 4);
            handleClickedOnProduct(parent, similarProducts.get(i));
        }
        gridPane.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));
        gridPane.setGridLinesVisible(true);
        scrollPane.setPrefSize(600, 300);
        similarProductsPopup.getContent().add(scrollPane);
    }

    private void handleClickedOnProduct(Parent parent, Product product) {
        parent.setOnMouseClicked(event -> {
            similarProductsPopup.hide();
            ProductMenu productMenu = new ProductMenu(product, isDiscount);
            FXMLLoader loader = new FXMLLoader(MainProductsMenu.class.getResource("/fxml/singleProduct.fxml"));
            loader.setController(productMenu);
            Parent base = null;
            try {
                base = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert base != null;
            App.currentScene = new Scene(base, 800, 500);
            App.currentStage.setScene(App.currentScene);
            App.currentStage.show();
        });
    }

    private void rate() {
        GridPane gridPane = new GridPane();
        setStars();
        Label rateText = new Label("Rate this product!");
        gridPane.add(rateText, 0, 0, 5, 1);
        for (int i = 0; i < 5; i++) {
            gridPane.add(stars[i], i + 1, 1);
        }

        submit = new Button("Submit");
        submit.getStyleClass().add("btn");
        submit.getStylesheets().add("/fxml/button.css");
        submit.setCursor(Cursor.HAND);

        gridPane.add(submit, 0, 2, 5, 1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setHgap(10);

        rateText.setOnMouseClicked(event -> {
            score = 0;
            handleScore();
        });

        rateStage = new Stage();
        Scene scene = new Scene(gridPane, 300, 180);

        rateStage.setScene(scene);
        rateStage.initModality(Modality.APPLICATION_MODAL);
        rateStage.show();
        setMouseClicked();
        submit.setOnAction(event -> submitRate());
    }

    private void submitRate() {
        product.increaseTotalScore(score);
    }

    private void setMouseClicked() {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            stars[i].setOnMouseClicked(event -> {
                score = finalI + 1;
                handleScore();
            });
        }
    }

    private void handleScore() {
        for (int i = 0; i < score; i++) {
            stars[i].setImage(new Image(FUL_STAR));
        }
        for (int i = score; i < 5; i++) {
            stars[i].setImage(new Image(STAR));
        }
    }

    private void setStars() {
        for (int i = 0; i < 5; i++) {
            stars[i] = new ImageView(new Image(STAR));
            stars[i].setFitHeight(40);
            stars[i].setFitWidth(40);
        }
    }

    private void checkBought() {
        if (person instanceof Customer
                && ((Customer) person).isProductBought(product)) {
            isBought = true;
        }
    }

    private void setStarRate() {
        StarRate starRate = new StarRate(product);
        FXMLLoader loader = new FXMLLoader(ProductMenu.class.getResource("/fxml/starRate.fxml"));
        loader.setController(starRate);
        try {
            this.starRate.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAvailable() {
        if (ProductController.getInstance().isProductAvailable(product))
            notAvailable.setVisible(false);
    }

    private void showComments() {
        ShowComments showCommentsCtrl = new ShowComments(product, isDiscount);
        FXMLLoader loader = new FXMLLoader(ProductMenu.class.getResource("/fxml/showComments.fxml"));
        loader.setController(showCommentsCtrl);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert parent != null;
        App.currentScene = new Scene(parent, 800, 500);
        App.currentStage.setScene(App.currentScene);
        App.currentStage.show();
    }

    private EventHandler<ActionEvent> showCommentEvent() {
        return event -> showComments();
    }

    private void addComment() {
        GridPane gridPane = new GridPane();
        Label label = new Label("Add your comment");
        comment = new TextField();
        title = new TextField();
        submit = new Button("Submit");
        submit.getStyleClass().add("btn");
        submit.getStylesheets().add("/fxml/button.css");
        submit.setCursor(Cursor.HAND);
        title.setPromptText("title");
        comment.setPromptText("comment");
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(label, 0, 0);
        gridPane.add(title, 0, 1);
        gridPane.add(comment, 0, 2);
        gridPane.add(submit, 0, 3);

        commentStage = new Stage();
        Scene scene = new Scene(gridPane, 300, 180);

        commentStage.setScene(scene);
        commentStage.initModality(Modality.APPLICATION_MODAL);
        commentStage.show();
        submitComment();
    }

    private void submitComment() {
        submit.setOnAction(event -> {
            if (title.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, commentStage, "Error", "Fill the title!");
                return;
            }
            if (comment.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, commentStage, "Error", "Fill the comment!");
                return;
            }
            Customer customer = (Customer) person;
            Comment newComment = new Comment(true, customer, comment.getText(), title.getText());
            if (customer.isProductBought(product)) {
                newComment.setBought(true);
            }
            product.addComment(newComment);
            Database.saveToFile(product, Database.createPath("products", product.getID()));
            commentStage.close();
        });
    }

    public void showAlert(Alert.AlertType alertType,Stage owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private void setProductInfo() {
        productName.setText(product.getName());
        productName.setFont(Font.font("Verdana", FontWeight.BOLD,20));

        properties.setText(getProperties());
        properties.setFont(Font.font("Verdana", 16));
    }

    private String getProperties() {
        String info = "";
        info += "brand :" + product.getBrand() + "\n";
        for (String key : product.getProperties().keySet()) {
            info += key + " :" + product.getProperties().get(key) + "\n";
        }
        info += product.getDescription() != null ? product.getDescription() : "";
        return info;
    }

    private EventHandler<ActionEvent> addCommentEvent() {
        return event -> addComment();
    }

    private EventHandler<MouseEvent> previousSeller() {
        return event -> {
            if (sellerCards.indexOf(currentCard) != 0) {
                forthButton.setOpacity(1);
                sellerCardBase.getChildren().remove(0, 1);
                sellerCardBase.getChildren().add(sellerCards.get(sellerCards.indexOf(currentCard) - 1));
                currentCard = sellerCards.get(sellerCards.indexOf(currentCard) - 1);
                if (sellerCards.indexOf(currentCard) == 0) {
                    backButton.setOpacity(0.2);
                }
            }
        };
    }

    private EventHandler<MouseEvent> nextSeller() {
        return event -> {
            if (sellerCards.indexOf(currentCard) != sellerCards.size() - 1) {
                backButton.setOpacity(1);
                sellerCardBase.getChildren().remove(0, 1);
                sellerCardBase.getChildren().add(sellerCards.get(sellerCards.indexOf(currentCard) + 1));
                currentCard = sellerCards.get(sellerCards.indexOf(currentCard) + 1);
                if (sellerCards.indexOf(currentCard) == sellerCards.size() - 1) {
                    forthButton.setOpacity(0.2);
                }
            }
        };
    }

    private void checkBackAndForth() {
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, previousSeller());
        forthButton.addEventHandler(MouseEvent.MOUSE_CLICKED, nextSeller());
    }

    private void setSellerCards() {
        for (Salesperson seller : ProductController.getInstance().getProductsSellers(product)) {
            if (seller.isInAuction(product))
                continue;
            if (isDiscount) {
                if (!seller.isInDiscount(product))
                    continue;
            }
            SellerInList sellerCard = new SellerInList(seller, product);
            FXMLLoader loader = new FXMLLoader(ProductMenu.class.getResource("/fxml/sellerInList.fxml"));
            loader.setController(sellerCard);
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sellerCards.add(parent);
        }
    }

    private void zoomImage() {
        double width = productImage.getFitWidth();
        double height = productImage.getFitHeight();

        productImage.setPreserveRatio(true);
        reset(productImage, width, height);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        productImage.setOnMousePressed(e -> {
            Point2D mousePress = imageViewToImage(productImage, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        productImage.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(productImage, new Point2D(e.getX(), e.getY()));
            shift(productImage, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(productImage, new Point2D(e.getX(), e.getY())));
        });

        productImage.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = productImage.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                    Math.min(10 / viewport.getWidth(), 10 / viewport.getHeight()),

                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );

            Point2D mouse = imageViewToImage(productImage, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            productImage.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        productImage.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(productImage, width, height);
            }
        });

    }

    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

}
