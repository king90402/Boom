/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ListaFavoritos.java to edit this template
 */

package Raiz.Estructuras;

import Raiz.Modelos.NodoFavorito;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo de los favoritos mediante una lista doblemente enlazada

public class ListaFavoritos {
    
    private NodoFavorito cabeza;
    private int tamaño;


    public ListaFavoritos() {
        this.cabeza = null;
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

    public boolean contiene(String idProducto) {
        NodoFavorito actual = cabeza;
        while (actual != null) {
            if (actual.getIdProducto().equals(idProducto)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    // ----- Obtención -----

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


    public NodoFavorito getCabeza() {
        return cabeza;
    }
}
