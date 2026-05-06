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
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author alejo
 */
public class Controladora implements Initializable { 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneLogin.setVisible(true);
        paneRegistro.setVisible(false);
        cargarClientesDesdeArchivo();
        
        terminarcorreo = new ArrayList<>();
        terminarcorreo.add("@gmail.com");
        terminarcorreo.add("@hotmail.com");
        terminarcorreo.add("@outlook.es");
        terminarcorreo.add("@yahoo.com");
        
        configurarAutoCompletadoCorreo();
        
        if (Estadisticas != null) {
        Estadisticas.setVisible(true);
    }
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
    
    //Paneles 
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegistro;
    @FXML private Pane Estadisticas;
  
    
    //Iniciar seccion
    @FXML
    private TextField correO; 
    @FXML
    private PasswordField password;
    @FXML private TextField passwordVisible;
    
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
   
    
    //Metodos de inicio de seccion usuarios (ambos roles)
    @FXML
    private void Inicioseccion_usuario(ActionEvent event){
        String user = correO.getText().trim();
        String pass = password.getText().trim();
      
        
        boolean esAdmin = false;
        boolean esCliente = false;
        
       for (int i = 0; i < contadorAdmins; i++) {
        admin a = listaAdmins[i];

        // verificamos correo y contraseña de cada admin guardado
       if (a.getCorreoAdmin().equals(user) && a.getContrasenaAdmin().equals(pass)) {
            esAdmin = true;
            cargarVista("Vista_Administracion.fxml", "Panel de Administración - BOOM", event);
            break;
        }
    }
       
       // verificamos correo y contraseña de cada cliente guardado
           if (!esAdmin) {
        for (int i = 0; i < contadorClientes; i++) {
            cliente c = listaClientes[i];
            if (c.getCorreoCliente().equals(user) && c.getContraseñaCliente().equals(pass)) {
                esCliente = true;
                cargarVista("Vista_Home.fxml", "Tienda BOOM", event);
                break;
            }
        }
    }
           
           if (!esAdmin && !esCliente) {
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
    }
    }
    
    // Mostrar contraseña al mantener precionado
    @FXML
private void mostrarContrasena(MouseEvent event) {
    passwordVisible.setText(contraS.getText());
    passwordVisible.setVisible(true);
    contraS.setVisible(false);
    passwordVisible.requestFocus(); 
    passwordVisible.positionCaret(passwordVisible.getText().length()); 
}

@FXML
private void ocultarContrasena(MouseEvent event) {
    contraS.setText(passwordVisible.getText());
    contraS.setVisible(true);
    passwordVisible.setVisible(false);
    contraS.requestFocus(); 
    contraS.positionCaret(contraS.getText().length());
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
    
    // Guardar un cliente
    private void guardarClientesEnArchivo() {
    try (PrintWriter writer = new PrintWriter(new FileWriter("clientes.txt"))) {
        for (int i = 0; i < contadorClientes; i++) {
            writer.println(listaClientes[i].toString());
        }
    } catch (IOException e) {
        System.err.println("Error al guardar clientes: " + e.getMessage());
    }
}
    
    // Cargar clientes en archivos
    private void cargarClientesDesdeArchivo() {
    File archivo = new File("clientes.txt");
    if (!archivo.exists()) return;

    try (Scanner scanner = new Scanner(archivo)) {
        while (scanner.hasNextLine() && contadorClientes < mx) {
            String linea = scanner.nextLine();
            String[] datos = linea.split(";");
            if (datos.length == 5) {
                cliente c = new cliente(datos[0], datos[1], datos[2], datos[3], datos[4]);
                listaClientes[contadorClientes] = c;
                contadorClientes++;
            }
        }
    } catch (FileNotFoundException e) {
        System.err.println("Archivo no encontrado: " + e.getMessage());
    }
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
                }else if (event.getCode() == KeyCode.ENTER) {
    actualizarSugerenciaCorreo(texto);
    correo_cliente.end();
    event.consume();
                }
            }
                  
        });
    }
    
    private void actualizarSugerenciaCorreo (String textO){
        if (textO == null || !textO.contains("@")) return;
    
    String base = textO.split("@")[0];
    String nuevoCorreo = base + terminarcorreo.get(indicecorreo);
    
    correo_cliente.setText(nuevoCorreo);
    
    // IMPORTANTE: Mueve el cursor al final para evitar errores de selección
    javafx.application.Platform.runLater(() -> {
        correo_cliente.positionCaret(nuevoCorreo.length());
    });
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
           guardarClientesEnArchivo();
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
    
    //cambiar de paneles
    private void cargarVista(String fxml, String titulo, ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
