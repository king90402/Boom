/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ProductoServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Estructuras.ListaProductos;
import Raiz.Modelos.Historial;
import Raiz.Modelos.Producto;
import Raiz.Utilidades.Ordenamiento;
import java.io.*;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada de gestionar todas las operaciones relacionadas con productos

public class ProductoServicio {

    // Singleton

    private static ProductoServicio instancia;
    public static ProductoServicio getInstancia() {
        if (instancia == null) {
            synchronized (ProductoServicio.class) {
                if (instancia == null) instancia = new ProductoServicio();
            }
        }
        return instancia;
    }


    private static final String ARCHIVO_PRODUCTOS = "productos.txt";
    public static final String[] CATEGORIAS = {
        "Tecnologia", "Hogar", "Deportes", "Moda y Belleza", "Ofertas"
    };

    public static final String[] ESTADOS = {"Nuevo", "Usado"};


    private ListaProductos listaProductos;


    private ProductoServicio() {
        this.listaProductos = new ListaProductos();
        cargarDesdeArchivo();
    }

    // ----------------------------------------------------------------
    //  CRUD (con historial)
    // ----------------------------------------------------------------

    /**
     * Crea un producto nuevo. Solo el admin llega aquí.
     */

    public boolean agregarProducto(String nombre, int cantidad, double precio,
                                   String estado, String marca, String categoria,
                                   String imagen, String idAdmin) {
        String id = listaProductos.obtenerSiguienteId();
        Producto p = new Producto(id, nombre, cantidad, precio, estado, marca, categoria, imagen);
        listaProductos.insertarAlFinal(p);
        appendAlArchivo(p);

        HistorialServicio.getInstancia().registrar(
            Historial.TipoAccion.PRODUCTO_CREADO,
            "Producto creado: " + nombre + " (stock: " + cantidad + ", $" + precio + ")",
            idAdmin, id, precio
        );
        return true;
    }

    /** Sobrecarga sin idAdmin (compatibilidad interna). */

    public boolean agregarProducto(String nombre, int cantidad, double precio,
                                   String estado, String marca, String categoria,
                                   String imagen) {
        return agregarProducto(nombre, cantidad, precio, estado, marca, categoria, imagen, "sistema");
    }


    public boolean agregarProducto(Producto producto) {
        if (listaProductos.existeProducto(producto.getIdProducto())) return false;
        listaProductos.insertarAlFinal(producto);
        appendAlArchivo(producto);
        return true;
    }

    /**
     * Elimina un producto. Registra en historial.
     */

    public boolean eliminarProducto(String id, String idAdmin) {
        Producto p = listaProductos.buscarPorId(id);
        if (p == null) return false;

        boolean ok = listaProductos.eliminarPorId(id);
        if (ok) {
            reescribirArchivo();
            HistorialServicio.getInstancia().registrar(
                Historial.TipoAccion.PRODUCTO_ELIMINADO,
                "Producto eliminado: " + p.getNombreProducto(),
                idAdmin, id, p.getPrecioProducto()
            );
        }
        return ok;
    }


    public boolean eliminarProducto(String id) {
        return eliminarProducto(id, "admin");
    }

    /**
     * Actualiza los datos de un producto. Registra en historial.
     */

    public boolean actualizarProducto(String id, Producto nuevosDatos, String idAdmin) {
        boolean ok = listaProductos.modificarProducto(id, nuevosDatos);
        if (ok) {
            reescribirArchivo();
            HistorialServicio.getInstancia().registrar(
                Historial.TipoAccion.PRODUCTO_EDITADO,
                "Producto editado: " + nuevosDatos.getNombreProducto(),
                idAdmin, id, nuevosDatos.getPrecioProducto()
            );
        }
        return ok;
    }


    public boolean actualizarProducto(String id, Producto nuevosDatos) {
        return actualizarProducto(id, nuevosDatos, "admin");
    }

    // ----------------------------------------------------------------
    //  GESTIÓN DE STOCK (sincronizada)
    // ----------------------------------------------------------------

    /**
     * Reduce el stock de un producto tras una compra.
     * Llamado por PedidoServicio.realizarCompra().
     */

    public synchronized boolean reducirStock(String id, int cantidad) {
        boolean ok = listaProductos.reducirStock(id, cantidad);
        if (ok) reescribirArchivo();
        return ok;
    }

    /**
     * Aumenta el stock (ingreso de mercancía por parte del admin).
     */

    public synchronized boolean aumentarStock(String id, int cantidad) {
        boolean ok = listaProductos.aumentarStock(id, cantidad);
        if (ok) reescribirArchivo();
        return ok;
    }


    public boolean actualizarPrecio(String id, double nuevoPrecio) {
        boolean ok = listaProductos.actualizarPrecio(id, nuevoPrecio);
        if (ok) reescribirArchivo();
        return ok;
    }

    /**
     * Gestiona el ingreso: si el producto ya existe, suma stock; si no, lo crea.
     */

    public boolean gestionarIngreso(String nombre, int cantidad, double precio,
                                    String estado, String marca, String categoria,
                                    String imagen, String idAdmin) {
        for (Producto p : listaProductos.obtenerTodos()) {
            if (p.getNombreProducto().equalsIgnoreCase(nombre)) {
                p.setCantidadProducto(p.getCantidadProducto() + cantidad);
                p.setPrecioProducto(precio);
                reescribirArchivo();
                HistorialServicio.getInstancia().registrar(
                    Historial.TipoAccion.PRODUCTO_EDITADO,
                    "Stock aumentado: " + nombre + " +" + cantidad,
                    idAdmin, p.getIdProducto(), precio
                );
                return true;
            }
        }
        return agregarProducto(nombre, cantidad, precio, estado, marca, categoria, imagen, idAdmin);
    }

    // ----------------------------------------------------------------
    //  BÚSQUEDA Y FILTROS
    // ----------------------------------------------------------------


    public Producto buscarPorId(String id) {
        return listaProductos.buscarPorId(id);
    }

    public ArrayList<Producto> buscarPorNombre(String n) {
        return listaProductos.buscarPorNombre(n);
    }

    public ArrayList<Producto> buscarPorCategoria(String c) {
        return listaProductos.buscarPorCategoria(c);
    }

    public ArrayList<Producto> buscarPorMarca(String m) {
        return listaProductos.buscarPorMarca(m);
    }

    public ArrayList<Producto> obtenerConStock() {
        return listaProductos.buscarConStock();
    }

    public ArrayList<Producto> buscarPorRangoPrecio(double min, double max) {
        return listaProductos.buscarPorRangoPrecio(min, max);
    }

    public ArrayList<Producto> obtenerTodos() {
        return listaProductos.obtenerTodos();
    }

    // ----------------------------------------------------------------
    //  ORDENAMIENTO
    // ----------------------------------------------------------------


    public ArrayList<Producto> obtenerOrdenadosPorNombre() {
        ArrayList<Producto> lista = listaProductos.obtenerTodos();
        Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_NOMBRE);
        return lista;
    }


    public ArrayList<Producto> obtenerOrdenadosPorPrecioAsc() {
        ArrayList<Producto> lista = listaProductos.obtenerTodos();
        Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_PRECIO_ASC);
        return lista;
    }


    public ArrayList<Producto> obtenerOrdenadosPorPrecioDesc() {
        ArrayList<Producto> lista = listaProductos.obtenerTodos();
        Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_PRECIO_DESC);
        return lista;
    }


    public ArrayList<Producto> buscarPorNombreConOrdenamiento(String nombre) {
        return Ordenamiento.busquedaSecuencialPorNombre(listaProductos.obtenerTodos(), nombre);
    }


    public ArrayList<Producto> buscarPorCategoriaOrdenadoPorPrecio(String categoria) {
        ArrayList<Producto> filtrados = listaProductos.buscarPorCategoria(categoria);
        Ordenamiento.quickSort(filtrados, Ordenamiento.COMPARAR_POR_PRECIO_ASC);
        return filtrados;
    }

    // ----------------------------------------------------------------
    //  ESTADÍSTICAS
    // ----------------------------------------------------------------


    public int getCantidadProductos() {
        return listaProductos.getTamaño();
    }

    public int getCantidadSinStock() {
        return listaProductos.contarSinStock();
    }

    public double getValorInventario() {
        return listaProductos.calcularValorInventario();
    }

    public int contarPorCategoria(String categoria) {
        return buscarPorCategoria(categoria).size();
    }

    // ----------------------------------------------------------------
    //  PERSISTENCIA
    // ----------------------------------------------------------------


    private void appendAlArchivo(Producto p) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS, true))) {
            w.write(p.toArchivoLinea());
            w.newLine();
        } catch (IOException e) {
            System.err.println("[ProductoServicio] Error al guardar: " + e.getMessage());
        }
    }


    private void reescribirArchivo() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            for (Producto p : listaProductos.obtenerTodos()) {
                w.write(p.toArchivoLinea());
                w.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ProductoServicio] Error al reescribir: " + e.getMessage());
        }
    }


    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        if (!archivo.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Producto p = Producto.fromArchivoLinea(linea);
                if (p != null && !listaProductos.existeProducto(p.getIdProducto())) {
                    listaProductos.insertarAlFinal(p);
                }
            }
        } catch (IOException e) {
            System.err.println("[ProductoServicio] Error al cargar: " + e.getMessage());
        }
    }


    public boolean existeProducto(String id) {
        return listaProductos.existeProducto(id);
    }

    public String obtenerSiguienteId() {
        return listaProductos.obtenerSiguienteId();
    }

    public ListaProductos getListaProductos() {
        return listaProductos;
    }


    public void limpiarTodo() {
        listaProductos.vaciarLista();
        try { new FileWriter(ARCHIVO_PRODUCTOS).close(); } catch (IOException e) {}
    }
}
