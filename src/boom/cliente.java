/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author EQUIPO
 */
public class cliente {
    public String NombreCliente;
    public String IdCliente;
    public String TelefonoCliente;
    public String CorreoCliente;
    public String ContraseñaCliente;
    public String DireccionCliente;
    
    public cliente(){       
       NombreCliente = "";
       IdCliente = "";
       TelefonoCliente = "";
       CorreoCliente = "";
       ContraseñaCliente = "";
       DireccionCliente = "";        
    } 
    
    public cliente(String NombreCliente, String IdCliente, String TelefonoCliente, String CorreoCliente, String ContraseñaCliente, String DireccionCliente) {
      this.NombreCliente = NombreCliente;
      this.IdCliente = IdCliente;
      this.TelefonoCliente = TelefonoCliente;
      this.CorreoCliente = CorreoCliente;
      this.ContraseñaCliente = ContraseñaCliente;
      this.DireccionCliente = DireccionCliente;
    }
    
    public String getNombreCliente() { return NombreCliente; }
    public String getIdCliente(){ return IdCliente; }
    public String getTelefonoCliente() { return TelefonoCliente; }
    public String getCorreoCliente() { return CorreoCliente; }
    public String getContraseñaCliente() { return ContraseñaCliente; }
    public String getDireccionCliente () { return DireccionCliente; }
    
    public void setNombreCliente(String NombreCliente){ this.NombreCliente = NombreCliente; }
    public void setIdCliente(String IdCliente){ this.IdCliente = IdCliente; }
    public void setTelefonoCliente(String TelefonoCliente){ this.TelefonoCliente = TelefonoCliente; }
    public void setCorreoCliente(String CorreoCliente){ this.CorreoCliente= CorreoCliente; }
    public void setContraseñaCliente(String ContraseñaCliente){ this.ContraseñaCliente = ContraseñaCliente; }
    public void setDireccionCliente(String DireccionCliente){ this.DireccionCliente = DireccionCliente; }
    
    
    
    
    
    
}
