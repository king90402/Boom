/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/NodoHistorial.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase NODO para la Pila de historial

public class NodoHistorial {
    
    private Historial accion;
    private NodoHistorial siguiente; // siguiente hacia la base de la pila


    public NodoHistorial(Historial accion) {
        this.accion    = accion;
        this.siguiente = null;
    }

    // Getters

    public Historial getAccion() {
        return accion;
    }

    public NodoHistorial getSiguiente() {
        return siguiente;
    }

    // Setters

    public void setAccion(Historial accion) {
        this.accion = accion;
    }

    public void setSiguiente(NodoHistorial siguiente) {
        this.siguiente = siguiente;
    }
}
