/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client2;

import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 *
 * @author Madaj
 */
public class MainApp2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
          
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Messager-APP Client");
        stage.setScene(scene);
        stage.setResizable(false);
        URL icon = MainApp2.class.getResource("/resources/icon.png");
        Image img = new Image(icon.toString());
        stage.getIcons().add(img);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
