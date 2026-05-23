/*
 * Servicio de Historial - Proyecto Boom Sincronizado
 * IMPORTANTE: El historial solo es visible para ADMINISTRADORES
 */

package Raiz.Servicios;

import Raiz.Estructuras.PilaHistorial;
import Raiz.Modelos.Historial;
import java.io.*;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Gestiona el historial de acciones del sistema usando estructura PILA
 * NOTA: Solo los administradores pueden ver el historial
 */

public class HistorialServicio {
    
    // Singleton
    private static HistorialServicio instancia;
    
    public static HistorialServicio getInstancia() {
        if (instancia == null) {
            synchronized (HistorialServicio.class) {
                if (instancia == null) {
                    instancia = new HistorialServicio();
                }
            }
        }
        return instancia;
    }
    
    // Constantes
    private static final String ARCHIVO_HISTORIAL = "historial.txt";
    private static final int MAX_HISTORIAL = 100;
    
    // Atributos
    private PilaHistorial pilaHistorial;
    private String idUsuarioActual;
    
    // Constructor privado
    private HistorialServicio() {
        this.pilaHistorial = new PilaHistorial(MAX_HISTORIAL);
        this.idUsuarioActual = null;
    }
    
    // ----- Inicializacion por usuario -----
    
    /**
     * Carga el historial completo (solo para admins)
     */
    public void cargarHistorialCompleto() {
        this.pilaHistorial.vaciar();
        cargarTodoDesdeArchivo();
    }
    
    /**
     * Carga el historial de un usuario especifico
     */
    public void cargarHistorialUsuario(String idUsuario) {
        this.idUsuarioActual = idUsuario;
        this.pilaHistorial.vaciar();
        cargarDesdeArchivo();
    }
    
    /**
     * Reinicia para cambio de usuario
     */
    public void reiniciar() {
        guardarEnArchivo();
        this.pilaHistorial.vaciar();
        this.idUsuarioActual = null;
    }
    
    // ----- Operaciones de Pila -----
    
    /**
     * PUSH - Registra una nueva accion en el historial
     */
    public void registrarAccion(Historial accion) {
        if (accion == null) return;
        pilaHistorial.push(accion);
        guardarEnArchivo();
    }
    
    /**
     * Registra una accion simple
     */
    public void registrarAccion(Historial.TipoAccion tipo, String descripcion) {
        Historial h = new Historial(tipo, descripcion, idUsuarioActual);
        registrarAccion(h);
    }
    
    /**
     * Registra una accion administrativa (para acciones de CRUD de usuarios)
     */
    public void registrarAccionAdmin(Historial.TipoAccion tipo, String descripcion, String idAdmin) {
        Historial h = new Historial(tipo, descripcion, idAdmin);
        registrarAccion(h);
    }
    
    /**
     * POP - Obtiene y elimina la ultima accion (para "deshacer")
     */
    public Historial deshacerUltimaAccion() {
        Historial ultima = pilaHistorial.pop();
        if (ultima != null) {
            guardarEnArchivo();
        }
        return ultima;
    }
    
    /**
     * PEEK - Obtiene la ultima accion sin eliminarla
     */
    public Historial verUltimaAccion() {
        return pilaHistorial.peek();
    }
    
    // ----- Consultas (SOLO PARA ADMINS) -----
    
    /**
     * Obtiene todo el historial (del mas reciente al mas antiguo)
     * NOTA: Este metodo debe ser llamado solo desde la vista de admin
     */
    public ArrayList<Historial> obtenerHistorialCompleto() {
        // Cargar todo el historial para admins
        PilaHistorial pilaTemp = new PilaHistorial(MAX_HISTORIAL * 10);
        
        File archivo = new File(ARCHIVO_HISTORIAL);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HISTORIAL))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Historial h = Historial.fromArchivoLinea(linea);
                if (h != null) {
                    pilaTemp.push(h);
                }
            }
        } catch (IOException e) {
            System.err.println("[HistorialServicio] Error al cargar historial: " + e.getMessage());
        }
        
        return pilaTemp.obtenerTodos();
    }
    
    /**
     * Obtiene el historial del usuario actual
     */
    public ArrayList<Historial> obtenerHistorial() {
        return pilaHistorial.obtenerTodos();
    }
    
    /**
     * Obtiene las ultimas N acciones
     */
    public ArrayList<Historial> obtenerUltimas(int cantidad) {
        ArrayList<Historial> todas = pilaHistorial.obtenerTodos();
        ArrayList<Historial> ultimas = new ArrayList<>();
        
        int limite = Math.min(cantidad, todas.size());
        for (int i = 0; i < limite; i++) {
            ultimas.add(todas.get(i));
        }
        
        return ultimas;
    }
    
    /**
     * Filtra historial por tipo de accion
     */
    public ArrayList<Historial> filtrarPorTipo(Historial.TipoAccion tipo) {
        ArrayList<Historial> todas = pilaHistorial.obtenerTodos();
        ArrayList<Historial> filtradas = new ArrayList<>();
        
        for (Historial h : todas) {
            if (h.getTipo() == tipo) {
                filtradas.add(h);
            }
        }
        
        return filtradas;
    }
    
    /**
     * Filtra historial completo por tipo (para admins)
     */
    public ArrayList<Historial> filtrarHistorialCompletoPorTipo(Historial.TipoAccion tipo) {
        ArrayList<Historial> todas = obtenerHistorialCompleto();
        ArrayList<Historial> filtradas = new ArrayList<>();
        
        for (Historial h : todas) {
            if (h.getTipo() == tipo) {
                filtradas.add(h);
            }
        }
        
        return filtradas;
    }
    
    public ArrayList<Historial> obtenerHistorialCompras() {
        return filtrarPorTipo(Historial.TipoAccion.COMPRA);
    }
    
    public ArrayList<Historial> obtenerHistorialBusquedas() {
        return filtrarPorTipo(Historial.TipoAccion.BUSQUEDA);
    }
    
    /**
     * Obtiene historial de acciones administrativas (para admins)
     */
    public ArrayList<Historial> obtenerHistorialAdmin() {
        ArrayList<Historial> todas = obtenerHistorialCompleto();
        ArrayList<Historial> adminAcciones = new ArrayList<>();
        
        for (Historial h : todas) {
            if (h.getTipo() == Historial.TipoAccion.USUARIO_CREADO ||
                h.getTipo() == Historial.TipoAccion.USUARIO_EDITADO ||
                h.getTipo() == Historial.TipoAccion.USUARIO_ELIMINADO) {
                adminAcciones.add(h);
            }
        }
        
        return adminAcciones;
    }
    
    public int getCantidadAcciones() {
        return pilaHistorial.getTamaño();
    }
    
    public boolean tieneHistorial() {
        return !pilaHistorial.estaVacia();
    }
    
    // ----- Operaciones de limpieza -----
    
    public void limpiarHistorial() {
        pilaHistorial.vaciar();
        guardarEnArchivo();
    }
    
    // ----- Persistencia -----
    
    private void cargarDesdeArchivo() {
        if (idUsuarioActual == null) return;
        
        File archivo = new File(ARCHIVO_HISTORIAL);
        if (!archivo.exists()) return;
        
        PilaHistorial pilaTemp = new PilaHistorial(MAX_HISTORIAL);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HISTORIAL))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Historial h = Historial.fromArchivoLinea(linea);
                if (h != null && h.getIdUsuario().equals(idUsuarioActual)) {
                    pilaTemp.push(h);
                }
            }
        } catch (IOException e) {
            System.err.println("[HistorialServicio] Error al cargar historial: " + e.getMessage());
        }
        
        // Invertir para que el mas reciente quede en el tope
        while (!pilaTemp.estaVacia()) {
            pilaHistorial.push(pilaTemp.pop());
        }
    }
    
    private void cargarTodoDesdeArchivo() {
        File archivo = new File(ARCHIVO_HISTORIAL);
        if (!archivo.exists()) return;
        
        PilaHistorial pilaTemp = new PilaHistorial(MAX_HISTORIAL * 10);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HISTORIAL))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Historial h = Historial.fromArchivoLinea(linea);
                if (h != null) {
                    pilaTemp.push(h);
                }
            }
        } catch (IOException e) {
            System.err.println("[HistorialServicio] Error al cargar historial: " + e.getMessage());
        }
        
        while (!pilaTemp.estaVacia()) {
            pilaHistorial.push(pilaTemp.pop());
        }
    }
    
    private void guardarEnArchivo() {
        if (idUsuarioActual == null) return;
        
        // Leer historial de otros usuarios
        ArrayList<String> lineasOtrosUsuarios = new ArrayList<>();
        
        File archivo = new File(ARCHIVO_HISTORIAL);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_HISTORIAL))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (!linea.trim().isEmpty()) {
                        Historial h = Historial.fromArchivoLinea(linea);
                        if (h != null && !h.getIdUsuario().equals(idUsuarioActual)) {
                            lineasOtrosUsuarios.add(linea);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("[HistorialServicio] Error al leer archivo: " + e.getMessage());
            }
        }
        
        // Escribir todo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_HISTORIAL))) {
            for (String linea : lineasOtrosUsuarios) {
                writer.write(linea);
                writer.newLine();
            }
            
            ArrayList<Historial> historialActual = pilaHistorial.obtenerTodos();
            for (int i = historialActual.size() - 1; i >= 0; i--) {
                writer.write(historialActual.get(i).toArchivoLinea());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[HistorialServicio] Error al guardar historial: " + e.getMessage());
        }
    }
    
    public String getIdUsuarioActual() {
        return idUsuarioActual;
    }
}
