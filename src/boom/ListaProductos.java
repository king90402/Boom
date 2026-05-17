/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

import java.util.ArrayList;

/**
 *
 * @author EQUIPO
 */

public class ListaProductos {
    private NodoProducto cabeza;
    private NodoProducto cola;
    private int tamaño;

    public ListaProductos() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamanio() {
        return tamaño;
    }

    
    public void insertarAlInicio(producto producto) {
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

    public void insertarAlFinal(producto producto) {
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

    public boolean insertarEnPosicion(producto producto, int posicion) {
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

    public producto eliminarDelInicio() {
        if (estaVacia()) {
            return null;
        }
        
        producto productoEliminado = cabeza.getProducto();
        
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

    public producto eliminarDelFinal() {
        if (estaVacia()) {
            return null;
        }
        
       producto productoEliminado = cola.getProducto();
        
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

    public producto buscarPorId(String id) {
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getIdProducto().equals(id)) {
                return actual.getProducto();
            }
            actual = actual.getSiguiente();
        }
        
        return null;
    }

    public ArrayList<producto> buscarPorNombre(String nombre) {
        ArrayList<producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getNombreProducto().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }

    public ArrayList<producto> buscarPorCategoria(String categoria) {
        ArrayList<producto> encontrados = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            if (actual.getProducto().getCategoriaProducto().equalsIgnoreCase(categoria)) {
                encontrados.add(actual.getProducto());
            }
            actual = actual.getSiguiente();
        }
        
        return encontrados;
    }

    public boolean modificarProducto(String id, producto nuevoProducto) {
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

    public boolean actualizarCantidad(String id, int nuevaCantidad) {
        producto producto = buscarPorId(id);
        
        if (producto != null) {
            producto.setCantidadProducto(nuevaCantidad);
            return true;
        }
        
        return false;
    }

    public boolean actualizarPrecio(String id, double nuevoPrecio) {
        producto producto = buscarPorId(id);
        
        if (producto != null) {
            producto.setPrecioProducto(nuevoPrecio);
            return true;
        }
        
        return false;
    }

    public ArrayList<producto> obtenerTodos() {
        ArrayList<producto> productos = new ArrayList<>();
        NodoProducto actual = cabeza;
        
        while (actual != null) {
            productos.add(actual.getProducto());
            actual = actual.getSiguiente();
        }
        
        return productos;
    }

    public ArrayList<producto> obtenerTodosReversa() {
        ArrayList<producto> productos = new ArrayList<>();
        NodoProducto actual = cola;
        
        while (actual != null) {
            productos.add(actual.getProducto());
            actual = actual.getAnterior();
        }
        
        return productos;
    }

    public producto obtenerEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) {
            return null;
        }
        
        NodoProducto actual = cabeza;
        
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        
        return actual.getProducto();
    }

    public boolean existeProducto(String id) {
        return buscarPorId(id) != null;
    }

    public void vaciarLista() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    public void imprimirLista() {
        NodoProducto actual = cabeza;
        System.out.println("=== LISTA DE PRODUCTOS ===");
        
        while (actual != null) {
            System.out.println(actual.getProducto().toString());
            actual = actual.getSiguiente();
        }
        
        System.out.println("Total de productos: " + tamaño);
    }

    public String obtenerSiguienteId() {
        int maxId = 0;
        NodoProducto actual = cabeza;
        
        while (actual != null) {

        if (Integer.parseInt(actual.getProducto().getIdProducto()) > maxId) {
        maxId = Integer.parseInt(actual.getProducto().getIdProducto());
    }
    actual = actual.getSiguiente();
    
    }
        
    int i = maxId + 1; 
     
    return String.valueOf(i);
        
}
    
}


