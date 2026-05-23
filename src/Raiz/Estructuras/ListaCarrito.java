/*
 * Estructura de Datos: LISTA DOBLEMENTE ENLAZADA para el carrito de compras
 * Proyecto Boom Sincronizado
 */

package Raiz.Estructuras;

import Raiz.Modelos.ItemCarrito;
import Raiz.Modelos.NodoItemCarrito;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Lista doblemente enlazada para manejar los ítems del carrito de compras
 */
public class ListaCarrito {

    private NodoItemCarrito cabeza;
    private NodoItemCarrito cola;
    private int tamaño;

    public ListaCarrito() {
        this.cabeza = null;
        this.cola   = null;
        this.tamaño = 0;
    }

    // ----- Consulta general -----

    public boolean estaVacia() { return cabeza == null; }
    public int getTamaño()     { return tamaño; }

    // ----- Inserción -----

    /** Agrega un ítem al final del carrito. O(1) */
    public void insertarAlFinal(ItemCarrito item) {
        NodoItemCarrito nuevo = new NodoItemCarrito(item);
        if (estaVacia()) {
            cabeza = nuevo;
            cola   = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamaño++;
    }

    // ----- Eliminación -----

    /** Elimina el ítem igual al dado (usa equals de ItemCarrito). O(n) */
    public boolean eliminar(ItemCarrito item) {
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            if (actual.getItem().equals(item)) {
                if (actual == cabeza && actual == cola) {
                    cabeza = null;
                    cola   = null;
                } else if (actual == cabeza) {
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(null);
                } else if (actual == cola) {
                    cola = cola.getAnterior();
                    cola.setSiguiente(null);
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /** Elimina el ítem que corresponde al idProducto dado. O(n) */
    public boolean eliminarPorIdProducto(String idProducto) {
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            if (actual.getItem().getProducto().getIdProducto().equals(idProducto)) {
                return eliminar(actual.getItem());
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // ----- Búsqueda -----

    /** Busca el ítem por idProducto. O(n) */
    public ItemCarrito buscarPorIdProducto(String idProducto) {
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            if (actual.getItem().getProducto().getIdProducto().equals(idProducto)) {
                return actual.getItem();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /** Indica si ya existe un ítem con ese idProducto. O(n) */
    public boolean contieneProducto(String idProducto) {
        return buscarPorIdProducto(idProducto) != null;
    }

    // ----- Obtención -----

    /** Devuelve todos los ítems como ArrayList (de cabeza a cola). O(n) */
    public ArrayList<ItemCarrito> obtenerTodos() {
        ArrayList<ItemCarrito> lista = new ArrayList<>();
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            lista.add(actual.getItem());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    // ----- Cálculos -----

    /** Suma los subtotales de todos los ítems. O(n) */
    public double calcularTotal() {
        double total = 0;
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            total += actual.getItem().getSubtotal();
            actual = actual.getSiguiente();
        }
        return total;
    }

    /** Suma las cantidades de todos los ítems. O(n) */
    public int getCantidadTotalProductos() {
        int total = 0;
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            total += actual.getItem().getCantidad();
            actual = actual.getSiguiente();
        }
        return total;
    }

    /** Verifica que todos los ítems tengan stock suficiente. O(n) */
    public boolean tieneStockSuficiente() {
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            if (!actual.getItem().tieneStockSuficiente()) return false;
            actual = actual.getSiguiente();
        }
        return true;
    }

    // ----- Limpieza -----

    public void vaciar() {
        cabeza = null;
        cola   = null;
        tamaño = 0;
    }

    public NodoItemCarrito getCabeza() { return cabeza; }
    public NodoItemCarrito getCola()   { return cola; }
}
