/*
 * Nodo para Historial - Pila (estructura LIFO)
 * Proyecto Boom Sincronizado
 */

package Raiz.Modelos;

/**
 * @author BoomTeam
 * Nodo específico para la pila del historial de acciones del sistema
 */
public class NodoHistorial {

    private Historial accion;
    private NodoHistorial siguiente; // siguiente hacia la base de la pila

    public NodoHistorial(Historial accion) {
        this.accion    = accion;
        this.siguiente = null;
    }

    // Getters
    public Historial getAccion()           { return accion; }
    public NodoHistorial getSiguiente()    { return siguiente; }

    // Setters
    public void setAccion(Historial accion)            { this.accion = accion; }
    public void setSiguiente(NodoHistorial siguiente)  { this.siguiente = siguiente; }
}
