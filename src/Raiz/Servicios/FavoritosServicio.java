/*
 * Servicio de Favoritos - Proyecto Boom Sincronizado
 */

package Raiz.Servicios;

import Raiz.Estructuras.ListaFavoritos;
import Raiz.Modelos.Producto;
import Raiz.Modelos.Historial;
import java.io.*;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Gestiona los productos favoritos del usuario
 */

public class FavoritosServicio {
    
    // Singleton
    private static FavoritosServicio instancia;
    
    public static FavoritosServicio getInstancia() {
        if (instancia == null) {
            synchronized (FavoritosServicio.class) {
                if (instancia == null) {
                    instancia = new FavoritosServicio();
                }
            }
        }
        return instancia;
    }
    
    // Constantes
    private static final String ARCHIVO_FAVORITOS = "favoritos.txt";
    
    // Atributos
    private ListaFavoritos idProductosFavoritos;
    private String idUsuarioActual;
    private HistorialServicio historialServicio;
    private ProductoServicio productoServicio;
    
    // Constructor privado
    private FavoritosServicio() {
        this.idProductosFavoritos = new ListaFavoritos();
        this.idUsuarioActual = null;
        this.historialServicio = HistorialServicio.getInstancia();
        this.productoServicio = ProductoServicio.getInstancia();
    }
    
    // ----- Inicializacion por usuario -----
    
    public void cargarFavoritosUsuario(String idUsuario) {
        this.idUsuarioActual = idUsuario;
        this.idProductosFavoritos.vaciar();
        cargarDesdeArchivo();
    }
    
    public void reiniciar() {
        this.idProductosFavoritos.vaciar();
        this.idUsuarioActual = null;
    }
    
    // ----- Operaciones de favoritos -----
    
    public boolean agregarFavorito(Producto producto) {
        if (producto == null || idUsuarioActual == null) return false;
        
        String idProducto = producto.getIdProducto();
        if (esFavorito(idProducto)) return false;
        
        idProductosFavoritos.insertarAlFinal(idProducto);
        guardarEnArchivo();
        registrarAccion(Historial.TipoAccion.AGREGAR_FAVORITO, producto);
        return true;
    }
    
    public boolean eliminarFavorito(String idProducto) {
        if (idProducto == null || idUsuarioActual == null) return false;
        if (!esFavorito(idProducto)) return false;
        
        Producto producto = productoServicio.buscarPorId(idProducto);
        boolean eliminado = idProductosFavoritos.eliminar(idProducto);
        
        if (eliminado) {
            guardarEnArchivo();
            if (producto != null) {
                registrarAccion(Historial.TipoAccion.QUITAR_FAVORITO, producto);
            }
        }
        return eliminado;
    }
    
    public boolean toggleFavorito(Producto producto) {
        if (producto == null) return false;
        
        if (esFavorito(producto.getIdProducto())) {
            return eliminarFavorito(producto.getIdProducto());
        } else {
            return agregarFavorito(producto);
        }
    }
    
    // ----- Consultas -----
    
    public boolean esFavorito(String idProducto) {
        return idProductosFavoritos.contiene(idProducto);
    }
    
    public ArrayList<Producto> obtenerFavoritos() {
        ArrayList<Producto> favoritos = new ArrayList<>();
        ArrayList<String> ids = idProductosFavoritos.obtenerTodos();
        
        for (String id : ids) {
            Producto p = productoServicio.buscarPorId(id);
            if (p != null) favoritos.add(p);
        }
        return favoritos;
    }
    
    public ArrayList<String> obtenerIdsFavoritos() {
        return idProductosFavoritos.obtenerTodos();
    }
    
    public int getCantidadFavoritos() { return idProductosFavoritos.getTamaño(); }
    public boolean tieneFavoritos() { return !idProductosFavoritos.estaVacia(); }
    
    // ----- Persistencia -----
    
    private void cargarDesdeArchivo() {
        if (idUsuarioActual == null) return;
        
        File archivo = new File(ARCHIVO_FAVORITOS);
        if (!archivo.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_FAVORITOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] partes = linea.split(";");
                
                if (partes.length >= 2 && partes[0].equals(idUsuarioActual)) {
                    String[] ids = partes[1].split(",");
                    for (String id : ids) {
                        if (!id.trim().isEmpty()) {
                            idProductosFavoritos.insertarAlFinal(id.trim());
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("[FavoritosServicio] Error al cargar favoritos: " + e.getMessage());
        }
    }
    
    private void guardarEnArchivo() {
        if (idUsuarioActual == null) return;
        
        ArrayList<String> lineasArchivo = new ArrayList<>();
        boolean usuarioEncontrado = false;
        
        File archivo = new File(ARCHIVO_FAVORITOS);
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_FAVORITOS))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (linea.startsWith(idUsuarioActual + ";")) {
                        lineasArchivo.add(crearLineaFavoritos());
                        usuarioEncontrado = true;
                    } else {
                        lineasArchivo.add(linea);
                    }
                }
            } catch (IOException e) {
                System.err.println("[FavoritosServicio] Error al leer archivo: " + e.getMessage());
            }
        }
        
        if (!usuarioEncontrado && !idProductosFavoritos.estaVacia()) {
            lineasArchivo.add(crearLineaFavoritos());
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_FAVORITOS))) {
            for (String linea : lineasArchivo) {
                if (!linea.trim().isEmpty()) {
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("[FavoritosServicio] Error al guardar favoritos: " + e.getMessage());
        }
    }
    
    private String crearLineaFavoritos() {
        ArrayList<String> ids = idProductosFavoritos.obtenerTodos();
        return idUsuarioActual + ";" + String.join(",", ids);
    }
    
    // ----- Metodos auxiliares -----
    
    private void registrarAccion(Historial.TipoAccion tipo, Producto producto) {
        Historial h = new Historial(tipo, producto.getNombreProducto(), idUsuarioActual,
                                    producto.getIdProducto(), producto.getPrecioProducto());
        historialServicio.registrarAccion(h);
    }
}
