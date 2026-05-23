/*
 * Servicio de Pedidos - Proyecto Boom Sincronizado
 */

package Raiz.Servicios;

import Raiz.Estructuras.ColaPedidos;
import Raiz.Modelos.Pedido;
import Raiz.Modelos.ItemCarrito;
import Raiz.Modelos.Historial;
import Raiz.Modelos.Usuario;
import java.io.*;
import java.util.ArrayList;

/**
 * @author BoomTeam
 * Gestiona los pedidos del sistema usando estructura COLA
 */

public class PedidoServicio {
    
    // Singleton
    private static PedidoServicio instancia;
    
    public static PedidoServicio getInstancia() {
        if (instancia == null) {
            synchronized (PedidoServicio.class) {
                if (instancia == null) {
                    instancia = new PedidoServicio();
                }
            }
        }
        return instancia;
    }
    
    // Constantes
    private static final String ARCHIVO_PEDIDOS = "pedidos.txt";
    
    // Atributos
    private ColaPedidos colaPedidosPendientes;
    private ArrayList<Pedido> todosLosPedidos;
    
    // Constructor privado
    private PedidoServicio() {
        this.colaPedidosPendientes = new ColaPedidos();
        this.todosLosPedidos = new ArrayList<>();
        cargarPedidosDesdeArchivo();
    }
    
    // ----- Operaciones de Cola -----
    
    public Pedido crearPedido(Usuario usuario, ArrayList<ItemCarrito> items) {
        if (usuario == null || items == null || items.isEmpty()) return null;
        
        Pedido nuevoPedido = new Pedido(usuario.getId(), usuario.getNombreCompleto(), 
                                        usuario.getDireccionCompleta());
        
        for (ItemCarrito item : items) {
            nuevoPedido.agregarItem(item);
        }
        
        colaPedidosPendientes.encolar(nuevoPedido);
        todosLosPedidos.add(nuevoPedido);
        guardarPedidosEnArchivo();
        registrarAccionCompra(usuario.getId(), nuevoPedido);
        
        return nuevoPedido;
    }
    
    public Pedido procesarSiguientePedido() {
        Pedido pedido = colaPedidosPendientes.desencolar();
        if (pedido != null) {
            pedido.setEstado(Pedido.EstadoPedido.PROCESANDO);
            guardarPedidosEnArchivo();
        }
        return pedido;
    }
    
    public Pedido verSiguientePedido() {
        return colaPedidosPendientes.verFrente();
    }
    
    // ----- Gestion de estados -----
    
    public boolean actualizarEstado(String idPedido, Pedido.EstadoPedido nuevoEstado) {
        for (Pedido p : todosLosPedidos) {
            if (p.getIdPedido().equals(idPedido)) {
                p.setEstado(nuevoEstado);
                if (nuevoEstado == Pedido.EstadoPedido.CANCELADO) {
                    removerDeCola(idPedido);
                }
                guardarPedidosEnArchivo();
                return true;
            }
        }
        return false;
    }
    
    public boolean marcarComoEnviado(String idPedido) {
        return actualizarEstado(idPedido, Pedido.EstadoPedido.ENVIADO);
    }
    
    public boolean marcarComoEntregado(String idPedido) {
        return actualizarEstado(idPedido, Pedido.EstadoPedido.ENTREGADO);
    }
    
    public boolean cancelarPedido(String idPedido) {
        Pedido pedido = buscarPorId(idPedido);
        if (pedido != null && pedido.puedeSerCancelado()) {
            return actualizarEstado(idPedido, Pedido.EstadoPedido.CANCELADO);
        }
        return false;
    }
    
    // ----- Consultas -----
    
    public Pedido buscarPorId(String idPedido) {
        for (Pedido p : todosLosPedidos) {
            if (p.getIdPedido().equals(idPedido)) return p;
        }
        return null;
    }
    
    public ArrayList<Pedido> obtenerPedidosUsuario(String idUsuario) {
        ArrayList<Pedido> pedidosUsuario = new ArrayList<>();
        for (Pedido p : todosLosPedidos) {
            if (p.getIdUsuario().equals(idUsuario)) {
                pedidosUsuario.add(p);
            }
        }
        return pedidosUsuario;
    }
    
    public ArrayList<Pedido> obtenerPorEstado(Pedido.EstadoPedido estado) {
        ArrayList<Pedido> filtrados = new ArrayList<>();
        for (Pedido p : todosLosPedidos) {
            if (p.getEstado() == estado) filtrados.add(p);
        }
        return filtrados;
    }
    
    public ArrayList<Pedido> obtenerPedidosPendientes() {
        return colaPedidosPendientes.obtenerTodos();
    }
    
    public ArrayList<Pedido> obtenerTodos() {
        return new ArrayList<>(todosLosPedidos);
    }
    
    public int getCantidadEnCola() { return colaPedidosPendientes.getTamaño(); }
    public int getCantidadTotal() { return todosLosPedidos.size(); }
    public boolean hayPedidosPendientes() { return !colaPedidosPendientes.estaVacia(); }
    
    // ----- Estadisticas -----
    
    public double getTotalVentas() {
        double total = 0;
        for (Pedido p : todosLosPedidos) {
            if (p.getEstado() != Pedido.EstadoPedido.CANCELADO) {
                total += p.getTotal();
            }
        }
        return total;
    }
    
    public int getCantidadCompletados() {
        int count = 0;
        for (Pedido p : todosLosPedidos) {
            if (p.getEstado() == Pedido.EstadoPedido.ENTREGADO) count++;
        }
        return count;
    }
    
    // ----- Metodos auxiliares -----
    
    private void removerDeCola(String idPedido) {
        colaPedidosPendientes.eliminarPorId(idPedido);
    }
    
    private void registrarAccionCompra(String idUsuario, Pedido pedido) {
        Historial h = new Historial(Historial.TipoAccion.COMPRA,
            "Pedido " + pedido.getIdPedido() + " - " + pedido.getCantidadItems() + " productos",
            idUsuario, null, pedido.getTotal());
        HistorialServicio.getInstancia().registrarAccion(h);
    }
    
    // ----- Persistencia -----
    
    private void guardarPedidosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PEDIDOS))) {
            for (Pedido p : todosLosPedidos) {
                writer.write(p.toArchivoLinea());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[PedidoServicio] Error al guardar pedidos: " + e.getMessage());
        }
    }
    
    private void cargarPedidosDesdeArchivo() {
        File archivo = new File(ARCHIVO_PEDIDOS);
        if (!archivo.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_PEDIDOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                // Parsing basico del pedido
            }
        } catch (IOException e) {
            System.err.println("[PedidoServicio] Error al cargar pedidos: " + e.getMessage());
        }
    }
}
