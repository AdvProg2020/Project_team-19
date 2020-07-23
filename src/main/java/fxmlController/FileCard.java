package fxmlController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.FileProduct;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static clientController.ServerConnection.*;
public class FileCard implements Initializable {
    private FileProduct fileProduct;
    public static String username;
    public static String password;
    @FXML
    private Label seller;

    @FXML
    private ImageView bank;

    @FXML
    private ImageView wallet;

    @FXML
    private Label price;

    @FXML
    private Label name;

    @FXML
    private Label description;

    public FileCard(FileProduct fileProduct) {
        this.fileProduct = fileProduct;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(fileProduct.getName());
        price.setText(fileProduct.getPrice() + "$");
        description.setText(fileProduct.getDescription());
        seller.setText(fileProduct.getSellerId());
        bank.setOnMouseClicked(event -> getInfo());
        bank.setCursor(Cursor.HAND);
        wallet.setCursor(Cursor.HAND);
        wallet.setOnMouseClicked(event -> walletPay());
    }

    void bankPay() {
        String response = fileBankPurchase(fileProduct.getId(), username, password);
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "", response);
        if (response.equalsIgnoreCase("successful")) {
            int index = fileProduct.getAddress().lastIndexOf("\\");
            String name = fileProduct.getAddress().substring(index + 1);
            System.out.println(name);
            buyFileRequest(fileProduct.getSellerId(), fileProduct.getId(), name);
        }
    }

    void getInfo() {

        Parent parent = null;
        Stage stage = new Stage();
        GetBankInfo getBankInfo = new GetBankInfo(stage);
        FXMLLoader loader = new FXMLLoader(FileCard.class.getResource("/fxml/getBankInfo.fxml"));
        loader.setController(getBankInfo);
        try {
             parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent, 300, 200);
        stage.setScene(scene);
        AtomicBoolean close = new AtomicBoolean(false);
        stage.setOnCloseRequest(event -> close.set(true));
        stage.showAndWait();

        if (!close.get())
            bankPay();
    }

    void walletPay() {
        String response = fileWalletPurchase(fileProduct.getId());
        App.showAlert(Alert.AlertType.INFORMATION, App.currentStage, "", response);
        if (response.equalsIgnoreCase("successful")) {
            int index = fileProduct.getAddress().lastIndexOf("\\");
            String name = fileProduct.getAddress().substring(index + 1);
            buyFileRequest(fileProduct.getSellerId(), fileProduct.getId(), name);
        }
    }
}
