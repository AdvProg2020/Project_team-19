package fxmlController;

import controller.PersonController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.Salesperson;
import view.App;

import java.net.URL;
import java.util.ResourceBundle;
import static clientController.ServerConnection.*;

public class WalletMenu implements Initializable {
//    private TextField tokenField = new TextField();
    private TextField balanceField = new TextField();
    private String balance;
    @FXML private Label accountId;
    @FXML private TextField password;
    @FXML private Label walletBalance;
    @FXML private ImageView decreaseWallet;
    @FXML private TextField username;
    @FXML private Label token;
    @FXML private Label decreaseWalletLabel;
    @FXML private ImageView getToken;
    @FXML private TextField tokenField;
    @FXML private FontAwesomeIcon back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkPersonForWithdraw();
        getToken.setOnMouseClicked(event -> getToken());
        getToken.setCursor(Cursor.HAND);
    }

    private void checkPersonForWithdraw() {
        Person loginPerson = PersonController.getInstance().getLoggedInPerson();
        if (!(loginPerson instanceof Salesperson)) {
            decreaseWalletLabel.setOpacity(0.3);
            decreaseWallet.setOpacity(0.3);
            decreaseWallet.setDisable(true);
        }
    }

    private void getToken() {
        if (username.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "", "Enter bank username\nHere is your app username");
            return;
        }
        if (password.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "", "Enter bank password\nHere is your app password");
            return;
        }
        String response = getBankToken(username.getText(), password.getText());
        token.setText(response);
    }

    private void showPopup(boolean forToken, TextField textField, boolean forEnterMoneyAfter) {
        textField.setText("");
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        Label label = new Label();
        label.setText(forToken ? "Enter token :" : "Enter price :");
        label.setFont(Font.font("Tw Cen MT Condensed", 18));
        textField.setFont(Font.font("Tw Cen MT Condensed", 18));
        textField.getStylesheets().add("/fxml/textField.css");
        Button submit = new Button("Submit");
        submit.getStylesheets().add("/fxml/button.css");
        submit.getStyleClass().add("btn");
        gridPane.add(label, 0, 0);
        gridPane.add(textField, 0, 1);
        gridPane.add(submit, 0, 2);
        submit.setOnAction(event -> {
            if (textField.getText().isEmpty()) {
                App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Empty", "Fill the box");
                return;
            }
            if (forToken)
                 textField.getText();
            else {
                if (!textField.getText().matches("^\\d*(\\.\\d+)?$")) {
                    App.showAlert(Alert.AlertType.ERROR, App.currentStage, "wrong number", "Enter number for balance");
                    return;
                }
                balance = textField.getText();
            }
            stage.close();
            if (forEnterMoneyAfter)
                showPopup(false, balanceField, false);
        });
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground((new Background(new BackgroundFill(Color.rgb(153,221,255), CornerRadii.EMPTY, Insets.EMPTY))));
        Scene scene = new Scene(gridPane, 300, 180);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void increaseWalletBalance(MouseEvent event) {
        //showPopup(true, tokenField, true);
        //create_receipt token move money bankId furushgah kir
        //pay
        String msg = "create_receipt " + tokenField.getText() + " ";
        //todo connectTo bank ina ba server
    }

    @FXML
    void decreaseWalletBalance(MouseEvent event) {
        //showPopup(true, balanceField, true);
        //create_receipt token move money shop bankId kir
        //pay
        //todo
    }

    @FXML
    void getBalance(MouseEvent event) {
        if (tokenField.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Fill", "fill token box");
            return;
        }
        String response = getBankBalance(tokenField.getText());
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "Bank response", response);
    }

    @FXML
    void increaseBankBalance(MouseEvent event) {
        showPopup(true, tokenField, true);
        //create_receipt token deposit money -1 bankId kir
        //pay
    }

    @FXML
    void getTransactionPlus(MouseEvent event) {
        if (tokenField.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Fill", "fill token box");
            return;
        }
        String response = getTransaction(tokenField.getText(), "+");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response);
        alert.showAndWait();
    }

    @FXML
    void getTransactionMinus(MouseEvent event) {
        if (tokenField.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Fill", "fill token box");
            return;
        }
        String response = getTransaction(tokenField.getText(), "-");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response);
        alert.showAndWait();
    }

    @FXML
    void getAllTransactions(MouseEvent event) {
        if (tokenField.getText().isEmpty()) {
            App.showAlert(Alert.AlertType.ERROR, App.currentStage, "Fill", "fill token box");
            return;
        }
        String response = getTransaction(tokenField.getText(), "*");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, response);
        alert.showAndWait();
    }

    @FXML
    void back ( MouseEvent event ) {
        App.setRoot ( "mainMenu" );
    }

    @FXML private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

}
