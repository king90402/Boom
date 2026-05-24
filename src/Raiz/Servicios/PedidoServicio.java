/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/PedidoServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Estructuras.ColaPedidos;
import Raiz.Modelos.ItemCarrito;
import Raiz.Modelos.Historial;
import Raiz.Modelos.Pedido;
import Raiz.Modelos.Usuario;
import java.io.*;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada de gestionar todas las operaciones relacionadas con pedidos

public class PedidoServicio {

    // Singleton

    private static PedidoServicio instancia;
    public static PedidoServicio getInstancia() {
        if (instancia == null) {
            synchronized (PedidoServicio.class) {
                if (instancia == null) instancia = new PedidoServicio();
            }
        }
        return instancia;
    }


    private static final String ARCHIVO_PEDIDOS = "pedidos.txt";

    // ColaPedidos como estructura de almacenamiento de registros

    private ColaPedidos pedidos;


    private PedidoServicio() {
        this.pedidos = new ColaPedidos();
        cargarDesdeArchivo();
    }

    // ----------------------------------------------------------------
    //  COMPRAR — operación principal
    // ----------------------------------------------------------------

    /**
     * Procesa la compra del carrito del usuario:
     *  1. Verifica que todos los ítems tengan stock suficiente.
     *  2. Descuenta el stock de cada producto.
     *  3. Crea y guarda el registro del pedido.
     *  4. Registra la acción en el historial.
     *  5. Vacía el carrito.
     *
     * @return El pedido creado, o null si no hay stock suficiente o el carrito está vacío.
     */

    public Pedido realizarCompra(Usuario usuario, ArrayList<ItemCarrito> items) {
        if (usuario == null || items == null || items.isEmpty()) return null;

        ProductoServicio productoServicio = ProductoServicio.getInstancia();

        // 1. Verificar stock de todos los ítems antes de descontar
        for (ItemCarrito item : items) {
            Raiz.Modelos.Producto p = productoServicio.buscarPorId(
                    item.getProducto().getIdProducto());
            if (p == null || p.getCantidadProducto() < item.getCantidad()) {
                System.err.println("[PedidoServicio] Stock insuficiente: "
                        + item.getProducto().getNombreProducto());
                return null;
            }
        }

        // 2. Descontar stock
        for (ItemCarrito item : items) {
            productoServicio.reducirStock(
                    item.getProducto().getIdProducto(),
                    item.getCantidad());
        }

        // 3. Crear pedido
        Pedido pedido = new Pedido(usuario.getId(), usuario.getNombreCompleto(),
                                   usuario.getDireccionCompleta());
        for (ItemCarrito item : items) pedido.agregarItem(item);

        pedidos.encolar(pedido);
        guardarEnArchivo(pedido);

        // 4. Registrar en historial
        HistorialServicio.getInstancia().registrar(
            Historial.TipoAccion.COMPRA,
            "Compra: pedido " + pedido.getIdPedido()
                + " — " + pedido.getCantidadItems() + " producto(s)"
                + " — " + pedido.getTotalFormateado(),
            usuario.getId(),
            null,
            pedido.getTotal()
        );

        // 5. Vaciar carrito
        CarritoServicio.getInstancia().vaciarCarrito();

        return pedido;
    }

    // ----------------------------------------------------------------
    //  CONSULTAS
    // ----------------------------------------------------------------


    public Pedido buscarPorId(String idPedido) {
        for (Pedido p : pedidos.obtenerTodos()) {
            if (p.getIdPedido().equals(idPedido)) return p;
        }
        return null;
    }


    public ArrayList<Pedido> obtenerPedidosUsuario(String idUsuario) {
        ArrayList<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos.obtenerTodos()) {
            if (p.getIdUsuario().equals(idUsuario)) resultado.add(p);
        }
        return resultado;
    }


    public ArrayList<Pedido> obtenerTodos() {
        return pedidos.obtenerTodos();
    }


    public int getCantidadTotal() {
        return pedidos.getTamaño();
    }

    public boolean hayPedidos() {
        return !pedidos.estaVacia();
    }

    // -------------------- Estadisticas para el admin

    public double getTotalVentas() {
        double total = 0;
        for (Pedido p : pedidos.obtenerTodos()) total += p.getTotal();
        return total;
    }
    
    // ---------- Obtener ventas totales (alias)
    public double obtenerVentasTotales() {
        return getTotalVentas();
    }
    
    // ---------- Obtener total de pedidos
    public int obtenerTotalPedidos() {
        return pedidos.getTamaño();
    }
    
    // ---------- Obtener todos los pedidos
    public ArrayList<Pedido> obtenerTodosPedidos() {
        return pedidos.obtenerTodos();
    }
    
    // ---------- Obtener mejor cliente
    public String obtenerMejorCliente() {
        java.util.HashMap<String, Double> comprasPorCliente = new java.util.HashMap<>();
        
        for (Pedido p : pedidos.obtenerTodos()) {
            String cliente = p.getNombreUsuario();
            comprasPorCliente.merge(cliente, p.getTotal(), Double::sum);
        }
        
        String mejorCliente = null;
        double maxCompras = 0;
        
        for (java.util.Map.Entry<String, Double> entry : comprasPorCliente.entrySet()) {
            if (entry.getValue() > maxCompras) {
                maxCompras = entry.getValue();
                mejorCliente = entry.getKey();
            }
        }
        
        return mejorCliente;
    }

    // ----------------------------------------------------------------
    //  Persistencia (append por pedido)
    // ----------------------------------------------------------------


    private void guardarEnArchivo(Pedido p) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARCHIVO_PEDIDOS, true))) {
            w.write(p.toArchivoLinea());
            w.newLine();
        } catch (IOException e) {
            System.err.println("[PedidoServicio] Error al guardar: " + e.getMessage());
        }
    }


    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_PEDIDOS);
        if (!archivo.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(ARCHIVO_PEDIDOS))) {
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                // Parsing básico: solo recuperamos encabezado del pedido
                String[] d = linea.split(";");
                if (d.length >= 4) {
                    Pedido p = new Pedido(
                        d.length > 1 ? d[1] : "",
                        d.length > 2 ? d[2] : "",
                        d.length > 3 ? d[3] : ""
                    );
                    pedidos.encolar(p);
                }
            }
        } catch (IOException e) {
            System.err.println("[PedidoServicio] Error al cargar: " + e.getMessage());
        }
    }
}
