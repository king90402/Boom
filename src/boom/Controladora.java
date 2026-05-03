/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author alejo
 */
public class Controladora {
    private static final int mx = 500;
    
    private producto[] listaProductos;
    private cliente[] listaClientes;
    private admin[] listaAdmins;

    private int contadorProductos;
    private int contadorClientes;
    private int contadorAdmins;

    public Controladora() {
        listaProductos = new producto[mx];
        listaClientes = new cliente[mx];
        listaAdmins = new admin[mx];
        
        contadorProductos = 0;
        contadorClientes = 0;
        contadorAdmins = 0;
    }
    
// Metodos correspondientes a la lista de productos 
    
    // Agregar un producto
    public boolean agregarProducto(producto producto) {
        if (contadorProductos < mx) {
            listaProductos[contadorProductos] = producto;
            contadorProductos++;
            return true;
        }
        return false;
    }
    
    // Eliminar un producto por su posicion 
    public boolean eliminarProducto(int pos) {
        if (pos >= 0 && pos < contadorProductos) {
            for (int i = pos; i < contadorProductos - 1; i++) {
                listaProductos[i] = listaProductos[i + 1];
            }
            listaProductos[contadorProductos - 1] = null;
            contadorProductos--;
            return true;
        }
        return false;
    }
    
    // Eliminar un producto por su ID
    public boolean eliminarProductoPorId(String IdProducto) {
        for (int i = 0; i < contadorProductos; i++) {
            if (listaProductos[i].getIdProducto().equals(IdProducto)) {
                return eliminarProducto(i);
            }
        }
        return false;
    }
    
    // Buscar un producto por su ID
    public producto buscarProductoPorId(String IdProducto) {
        for (int i = 0; i < contadorProductos; i++) {
            if (listaProductos[i].getIdProducto().equals(IdProducto)) {
                return listaProductos[i];
            }
        }
        return null;
    }
    
    // Buscar productos por nombre (puede haber varios)
    public producto[] buscarProductosPorNombre(String NombreProducto) {
        producto[] resultados = new producto[contadorProductos];
        int contador = 0;
        for (int i = 0; i < contadorProductos; i++) {
            if (listaProductos[i].getNombreProducto().toLowerCase().contains(NombreProducto.toLowerCase())) {
                resultados[contador] = listaProductos[i];
                contador++;
            }
        }
        // Crear array del tamaño exacto
        producto[] resultadosFinales = new producto[contador];
        for (int i = 0; i < contador; i++) {
            resultadosFinales[i] = resultados[i];
        }
        return resultadosFinales;
    }
    
    // Buscar productos por categoría
    public producto[] buscarProductosPorCategoria(String CategoriaProducto) {
        producto[] resultados = new producto[contadorProductos];
        int contador = 0;
        for (int i = 0; i < contadorProductos; i++) {
            if (listaProductos[i].getCategoriaProducto().equalsIgnoreCase(CategoriaProducto)) {
                resultados[contador] = listaProductos[i];
                contador++;
            }
        }
        producto[] resultadosFinales = new producto[contador];
        for (int i = 0; i < contador; i++) {
            resultadosFinales[i] = resultados[i];
        }
        return resultadosFinales;
    }
    
    // Obtener la cantidad de productos
    public int getCantidadProductos() {
        return contadorProductos;
    }

// Metodos correspondientes a la lista de clientes
    
    // Agregar un cliente
    public boolean agregarCliente(cliente cliente) {
        if (contadorClientes < mx) {
            listaClientes[contadorClientes] = cliente;
            contadorClientes++;
            return true;
        }
        return false;
    }
    
    // Eliminar un cliente por su posicion 
    public boolean eliminarCliente(int pos) {
        if (pos >= 0 && pos < contadorClientes) {
            for (int i = pos; i < contadorClientes - 1; i++) {
                listaClientes[i] = listaClientes[i + 1];
            }
            listaClientes[contadorClientes - 1] = null;
            contadorClientes--;
            return true;
        }
        return false;
    }
    
    // Eliminar un cliente por su ID
    public boolean eliminarClientePorId(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(IdCliente)) {
                return eliminarCliente(i);
            }
        }
        return false;
    }
    
    // Obtener un cliente por su posicion
    public cliente obtenerCliente(int pos) {
        if (pos >= 0 && pos < contadorClientes) {
            return listaClientes[pos];
        }
        return null;
    }
    
    // Buscar un cliente por su ID
    public cliente buscarClientePorId(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(IdCliente)) {
                return listaClientes[i];
            }
        }
        return null;
    }
    
    // Buscar posicion de un cliente por su ID
    public int buscarIndiceClientePorId(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(IdCliente)) {
                return i;
            }
        }
        return -1;
    }
    
    // Verificar si un correo de cliente ya existe
    public boolean existeCorreoCliente(String correoCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getCorreoCliente().equals(correoCliente)) {
                return true;
            }
        }
        return false;
    }
    
    // Obtener la cantidad de clientes
    public int getCantidadClientes() {
        return contadorClientes;
    }
    
// Metodos para la lista de admins
    
    // Agregar un admin al array
    public boolean agregarAdmin(admin admin) {
        if (contadorAdmins < mx) {
            listaAdmins[contadorAdmins] = admin;
            contadorAdmins++;
            return true;
        }
        return false;
    }
    
    // Eliminar un admin por su posicion
    public boolean eliminarAdmin(int pos) {
        if (pos >= 0 && pos < contadorAdmins) {
            for (int i = pos; i < contadorAdmins - 1; i++) {
                listaAdmins[i] = listaAdmins[i + 1];
            }
            listaAdmins[contadorAdmins - 1] = null;
            contadorAdmins--;
            return true;
        }
        return false;
    }
    
    // Eliminar un admin por su ID
    public boolean eliminarAdminPorId(String IdAdmin) {
        for (int i = 0; i < contadorAdmins; i++) {
            if (listaAdmins[i].getIdAdmin().equals(IdAdmin)) {
                return eliminarAdmin(i);
            }
        }
        return false;
    }
    
    // Obtener un admin por su posicion
    public admin obtenerAdmin(int pos) {
        if (pos >= 0 && pos < contadorAdmins) {
            return listaAdmins[pos];
        }
        return null;
    }
    
    // Buscar un admin por su ID
    public admin buscarAdminPorId(String IdAdmin) {
        for (int i = 0; i < contadorAdmins; i++) {
            if (listaAdmins[i].getIdAdmin().equals(IdAdmin)) {
                return listaAdmins[i];
            }
        }
        return null;
    }
    
    // Buscar posicion de un admin por su ID
    public int buscarIndiceAdminPorId(String IdAdmin) {
        for (int i = 0; i < contadorAdmins; i++) {
            if (listaAdmins[i].getIdAdmin().equals(IdAdmin)) {
                return i;
            }
        }
        return -1;
    }
    
    // Verificar si un correo de admin ya existe
    public boolean existeCorreoAdmin(String correoAdmin) {
        for (int i = 0; i < contadorAdmins; i++) {
            if (listaAdmins[i].getCorreoAdmin().equals(correoAdmin)) {
                return true;
            }
        }
        return false;
    }
    
    // Obtener la cantidad de admins
    public int getCantidadAdmins() {
        return contadorAdmins;
    }
    
// Metodos auxiliares
    
    // Obtener el tamaño máximo
    public int getTamanoMaximo() {
        return mx;
    }
    
    // Limpiar todos los datos
    public void limpiarTodo() {
        listaProductos = new producto[mx];
        listaClientes = new cliente[mx];
        listaAdmins = new admin[mx];
        contadorProductos = 0;
        contadorClientes = 0;
        contadorAdmins = 0;
    }

}
