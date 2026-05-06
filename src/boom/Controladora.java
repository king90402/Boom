/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane; 
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author alejo
 */
public class Controladora implements Initializable { 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneLogin.setVisible(true);
        paneRegistro.setVisible(false);
        
        terminarcorreo = new ArrayList<>();
        terminarcorreo.add("@gmail.com");
        terminarcorreo.add("@hotmail.com");
        terminarcorreo.add("@outlook.es");
        terminarcorreo.add("@yahoo.com");
        
        configurarAutoCompletadoCorreo();
    }
    
    private static final int mx = 500;
    
    private producto[] listaProductos;
    private cliente[] listaClientes;
    private admin[] listaAdmins;

    private int contadorProductos;
    private int contadorClientes;
    private int contadorAdmins;
    private List<String> terminarcorreo;
    private int indicecorreo = 0;
    
    //Paneles Login registro
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegistro;
    
    //Iniciar seccion
    @FXML
    private TextField correO; 
    @FXML
    private PasswordField password;
    
    //Registrarse
    @FXML private TextField correo_cliente;
    @FXML private TextField telefono_cliente;
    @FXML private TextField user;
    @FXML private TextField contraS;


    
    //Metodo ir a registro
    @FXML
    private void irARegistro(MouseEvent event){
        paneLogin.setVisible(false);
        paneRegistro.setVisible(true);
    }
    
    //Metodo ir a iniciar seccion
    @FXML
    private void irALogin(MouseEvent event){
        paneLogin.setVisible(true);
        paneRegistro.setVisible(false);
    }
   
    
    //Metodo inicio de seccion administrador
    @FXML
    private void Inicioseccion_admin(ActionEvent event){
        String user = correO.getText().trim();
        String pass = password.getText().trim();
      
        
        boolean encontrado = false;
        
       for (int i = 0; i < contadorAdmins; i++) {
        admin a = listaAdmins[i];
        System.out.println("Comparando con Admin[" + i + "]: [" + a.getCorreoAdmin() + "] / [" + a.getContrasenaAdmin() + "]");
        
        // Comparamos correo y contraseña de cada admin guardado
        if (a.getCorreoAdmin().equals(user) && a.getContrasenaAdmin().equals(pass)) {
            encontrado = true;
            
           try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Administracion.fxml"));
                Parent root = loader.load();
                
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Panel de Administración - Sparkle");
                
                
                stage.show();
                
               
                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cargar la vista de administración: " + e.getMessage());
                e.printStackTrace();
            }
            break; 
        }
    }

    if (!encontrado) {
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Intente de nuevo.");
    }
    }

    public Controladora() {
        listaProductos = new producto[mx];
        listaClientes = new cliente[mx];
        listaAdmins = new admin[mx];
        
        contadorProductos = 0;
        contadorClientes = 0;
        contadorAdmins = 0;
        
        //Admin ya preestablecido
        
        admin principal = new admin(
    "Diomedes Díaz",                   
    "Admin principal",               
    "+57 3148011595",                 
    "elcaciquedelajunta2013@outlook.es", 
    "Diomedes2013",                  
    "Cl 26 # 5 - 1957 La junta"        
);
        listaAdmins[contadorAdmins] = principal;
        contadorAdmins++;
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
    public boolean eliminarClientePoruser(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getNombreCliente().equals(IdCliente)) {
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
    
    // Buscar un cliente por su nombre
    public cliente buscarClientePoruser(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getNombreCliente().equals(IdCliente)) {
                return listaClientes[i];
            }
        }
        return null;
    }
    
    // Buscar posicion de un cliente por su nombre
    public int buscarIndiceClientePoruser(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getNombreCliente().equals(IdCliente)) {
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
    
    //Metodos para registrar clientes
    
    //Autocompletar correos electronicos
    
    private void configurarAutoCompletadoCorreo(){
      correo_cliente.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
        String texto = correo_cliente.getText();

        if (texto.contains("@")) {
                if(event.getCode() == KeyCode.UP){
                    indicecorreo = (indicecorreo + 1) % terminarcorreo.size();
                actualizarSugerenciaCorreo(texto);
                event.consume();
                } else if(event.getCode() == KeyCode.DOWN){
                   indicecorreo = (indicecorreo - 1 + terminarcorreo.size()) % terminarcorreo.size();
                actualizarSugerenciaCorreo(texto);
                event.consume();
                }else if (event.getCode() == KeyCode.ENTER){
                    actualizarSugerenciaCorreo(texto);
                correo_cliente.end();
                }
            }
                  
        });
    }
    
    private void actualizarSugerenciaCorreo (String textO){
        String base = textO.split("@")[0];
        correo_cliente.setText(base + terminarcorreo.get(indicecorreo));
    }
    
    //Registrar informacion de un cliente
    
    @FXML
    private void registrarUser(ActionEvent event){
        String correo = correo_cliente.getText().trim();
        String tel = telefono_cliente.getText();
        String usEr = user.getText().trim();
        String paSs = contraS.getText().trim();
        
        if (correo.isEmpty() || !correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            JOptionPane.showMessageDialog(null, "Hubo un error al registrar el correo. Vuelva a intentarlo.");
            return;
        }
         if (tel.isEmpty() || !tel.startsWith("+57") || tel.length() > 15){
            JOptionPane.showMessageDialog(null, "Hubo un error al registrar el telefono. Vuelva a intentarlo.");
            return;
        }
          if (usEr.isEmpty() || usEr.length() < 5){
            JOptionPane.showMessageDialog(null, "Hubo un error al registrar el usuario. Vuelva a intentarlo.");
            return;
        }
           if (paSs.isEmpty()){
            JOptionPane.showMessageDialog(null, "Hubo un error al registrar el correo. Vuelva a intentarlo.");
            return;
        }
       
       cliente nuevoC = new cliente(usEr, tel, correo, paSs, "Agregue una direccion por favor.");
       if (agregarCliente(nuevoC)){
           JOptionPane.showMessageDialog(null, "Registro exitoso!!");
           irALogin(null);
       } else {
           JOptionPane.showMessageDialog(null, "Error desconocido", "Error", JOptionPane.WARNING_MESSAGE);
       }
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
