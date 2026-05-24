/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ListaCarrito.java to edit this template
 */

package Raiz.Estructuras;

import Raiz.Modelos.ItemCarrito;
import Raiz.Modelos.NodoItemCarrito;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo del carrito mediante una lista doblemente enlazada

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


    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamaño() {
        return tamaño;
    }

    // ----- Inserción -----

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

    public boolean contieneProducto(String idProducto) {
        return buscarPorIdProducto(idProducto) != null;
    }

    // ----- Obtención -----

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

    public double calcularTotal() {
        double total = 0;
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            total += actual.getItem().getSubtotal();
            actual = actual.getSiguiente();
        }
        return total;
    }

    public int getCantidadTotalProductos() {
        int total = 0;
        NodoItemCarrito actual = cabeza;
        while (actual != null) {
            total += actual.getItem().getCantidad();
            actual = actual.getSiguiente();
        }
        return total;
    }

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


    public NodoItemCarrito getCabeza() {
        return cabeza;
    }

    public NodoItemCarrito getCola() {
        return cola;
    }
}
