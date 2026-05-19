/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package Raiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author alejo
 */

// ---------- Clase principal e incial para la carga de la apliaccion 

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Carga de la Ventana de Registro e Inicio de Sesion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Raiz/Vistas/Vista_Login-Registro.fxml"));
        Scene scene = new Scene(loader.load());
        
        // Configuracion de la ventana
        stage.setScene(scene);
        stage.setTitle("Boom");
        stage.setMinWidth(800);
        stage.setMinHeight(450);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
