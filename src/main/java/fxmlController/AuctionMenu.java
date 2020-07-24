package fxmlController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Auction;
import server.Request;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static fxmlController.MainMenuController.isInAllAuction;
import static clientController.ServerConnection.*;
import static server.PacketType.SUPPORT_CHAT_BACK;

public class AuctionMenu implements Initializable { //todo
    private Auction auction;
    private Parent productCard;
    private double previousPrice = 0;
    @FXML
    private Label timer;
    @FXML
    private TextArea see;
    @FXML
    private TextArea add;
    @FXML
    private Pane productPane;
    @FXML
    private TextField offerPrice;
    @FXML
    private ImageView submitPrice;
    @FXML
    private Button send;
    @FXML
    private Label basePrice;
    @FXML
    private FontAwesomeIcon back;

    ExecutorService executor = Executors.newCachedThreadPool ( );

    public AuctionMenu(Auction auction, Parent productCard) {
        this.auction = auction;
        this.productCard = productCard;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        see.setText ( auction.messages );

//        new Thread ( "Listening Thread" ) {
//            @Override
//            public void run () {
//                while(true) {
//                    try {
//                        String string = sendAuctionMessage ( null , true );
//                        if ( string.equals ( "/back/" ) )
//                            break;
//                        displayMessage ( string );
//                    } catch (Exception e) {
//                        System.out.println ( e.getMessage () );
//                        break;
//                    }
//                }
//            }
//
//            private void displayMessage ( String string ) {
//                Task display_message = new Task<Void>() {
//                    @Override
//                    public Void call() throws Exception {
//
//                        Platform.runLater( () -> see.appendText(string) );
//
//                        return null;
//                    }
//                };
//
//                executor.execute(display_message);
//            }
//
//        }.start ();

        setTimer();
//        send.setOnMouseClicked(event -> {
//            if (add.getText().isEmpty()) {
//                App.error("write something :|");
//            } else {
//                sendAuctionMessage ( new ArrayList < String > ( ) {{
//                    add ( add.getText ( ) + '\n' );
//                    add ( auction.getId ( ) );
//                }} , false );
//                add.setText ( "" );
//            }
//        });
        basePrice.setText(auction.getBasePrice() + "$");
        productPane.getChildren().add(productCard);
        submitPrice.setOnMouseClicked(event -> submitPrice());
        submitPrice.setCursor(Cursor.HAND);
        setback();
    }

    private void submitPrice() {
        if (offerPrice.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "enter price", "Enter price");
            return;
        }
        if (!offerPrice.getText().matches("^\\d*(\\.\\d+)?$")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "enter price", "Enter price (like 20.10)");
            return;
        }

        if (previousPrice >= Double.parseDouble(offerPrice.getText())) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "enter price", "you can just increase your offer price.");
            return;
        }

        if (Double.parseDouble(offerPrice.getText()) < Double.parseDouble(offerPrice.getText().replace("$", ""))) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "enter price", "Enter price more than base price.");
            return;
        }

        String offerPriceResponse = getOfferPriceForAuction(auction.getId(), Double.parseDouble(offerPrice.getText()));
        if (!offerPriceResponse.equals("successful")) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "poor :(", offerPriceResponse);
            return;
        }
        basePrice.setText(offerPrice.getText() + "$");
        App.showAlert(Alert.AlertType.CONFIRMATION, App.currentStage, "entered price", offerPrice.getText() + "$ submitted");

        previousPrice = Double.parseDouble(offerPrice.getText());
    }

    private void setTimer() {
        Timer timer1 = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    LocalDateTime now = LocalDateTime.now();
                    Duration duration = Duration.between(now, auction.getEndTime());
                    int day = (int) TimeUnit.SECONDS.toDays(duration.getSeconds());
                    long hours = TimeUnit.SECONDS.toHours(duration.getSeconds()) - (day *24);
                    long minute = TimeUnit.SECONDS.toMinutes(duration.getSeconds()) - (TimeUnit.SECONDS.toHours(duration.getSeconds())* 60);
                    long second = TimeUnit.SECONDS.toSeconds(duration.getSeconds()) - (TimeUnit.SECONDS.toMinutes(duration.getSeconds()) *60);
                    timer.setText(hours + ":" + minute + ":" + second);
                    if (duration.isZero() || duration.isNegative()) {
                        timer1.cancel();
                        showWinner();
                    }
                });
                Platform.setImplicitExit(false);
            }
        };
        timer1.schedule(timerTask, new Date(), 1000);
    }

    private void showWinner() {
        String response = auctionPurchase(auction.getId());
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "", response);
        submitPrice.setDisable(true);
    }

    private void setback() {
        back.setOnMouseClicked ( event -> {
            isInAllAuction = true;
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (isInAllAuction) {
                        App.setRoot("allAuctionsMenu");
                        System.out.println("load");
                    } else
                        timer.cancel();
                }
            };
            timer.schedule(timerTask, new Date(), 60000);
            try {
                dataOutputStream.writeUTF(toJson(new Request (SUPPORT_CHAT_BACK, null, "")));
            } catch (IOException ioException) {
                ioException.printStackTrace ( );
            }
        } );
        back.setOnMousePressed ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" ) );
        back.setOnMouseReleased ( event -> back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" ) );
    }
}
