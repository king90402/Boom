/*
 * Modelo Pedido - Proyecto Boom Sincronizado
 */

package Raiz.Modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Representa un pedido realizado por un usuario
 */

public class Pedido {
    
    public enum EstadoPedido {
        PENDIENTE,
        PROCESANDO,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }
    
    // Atributos
    private String idPedido;
    private String idUsuario;
    private String nombreUsuario;
    private String direccionEnvio;
    private ArrayList<ItemCarrito> items;
    private double total;
    private EstadoPedido estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    private static int contadorId = 0;
    
    // Constructor
    public Pedido(String idUsuario, String nombreUsuario, String direccionEnvio) {
        this.idPedido = generarId();
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.direccionEnvio = direccionEnvio;
        this.items = new ArrayList<>();
        this.total = 0;
        this.estado = EstadoPedido.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    private String generarId() {
        return "P" + String.format("%08d", ++contadorId);
    }
    
    // Getters
    public String getIdPedido() { return idPedido; }
    public String getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getDireccionEnvio() { return direccionEnvio; }
    public ArrayList<ItemCarrito> getItems() { return items; }
    public double getTotal() { return total; }
    public EstadoPedido getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    
    // Setters
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
    
    // Metodos para items
    public void agregarItem(ItemCarrito item) {
        items.add(item);
        calcularTotal();
    }
    
    public void setItems(ArrayList<ItemCarrito> items) {
        this.items = items;
        calcularTotal();
    }
    
    private void calcularTotal() {
        total = 0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
    }
    
    public int getCantidadItems() {
        int cantidad = 0;
        for (ItemCarrito item : items) {
            cantidad += item.getCantidad();
        }
        return cantidad;
    }
    
    // Metodos auxiliares
    public String getTotalFormateado() {
        return String.format("$%,.2f", total);
    }
    
    public String getFechaCreacionFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaCreacion.format(formatter);
    }
    
    public String getFechaCorta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaCreacion.format(formatter);
    }
    
    public String getEstadoTexto() {
        switch (estado) {
            case PENDIENTE: return "Pendiente";
            case PROCESANDO: return "Procesando";
            case ENVIADO: return "Enviado";
            case ENTREGADO: return "Entregado";
            case CANCELADO: return "Cancelado";
            default: return "Desconocido";
        }
    }
    
    public String getEstadoColor() {
        switch (estado) {
            case PENDIENTE: return "#FFA500";
            case PROCESANDO: return "#3498DB";
            case ENVIADO: return "#9B59B6";
            case ENTREGADO: return "#27AE60";
            case CANCELADO: return "#E74C3C";
            default: return "#95A5A6";
        }
    }
    
    public boolean puedeSerCancelado() {
        return estado == EstadoPedido.PENDIENTE || estado == EstadoPedido.PROCESANDO;
    }
    
    // Serializacion
    public String toArchivoLinea() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        
        StringBuilder itemsStr = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            itemsStr.append(item.getProducto().getIdProducto())
                    .append(":")
                    .append(item.getCantidad());
            if (i < items.size() - 1) itemsStr.append(",");
        }
        
        return String.join(";",
            idPedido,
            idUsuario,
            nombreUsuario != null ? nombreUsuario : "",
            direccionEnvio != null ? direccionEnvio.replace(";", ",") : "",
            itemsStr.toString(),
            String.valueOf(total),
            estado.name(),
            fechaCreacion.format(formatter),
            fechaActualizacion.format(formatter)
        );
    }
    
    @Override
    public String toString() {
        return String.format("Pedido[%s] %s - %s items - %s - %s", 
            idPedido, nombreUsuario, getCantidadItems(), getTotalFormateado(), getEstadoTexto());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pedido pedido = (Pedido) obj;
        return idPedido != null && idPedido.equals(pedido.idPedido);
    }
    
    @Override
    public int hashCode() {
        return idPedido != null ? idPedido.hashCode() : 0;
    }
}
