/*
 * Estructura de Datos: PILA (LIFO) para el historial de acciones
 * Proyecto Boom Sincronizado
 * UNIDAD 5: PILAS
 *
 * Principio LIFO: la acción más reciente queda en el tope.
 * Solo visible para administradores del sistema.
 */

package Raiz.Estructuras;

import Raiz.Modelos.Historial;
import Raiz.Modelos.NodoHistorial;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Pila específica para el historial de acciones del sistema
 */
public class PilaHistorial {

    private NodoHistorial tope;
    private int tamaño;
    private int capacidadMaxima; // -1 = ilimitada

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

    public boolean estaVacia() { return tope == null; }
    public boolean estaLlena() { return capacidadMaxima > 0 && tamaño >= capacidadMaxima; }
    public int getTamaño()     { return tamaño; }

    // ----- Operaciones LIFO -----

    /**
     * PUSH — Registra una nueva acción en el tope. O(1)
     * @return false si la pila está llena
     */
    public boolean push(Historial accion) {
        if (estaLlena()) return false;
        NodoHistorial nuevo = new NodoHistorial(accion);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamaño++;
        return true;
    }

    /**
     * POP — Extrae y retorna la acción más reciente (deshacer). O(1)
     * @return null si está vacía
     */
    public Historial pop() {
        if (estaVacia()) return null;
        Historial accion = tope.getAccion();
        tope = tope.getSiguiente();
        tamaño--;
        return accion;
    }

    /**
     * PEEK — Consulta la acción más reciente sin extraerla. O(1)
     * @return null si está vacía
     */
    public Historial peek() {
        return tope != null ? tope.getAccion() : null;
    }

    // ----- Búsqueda y filtros -----

    /** Filtra acciones por tipo. O(n) */
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

    /** Filtra acciones por usuario. O(n) */
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

    /** Devuelve solo acciones administrativas (CRUD de usuarios). O(n) */
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

    /** Devuelve todas las acciones (más reciente primero). O(n) */
    public ArrayList<Historial> obtenerTodos() {
        ArrayList<Historial> lista = new ArrayList<>();
        NodoHistorial actual = tope;
        while (actual != null) {
            lista.add(actual.getAccion());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    /** Devuelve las últimas N acciones. O(n) */
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

    public NodoHistorial getTope() { return tope; }

    @Override
    public String toString() {
        return "PilaHistorial[tamaño=" + tamaño +
               (capacidadMaxima > 0 ? "/" + capacidadMaxima : "") +
               ", tope=" + (tope != null ? tope.getAccion().getTipo() : "vacía") + "]";
    }
}
