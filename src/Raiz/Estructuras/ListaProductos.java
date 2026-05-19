/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ListaProductos.java to edit this template
 */

package Raiz.Estructuras;

import Raiz.Modelos.NodoProducto;
import Raiz.Modelos.Producto;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo de los productos mediante una lista doblemente enlazada

public class ListaProductos {
    
    // Atrbutos
    
    private NodoProducto cabeza;
    private NodoProducto cola;
    private int tamaño;
    
    // Constructor
    
    public ListaProductos() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }
    
    // Metodos consulta general
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    // ----- Metodos de insercion 
    
    // Complejidad: O(1)
    
    public void insertarAlInicio(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);
        
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            cabeza = nuevoNodo;
        }
        tamaño++;
    }
    
    // Complejidad: O(1)
    
    public void insertarAlFinal(Producto producto) {
        NodoProducto nuevoNodo = new NodoProducto(producto);
        
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
        tamaño++;
    }
    
    // Complejidad: O(n)
    
    public boolean insertarEnPosicion(Producto producto, int posicion) {
        if (posicion < 0 || posicion > tamaño) {
            return false;
        }
        
        if (posicion == 0) {
            insertarAlInicio(producto);
            return true;
        }
        
        if (posicion == tamaño) {
            insertarAlFinal(producto);
            return true;
        }
        
        NodoProducto nuevoNodo = new NodoProducto(producto);
        NodoProducto actual = cabeza;
        
        for (int i = 0; i < posicion - 1; i++) {
            actual = actual.getSiguiente();
        }
        
        nuevoNodo.setSiguiente(actual.getSiguiente());
        nuevoNodo.setAnterior(actual);
        actual.getSiguiente().setAnterior(nuevoNodo);
        actual.setSiguiente(nuevoNodo);
        tamaño++;
        
        return true;
    }
    
    // ----- Metodos eliminacion
    
    // Complejidad: O(1)
    
    public Producto eliminarDelInicio() {
        if (estaVacia()) {
            return null;
        }
        
        Producto productoEliminado = cabeza.getProducto();
        
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cabeza = cabeza.getSiguiente();
            cabeza.setAnterior(null);
        }
        tamaño--;
        
        return productoEliminado;
    }
    
    // Complejidad: O(1)
    
    public Producto eliminarDelFinal() {
        if (estaVacia()) {
            return null;
        }
        
        Producto productoEliminado = cola.getProducto();
        
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cola = cola.getAnterior();
            cola.setSiguiente(null);
        }
        tamaño--;
        
        return productoEliminado;
    }
    
    // Complejidad: O(n)
    
    public boolean eliminarPorId(String id) {
        if (estaVacia()) {
            return false;
        }
        
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getIdProducto().equals(id)) {
                if (actual == cabeza) {
                    eliminarDelInicio();
                } else if (actual == cola) {
                    eliminarDelFinal();
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                    tamaño--;
                }
                return true;
            }
            actual = actual.getSiguiente();
        }
        
        return false;
    }
    
    // ----- Metodos busquedad
    
    // Complejidad: O(n)
    
    public Producto buscarPorId(String id) {
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getIdProducto().equals(id)) {
                return actual.getProducto();
            }
            actual = actual.getSiguiente();
        }
        
        return null;
    }
    
    // Complejidad: O(n)
    
    public ArrayList<Producto> buscarPorNombre(String nombre) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getNombreProducto().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }
    
    // Complejidad: O(n)
    
    public ArrayList<Producto> buscarPorCategoria(String categoria) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getCategoriaProducto().equalsIgnoreCase(categoria)) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }
    
    public ArrayList<Producto> buscarPorMarca(String marca) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getMarcaProducto().equalsIgnoreCase(marca)) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }
    
    public ArrayList<Producto> buscarConStock() {
        ArrayList<Producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().tieneStock()) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }
    
    public ArrayList<Producto> buscarPorRangoPrecio(double min, double max) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            double precio = actual.getProducto().getPrecioProducto();
            if (precio >= min && precio <= max) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }
    
    // ----- Metodos modificacion
    
    // Complejidad: O(n)
    
    public boolean modificarProducto(String id, Producto nuevoProducto) {
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getIdProducto().equals(id)) {
                actual.setProducto(nuevoProducto);
                return true;
            }
            actual = actual.getSiguiente();
        }
        
        return false;
    }
    
    // Complejidad: O(n)
    
    public boolean actualizarCantidad(String id, int nuevaCantidad) {
        Producto producto = buscarPorId(id);
        
        if (producto != null) {
            producto.setCantidadProducto(nuevaCantidad);
            return true;
        }
        
        return false;
    }
    
    public boolean actualizarPrecio(String id, double nuevoPrecio) {
        Producto producto = buscarPorId(id);
        
        if (producto != null) {
            producto.setPrecioProducto(nuevoPrecio);
            return true;
        }
        
        return false;
    }

    public boolean aumentarStock(String id, int cantidad) {
        Producto producto = buscarPorId(id);
        
        if (producto != null && cantidad > 0) {
            producto.aumentarStock(cantidad);
            return true;
        }
        
        return false;
    }

    public boolean reducirStock(String id, int cantidad) {
        Producto producto = buscarPorId(id);
        
        if (producto != null) {
            return producto.reducirStock(cantidad);
        }
        
        return false;
    }
    
    // ----- Metodos de retorno u obtencion
    
    // Retorna todos los productos
    
    public ArrayList<Producto> obtenerTodos() {
        ArrayList<Producto> productos = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            productos.add(actual.getProducto());
            actual = actual.getSiguiente();
        }
        
        return productos;
    }
    
    // Retorna todos los productos invertidos
    
    public ArrayList<Producto> obtenerTodosReversa() {
        ArrayList<Producto> productos = new ArrayList<>();
        NodoProducto actual = cola;
        
        while (actual != null) {
            productos.add(actual.getProducto());
            actual = actual.getAnterior();
        }
        
        return productos;
    }
    
    // Retorna un producto en una posicion
    
    public Producto obtenerEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            return null;
        }
        
        NodoProducto actual = cabeza;
        
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        
        return actual.getProducto();
    }
    
    // ----- Metodos de verificacion
    
    // Verifica si existe un producto con ese ID
    
    public boolean existeProducto(String id) {
        return buscarPorId(id) != null;
    }
    
    // ----- Metodos funcionales
    
    public void vaciarLista() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    
    // Retorna el siguiente ID disponible
    
    public String obtenerSiguienteId() {
        int maxId = 0;
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            try {
                int idActual = Integer.parseInt(actual.getProducto().getIdProducto());
                if (idActual > maxId) {
                    maxId = idActual;
                }
            } catch (NumberFormatException e) {
            }
            actual = actual.getSiguiente();
        }
        
        return String.valueOf(maxId + 1);
    }

    // Calcula el valor total del inventario
    
    public double calcularValorInventario() {
        double total = 0;
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            Producto p = actual.getProducto();
            total += p.getPrecioProducto() * p.getCantidadProducto();
            actual = actual.getSiguiente();
        }
        
        return total;
    }
    
    // Cuenta todos los productos sin stock
    
    public int contarSinStock() {
        int contador = 0;
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (!actual.getProducto().tieneStock()) {
                contador++;
            }
            actual = actual.getSiguiente();
        }
        
        return contador;
    }
    
    // ----- Acceso a nodos principales
    
    public NodoProducto getCabeza() {
        return cabeza;
    }
    
    public NodoProducto getCola() {
        return cola;
    }
}
