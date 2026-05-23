/*
 * Servicio de Carrito - Proyecto Boom Sincronizado
 */

package Raiz.Servicios;

import Raiz.Estructuras.ListaCarrito;
import Raiz.Modelos.ItemCarrito;
import Raiz.Modelos.Producto;
import Raiz.Modelos.Historial;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Gestiona el carrito de compras del usuario actual
 */

public class CarritoServicio {
    
    // Singleton
    private static CarritoServicio instancia;
    
    public static CarritoServicio getInstancia() {
        if (instancia == null) {
            synchronized (CarritoServicio.class) {
                if (instancia == null) {
                    instancia = new CarritoServicio();
                }
            }
        }
        return instancia;
    }
    
    // Atributos
    private ListaCarrito items;
    private HistorialServicio historialServicio;
    
    // Constructor privado
    private CarritoServicio() {
        this.items = new ListaCarrito();
        this.historialServicio = HistorialServicio.getInstancia();
    }
    
    // ----- Operaciones del carrito -----
    
    public boolean agregarProducto(Producto producto) {
        return agregarProducto(producto, 1);
    }
    
    public boolean agregarProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) return false;
        if (producto.getCantidadProducto() < cantidad) return false;
        
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(producto.getIdProducto())) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                if (nuevaCantidad > producto.getCantidadProducto()) return false;
                item.incrementarCantidad(cantidad);
                registrarAccion(Historial.TipoAccion.AGREGAR_CARRITO, producto);
                return true;
            }
        }
        
        items.insertarAlFinal(new ItemCarrito(producto, cantidad));
        registrarAccion(Historial.TipoAccion.AGREGAR_CARRITO, producto);
        return true;
    }
    
    public boolean eliminarProducto(String idProducto) {
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                items.eliminar(item);
                registrarAccion(Historial.TipoAccion.QUITAR_CARRITO, item.getProducto());
                return true;
            }
        }
        return false;
    }
    
    public boolean actualizarCantidad(String idProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) return eliminarProducto(idProducto);
        
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                if (nuevaCantidad > item.getProducto().getCantidadProducto()) return false;
                item.setCantidad(nuevaCantidad);
                return true;
            }
        }
        return false;
    }
    
    public boolean incrementarCantidad(String idProducto) {
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                if (item.getCantidad() < item.getProducto().getCantidadProducto()) {
                    item.incrementarCantidad();
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    public boolean decrementarCantidad(String idProducto) {
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                if (!item.decrementarCantidad()) {
                    return eliminarProducto(idProducto);
                }
                return true;
            }
        }
        return false;
    }
    
    // ----- Consultas -----
    
    public ArrayList<ItemCarrito> obtenerItems() { return items.obtenerTodos(); }
    
    public boolean contieneProducto(String idProducto) {
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(idProducto)) return true;
        }
        return false;
    }
    
    public ItemCarrito obtenerItem(String idProducto) {
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getIdProducto().equals(idProducto)) return item;
        }
        return null;
    }
    
    public double calcularTotal() {
        double total = 0;
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    public String getTotalFormateado() {
        return String.format("$%,.2f", calcularTotal());
    }
    
    public int getCantidadItems() { return items.getTamaño(); }
    
    public int getCantidadTotalProductos() {
        int total = 0;
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            total += item.getCantidad();
        }
        return total;
    }
    
    public boolean estaVacio() { return items.estaVacia(); }
    
    public boolean tieneStockSuficiente() {
        ArrayList<ItemCarrito> listaItems = items.obtenerTodos();
        for (ItemCarrito item : listaItems) {
            if (!item.tieneStockSuficiente()) return false;
        }
        return true;
    }
    
    // ----- Operaciones de limpieza -----
    
    public void vaciarCarrito() { items.vaciar(); }
    public void reiniciar() { items.vaciar(); }
    
    // ----- Metodos auxiliares -----
    
    private void registrarAccion(Historial.TipoAccion tipo, Producto producto) {
        String idUsuario = SesionServicio.getInstancia().getUsuarioActual() != null 
            ? SesionServicio.getInstancia().getUsuarioActual().getId() 
            : "anonimo";
            
        Historial h = new Historial(tipo, producto.getNombreProducto(), idUsuario,
                                    producto.getIdProducto(), producto.getPrecioProducto());
        historialServicio.registrarAccion(h);
    }
}
