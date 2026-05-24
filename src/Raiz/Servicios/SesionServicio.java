/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/SesionServicio.java to edit this template
 */

package Raiz.Servicios;

import Raiz.Modelos.Usuario;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo de la sesion del usuario activo

public class SesionServicio {
    
    // Singleton

    private static SesionServicio instancia;
    

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
    
    // ----- Metodos de sesion -----
    

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
        this.sesionActiva = true;
    }


    public void cerrarSesion() {
        this.usuarioActual = null;
        this.sesionActiva = false;
    }
    

    public boolean haySesionActiva() {
        return sesionActiva && usuarioActual != null;
    }
    
    // ----- Getters -----
    

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    

    public boolean esAdmin() {
        return usuarioActual != null && usuarioActual.esAdmin();
    }
    

    public boolean esCliente() {
        return usuarioActual != null && usuarioActual.esCliente();
    }
    

    public String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombreCompleto() : "";
    }
    

    public String getInicialesUsuario() {
        return usuarioActual != null ? usuarioActual.getIniciales() : "";
    }
    

    public String getCorreoUsuario() {
        return usuarioActual != null ? usuarioActual.getCorreo() : "";
    }
    

    public String getRolUsuario() {
        return usuarioActual != null ? usuarioActual.getRol().name() : "";
    }
    

    public String getIdUsuarioActual() {
        return usuarioActual != null ? usuarioActual.getId() : "";
    }
    
    // ----- Setter -----
    

    public void actualizarUsuarioActual(Usuario usuarioActualizado) {
        if (haySesionActiva() && usuarioActualizado != null) {
            this.usuarioActual = usuarioActualizado;
        }
    }
}
