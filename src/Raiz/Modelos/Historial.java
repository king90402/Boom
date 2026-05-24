/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Historial.java to edit this template
 */

package Raiz.Modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author alejo
 */

// --------- Clase tipo POJO de historial con todos sus atributos

public class Historial {
    
    public enum TipoAccion {
        // Acciones de usuario (cliente)
        INICIO_SESION,
        CIERRE_SESION,
        VER_PRODUCTO,
        BUSQUEDA,
        AGREGAR_CARRITO,
        QUITAR_CARRITO,
        AGREGAR_FAVORITO,
        QUITAR_FAVORITO,
        COMPRA,
        // Acciones administrativas
        USUARIO_CREADO,
        USUARIO_EDITADO,
        USUARIO_ELIMINADO,
        PRODUCTO_CREADO,
        PRODUCTO_EDITADO,
        PRODUCTO_ELIMINADO
    }

    // Atributos

    private String idAccion;
    private TipoAccion tipo;
    private String descripcion;
    private String idUsuario;       
    private String idProducto;      

    private LocalDateTime fecha;
    private double monto;
    private static int contadorId = 0;

    // Constructor completo

    public Historial(TipoAccion tipo, String descripcion, String idUsuario,
                     String idProducto, double monto) {
        this.idAccion    = generarId();
        this.tipo        = tipo;
        this.descripcion = descripcion;
        this.idUsuario   = idUsuario;
        this.idProducto  = idProducto;
        this.fecha       = LocalDateTime.now();
        this.monto       = monto;
    }

    // Constructor simple (sin producto ni monto)

    public Historial(TipoAccion tipo, String descripcion, String idUsuario) {
        this(tipo, descripcion, idUsuario, null, 0);
    }


    private String generarId() {
        return "H" + String.format("%06d", ++contadorId);
    }

    // Getters

    public String getIdAccion() {
        return idAccion;
    }

    public TipoAccion getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getMonto() {
        return monto;
    }

    // Métodos de formato

    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }


    public String getFechaCorta() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    public String getHora() {
        return fecha.format(DateTimeFormatter.ofPattern("HH:mm"));
    }


    public String getTipoTexto() {
        switch (tipo) {
            case INICIO_SESION:       return "Inicio de sesión";
            case CIERRE_SESION:       return "Cierre de sesión";
            case VER_PRODUCTO:        return "Producto visualizado";
            case BUSQUEDA:            return "Búsqueda";
            case AGREGAR_CARRITO:     return "Agregado al carrito";
            case QUITAR_CARRITO:      return "Quitado del carrito";
            case AGREGAR_FAVORITO:    return "Agregado a favoritos";
            case QUITAR_FAVORITO:     return "Quitado de favoritos";
            case COMPRA:              return "Compra realizada";
            case USUARIO_CREADO:      return "Usuario creado";
            case USUARIO_EDITADO:     return "Usuario editado";
            case USUARIO_ELIMINADO:   return "Usuario eliminado";
            case PRODUCTO_CREADO:     return "Producto creado";
            case PRODUCTO_EDITADO:    return "Producto editado";
            case PRODUCTO_ELIMINADO:  return "Producto eliminado";
            default:                  return "Acción";
        }
    }


    public String getIcono() {
        switch (tipo) {
            case INICIO_SESION:      return "login";
            case CIERRE_SESION:      return "logout";
            case VER_PRODUCTO:       return "eye";
            case BUSQUEDA:           return "search";
            case AGREGAR_CARRITO:    return "cart-plus";
            case QUITAR_CARRITO:     return "cart-minus";
            case AGREGAR_FAVORITO:   return "heart-fill";
            case QUITAR_FAVORITO:    return "heart";
            case COMPRA:             return "cart-check";
            case USUARIO_CREADO:     return "user-plus";
            case USUARIO_EDITADO:    return "user-edit";
            case USUARIO_ELIMINADO:  return "user-minus";
            case PRODUCTO_CREADO:    return "box-seam";
            case PRODUCTO_EDITADO:   return "pencil-square";
            case PRODUCTO_ELIMINADO: return "trash";
            default:                 return "circle";
        }
    }

    // Indica si es una acción que solo debe ver el admin

    public boolean esAccionAdmin() {
        return tipo == TipoAccion.USUARIO_CREADO  ||
               tipo == TipoAccion.USUARIO_EDITADO ||
               tipo == TipoAccion.USUARIO_ELIMINADO ||
               tipo == TipoAccion.PRODUCTO_CREADO   ||
               tipo == TipoAccion.PRODUCTO_EDITADO  ||
               tipo == TipoAccion.PRODUCTO_ELIMINADO;
    }

    // Serialización

    public String toArchivoLinea() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return String.join(";",
            idAccion,
            tipo.name(),
            descripcion  != null ? descripcion.replace(";", ",")  : "",
            idUsuario    != null ? idUsuario   : "",
            idProducto   != null ? idProducto  : "",
            fecha.format(f),
            String.valueOf(monto)
        );
    }


    public static Historial fromArchivoLinea(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;
        String[] d = linea.split(";");
        if (d.length < 7) return null;
        try {
            TipoAccion tipo   = TipoAccion.valueOf(d[1].trim());
            String desc       = d[2].trim();
            String idUsr      = d[3].trim();
            String idProd     = d[4].trim().isEmpty() ? null : d[4].trim();
            double monto      = Double.parseDouble(d[6].trim());
            Historial h = new Historial(tipo, desc, idUsr, idProd, monto);
            h.fecha = LocalDateTime.parse(d[5].trim(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            return h;
        } catch (Exception e) {
            return null;
        }
    }

    @Override

    public String toString() {
        return String.format("[%s] %s - %s (usuario: %s)",
                getFechaFormateada(), getTipoTexto(), descripcion, idUsuario);
    }

    @Override

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Historial)) return false;
        return idAccion != null && idAccion.equals(((Historial) obj).idAccion);
    }

    @Override

    public int hashCode() {
        return idAccion != null ? idAccion.hashCode() : 0;
    }
}
