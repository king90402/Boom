/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/NodoUsuario.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase NODO para la LDE usuarios

public class NodoUsuario {
    
    // Atributos
    
    private Usuario usuario;
    private NodoUsuario siguiente;
    private NodoUsuario anterior;
    
    // Constructor

    public NodoUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.siguiente = null;
        this.anterior = null;
    }
    
    // Getters
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public NodoUsuario getSiguiente() {
        return siguiente;
    }
    
    public NodoUsuario getAnterior() {
        return anterior;
    }
    
    // Setters
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void setSiguiente(NodoUsuario siguiente) {
        this.siguiente = siguiente;
    }
    
    public void setAnterior(NodoUsuario anterior) {
        this.anterior = anterior;
    }
    
    // Metodos auxiliares

    public boolean tieneSiguiente() {
        return siguiente != null;
    }

    public boolean tieneAnterior() {
        return anterior != null;
    }
    
    @Override
    public String toString() {
        return "Nodo[" + (usuario != null ? usuario.getNombreCompleto() : "null") + "]";
    }
}
