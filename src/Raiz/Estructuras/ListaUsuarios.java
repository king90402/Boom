/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/ListaUsuarios.java to edit this template
 */

package Raiz.Estructuras;

import Raiz.Modelos.NodoUsuario;
import Raiz.Modelos.Usuario;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo de los usuarios mediante una lista doblemente enlazada

public class ListaUsuarios {
    

    private NodoUsuario cabeza;  

    private NodoUsuario cola;    

    private int tamaño;         
    

    public ListaUsuarios() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }
    
    // Metodos consulta

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamaño() {
        return tamaño;
    }
    
    // ----- Metodos de insercion -----

    public void insertarAlInicio(Usuario usuario) {
        NodoUsuario nuevoNodo = new NodoUsuario(usuario);
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

    public void insertarAlFinal(Usuario usuario) {
        NodoUsuario nuevoNodo = new NodoUsuario(usuario);
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
    
    public boolean insertarEnPosicion(Usuario usuario, int posicion) {
        if (posicion < 0 || posicion > tamaño) return false;
        if (posicion == 0) { insertarAlInicio(usuario); return true; }
        if (posicion == tamaño) { insertarAlFinal(usuario); return true; }
        
        NodoUsuario nuevoNodo = new NodoUsuario(usuario);
        NodoUsuario actual = cabeza;
        
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
    
    // ----- Metodos eliminacion -----
 
    public Usuario eliminarDelInicio() {
        if (estaVacia()) return null;
        
        Usuario usuarioEliminado = cabeza.getUsuario();
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cabeza = cabeza.getSiguiente();
            cabeza.setAnterior(null);
        }
        tamaño--;
        return usuarioEliminado;
    }
    

    public Usuario eliminarDelFinal() {
        if (estaVacia()) return null;
        
        Usuario usuarioEliminado = cola.getUsuario();
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cola = cola.getAnterior();
            cola.setSiguiente(null);
        }
        tamaño--;
        return usuarioEliminado;
    }
    

    public boolean eliminarPorId(String id) {
        if (estaVacia()) return false;
        
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getId().equals(id)) {
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

    public boolean eliminarPorCorreo(String correo) {
        if (estaVacia()) return false;
        
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getCorreo().equalsIgnoreCase(correo)) {
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
    
    // ----- Metodos busqueda -----
    

    public Usuario buscarPorId(String id) {
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getId().equals(id)) {
                return actual.getUsuario();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    

    public Usuario buscarPorCorreo(String correo) {
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getCorreo().equalsIgnoreCase(correo)) {
                return actual.getUsuario();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
     

    public ArrayList<Usuario> buscarPorNombre(String nombre) {
        ArrayList<Usuario> encontrados = new ArrayList<>();
        NodoUsuario actual = cabeza;
        while (actual != null) {
            String nombreCompleto = actual.getUsuario().getNombreCompleto().toLowerCase();
            if (nombreCompleto.contains(nombre.toLowerCase())) {
                encontrados.add(actual.getUsuario());
            }
            actual = actual.getSiguiente();
        }
        return encontrados;
    }
    

    public ArrayList<Usuario> buscarPorRol(Usuario.Rol rol) {
        ArrayList<Usuario> encontrados = new ArrayList<>();
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getRol() == rol) {
                encontrados.add(actual.getUsuario());
            }
            actual = actual.getSiguiente();
        }
        return encontrados;
    }
    
    // ----- Metodos de verificacion -----
    

    public boolean existeId(String id) {
        return buscarPorId(id) != null;
    }

    public boolean existeCorreo(String correo) {
        return buscarPorCorreo(correo) != null;
    }
    
    // ----- Metodos modificacion -----
    

    public boolean modificarUsuario(String id, Usuario nuevosDatos) {
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getId().equals(id)) {
                actual.setUsuario(nuevosDatos);
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    // ----- Metodos de obtencion -----
    

    public ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        NodoUsuario actual = cabeza;
        while (actual != null) {
            usuarios.add(actual.getUsuario());
            actual = actual.getSiguiente();
        }
        return usuarios;
    }
    
    /**
     * Obtiene todos los usuarios excepto el especificado por ID
     * Util para que admin no se vea a si mismo en la lista de usuarios
     */

    public ArrayList<Usuario> obtenerTodosExcepto(String idExcluir) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (!actual.getUsuario().getId().equals(idExcluir)) {
                usuarios.add(actual.getUsuario());
            }
            actual = actual.getSiguiente();
        }
        return usuarios;
    }
    

    public ArrayList<Usuario> obtenerTodosReversa() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        NodoUsuario actual = cola;
        while (actual != null) {
            usuarios.add(actual.getUsuario());
            actual = actual.getAnterior();
        }
        return usuarios;
    }
    

    public Usuario obtenerEnPosicion(int posicion) {
        if (posicion < 0 || posicion >= tamaño) return null;
        NodoUsuario actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getUsuario();
    }
    
    // ----- Metodos de contabilizacion -----
    

    public int contarPorRol(Usuario.Rol rol) {
        int contador = 0;
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (actual.getUsuario().getRol() == rol) contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }


    public int contarClientes() {
        return contarPorRol(Usuario.Rol.CLIENTE);
    }

    public int contarAdmins() {
        return contarPorRol(Usuario.Rol.ADMIN);
    }
    
    // ----- Metodos funcionales -----
    

    public void vaciarLista() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    

    public NodoUsuario getCabeza() {
        return cabeza;
    }

    public NodoUsuario getCola() {
        return cola;
    }
}
