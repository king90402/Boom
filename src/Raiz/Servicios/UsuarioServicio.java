/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/UsuarioServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Estructuras.ListaUsuarios;
import Raiz.Modelos.Usuario;
import java.io.*;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada de gestionar todas las operaciones relacionadas con usuarios

public class UsuarioServicio {
    
    // Singleton
    
    private static UsuarioServicio instancia;
    
    public static UsuarioServicio getInstancia() {
        if (instancia == null) {
            synchronized (UsuarioServicio.class) {
                if (instancia == null) {
                    instancia = new UsuarioServicio();
                }
            }
        }
        return instancia;
    }
    
    // Constantes
    
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final int MAX_USUARIOS = 500;
    
    // Atributos
    
    private ListaUsuarios listaUsuarios;
    private boolean datosInicializados;
    
    // Constructor privado
    
    private UsuarioServicio() {
        this.listaUsuarios = new ListaUsuarios();
        this.datosInicializados = false;
        cargarUsuariosDesdeArchivo();
    }
    
    // ----- Metodos CRUD
    
    // Registra un nuevo usuario 

    public boolean registrarUsuario(Usuario usuario) {
        
        if (listaUsuarios.getTamaño() >= MAX_USUARIOS) {
            System.err.println("[UsuarioServicio] Sistema lleno");
            return false;
        }
        
        if (listaUsuarios.existeCorreo(usuario.getCorreo())) {
            System.err.println("[UsuarioServicio] Correo ya registrado: " + usuario.getCorreo());
            return false;
        }
        
        if (listaUsuarios.existeId(usuario.getId())) {
            System.err.println("[UsuarioServicio] ID ya registrado: " + usuario.getId());
            return false;
        }
        
        // Agregar a la lista
        listaUsuarios.insertarAlFinal(usuario);
        
        // Guardado en archivo
        return guardarUsuarioEnArchivo(usuario);
    }
    
    // Elimina un usuario por su ID
 
    public boolean eliminarUsuario(String id) {
        boolean eliminado = listaUsuarios.eliminarPorId(id);
        if (eliminado) {

            reescribirArchivoUsuarios();
        }
        return eliminado;
    }
    
    // Actualiza los datos de un usuario

    public boolean actualizarUsuario(String id, Usuario nuevosDatos) {
        boolean actualizado = listaUsuarios.modificarUsuario(id, nuevosDatos);
        if (actualizado) {
            reescribirArchivoUsuarios();
        }
        return actualizado;
    }
    
    // ----- Metodos busquedad 
    
    // Busca un usuario por ID

    public Usuario buscarPorId(String id) {
        return listaUsuarios.buscarPorId(id);
    }
    
    // Busca un usuario por correo
 
    public Usuario buscarPorCorreo(String correo) {
        return listaUsuarios.buscarPorCorreo(correo);
    }
    
    // Busca usuarios por nombre

    public ArrayList<Usuario> buscarPorNombre(String nombre) {
        return listaUsuarios.buscarPorNombre(nombre);
    }
    
    // Obtiene todos los usuarios

    public ArrayList<Usuario> obtenerTodos() {
        return listaUsuarios.obtenerTodos();
    }
    
    // Obtiene usuarios por rol

    public ArrayList<Usuario> obtenerPorRol(Usuario.Rol rol) {
        return listaUsuarios.buscarPorRol(rol);
    }
    
    // ----- Metodos de verificacion existencia
    
    // Verifica si existe un correo

    public boolean existeCorreo(String correo) {
        return listaUsuarios.existeCorreo(correo);
    }
    
    // Verifica si existe un ID
 
    public boolean existeId(String id) {
        return listaUsuarios.existeId(id);
    }
    
    // ----- Autenticacion

    public Usuario autenticar(String identificador, String contraseña) {
        if (identificador == null || contraseña == null) {
            return null;
        }
        
        String idNormalizado = identificador.trim().toLowerCase();
        
        // Buscar en la lista
        ArrayList<Usuario> usuarios = listaUsuarios.obtenerTodos();
        
        for (Usuario u : usuarios) {
            boolean coincideCredencial = 
                u.getCorreo().equalsIgnoreCase(idNormalizado) ||
                u.getNombre().equalsIgnoreCase(identificador.trim()) ||
                u.getId().equals(identificador.trim());
            
            if (coincideCredencial && u.getContraseña().equals(contraseña)) {
                return u;
            }
        }
        
        return null;
    }
    
    // ----- Estadisticas
    
    // Retorna la cantidad total de usuarios

    public int getCantidadUsuarios() {
        return listaUsuarios.getTamaño();
    }
    
    // Retorna la cantidad de clientes

    public int getCantidadClientes() {
        return listaUsuarios.contarClientes();
    }
    
    // Retorna la cantidad de administradores

    public int getCantidadAdmins() {
        return listaUsuarios.contarAdmins();
    }

    public int getMaxUsuarios() {
        return MAX_USUARIOS;
    }
    
    // ------ Persistencia
    
    // Guarda un usuario al final del archivo

    private boolean guardarUsuarioEnArchivo(Usuario u) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            writer.write(u.toArchivoLinea());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }
    
    // Reescribe todo el archivo de usuarios

    private void reescribirArchivoUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            ArrayList<Usuario> usuarios = listaUsuarios.obtenerTodos();
            for (Usuario u : usuarios) {
                writer.write(u.toArchivoLinea());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al reescribir archivo: " + e.getMessage());
        }
    }
    
    // Carga los usuarios desde el archivo al iniciar

    private void cargarUsuariosDesdeArchivo() {
        File archivo = new File(ARCHIVO_USUARIOS);
        
        if (!archivo.exists()) {
            datosInicializados = true;
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            int cargados = 0;
            
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                Usuario u = Usuario.fromArchivoLinea(linea);
                
                if (u != null && 
                    !listaUsuarios.existeCorreo(u.getCorreo()) && 
                    !listaUsuarios.existeId(u.getId())) {
                    listaUsuarios.insertarAlFinal(u);
                    cargados++;
                }
            }
            
            datosInicializados = true;
            
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al cargar usuarios: " + e.getMessage());
        }
    }
    
    // ------ Metodos funcionales
    
    // Limpia todos los usuarios

    public void limpiarTodo() {
        listaUsuarios.vaciarLista();
        try {
            new FileWriter(ARCHIVO_USUARIOS).close();
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al limpiar archivo");
        }
    }
    
    // Retorna la lista interna

    public ListaUsuarios getListaUsuarios() {
        return listaUsuarios;
    }
}
