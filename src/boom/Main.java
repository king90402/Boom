/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author alejo
 */

    
    public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Login-Registro.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Boom");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
    

