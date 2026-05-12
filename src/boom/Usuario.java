/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author alejo
 */
public class Usuario {
    
    // Enum para definir los roles disponibles
    public enum Rol {
        CLIENTE,
        ADMIN
    }
    
    private String correo;
    private String nombre;
    private String apellido;
    private String id;
    private String telefono;
    private String departamento;
    private String ciudad;
    private String direccion;
    private String contraseña;
    private String fechaNacimiento;
    private Rol rol;
    
    public Usuario() {
        this.correo = "";
        this.nombre = "";
        this.apellido = "";
        this.id = "";
        this.telefono = "";
        this.departamento = "";
        this.ciudad = "";
        this.direccion = "";
        this.contraseña = "";
        this.fechaNacimiento = "";
        this.rol = Rol.CLIENTE; 
    }
    
    public Usuario(String correo, String nombre, String apellido, String id, String fechaNacimiento, String telefono, String departamento, String ciudad, String direccion, String contraseña) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.contraseña = contraseña;
        this.rol = Rol.CLIENTE;
    }
    
    public Usuario(String correo, String nombre, String apellido, String id, String fechaNacimiento, String telefono, String departamento, String ciudad, String direccion, String contraseña, Rol rol) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
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
    
    public String getTelefono() {
        return telefono;
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
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public Rol getRol() {
        return rol;
    }
    
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

            if (direccionCompleta.length() > 0) {
                direccionCompleta.append(", ");
            }
            direccionCompleta.append(ciudad);
        }
        if (departamento != null && !departamento.isEmpty()) {

            if (direccionCompleta.length() > 0) {
                direccionCompleta.append(", ");
            }
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
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
    
    public void setContraseña(String contrasena) {
        this.contraseña = contrasena;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    

    //Formato: correo;nombre;apellido;id;fechaNacimiento;telefono;departamento;ciudad;direccion;contrasena;rol

    public String toArchivoLinea() {
        return String.join(";",
            correo != null ? correo : "",
            nombre != null ? nombre : "",
            apellido != null ? apellido : "",
            id != null ? id : "",
            fechaNacimiento != null ? fechaNacimiento : "",
            telefono != null ? telefono : "",
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
                datos[0].trim(),  // correo
                datos[1].trim(),  // nombre
                datos[2].trim(),  // apellido
                datos[3].trim(),  // id
                datos[4].trim(),  // fechaNacimiento
                datos[5].trim(),  // telefono
                datos[6].trim(),  // departamento
                datos[7].trim(),  // ciudad
                datos[8].trim(),  // direccion
                datos[9].trim()   // contraseña
            );
            
            try {
                u.setRol(Rol.valueOf(datos[10].trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                u.setRol(Rol.CLIENTE);
            }
            return u;
        }

        else if (datos.length >= 10) {
            return new Usuario(
                datos[0].trim(),  // correo
                datos[1].trim(),  // nombre
                datos[2].trim(),  // apellido
                datos[3].trim(),  // id
                datos[4].trim(),  // fechaNacimiento
                datos[5].trim(),  // telefono
                datos[6].trim(),  // departamento
                datos[7].trim(),  // ciudad
                datos[8].trim(),  // direccion
                datos[9].trim()   // contraseña

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
