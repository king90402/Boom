/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/NodoFavorito.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase NODO para la LDE favoritos

public class NodoFavorito {
    
    private String idProducto;
    private NodoFavorito siguiente;


    public NodoFavorito(String idProducto) {
        this.idProducto = idProducto;
        this.siguiente  = null;
    }

    // Getters

    public String getIdProducto() {
        return idProducto;
    }

    public NodoFavorito getSiguiente() {
        return siguiente;
    }

    // Setters

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setSiguiente(NodoFavorito siguiente) {
        this.siguiente = siguiente;
    }
}
