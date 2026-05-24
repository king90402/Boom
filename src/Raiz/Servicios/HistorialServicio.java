/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/HistorialServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Estructuras.PilaHistorial;
import Raiz.Modelos.Historial;
import java.io.*;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada de gestionar todas las operaciones relacionadas con el historial

public class HistorialServicio {

    // Singleton

    private static HistorialServicio instancia;
    public static HistorialServicio getInstancia() {
        if (instancia == null) {
            synchronized (HistorialServicio.class) {
                if (instancia == null) instancia = new HistorialServicio();
            }
        }
        return instancia;
    }

    // Constantes

    private static final String ARCHIVO_HISTORIAL = "historial.txt";

    // La pila en memoria solo actúa como caché de escritura;
    // para consultas completas siempre se lee el archivo.

    private PilaHistorial pilaCache;


    private HistorialServicio() {
        this.pilaCache = new PilaHistorial();
    }

    // ----------------------------------------------------------------
    //  REGISTRO (todos los servicios llaman aquí)
    // ----------------------------------------------------------------

    /**
     * Registra cualquier acción del sistema y la persiste inmediatamente.
     */

    public void registrarAccion(Historial accion) {
        if (accion == null) return;
        pilaCache.push(accion);
        appendAlArchivo(accion);
    }

    /** Registra una acción pasando los datos directamente. */

    public void registrar(Historial.TipoAccion tipo, String descripcion, String idUsuario) {
        registrarAccion(new Historial(tipo, descripcion, idUsuario));
    }

    /** Registra una acción con producto y monto asociados. */

    public void registrar(Historial.TipoAccion tipo, String descripcion,
                          String idUsuario, String idProducto, double monto) {
        registrarAccion(new Historial(tipo, descripcion, idUsuario, idProducto, monto));
    }

    // ----------------------------------------------------------------
    //  CONSULTAS (solo para admin)
    // ----------------------------------------------------------------

    /** Devuelve TODAS las acciones del sistema (más reciente primero). */

    public ArrayList<Historial> obtenerTodo() {
        return cargarTodoDesdeArchivo();
    }

    /** Alias de obtenerTodo() para compatibilidad. */

    public ArrayList<Historial> obtenerHistorialCompleto() {
        return obtenerTodo();
    }

    /** Devuelve las acciones de un usuario específico (más reciente primero). */

    public ArrayList<Historial> obtenerPorUsuario(String idUsuario) {
        ArrayList<Historial> resultado = new ArrayList<>();
        for (Historial h : cargarTodoDesdeArchivo()) {
            if (idUsuario.equals(h.getIdUsuario())) resultado.add(h);
        }
        return resultado;
    }

    /** Devuelve las acciones de un tipo específico. */

    public ArrayList<Historial> obtenerPorTipo(Historial.TipoAccion tipo) {
        ArrayList<Historial> resultado = new ArrayList<>();
        for (Historial h : cargarTodoDesdeArchivo()) {
            if (h.getTipo() == tipo) resultado.add(h);
        }
        return resultado;
    }

    /** Alias de obtenerPorTipo() para compatibilidad. */

    public ArrayList<Historial> filtrarHistorialCompletoPorTipo(Historial.TipoAccion tipo) {
        return obtenerPorTipo(tipo);
    }

    /** Devuelve solo las acciones administrativas (CRUD usuarios y productos). */

    public ArrayList<Historial> obtenerAccionesAdmin() {
        ArrayList<Historial> resultado = new ArrayList<>();
        for (Historial h : cargarTodoDesdeArchivo()) {
            if (h.esAccionAdmin()) resultado.add(h);
        }
        return resultado;
    }

    /** Alias de obtenerAccionesAdmin() para compatibilidad. */

    public ArrayList<Historial> obtenerHistorialAdmin() {
        return obtenerAccionesAdmin();
    }

    /** Devuelve las últimas N acciones del sistema. */

    public ArrayList<Historial> obtenerUltimas(int n) {
        ArrayList<Historial> todas = cargarTodoDesdeArchivo();
        int limite = Math.min(n, todas.size());
        return new ArrayList<>(todas.subList(0, limite));
    }

    /** Devuelve las últimas N acciones de un usuario. */

    public ArrayList<Historial> obtenerUltimasPorUsuario(String idUsuario, int n) {
        ArrayList<Historial> porUsuario = obtenerPorUsuario(idUsuario);
        int limite = Math.min(n, porUsuario.size());
        return new ArrayList<>(porUsuario.subList(0, limite));
    }


    public int getCantidadTotal() {
        return cargarTodoDesdeArchivo().size();
    }

    // ----------------------------------------------------------------
    //  Limpieza
    // ----------------------------------------------------------------


    public void limpiarTodo() {
        pilaCache.vaciar();
        try { new FileWriter(ARCHIVO_HISTORIAL).close(); }
        catch (IOException e) { System.err.println("[HistorialServicio] Error al limpiar: " + e.getMessage()); }
    }

    // ----------------------------------------------------------------
    //  Persistencia
    // ----------------------------------------------------------------

    /** Agrega una línea al final del archivo (append). O(1). */

    private void appendAlArchivo(Historial h) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_HISTORIAL, true))) {
            writer.write(h.toArchivoLinea());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("[HistorialServicio] Error al escribir: " + e.getMessage());
        }
    }

    /**
     * Lee el archivo completo y retorna la lista con el más reciente primero
     * (el archivo está en orden cronológico, así que invertimos).
     */

    private ArrayList<Historial> cargarTodoDesdeArchivo() {
        ArrayList<Historial> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_HISTORIAL);
        if (!archivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HISTORIAL))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Historial h = Historial.fromArchivoLinea(linea);
                if (h != null) lista.add(0, h); // insertar al inicio = más reciente primero
            }
        } catch (IOException e) {
            System.err.println("[HistorialServicio] Error al leer: " + e.getMessage());
        }
        return lista;
    }
}
