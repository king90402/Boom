/*
 * Modelo ItemCarrito - Proyecto Boom Sincronizado
 */

package Raiz.Modelos;

/**
 * @author BoomTeam
 * Representa un producto en el carrito con su cantidad
 */

public class ItemCarrito {
    
    // Atributos
    private Producto producto;
    private int cantidad;
    
    // Constructor
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public ItemCarrito(Producto producto) {
        this(producto, 1);
    }
    
    // Getters y Setters
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
    // Metodos auxiliares
    public void incrementarCantidad() { this.cantidad++; }
    
    public void incrementarCantidad(int cantidad) { this.cantidad += cantidad; }
    
    public boolean decrementarCantidad() {
        if (this.cantidad > 1) {
            this.cantidad--;
            return true;
        }
        return false;
    }
    
    public double getSubtotal() {
        return producto.getPrecioProducto() * cantidad;
    }
    
    public String getSubtotalFormateado() {
        return String.format("$%,.2f", getSubtotal());
    }
    
    public boolean tieneStockSuficiente() {
        return producto.getCantidadProducto() >= cantidad;
    }
    
    public int getStockDisponible() {
        return producto.getCantidadProducto();
    }
    
    // Serializacion
    public String toArchivoLinea() {
        return String.join(";", producto.getIdProducto(), String.valueOf(cantidad));
    }
    
    @Override
    public String toString() {
        return String.format("%s x%d = %s", producto.getNombreProducto(), cantidad, getSubtotalFormateado());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemCarrito item = (ItemCarrito) obj;
        return producto != null && producto.equals(item.producto);
    }
    
    @Override
    public int hashCode() {
        return producto != null ? producto.hashCode() : 0;
    }
}
