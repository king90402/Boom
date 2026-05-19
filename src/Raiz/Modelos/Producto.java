/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Producto.java to edit this template
 */

package Raiz.Modelos;

/**
 * @author alejo
 */

// --------- Clase tipo POJO de producto con todos sus atributos

public class Producto {
    
    // Atributos
    
    private String idProducto;
    private String nombreProducto;
    private int cantidadProducto;
    private double precioProducto;
    private String estadoProducto;      
    private String marcaProducto;
    private String categoriaProducto;   
    private String imagenProducto;      
    
    // Constructor vacio - inicializado con valores por defecto

    public Producto() {
        this.idProducto = "";
        this.nombreProducto = "";
        this.cantidadProducto = 0;
        this.precioProducto = 0.0;
        this.estadoProducto = "";
        this.marcaProducto = "";
        this.categoriaProducto = "";
        this.imagenProducto = "";
    }
    
    // Constructor

    public Producto(String idProducto, String nombreProducto, int cantidadProducto, double precioProducto, String estadoProducto, String marcaProducto, String categoriaProducto, String imagenProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
        this.estadoProducto = estadoProducto;
        this.marcaProducto = marcaProducto;
        this.categoriaProducto = categoriaProducto;
        this.imagenProducto = imagenProducto;
    }
    
    // Getters

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public String getEstadoProducto() {
        return estadoProducto;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }
    
    // Setters

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
    
    // ----- Metodos auxiliares
    
    // Verifica si el producto tiene stock disponible
    
    public boolean tieneStock() {
        return cantidadProducto > 0;
    }
    
    // Verifica si el producto es nuevo

    public boolean esNuevo() {
        return "Nuevo".equalsIgnoreCase(estadoProducto);
    }
    
    // Retorna el precio del producto con formato de moneda
    public String getPrecioFormateado() {
        return String.format("$%,.2f", precioProducto);
    }
    
    // Reduce el stock si hay el suficiente disponible
    
    public boolean reducirStock(int cantidad) {
        if (cantidadProducto >= cantidad) {
            cantidadProducto -= cantidad;
            return true;
        }
        return false;
    }
    
    // Aumenta el stock
    
    public void aumentarStock(int cantidad) {
        if (cantidad > 0) {
            cantidadProducto += cantidad;
        }
    }
    
    // ----- Serializacion de los datos (atributos) de producto 
    
    // COnvierte los datos en linea de texto para su respectivo guardado en archivo productos
    
    public String toArchivoLinea() {
        return String.join(";",
            idProducto != null ? idProducto : "",
            nombreProducto != null ? nombreProducto : "",
            String.valueOf(cantidadProducto),
            String.valueOf(precioProducto),
            estadoProducto != null ? estadoProducto : "",
            marcaProducto != null ? marcaProducto : "",
            categoriaProducto != null ? categoriaProducto : "",
            imagenProducto != null ? imagenProducto : ""
        );
    }
    
    // Crea productos a partir de lineas de texto en el archivo productos
    
    public static Producto fromArchivoLinea(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return null;
        }
        
        String[] datos = linea.split(";");
        
        if (datos.length >= 8) {
            try {
                return new Producto(
                    datos[1].trim(),                    // nombre
                    datos[0].trim(),                    // id
                    Integer.parseInt(datos[2].trim()),  // cantidad
                    Double.parseDouble(datos[3].trim()),// precio
                    datos[4].trim(),                    // estado
                    datos[5].trim(),                    // marca
                    datos[6].trim(),                    // categoria
                    datos[7].trim()                     // imagen
                );
            } catch (NumberFormatException e) {
                return null;
            }
        }
        
        return null;
    }
    
    // ----- Metodos override
    
    @Override
    public String toString() {
        return "ID: " + idProducto + " | Nombre: " + nombreProducto + 
               " | Precio: " + getPrecioFormateado() + " | Stock: " + cantidadProducto;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return idProducto != null && idProducto.equals(producto.idProducto);
    }
    
    @Override
    public int hashCode() {
        return idProducto != null ? idProducto.hashCode() : 0;
    }
}
