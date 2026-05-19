/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ProductoServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Estructuras.ListaProductos;
import Raiz.Modelos.Producto;
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
                if (instancia == null) {
                    instancia = new ProductoServicio();
                }
            }
        }
        return instancia;
    }
    
    // Constantes
    
    private static final String ARCHIVO_PRODUCTOS = "productos.txt";
    
    public static final String[] CATEGORIAS = {
        "Tecnologia", "Hogar", "Deportes", "Moda y Belleza", "Ofertas"
    };
    
    public static final String[] ESTADOS = {"Nuevo", "Usado"};
    
    // Atributos
    
    private ListaProductos listaProductos;
    private boolean datosInicializados;
    
    // Constructor privado
    
    private ProductoServicio() {
        this.listaProductos = new ListaProductos();
        this.datosInicializados = false;
        cargarProductosDesdeArchivo();
    }
    
    // ----- Metodos CRUD
    
    // Agrega un nuevo producto al inventario - ID Automatico

    public boolean agregarProducto(String nombre, int cantidad, double precio, 
                                   String estado, String marca, String categoria, 
                                   String imagen) {
        String id = listaProductos.obtenerSiguienteId();
        Producto nuevoProducto = new Producto(nombre, id, cantidad, precio, 
                                               estado, marca, categoria, imagen);
        
        listaProductos.insertarAlFinal(nuevoProducto);
        guardarProductoEnArchivo(nuevoProducto);
        
        return true;
    }
    
    // Agrega un producto existente (con ID)

    public boolean agregarProducto(Producto producto) {
        if (listaProductos.existeProducto(producto.getIdProducto())) {
            return false;
        }
        
        listaProductos.insertarAlFinal(producto);
        guardarProductoEnArchivo(producto);
        return true;
    }
    
    // Elimina un producto por ID

    public boolean eliminarProducto(String id) {
        boolean eliminado = listaProductos.eliminarPorId(id);
        if (eliminado) {
            reescribirArchivoProductos();
        }
        return eliminado;
    }
    
    // Actualiza un producto existente

    public boolean actualizarProducto(String id, Producto nuevosDatos) {
        boolean actualizado = listaProductos.modificarProducto(id, nuevosDatos);
        if (actualizado) {
            reescribirArchivoProductos();
        }
        return actualizado;
    }
    
    // ----- Metodos busquedad y filtro
    
    // Busca un producto por ID

    public Producto buscarPorId(String id) {
        return listaProductos.buscarPorId(id);
    }
    
    // Busca productos por nombre 

    public ArrayList<Producto> buscarPorNombre(String nombre) {
        return listaProductos.buscarPorNombre(nombre);
    }
    
    // Busca productos por categoria

    public ArrayList<Producto> buscarPorCategoria(String categoria) {
        return listaProductos.buscarPorCategoria(categoria);
    }
    
    // Busca productos por marca
    
    public ArrayList<Producto> buscarPorMarca(String marca) {
        return listaProductos.buscarPorMarca(marca);
    }
    
    // Busca productos con stock disponible

    public ArrayList<Producto> obtenerConStock() {
        return listaProductos.buscarConStock();
    }
    
    // Busca productos por rango de precio

    public ArrayList<Producto> buscarPorRangoPrecio(double min, double max) {
        return listaProductos.buscarPorRangoPrecio(min, max);
    }
    
    // Obtiene todos los productos

    public ArrayList<Producto> obtenerTodos() {
        return listaProductos.obtenerTodos();
    }
    
    // ----- Gestion de inventario
    
    // Gestiona el ingreso de productos. Si existe aumenta stock, sino lo crea

    public boolean gestionarIngresoProducto(String nombre, int cantidad, double precio,
                                            String estado, String marca, String categoria,
                                            String imagen) {

        ArrayList<Producto> encontrados = buscarPorNombre(nombre);
        
        for (Producto p : encontrados) {
            if (p.getNombreProducto().equalsIgnoreCase(nombre)) {

                int nuevoStock = p.getCantidadProducto() + cantidad;
                p.setCantidadProducto(nuevoStock);
                p.setPrecioProducto(precio);
                reescribirArchivoProductos();
                return true;
            }
        }
        
        return agregarProducto(nombre, cantidad, precio, estado, marca, categoria, imagen);
    }
    
    // Aumenta stock del producto
    
    public boolean aumentarStock(String id, int cantidad) {
        boolean resultado = listaProductos.aumentarStock(id, cantidad);
        if (resultado) {
            reescribirArchivoProductos();
        }
        return resultado;
    }
    
    // Reduce stock del producto (Venta)
    
    public boolean reducirStock(String id, int cantidad) {
        boolean resultado = listaProductos.reducirStock(id, cantidad);
        if (resultado) {
            reescribirArchivoProductos();
        }
        return resultado;
    }
    
    // Actualiza el precio del producto
    
    public boolean actualizarPrecio(String id, double nuevoPrecio) {
        boolean resultado = listaProductos.actualizarPrecio(id, nuevoPrecio);
        if (resultado) {
            reescribirArchivoProductos();
        }
        return resultado;
    }
    
    // ----- Estadisticas
    
    // Retorna total productos
    
    public int getCantidadProductos() {
        return listaProductos.getTamaño();
    }
    
    // Retorna cantidad productos sin stock
    
    public int getCantidadSinStock() {
        return listaProductos.contarSinStock();
    }
    
    // Retorna el valor total del inventario
    
    public double getValorInventario() {
        return listaProductos.calcularValorInventario();
    }
    
    // Retorna cantidad productos por categoria
    
    public int contarPorCategoria(String categoria) {
        return buscarPorCategoria(categoria).size();
    }
    
    // ----- Persistencia
    
    // Guarda producto en su archivo
    
    private boolean guardarProductoEnArchivo(Producto p) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS, true))) {
            writer.write(p.toArchivoLinea());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("[ProductoService] Error al guardar producto: " + e.getMessage());
            return false;
        }
    }
    
    // Reescribe todo el archivo de productos
     
    private void reescribirArchivoProductos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            ArrayList<Producto> productos = listaProductos.obtenerTodos();
            for (Producto p : productos) {
                writer.write(p.toArchivoLinea());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[ProductoService] Error al reescribir archivo: " + e.getMessage());
        }
    }
    
    // Carga productos desde archivo al iniciar
    
    private void cargarProductosDesdeArchivo() {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        
        if (!archivo.exists()) {
            datosInicializados = true;
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
            String linea;
            int cargados = 0;
            
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                Producto p = Producto.fromArchivoLinea(linea);
                
                if (p != null && !listaProductos.existeProducto(p.getIdProducto())) {
                    listaProductos.insertarAlFinal(p);
                    cargados++;
                }
            }
            
            datosInicializados = true;
            
        } catch (IOException e) {
            System.err.println("[ProductoService] Error al cargar productos: " + e.getMessage());
        }
    }
    
    // ------ Metodos funcionales
    
    // Verifica existe un producto
    
    public boolean existeProducto(String id) {
        return listaProductos.existeProducto(id);
    }
    
    // Genera el siguiente ID disponible
    
    public String obtenerSiguienteId() {
        return listaProductos.obtenerSiguienteId();
    }
    
    // Limpia todo el inventario
    
    public void limpiarTodo() {
        listaProductos.vaciarLista();
        try {
            new FileWriter(ARCHIVO_PRODUCTOS).close();
        } catch (IOException e) {
            System.err.println("[ProductoService] Error al limpiar archivo");
        }
    }
 
    // Retorna la lista interna
    
    public ListaProductos getListaProductos() {
        return listaProductos;
    }
}
