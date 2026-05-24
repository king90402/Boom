/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Usuario.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase tipo POJO de usuario con todos sus atributos

public class Usuario {
    
    // Enum de roles

    public enum Rol {
        CLIENTE,
        ADMIN
    }
    
    // Atributos

    private String correo;
    private String nombre;
    private String apellido;
    private String id;
    private String fechaNacimiento;
    private String celular;
    private String departamento;
    private String ciudad;
    private String direccion;
    private String contraseña;
    private Rol rol;
    
    // Constructor vacio - inicializado con valores por defecto (CLIENTE)

    public Usuario() {
        this.correo = "";
        this.nombre = "";
        this.apellido = "";
        this.id = "";
        this.fechaNacimiento = "";
        this.celular = "";
        this.departamento = "";
        this.ciudad = "";
        this.direccion = "";
        this.contraseña = "";
        this.rol = Rol.CLIENTE;
    }
    
    // Constructor para CLIENTE (sin rol)

    public Usuario(String correo, String nombre, String apellido, String id, String fechaNacimiento, 
                   String celular, String departamento, String ciudad, String direccion, String contraseña) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.contraseña = contraseña;
        this.rol = Rol.CLIENTE;
    }
    
    // Constructor completo con rol

    public Usuario(String correo, String nombre, String apellido, String id, String fechaNacimiento, 
                   String celular, String departamento, String ciudad, String direccion, String contraseña, Rol rol) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
    // Getters

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getId() {
        return id;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Rol getRol() {
        return rol;
    }
    
    // Setters

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    // ----- Metodos auxiliares -----
    

    public String getNombreCompleto() {
        if (apellido == null || apellido.isEmpty()) {
            return nombre;
        }
        return nombre + " " + apellido;
    }


    public String getIniciales() {
        if (nombre == null || nombre.isEmpty()) {
            return "";
        }
        String inicial1 = nombre.substring(0, 1).toUpperCase();
        String inicial2 = (apellido != null && !apellido.isEmpty()) 
                          ? apellido.substring(0, 1).toUpperCase() 
                          : "";
        return inicial1 + inicial2;
    }
    

    public String getDireccionCompleta() {
        StringBuilder direccionCompleta = new StringBuilder();
        
        if (direccion != null && !direccion.isEmpty()) {
            direccionCompleta.append(direccion);
        }
        if (ciudad != null && !ciudad.isEmpty()) {
            if (direccionCompleta.length() > 0) direccionCompleta.append(", ");
            direccionCompleta.append(ciudad);
        }
        if (departamento != null && !departamento.isEmpty()) {
            if (direccionCompleta.length() > 0) direccionCompleta.append(", ");
            direccionCompleta.append(departamento);
        }
        return direccionCompleta.toString();
    }
    

    public boolean esAdmin() {
        return rol == Rol.ADMIN;
    }

    public boolean esCliente() {
        return rol == Rol.CLIENTE;
    }
    
    // ----- Serializacion -----
    

    public String toArchivoLinea() {
        return String.join(";",
            correo != null ? correo : "",
            nombre != null ? nombre : "",
            apellido != null ? apellido : "",
            id != null ? id : "",
            fechaNacimiento != null ? fechaNacimiento : "",
            celular != null ? celular : "",
            departamento != null ? departamento : "",
            ciudad != null ? ciudad : "",
            direccion != null ? direccion : "",
            contraseña != null ? contraseña : "",
            rol != null ? rol.name() : Rol.CLIENTE.name()
        );
    }
    

    public static Usuario fromArchivoLinea(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return null;
        }
        
        String[] datos = linea.split(";");
        
        if (datos.length >= 11) {
            Usuario u = new Usuario(
                datos[0].trim(), datos[1].trim(), datos[2].trim(),
                datos[3].trim(), datos[4].trim(), datos[5].trim(),
                datos[6].trim(), datos[7].trim(), datos[8].trim(),
                datos[9].trim()
            );
            try {
                u.setRol(Rol.valueOf(datos[10].trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                u.setRol(Rol.CLIENTE);
            }
            return u;
        } else if (datos.length >= 10) {
            return new Usuario(
                datos[0].trim(), datos[1].trim(), datos[2].trim(),
                datos[3].trim(), datos[4].trim(), datos[5].trim(),
                datos[6].trim(), datos[7].trim(), datos[8].trim(),
                datos[9].trim()
            );
        }
        return null;
    }
    
    @Override

    public String toString() {
        return String.format("Usuario[nombre=%s %s, correo=%s, rol=%s]", 
                            nombre, apellido, correo, rol);
    }
    
    @Override

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return (id != null && id.equals(usuario.id)) || 
               (correo != null && correo.equalsIgnoreCase(usuario.correo));
    }
    
    @Override

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (correo != null ? correo.toLowerCase().hashCode() : 0);
        return result;
    }
}
