/*
 * Nodo para Favorito - Lista Simplemente Enlazada
 * Proyecto Boom Sincronizado
 */

package Raiz.Modelos;

/**
 * @author BoomTeam
 * Nodo específico para la lista de productos favoritos (almacena el ID del producto)
 */
public class NodoFavorito {

    private String idProducto;
    private NodoFavorito siguiente;

    public NodoFavorito(String idProducto) {
        this.idProducto = idProducto;
        this.siguiente  = null;
    }

    // Getters
    public String getIdProducto()         { return idProducto; }
    public NodoFavorito getSiguiente()    { return siguiente; }

    // Setters
    public void setIdProducto(String idProducto)       { this.idProducto = idProducto; }
    public void setSiguiente(NodoFavorito siguiente)   { this.siguiente = siguiente; }
}
