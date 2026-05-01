/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;

/**
 *
 * @author EQUIPO
 */
public class admin {
   public String NombreAdmin;
    public String IdAdmin;
    public String TelefonoAdmin;
    public String CorreoAdmin;
    public String ContraseñaAdmin;
    public String DireccionAdmin;
    
    public admin(){       
       NombreAdmin = "";
       IdAdmin = "";
       TelefonoAdmin = "";
       CorreoAdmin = "";
       ContraseñaAdmin = "";
       DireccionAdmin = "";        
    } 
    
    public admin(String NombreAdmin, String IdAdmin, String TelefonoAdmin, String CorreoAdmin, String ContraseñaAdmin, String DireccionAdmin) {
      this.NombreAdmin = NombreAdmin;
      this.IdAdmin = IdAdmin;
      this.TelefonoAdmin = TelefonoAdmin;
      this.CorreoAdmin = CorreoAdmin;
      this.ContraseñaAdmin = ContraseñaAdmin;
      this.DireccionAdmin = DireccionAdmin;
    }
    
    public String getNombreAdmin() { return NombreAdmin; }
    public String getIdAdmin(){ return IdAdmin; }
    public String getTelefonoAdmin() { return TelefonoAdmin; }
    public String getCorreoAdmin() { return CorreoAdmin; }
    public String getContraseñaAdmin() { return ContraseñaAdmin; }
    public String getDireccionAdmin () { return DireccionAdmin; }
    
    public void setNombreAdmin(String NombreAdmin){ this.NombreAdmin = NombreAdmin; }
    public void setIdAdmin(String IdAdmin){ this.IdAdmin = IdAdmin; }
    public void setTelefonoAdmin(String TelefonoAdmin){ this.TelefonoAdmin = TelefonoAdmin; }
    public void setCorreoAdmin(String CorreoAdmin){ this.CorreoAdmin= CorreoAdmin; }
    public void setContraseñaAdmin(String ContraseñaAdmin){ this.ContraseñaAdmin = ContraseñaAdmin; }
    public void setDireccionAdmin(String DireccionAdmin){ this.DireccionAdmin = DireccionAdmin; } 
}
