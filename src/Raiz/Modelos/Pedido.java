/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Pedido.java to edit this template
 */

package Raiz.Modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase tipo POJO de pedido con todos sus atributos

public class Pedido {

    // Atributos

    private String idPedido;
    private String idUsuario;
    private String nombreUsuario;
    private String direccionEnvio;
    private ArrayList<ItemCarrito> items;
    private double total;
    private LocalDateTime fechaCompra;
    private static int contadorId = 0;

    // Constructor

    public Pedido(String idUsuario, String nombreUsuario, String direccionEnvio) {
        this.idPedido       = generarId();
        this.idUsuario      = idUsuario;
        this.nombreUsuario  = nombreUsuario;
        this.direccionEnvio = direccionEnvio;
        this.items          = new ArrayList<>();
        this.total          = 0;
        this.fechaCompra    = LocalDateTime.now();
    }


    private String generarId() {
        return "P" + String.format("%08d", ++contadorId);
    }

    // Getters

    public String getIdPedido() {
        return idPedido;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public ArrayList<ItemCarrito> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    // Items

    public void agregarItem(ItemCarrito item) {
        items.add(item);
        recalcularTotal();
    }


    public void setItems(ArrayList<ItemCarrito> items) {
        this.items = items;
        recalcularTotal();
    }


    private void recalcularTotal() {
        total = 0;
        for (ItemCarrito item : items) total += item.getSubtotal();
    }


    public int getCantidadItems() {
        int cantidad = 0;
        for (ItemCarrito item : items) cantidad += item.getCantidad();
        return cantidad;
    }

    // Formato

    public String getTotalFormateado() {
        return String.format("$%,.2f", total);
    }


    public String getFechaFormateada() {
        return fechaCompra.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


    public String getFechaCorta() {
        return fechaCompra.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Serialización

    public String toArchivoLinea() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        StringBuilder itemsStr = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            itemsStr.append(item.getProducto().getIdProducto())
                    .append(":").append(item.getCantidad())
                    .append(":").append(item.getProducto().getPrecioProducto());
            if (i < items.size() - 1) itemsStr.append(",");
        }
        return String.join(";",
            idPedido,
            idUsuario,
            nombreUsuario   != null ? nombreUsuario.replace(";", ",")  : "",
            direccionEnvio  != null ? direccionEnvio.replace(";", ",") : "",
            itemsStr.toString(),
            String.valueOf(total),
            fechaCompra.format(f)
        );
    }

    @Override

    public String toString() {
        return String.format("Pedido[%s] %s - %d productos - %s - %s",
                idPedido, nombreUsuario, getCantidadItems(),
                getTotalFormateado(), getFechaFormateada());
    }

    @Override

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pedido)) return false;
        return idPedido != null && idPedido.equals(((Pedido) obj).idPedido);
    }

    @Override

    public int hashCode() {
        return idPedido != null ? idPedido.hashCode() : 0;
    }
}
