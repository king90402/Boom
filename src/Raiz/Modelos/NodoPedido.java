/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/NodoPedido.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase NODO para la Cola de pedidos

public class NodoPedido {
    
    private Pedido pedido;
    private NodoPedido siguiente; // siguiente hacia el final de la cola


    public NodoPedido(Pedido pedido) {
        this.pedido    = pedido;
        this.siguiente = null;
    }

    // Getters

    public Pedido getPedido() {
        return pedido;
    }

    public NodoPedido getSiguiente() {
        return siguiente;
    }

    // Setters

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setSiguiente(NodoPedido siguiente) {
        this.siguiente = siguiente;
    }
}
