/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client2;

import java.awt.Desktop;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Freiherr von und zu Madaj
 */
public class SceneController2 implements Initializable {

    @FXML
    private MenuBar menubar;

    @FXML
    private Button button_send;

    @FXML
    private TextField tf_message;

    @FXML
    private VBox vbox_message;

    @FXML
    private ScrollPane sp_main;

    private Client client;

    @FXML
    void closeapplicationWhenPressclosed(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void openhelpwindowOnClick(ActionEvent event) throws URISyntaxException, IOException {

        Desktop.getDesktop().browse(new URI("http://www.google.de"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            client = new Client(new Socket("localhost", 8080));
            System.out.println("Connected to Server");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO FEHLER");

        }

        vbox_message.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                sp_main.setVvalue((Double) newValue);

            }

        });

        client.receiveMessageFromServer(vbox_message);

        button_send.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String messageToSend = tf_message.getText();
                if (!messageToSend.isEmpty()) {

                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239, 242,255);" + "-fx-background-color: rgb(15,125,242);" + " -fx-background-radius: 20px");
                    text.setFill(Color.color(0.934, 0.945, 0.966));

                    hBox.getChildren().add(textFlow);

                    vbox_message.getChildren().add(hBox);

                    client.sendMessageToServer(messageToSend);
                    tf_message.clear();
                }

            }

        });

    }

    public static void addLabel(String msgFromServer, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color: rgb(233, 233,235);" + " -fx-background-radius: 20px:");

        textFlow.setPadding(new Insets(5, 5, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }

        });

    }
}
