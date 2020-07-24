package fxmlController;

import clientController.ServerConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Support;
import server.Request;
import view.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static clientController.ServerConnection.*;
import static server.PacketType.EXIT;
import static server.PacketType.SUPPORT_CHAT_BACK;

public class SupportChatSupport implements Initializable {

    public TextArea see;
    public TextField type;
    public Button add;
    public ScrollPane scrollPane;
    public GridPane gridPane;
    public String chattingWith;

    ExecutorService executor = Executors.newCachedThreadPool ( );

    @FXML
    private FontAwesomeIcon back;

    @FXML
    void back ( MouseEvent event ) {
        try {
            dataOutputStream.writeUTF(toJson(new Request (SUPPORT_CHAT_BACK, null, "")));
        } catch (IOException ioException) {
            ioException.printStackTrace ( );
        }
        App.setRoot ( "supportMenu" );
    }

    @FXML
    private void backSizeBig ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 20;-fx-effect: innershadow(gaussian, #17b5ff,75,0,5,0);" );
    }

    @FXML private void backSizeSmall ( MouseEvent mouseEvent ) {
        back.setStyle ( "-fx-font-family: FontAwesome; -fx-font-size: 1em" );
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {

        ArrayList< String > clientUsernamesWithChats = getAllClientsWithChats();
        int i = 0;
        for (String clientUsernameWithChats : clientUsernamesWithChats) {
            Label label = new Label ( clientUsernameWithChats );
            label.setStyle ( "-fx-font-family: 'Consolas'; -fx-font-size: 20; -fx-font-color: white; -fx-border-color: #225f8e; -fx-background-color: #89b7ff;" );
            label.setPrefHeight ( 50 );
            GridPane.setHalignment ( label , HPos.CENTER );
            gridPane.setGridLinesVisible ( true );
            if (i == 0) {
                chattingWith = label.getText ();

                supportChatOpen ( new ArrayList <String> ( ) {{
                    add ( "support" );
                    add ( chattingWith );
                }} );
            }
            gridPane.add ( label , 0 , i++ );
        }


        gridPane.getChildren ().forEach ( item -> item.setOnMouseClicked ( event -> {

            see.setText ( "" );

            chattingWith = ((Label)item).getText ();

            supportChatOpen ( new ArrayList <String> ( ) {{
                add ( "support" );
                add ( chattingWith );
            }} );
        } ) );

        new Thread ( "Listening Thread" ) {
            @Override
            public void run () {
                try {
                    while(true) {
                        String string = dataInputStream.readUTF ( );
                        if ( string.equals ( "/back/" ) )
                            break;
                        else if ( string.startsWith ( "/open/" ) ) {
                            string = string.substring ( 6 );
                        }
                        displayMessage(string);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace ( );
                }
            }

            private void displayMessage ( String string ) {
                Task display_message = new Task<Void>() {
                    @Override
                    public Void call() throws Exception {

                        Platform.runLater( () -> see.appendText(string) );

                        return null;
                    }
                };

                executor.execute(display_message);
            }

        }.start ();

    }

    public void addAction ( ActionEvent event ) {
        String string = type.getText () + '\n';
        type.setText ( "" );
        supportChatSend ( new ArrayList <String> ( ) {{
            add ( "support" );
            add ( string );
            add ( chattingWith );
        }} );
    }
}
