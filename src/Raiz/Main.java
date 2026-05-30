/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package Raiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author alejo
 */

// ---------- Clase principal e incial para la carga de la apliaccion 

public class Main extends Application {
    
    private void loadFont(String path) {
        var stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Fuente no encontrada: " + path);
            return;
        }
        Font.loadFont(stream, 12);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Carga de fuentes
        
        loadFont("/Raiz/Fuente/Inter_18pt-Black.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-Bold.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-ExtraBold.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-ExtraLight.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-Italic.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-Light.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-Medium.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-Regular.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-SemiBold.ttf");
        loadFont("/Raiz/Fuente/Inter_18pt-Thin.ttf");
 
        loadFont("/Raiz/Fuente/Poppins-Black.ttf");
        loadFont("/Raiz/Fuente/Poppins-Bold.ttf");
        loadFont("/Raiz/Fuente/Poppins-ExtraBold.ttf");
        loadFont("/Raiz/Fuente/Poppins-ExtraLight.ttf");
        loadFont("/Raiz/Fuente/Poppins-Light.ttf");
        loadFont("/Raiz/Fuente/Poppins-Medium.ttf");
        loadFont("/Raiz/Fuente/Poppins-Regular.ttf");
        loadFont("/Raiz/Fuente/Poppins-SemiBold.ttf");
        loadFont("/Raiz/Fuente/Poppins-Thin.ttf");
        
        loadFont("/Raiz/Fuente/Roboto-Regular.ttf");
        loadFont("/Raiz/Fuente/Roboto-Black.ttf");
        loadFont("/Raiz/Fuente/Roboto-Bold.ttf");
        loadFont("/Raiz/Fuente/Roboto-BoldCondensed.ttf");
        loadFont("/Raiz/Fuente/Roboto-Condensed.ttf");
        loadFont("/Raiz/Fuente/Roboto-Thin.ttf");
        loadFont("/Raiz/Fuente/Roboto-Light.ttf");
        loadFont("/Raiz/Fuente/Roboto-Medium.ttf");
        
        
        // Carga de la Ventana de Registro e Inicio de Sesion
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Raiz/Vistas/Vista_Login-Registro.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        // Configuracion de la ventana
        
        primaryStage.setTitle("Boom");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(450);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
