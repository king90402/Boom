/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/NodoItemCarrito.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase NODO para la LDE carrito

public class NodoItemCarrito {
    
    private ItemCarrito item;
    private NodoItemCarrito siguiente;
    private NodoItemCarrito anterior;


    public NodoItemCarrito(ItemCarrito item) {
        this.item = item;
        this.siguiente = null;
        this.anterior = null;
    }

    // Getters

    public ItemCarrito getItem() {
        return item;
    }

    public NodoItemCarrito getSiguiente() {
        return siguiente;
    }

    public NodoItemCarrito getAnterior() {
        return anterior;
    }

    // Setters

    public void setItem(ItemCarrito item) {
        this.item = item;
    }

    public void setSiguiente(NodoItemCarrito siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(NodoItemCarrito anterior) {
        this.anterior = anterior;
    }
}
