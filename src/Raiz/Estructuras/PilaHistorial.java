/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/PilaHistorial.java to edit this template
 */

package Raiz.Estructuras;

import Raiz.Modelos.Historial;
import Raiz.Modelos.NodoHistorial;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo del historial mediante una pila

public class PilaHistorial {
    
    private NodoHistorial tope;
    private int tamaño;
    private int capacidadMaxima; 


    public PilaHistorial() {
        this.tope             = null;
        this.tamaño           = 0;
        this.capacidadMaxima  = -1;
    }


    public PilaHistorial(int capacidadMaxima) {
        this.tope             = null;
        this.tamaño           = 0;
        this.capacidadMaxima  = capacidadMaxima;
    }

    // ----- Consulta general -----

    public boolean estaVacia() {
        return tope == null;
    }

    public boolean estaLlena() {
        return capacidadMaxima > 0 && tamaño >= capacidadMaxima;
    }

    public int getTamaño() {
        return tamaño;
    }

    // ----- Operaciones LIFO -----

    // PUSH — Registra una nueva acción en el tope. 

    public boolean push(Historial accion) {
        if (estaLlena()) return false;
        NodoHistorial nuevo = new NodoHistorial(accion);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamaño++;
        return true;
    }

    // POP — Extrae y retorna la acción más reciente (deshacer). 

    public Historial pop() {
        if (estaVacia()) return null;
        Historial accion = tope.getAccion();
        tope = tope.getSiguiente();
        tamaño--;
        return accion;
    }

    //PEEK — Consulta la acción más reciente sin extraerla. 

    public Historial peek() {
        return tope != null ? tope.getAccion() : null;
    }

    // ----- Búsqueda y filtros -----

    public ArrayList<Historial> filtrarPorTipo(Historial.TipoAccion tipo) {
        ArrayList<Historial> resultado = new ArrayList<>();
        NodoHistorial actual = tope;
        while (actual != null) {
            if (actual.getAccion().getTipo() == tipo) {
                resultado.add(actual.getAccion());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }
    
    public ArrayList<Historial> filtrarPorUsuario(String idUsuario) {
        ArrayList<Historial> resultado = new ArrayList<>();
        NodoHistorial actual = tope;
        while (actual != null) {
            if (idUsuario.equals(actual.getAccion().getIdUsuario())) {
                resultado.add(actual.getAccion());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    public ArrayList<Historial> obtenerAccionesAdmin() {
        ArrayList<Historial> resultado = new ArrayList<>();
        NodoHistorial actual = tope;
        while (actual != null) {
            Historial.TipoAccion t = actual.getAccion().getTipo();
            if (t == Historial.TipoAccion.USUARIO_CREADO  ||
                t == Historial.TipoAccion.USUARIO_EDITADO ||
                t == Historial.TipoAccion.USUARIO_ELIMINADO) {
                resultado.add(actual.getAccion());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    // ----- Obtención -----

    public ArrayList<Historial> obtenerTodos() {
        ArrayList<Historial> lista = new ArrayList<>();
        NodoHistorial actual = tope;
        while (actual != null) {
            lista.add(actual.getAccion());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public ArrayList<Historial> obtenerUltimas(int n) {
        ArrayList<Historial> lista = new ArrayList<>();
        NodoHistorial actual = tope;
        int contador = 0;
        while (actual != null && contador < n) {
            lista.add(actual.getAccion());
            actual = actual.getSiguiente();
            contador++;
        }
        return lista;
    }

    // ----- Limpieza -----

    public void vaciar() {
        tope   = null;
        tamaño = 0;
    }


    public NodoHistorial getTope() {
        return tope;
    }

    @Override

    public String toString() {
        return "PilaHistorial[tamaño=" + tamaño +
               (capacidadMaxima > 0 ? "/" + capacidadMaxima : "") +
               ", tope=" + (tope != null ? tope.getAccion().getTipo() : "vacía") + "]";
    }
}
