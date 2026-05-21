/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Raiz/Utilidades/HistorialAcciones.java
package Raiz.Utilidades;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class HistorialAcciones {

    public static class Accion {
        public final String tipo;        // "AGREGAR", "ELIMINAR", "MODIFICAR"
        public final String descripcion;
        public final String idProducto;
        public final long   timestamp;

        public Accion(String tipo, String descripcion, String idProducto) {
            this.tipo        = tipo;
            this.descripcion = descripcion;
            this.idProducto  = idProducto;
            this.timestamp   = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "[" + tipo + "] " + descripcion;
        }
    }

    private static final int MAX = 50;

    private final Deque<Accion> pilaDeshacer = new ArrayDeque<>();
    private final Deque<Accion> pilaRehacer  = new ArrayDeque<>();

    public void registrar(String tipo, String descripcion, String idProducto) {
        if (pilaDeshacer.size() >= MAX) {
            ((ArrayDeque<Accion>) pilaDeshacer).removeLast();
        }
        pilaDeshacer.push(new Accion(tipo, descripcion, idProducto));
        pilaRehacer.clear();
    }

    public Accion deshacer() {
        if (pilaDeshacer.isEmpty()) return null;
        Accion accion = pilaDeshacer.pop();
        pilaRehacer.push(accion);
        return accion;
    }

    public Accion rehacer() {
        if (pilaRehacer.isEmpty()) return null;
        Accion accion = pilaRehacer.pop();
        pilaDeshacer.push(accion);
        return accion;
    }

    public ArrayList<String> getHistorialLegible() {
        ArrayList<String> lista = new ArrayList<>();
        for (Accion a : pilaDeshacer) {
            lista.add(a.toString());
        }
        return lista;
    }

    public boolean puedeDeshacer() { return !pilaDeshacer.isEmpty(); }
    public boolean puedeRehacer()  { return !pilaRehacer.isEmpty(); }
    public void    limpiar()       { pilaDeshacer.clear(); pilaRehacer.clear(); }
}