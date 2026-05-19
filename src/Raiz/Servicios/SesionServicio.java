/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/SesionServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Modelos.Usuario;

/**
 * @author akejo
 */

// --------- Clase encargada de gestionar la sesion del usuario actual 

public class SesionServicio {
    
    // Singleton
    
    private static SesionServicio instancia;
    
    /**
     * Obtiene la instancia unica del servicio
     * Thread-safe con double-checked locking
     */
    
    public static SesionServicio getInstancia() {
        if (instancia == null) {
            synchronized (SesionServicio.class) {
                if (instancia == null) {
                    instancia = new SesionServicio();
                }
            }
        }
        return instancia;
    }
    
    // Atributos
    
    private Usuario usuarioActual;
    private boolean sesionActiva;
    
    // Constructor privado
    
    private SesionServicio() {
        this.usuarioActual = null;
        this.sesionActiva = false;
    }
    
    // ----- Metodos de inicio de sesión
    
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
        this.sesionActiva = true;
    }

    public void cerrarSesion() {
        if (usuarioActual != null) {
        }
        this.usuarioActual = null;
        this.sesionActiva = false;
    }
    
    public boolean haySesionActiva() {
        return sesionActiva && usuarioActual != null;
    }
    
    // Getter
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    // Verifica si el usuario actual es admin
    
    public boolean esAdmin() {
        return usuarioActual != null && usuarioActual.esAdmin();
    }
    
    // Verifica si el usuario actual es cliente
    
    public boolean esCliente() {
        return usuarioActual != null && usuarioActual.esCliente();
    }
    
    // Obtiene el nombre del usuario actual
    
    public String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombreCompleto() : "";
    }
    
    // Obtiene las iniciales del usuario actual

    public String getInicialesUsuario() {
        return usuarioActual != null ? usuarioActual.getIniciales() : "";
    }
    
    // Obtiene el correo del usuario actual

    public String getCorreoUsuario() {
        return usuarioActual != null ? usuarioActual.getCorreo() : "";
    }
    
    // Obtiene el rol del usuario actual como String

    public String getRolUsuario() {
        return usuarioActual != null ? usuarioActual.getRol().name() : "";
    }
    
    // Setter
    
    public void actualizarUsuarioActual(Usuario usuarioActualizado) {
        if (haySesionActiva() && usuarioActualizado != null) {
            this.usuarioActual = usuarioActualizado;
        }
    }
}
