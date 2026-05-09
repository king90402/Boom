/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author EQUIPO
 */
public class clienteBoom {
    public String CorreoCliente;
    public String NombreCliente;
    public String ApellidoCliente;
    public String FechaCliente;
    public String IdCliente;
    public String CelularCliente;
    public String DepartamentoCliente;
    public String CiudadCliente;
    public String DireccionCliente;
    public String ContraseñaCliente;
    
    public clienteBoom(){       
       NombreCliente = "";
       CorreoCliente = "";
       NombreCliente = "";
       ApellidoCliente = "";
       FechaCliente = "";
       IdCliente = "";
       CelularCliente = "";
       DepartamentoCliente = "";
       CiudadCliente = "";
       DireccionCliente = "";
       ContraseñaCliente = "";        
    } 
    
    public clienteBoom(String CorreoCliente, String NombreCliente, String ApellidoCliente, String FechaCliente, String IdCliente, String CelularCliente, String DepartamentoCliente, String CiudadCliente, String DireccionCliente, String ContraseñaCliente) {
       this.CorreoCliente = CorreoCliente;
       this.NombreCliente = NombreCliente;
       this.ApellidoCliente = ApellidoCliente;
       this.FechaCliente = FechaCliente;
       this.IdCliente = IdCliente;
       this.CelularCliente = CelularCliente;
       this.DepartamentoCliente = DepartamentoCliente;
       this.CiudadCliente = CiudadCliente;
       this.DireccionCliente = DireccionCliente;
       this.ContraseñaCliente = ContraseñaCliente;
    }
    
    public String getCorreoCliente() {
        return CorreoCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public String getApellidoCliente() {
        return ApellidoCliente;
    }

    public String getFechaCliente() {
        return FechaCliente;
    }

    public String getIdCliente() {
        return IdCliente;
    }

    public String getCelularCliente() {
        return CelularCliente;
    }

    public String getDepartamentoCliente() {
        return DepartamentoCliente;
    }

    public String getCiudadCliente() {
        return CiudadCliente;
    }

    public String getDireccionCliente() {
        return DireccionCliente;
    }

    public String getContraseñaCliente() {
        return ContraseñaCliente;
    }

    
    
    public void setCorreoCliente(String CorreoCliente) {
        this.CorreoCliente = CorreoCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public void setApellidoCliente(String ApellidoCliente) {
        this.ApellidoCliente = ApellidoCliente;
    }

    public void setFechaCliente(String FechaCliente) {
        this.FechaCliente = FechaCliente;
    }

    public void setIdCliente(String IdCliente) {
        this.IdCliente = IdCliente;
    }

    public void setCelularCliente(String CelularCliente) {
        this.CelularCliente = CelularCliente;
    }

    public void setDepartamentoCliente(String DepartamentoCliente) {
        this.DepartamentoCliente = DepartamentoCliente;
    }

    public void setCiudadCliente(String CiudadCliente) {
        this.CiudadCliente = CiudadCliente;
    }

    public void setDireccionCliente(String DireccionCliente) {
        this.DireccionCliente = DireccionCliente;
    }

    public void setContraseñaCliente(String ContraseñaCliente) {
        this.ContraseñaCliente = ContraseñaCliente;
    }  
    
}
