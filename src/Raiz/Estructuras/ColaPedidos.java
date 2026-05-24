/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ColaPedidos.java to edit this template
 */

package Raiz.Estructuras;

import Raiz.Modelos.NodoPedido;
import Raiz.Modelos.Pedido;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo de los pedidos mediante una cola

public class ColaPedidos {
    
    private NodoPedido frente; 
    private NodoPedido final_; 

    private int tamaño;


    public ColaPedidos() {
        this.frente = null;
        this.final_ = null;
        this.tamaño = 0;
    }

    // ----- Consulta general -----


    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamaño() {
        return tamaño;
    }

    // ----- Operaciones FIFO -----

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

    public Pedido desencolar() {
        if (estaVacia()) return null;
        Pedido pedido = frente.getPedido();
        frente = frente.getSiguiente();
        tamaño--;
        if (frente == null) final_ = null;
        return pedido;
    }

    public Pedido verFrente() {
        return frente != null ? frente.getPedido() : null;
    }

    // ----- Búsqueda -----

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

    /** Indica si existe un pedido con ese ID en la cola. */

    public boolean contienePedido(String idPedido) {
        return buscarPorId(idPedido) != null;
    }

    // ----- Eliminación -----

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


    public NodoPedido getFrente() {
        return frente;
    }

    public NodoPedido getFinal() {
        return final_;
    }

    @Override

    public String toString() {
        return "ColaPedidos[tamaño=" + tamaño +
               ", frente=" + (frente != null ? frente.getPedido().getIdPedido() : "vacía") + "]";
    }
}
