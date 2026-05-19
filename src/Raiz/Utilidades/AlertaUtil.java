/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AletaUtil.java to edit this template
 */

package Raiz.Utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * @author alejo
 */

// --------- Clase para alerta nativa de JavaFX

public class AlertaUtil {
    
    // ----- Tipos de alerta
    
    public static void mostrarInformacion(String titulo, String mensaje) {
        mostrarAlerta(AlertType.INFORMATION, titulo, mensaje);
    }
    
    public static void mostrarAdvertencia(String titulo, String mensaje) {
        mostrarAlerta(AlertType.WARNING, titulo, mensaje);
    }
    
    public static void mostrarError(String titulo, String mensaje) {
        mostrarAlerta(AlertType.ERROR, titulo, mensaje);
    }
    
    public static boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    public static boolean mostrarConfirmacionPersonalizada(String titulo, String mensaje, 
                                                           String textoAceptar, String textoCancelar) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        
        ButtonType botonAceptar = new ButtonType(textoAceptar);
        ButtonType botonCancelar = new ButtonType(textoCancelar);
        
        alerta.getButtonTypes().setAll(botonAceptar, botonCancelar);
        
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == botonAceptar;
    }
    
    // ---- Aspecto alert
    
    private static void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    // ----- Metodos validadores
    
    public static void mostrarExito(String operacion) {
        mostrarInformacion("Operacion exitosa", 
            operacion + " se ha completado correctamente.");
    }

    public static void mostrarCamposVacios() {
        mostrarAdvertencia("Campos vacios", 
            "Por favor complete todos los campos requeridos.");
    }

    public static void mostrarErrorConexion() {
        mostrarError("Error de conexion", 
            "No se pudo establecer conexion. Intente de nuevo.");
    }
    
    public static void mostrarSesionExpirada() {
        mostrarAdvertencia("Sesion expirada", 
            "Su sesion ha expirado. Por favor inicie sesion de nuevo.");
    }
    
    public static boolean confirmarEliminacion(String elemento) {
        return mostrarConfirmacion("Confirmar eliminacion", 
            "Esta seguro de eliminar " + elemento + "?\nEsta accion no se puede deshacer.");
    }
    
    public static boolean confirmarCerrarSesion() {
        return mostrarConfirmacion("Cerrar sesion", 
            "Esta seguro de cerrar la sesion actual?");
    }
}
