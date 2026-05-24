/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/UsuarioServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Estructuras.ListaUsuarios;
import Raiz.Modelos.Historial;
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
                if (instancia == null) instancia = new UsuarioServicio();
            }
        }
        return instancia;
    }


    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final int    MAX_USUARIOS      = 500;
    private ListaUsuarios listaUsuarios;


    private UsuarioServicio() {
        this.listaUsuarios = new ListaUsuarios();
        cargarDesdeArchivo();
    }

    // ----------------------------------------------------------------
    //  REGISTRO PÚBLICO (siempre CLIENTE)
    // ----------------------------------------------------------------

    /**
     * Registra un nuevo usuario desde el formulario público.
     * El rol siempre será CLIENTE, sin excepción.
     */

    public boolean registrarUsuario(Usuario usuario) {
        if (listaUsuarios.getTamaño() >= MAX_USUARIOS) return false;
        if (listaUsuarios.existeCorreo(usuario.getCorreo()))  return false;
        if (listaUsuarios.existeId(usuario.getId()))          return false;

        // Forzar rol CLIENTE — nadie puede auto-asignarse ADMIN desde el registro
        usuario.setRol(Usuario.Rol.CLIENTE);

        listaUsuarios.insertarAlFinal(usuario);
        appendAlArchivo(usuario);

        HistorialServicio.getInstancia().registrar(
            Historial.TipoAccion.INICIO_SESION,
            "Nuevo registro: " + usuario.getNombreCompleto(),
            usuario.getId()
        );
        return true;
    }

    // ----------------------------------------------------------------
    //  CRUD ADMINISTRATIVO (solo sobre CLIENTES)
    // ----------------------------------------------------------------

    /**
     * El admin crea un nuevo cliente.
     * Nunca se puede crear otro admin por esta vía.
     */

    public boolean crearCliente(Usuario usuario, String idAdmin) {
        if (listaUsuarios.getTamaño() >= MAX_USUARIOS) return false;
        if (listaUsuarios.existeCorreo(usuario.getCorreo())) return false;
        if (listaUsuarios.existeId(usuario.getId()))          return false;

        // Siempre CLIENTE
        usuario.setRol(Usuario.Rol.CLIENTE);

        listaUsuarios.insertarAlFinal(usuario);
        appendAlArchivo(usuario);

        HistorialServicio.getInstancia().registrar(
            Historial.TipoAccion.USUARIO_CREADO,
            "Cliente creado por admin: " + usuario.getNombreCompleto()
                + " (" + usuario.getCorreo() + ")",
            idAdmin
        );
        return true;
    }

    /**
     * El admin edita los datos de un CLIENTE.
     * No puede editar a otro admin ni cambiar el rol a ADMIN.
     */

    public boolean editarCliente(String idCliente, Usuario nuevosDatos, String idAdmin) {
        Usuario objetivo = buscarPorId(idCliente);
        if (objetivo == null || objetivo.esAdmin()) {
            System.err.println("[UsuarioServicio] No se puede editar: no existe o es admin.");
            return false;
        }

        // Preservar rol CLIENTE
        nuevosDatos.setRol(Usuario.Rol.CLIENTE);

        boolean ok = listaUsuarios.modificarUsuario(idCliente, nuevosDatos);
        if (ok) {
            reescribirArchivo();
            HistorialServicio.getInstancia().registrar(
                Historial.TipoAccion.USUARIO_EDITADO,
                "Cliente editado: " + nuevosDatos.getNombreCompleto(),
                idAdmin
            );
        }
        return ok;
    }

    /**
     * El admin elimina un CLIENTE.
     * No puede eliminar a otro admin ni a sí mismo.
     */

    public boolean eliminarCliente(String idCliente, String idAdmin) {
        if (idCliente.equals(idAdmin)) {
            System.err.println("[UsuarioServicio] El admin no puede eliminarse a sí mismo.");
            return false;
        }
        Usuario objetivo = buscarPorId(idCliente);
        if (objetivo == null || objetivo.esAdmin()) {
            System.err.println("[UsuarioServicio] No se puede eliminar: no existe o es admin.");
            return false;
        }

        boolean ok = listaUsuarios.eliminarPorId(idCliente);
        if (ok) {
            reescribirArchivo();
            HistorialServicio.getInstancia().registrar(
                Historial.TipoAccion.USUARIO_ELIMINADO,
                "Cliente eliminado: " + objetivo.getNombreCompleto()
                    + " (" + objetivo.getCorreo() + ")",
                idAdmin
            );
        }
        return ok;
    }

    /**
     * El propio usuario edita su perfil (nombre, dirección, etc.).
     * No puede cambiarse el rol.
     */

    public boolean actualizarPerfilPropio(String idUsuario, Usuario nuevosDatos) {
        Usuario actual = buscarPorId(idUsuario);
        if (actual == null) return false;

        // Preservar el rol original
        nuevosDatos.setRol(actual.getRol());

        boolean ok = listaUsuarios.modificarUsuario(idUsuario, nuevosDatos);
        if (ok) reescribirArchivo();
        return ok;
    }

    /**
     * El usuario elimina su propia cuenta.
     * Un ADMIN no puede eliminarse a sí mismo para preservar el sistema.
     */

    public boolean eliminarCuentaPropia(String idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);
        if (usuario == null) return false;
        
        // El admin principal no puede eliminarse a sí mismo
        if (usuario.esAdmin()) {
            System.err.println("[UsuarioServicio] El admin no puede eliminar su propia cuenta.");
            return false;
        }
        
        boolean ok = listaUsuarios.eliminarPorId(idUsuario);
        if (ok) {
            reescribirArchivo();
            HistorialServicio.getInstancia().registrar(
                Historial.TipoAccion.USUARIO_ELIMINADO,
                "Usuario eliminó su propia cuenta: " + usuario.getNombreCompleto(),
                idUsuario
            );
        }
        return ok;
    }

    /**
     * Obtiene todos los usuarios excepto el especificado por ID.
     * Útil para que el admin no se vea a sí mismo en la lista.
     */

    public ArrayList<Usuario> obtenerTodosExcepto(String idExcluir) {
        return listaUsuarios.obtenerTodosExcepto(idExcluir);
    }

    // ----------------------------------------------------------------
    //  BÚSQUEDA
    // ----------------------------------------------------------------


    public Usuario buscarPorId(String id) {
        return listaUsuarios.buscarPorId(id);
    }

    public Usuario buscarPorCorreo(String correo) {
        return listaUsuarios.buscarPorCorreo(correo);
    }

    public ArrayList<Usuario> buscarPorNombre(String nombre) {
        return listaUsuarios.buscarPorNombre(nombre);
    }

    public ArrayList<Usuario> obtenerTodos() {
        return listaUsuarios.obtenerTodos();
    }

    public ArrayList<Usuario> obtenerClientes() {
        return listaUsuarios.buscarPorRol(Usuario.Rol.CLIENTE);
    }

    /**
     * Lista de clientes que el admin ve en el panel (excluye al propio admin).
     */

    public ArrayList<Usuario> obtenerClientesParaAdmin(String idAdmin) {
        ArrayList<Usuario> clientes = new ArrayList<>();
        for (Usuario u : listaUsuarios.obtenerTodos()) {
            if (u.esCliente() && !u.getId().equals(idAdmin)) clientes.add(u);
        }
        return clientes;
    }


    public boolean existeCorreo(String correo) {
        return listaUsuarios.existeCorreo(correo);
    }

    public boolean existeId(String id) {
        return listaUsuarios.existeId(id);
    }

    // ----------------------------------------------------------------
    //  AUTENTICACIÓN
    // ----------------------------------------------------------------


    public Usuario autenticar(String identificador, String contrasena) {
        if (identificador == null || contrasena == null) return null;
        String norm = identificador.trim().toLowerCase();
        for (Usuario u : listaUsuarios.obtenerTodos()) {
            boolean coincide =
                u.getCorreo().equalsIgnoreCase(norm)  ||
                u.getNombre().equalsIgnoreCase(identificador.trim()) ||
                u.getId().equals(identificador.trim());
            if (coincide && u.getContraseña().equals(contrasena)) return u;
        }
        return null;
    }

    // ----------------------------------------------------------------
    //  ESTADÍSTICAS
    // ----------------------------------------------------------------


    public int getCantidadUsuarios() {
        return listaUsuarios.getTamaño();
    }

    public int getCantidadClientes() {
        return listaUsuarios.contarClientes();
    }
    
    // ---------- Contar clientes (alias para estadisticas admin)
    public int contarClientes() {
        return getCantidadClientes();
    }

    public int getCantidadAdmins() {
        return listaUsuarios.contarAdmins();
    }

    public int getMaxUsuarios() {
        return MAX_USUARIOS;
    }

    // ----------------------------------------------------------------
    //  PERSISTENCIA
    // ----------------------------------------------------------------


    private void appendAlArchivo(Usuario u) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            w.write(u.toArchivoLinea());
            w.newLine();
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al guardar: " + e.getMessage());
        }
    }


    private void reescribirArchivo() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Usuario u : listaUsuarios.obtenerTodos()) {
                w.write(u.toArchivoLinea());
                w.newLine();
            }
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al reescribir: " + e.getMessage());
        }
    }


    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) return;
        try (BufferedReader r = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = r.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                Usuario u = Usuario.fromArchivoLinea(linea);
                if (u != null
                        && !listaUsuarios.existeCorreo(u.getCorreo())
                        && !listaUsuarios.existeId(u.getId())) {
                    listaUsuarios.insertarAlFinal(u);
                }
            }
        } catch (IOException e) {
            System.err.println("[UsuarioServicio] Error al cargar: " + e.getMessage());
        }
    }


    public void limpiarTodo() {
        listaUsuarios.vaciarLista();
        try { new FileWriter(ARCHIVO_USUARIOS).close(); }
        catch (IOException e) { System.err.println("[UsuarioServicio] Error al limpiar"); }
    }


    public ListaUsuarios getListaUsuarios() {
        return listaUsuarios;
    }
}
