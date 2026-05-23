/*
 * Modelo Historial - Proyecto Boom Sincronizado
 * Para registrar acciones del sistema (SOLO VISIBLE PARA ADMIN)
 */

package Raiz.Modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author BoomTeam
 * Clase para almacenar acciones del historial
 * NOTA: El historial solo es visible para administradores
 */

public class Historial {
    
    // Tipos de accion
    public enum TipoAccion {
        COMPRA,
        AGREGAR_CARRITO,
        QUITAR_CARRITO,
        AGREGAR_FAVORITO,
        QUITAR_FAVORITO,
        BUSQUEDA,
        VER_PRODUCTO,
        INICIO_SESION,
        CIERRE_SESION,
        // Acciones administrativas
        USUARIO_CREADO,
        USUARIO_EDITADO,
        USUARIO_ELIMINADO
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
    public Historial(TipoAccion tipo, String descripcion, String idUsuario, String idProducto, double monto) {
        this.idAccion = generarId();
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.fecha = LocalDateTime.now();
        this.monto = monto;
    }
    
    // Constructor simple
    public Historial(TipoAccion tipo, String descripcion, String idUsuario) {
        this(tipo, descripcion, idUsuario, null, 0);
    }
    
    // Generar ID unico
    private String generarId() {
        return "H" + String.format("%06d", ++contadorId);
    }
    
    // Getters
    public String getIdAccion() { return idAccion; }
    public TipoAccion getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public String getIdUsuario() { return idUsuario; }
    public String getIdProducto() { return idProducto; }
    public LocalDateTime getFecha() { return fecha; }
    public double getMonto() { return monto; }
    
    // Metodos auxiliares
    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fecha.format(formatter);
    }
    
    public String getFechaCorta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
    
    public String getHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return fecha.format(formatter);
    }
    
    public String getTipoTexto() {
        switch (tipo) {
            case COMPRA: return "Compra realizada";
            case AGREGAR_CARRITO: return "Agregado al carrito";
            case QUITAR_CARRITO: return "Quitado del carrito";
            case AGREGAR_FAVORITO: return "Agregado a favoritos";
            case QUITAR_FAVORITO: return "Quitado de favoritos";
            case BUSQUEDA: return "Busqueda";
            case VER_PRODUCTO: return "Producto visualizado";
            case INICIO_SESION: return "Inicio de sesion";
            case CIERRE_SESION: return "Cierre de sesion";
            case USUARIO_CREADO: return "Usuario creado";
            case USUARIO_EDITADO: return "Usuario editado";
            case USUARIO_ELIMINADO: return "Usuario eliminado";
            default: return "Accion";
        }
    }
    
    public String getIcono() {
        switch (tipo) {
            case COMPRA: return "cart-check";
            case AGREGAR_CARRITO: return "cart-plus";
            case QUITAR_CARRITO: return "cart-minus";
            case AGREGAR_FAVORITO: return "heart-fill";
            case QUITAR_FAVORITO: return "heart";
            case BUSQUEDA: return "search";
            case VER_PRODUCTO: return "eye";
            case INICIO_SESION: return "login";
            case CIERRE_SESION: return "logout";
            case USUARIO_CREADO: return "user-plus";
            case USUARIO_EDITADO: return "user-edit";
            case USUARIO_ELIMINADO: return "user-minus";
            default: return "circle";
        }
    }
    
    // Serializacion
    public String toArchivoLinea() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return String.join(";",
            idAccion,
            tipo.name(),
            descripcion != null ? descripcion : "",
            idUsuario != null ? idUsuario : "",
            idProducto != null ? idProducto : "",
            fecha.format(formatter),
            String.valueOf(monto)
        );
    }
    
    public static Historial fromArchivoLinea(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return null;
        }
        
        String[] datos = linea.split(";");
        
        if (datos.length >= 7) {
            try {
                TipoAccion tipo = TipoAccion.valueOf(datos[1].trim());
                String descripcion = datos[2].trim();
                String idUsuario = datos[3].trim();
                String idProducto = datos[4].trim().isEmpty() ? null : datos[4].trim();
                double monto = Double.parseDouble(datos[6].trim());
                
                Historial h = new Historial(tipo, descripcion, idUsuario, idProducto, monto);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                h.fecha = LocalDateTime.parse(datos[5].trim(), formatter);
                
                return h;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", getFechaFormateada(), getTipoTexto(), descripcion);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Historial historial = (Historial) obj;
        return idAccion != null && idAccion.equals(historial.idAccion);
    }
    
    @Override
    public int hashCode() {
        return idAccion != null ? idAccion.hashCode() : 0;
    }
}
