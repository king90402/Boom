/*
 * Estructura de Datos: LISTA SIMPLEMENTE ENLAZADA para favoritos del usuario
 * Proyecto Boom Sincronizado
 */

package Raiz.Estructuras;

import Raiz.Modelos.NodoFavorito;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Lista simplemente enlazada para manejar los IDs de productos favoritos de un usuario
 */
public class ListaFavoritos {

    private NodoFavorito cabeza;
    private int tamaño;

    public ListaFavoritos() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    // ----- Consulta general -----

    public boolean estaVacia() { return cabeza == null; }
    public int getTamaño()     { return tamaño; }

    // ----- Inserción -----

    /** Agrega el ID al final de la lista. O(n) */
    public void insertarAlFinal(String idProducto) {
        NodoFavorito nuevo = new NodoFavorito(idProducto);
        if (estaVacia()) {
            cabeza = nuevo;
        } else {
            NodoFavorito actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamaño++;
    }

    // ----- Eliminación -----

    /** Elimina el primer nodo con ese idProducto. O(n) */
    public boolean eliminar(String idProducto) {
        if (estaVacia()) return false;

        if (cabeza.getIdProducto().equals(idProducto)) {
            cabeza = cabeza.getSiguiente();
            tamaño--;
            return true;
        }

        NodoFavorito actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getIdProducto().equals(idProducto)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // ----- Búsqueda -----

    /** Indica si el idProducto ya está en favoritos. O(n) */
    public boolean contiene(String idProducto) {
        NodoFavorito actual = cabeza;
        while (actual != null) {
            if (actual.getIdProducto().equals(idProducto)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    // ----- Obtención -----

    /** Devuelve todos los IDs como ArrayList. O(n) */
    public ArrayList<String> obtenerTodos() {
        ArrayList<String> lista = new ArrayList<>();
        NodoFavorito actual = cabeza;
        while (actual != null) {
            lista.add(actual.getIdProducto());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    // ----- Limpieza -----

    public void vaciar() {
        cabeza = null;
        tamaño = 0;
    }

    public NodoFavorito getCabeza() { return cabeza; }
}
