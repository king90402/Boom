/*
 * Nodo para Pedido - Cola (estructura FIFO)
 * Proyecto Boom Sincronizado
 */

package Raiz.Modelos;

/**
 * @author BoomTeam
 * Nodo específico para la cola de pedidos pendientes
 */
public class NodoPedido {

    private Pedido pedido;
    private NodoPedido siguiente; // siguiente hacia el final de la cola

    public NodoPedido(Pedido pedido) {
        this.pedido    = pedido;
        this.siguiente = null;
    }

    // Getters
    public Pedido getPedido()           { return pedido; }
    public NodoPedido getSiguiente()    { return siguiente; }

    // Setters
    public void setPedido(Pedido pedido)           { this.pedido = pedido; }
    public void setSiguiente(NodoPedido siguiente) { this.siguiente = siguiente; }
}
