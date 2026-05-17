/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author EQUIPO
 */

public class NodoProducto {
    private producto producto;
    private NodoProducto siguiente;
    private NodoProducto anterior;
    
    public NodoProducto(producto producto) {
        this.producto = producto;
        this.siguiente = null;
        this.anterior = null;
    }

    public producto getProducto() {
        return producto;
    }

    public void setProducto(producto producto) {
        this.producto = producto;
    }

    public NodoProducto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoProducto siguiente) {
        this.siguiente = siguiente;
    }

    public NodoProducto getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoProducto anterior) {
        this.anterior = anterior;
    }
}
