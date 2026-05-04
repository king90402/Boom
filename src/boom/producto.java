/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author EQUIPO
 */
public class producto {
    public String NombreProducto;
    public String IdProducto;
    public int CantidadProducto;
    public double PrecioProducto;
    public String EstadoProducto;
    public String MarcaProducto;
    public String CategoriaProducto;
    public String imagenProducto;

    public producto() {
        NombreProducto = "";
        IdProducto = "";
        CantidadProducto = 0;
        PrecioProducto = 0.0;
        EstadoProducto = "";
        MarcaProducto = "";
        CategoriaProducto = "";
        imagenProducto= "";
    }

    public producto(String NombreProducto, String IdProducto, int CantidadProducto, double PrecioProducto, String EstadoProducto, String MarcaProducto, String CategoriaProducto, String imagenProducto) {
        this.NombreProducto = NombreProducto;
        this.IdProducto = IdProducto;
        this.CantidadProducto = CantidadProducto;
        this.PrecioProducto = PrecioProducto;
        this.EstadoProducto = EstadoProducto;
        this.MarcaProducto = MarcaProducto;
        this.CategoriaProducto = CategoriaProducto;
      this.imagenProducto = imagenProducto;
    }


    public String getNombreProducto() { return NombreProducto; }
    public String getIdProducto() { return IdProducto; }
    public int getCantidadProducto() { return CantidadProducto; }
    public double getPrecioProducto() { return PrecioProducto; }
    public String getEstadoProducto() { return EstadoProducto; }
    public String getMarcaProducto() { return MarcaProducto; }
    public String getCategoriaProducto() { return CategoriaProducto; }
    public String getImagenProducto() { return imagenProducto;}
    

    public void setNombreProducto(String NombreProducto) { this.NombreProducto = NombreProducto; }
    public void setIdProducto(String IdProducto) { this.IdProducto = IdProducto; }
    public void setCantidadProducto(int CantidadProducto) { this.CantidadProducto = CantidadProducto; }
    public void setPrecioProducto(double PrecioProducto) { this.PrecioProducto = PrecioProducto; }
    public void setEstadoProducto(String EstadoProducto) { this.EstadoProducto = EstadoProducto; }
    public void setMarcaProducto(String MarcaProducto) { this.MarcaProducto = MarcaProducto; }
    public void setCategoriaProducto(String CategoriaProducto) { this.CategoriaProducto = CategoriaProducto; }
    public void setImagenProducto(String imagenProducto) { this.imagenProducto = imagenProducto;}
    
}
