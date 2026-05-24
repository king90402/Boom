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
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Carga de fuentes
        
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Black.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-ExtraBold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-ExtraLight.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Italic.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Light.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-SemiBold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Inter_18pt-Thin.ttf"), 12);

        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-Black.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-ExtraBold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-ExtraLight.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-Light.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-SemiBold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("Raiz/Fuente/Poppins-Thin.ttf"), 12);
        
        // Carga de la Ventana de Registro e Inicio de Sesion
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Raiz/Vistas/Vista_Login-Registro.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        // Configuracion de la ventana
        
        primaryStage.setTitle("Boom");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(450);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
