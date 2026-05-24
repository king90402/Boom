/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/NodoProducto.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase NODO para la LDE productos

public class NodoProducto {
    

    private Producto producto;
    private NodoProducto siguiente;
    private NodoProducto anterior;
    

    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
        this.anterior = null;
    }
    
    // Getters

    public Producto getProducto() {
        return producto;
    }

    public NodoProducto getSiguiente() {
        return siguiente;
    }

    public NodoProducto getAnterior() {
        return anterior;
    }
    
    // Setters

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setSiguiente(NodoProducto siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(NodoProducto anterior) {
        this.anterior = anterior;
    }
}
