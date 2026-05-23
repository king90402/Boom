/*
 * Estructura de Datos: COLA (FIFO) para pedidos pendientes
 * Proyecto Boom Sincronizado
 * UNIDAD 6: COLAS
 *
 * Principio FIFO: el primer pedido en entrar es el primero en procesarse.
 */

package Raiz.Estructuras;

import Raiz.Modelos.NodoPedido;
import Raiz.Modelos.Pedido;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Cola específica para manejar los pedidos pendientes del sistema
 */
public class ColaPedidos {

    private NodoPedido frente; // primer pedido en entrar (sale primero)
    private NodoPedido final_; // último pedido en entrar
    private int tamaño;

    public ColaPedidos() {
        this.frente = null;
        this.final_ = null;
        this.tamaño = 0;
    }

    // ----- Consulta general -----

    public boolean estaVacia() { return frente == null; }
    public int getTamaño()     { return tamaño; }

    // ----- Operaciones FIFO -----

    /**
     * ENCOLAR — Agrega un pedido al final de la cola. O(1)
     */
    public void encolar(Pedido pedido) {
        NodoPedido nuevo = new NodoPedido(pedido);
        if (estaVacia()) {
            frente = nuevo;
            final_ = nuevo;
        } else {
            final_.setSiguiente(nuevo);
            final_ = nuevo;
        }
        tamaño++;
    }

    /**
     * DESENCOLAR — Extrae y retorna el pedido del frente. O(1)
     * @return null si la cola está vacía
     */
    public Pedido desencolar() {
        if (estaVacia()) return null;
        Pedido pedido = frente.getPedido();
        frente = frente.getSiguiente();
        tamaño--;
        if (frente == null) final_ = null;
        return pedido;
    }

    /**
     * VER FRENTE — Consulta el siguiente pedido a procesar sin extraerlo. O(1)
     * @return null si la cola está vacía
     */
    public Pedido verFrente() {
        return frente != null ? frente.getPedido() : null;
    }

    // ----- Búsqueda -----

    /** Busca un pedido por su ID. O(n) */
    public Pedido buscarPorId(String idPedido) {
        NodoPedido actual = frente;
        while (actual != null) {
            if (actual.getPedido().getIdPedido().equals(idPedido)) {
                return actual.getPedido();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /** Indica si existe un pedido con ese ID en la cola. O(n) */
    public boolean contienePedido(String idPedido) {
        return buscarPorId(idPedido) != null;
    }

    // ----- Eliminación -----

    /**
     * Elimina un pedido específico de la cola (p. ej. cuando se cancela). O(n)
     */
    public boolean eliminarPorId(String idPedido) {
        if (estaVacia()) return false;

        if (frente.getPedido().getIdPedido().equals(idPedido)) {
            desencolar();
            return true;
        }

        NodoPedido actual = frente;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getPedido().getIdPedido().equals(idPedido)) {
                if (actual.getSiguiente() == final_) {
                    final_ = actual;
                }
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // ----- Obtención -----

    /** Devuelve todos los pedidos en orden FIFO (frente primero). O(n) */
    public ArrayList<Pedido> obtenerTodos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        NodoPedido actual = frente;
        while (actual != null) {
            lista.add(actual.getPedido());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    // ----- Limpieza -----

    public void vaciar() {
        frente = null;
        final_ = null;
        tamaño = 0;
    }

    public NodoPedido getFrente() { return frente; }
    public NodoPedido getFinal()  { return final_; }

    @Override
    public String toString() {
        return "ColaPedidos[tamaño=" + tamaño +
               ", frente=" + (frente != null ? frente.getPedido().getIdPedido() : "vacía") + "]";
    }
}
