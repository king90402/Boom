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
import java.io.*;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.TextField;
/**
 *
 * @author all
 */
public class Controladora {
    //Tamaño maximo de los arreglos
    private static final int mx = 500;
    
    // Constantes para validaciones
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 30;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 100;
    private static final String CLIENTES_FILE = "clientes.txt";
    
    // Patron de validacion para correo (mejorado)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9]([a-zA-Z0-9._-]*[a-zA-Z0-9])?@[a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?(\\.[a-zA-Z]{2,})+$"
    );
    
    // dominios validos
    private static final String[] DOMINIOS_VALIDOS = {
        "gmail.com", "hotmail.com", "outlook.com", "outlook.es", "yahoo.com", 
        "yahoo.es", "icloud.com", "live.com", "msn.com", "protonmail.com",
        "mail.com", "aol.com", "zoho.com", "gmx.com", "yandex.com"
    };
    
    // Patron de validacion para ID
    private static final Pattern ID_PATTERN = Pattern.compile(
        "^[0-9]{6,12}$"
    );
    
    // Patron de validacion para telefono
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[0-9]{7,15}$"
    );
    
    private producto[] listaProductos;
    private cliente[] listaClientes;
    private admin[] listaAdmins;

    private int contadorProductos;
    private int contadorClientes;
    private int contadorAdmins;
    
    // Log in
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegistro;
    @FXML
    private TextField correO; 
    @FXML
    private PasswordField password;
    //Registro
    @FXML
    private TextField txtCorreoRegistro;
    @FXML
    private TextField txtTelefonoRegistro;
    @FXML
    private TextField txtUsuarioRegistro;
    @FXML
    private PasswordField txtPasswordRegistro;
    @FXML
    private TextField txtIdClienteRegistro;


//--------------------------------------------     Metodos de log in 
     @FXML
    private void irAPanelAdmin(ActionEvent event){
        // Navegar a la vista principal de admin
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Home.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Boom - Ofertas");
            stage.show();
            
            // Cerrar ventana actual
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Metodo ir a registro
    @FXML
    private void irARegistro(MouseEvent event){
        limpiarCamposRegistro();
        paneLogin.setVisible(false);
        paneRegistro.setVisible(true);
    }
    
    //Metodo ir a login desde registro
    @FXML
    private void irALogin(MouseEvent event){
        paneRegistro.setVisible(false);
        paneLogin.setVisible(true);
    }

    
    //Metodo ir a iniciar seccion
    @FXML
    private void iniciarSesion(ActionEvent event) {
        String user = correO.getText().trim();
        String contra = password.getText();
        
        // Validar campos vacios
        if (user.isEmpty() || contra.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Por favor ingrese su correo y contraseña.", 
                "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String userNormalizado = user.toLowerCase();
        // Verificacion de admin
        for (int i = 0; i < contadorAdmins; i++) {
            admin a = listaAdmins[i];
            if ((a.getCorreoAdmin().equalsIgnoreCase(userNormalizado) || 
                 a.getIdAdmin().equalsIgnoreCase(user)) && 
                a.getContrasenaAdmin().equals(contra)) {
                abrirVistaAdmin(event);
                return;
            }
        }
        // Inicio con cliente
        for (int i = 0; i < contadorClientes; i++) {
            cliente c = listaClientes[i];
            if ((c.getCorreoCliente().equalsIgnoreCase(userNormalizado) || 
                 c.getNombreCliente().equalsIgnoreCase(user) ||
                 c.getIdCliente().equalsIgnoreCase(user)) && 
                c.getContraseñaCliente().equals(contra)) {
                
                abrirVistaHome(event, c.getNombreCliente());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, 
            "Usuario o contraseña incorrectos.", 
            "Error de autenticación", JOptionPane.ERROR_MESSAGE);
    }
    
    @FXML
    private void Inicioseccion_admin(ActionEvent event) {
        iniciarSesion(event);
    }
 
    private void abrirVistaAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Administracion.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Panel de Administracion - Boom");
            stage.show();
            
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la vista de administración: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirVistaHome(ActionEvent event, String nombreUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Home.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Boom - Bienvenido " + nombreUsuario);
            stage.show();
            
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la vista principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Controladora() {
        listaProductos = new producto[mx];
        listaClientes = new cliente[mx];
        listaAdmins = new admin[mx];
        
        contadorProductos = 0;
        contadorClientes = 0;
        contadorAdmins = 0;
        
        cargarClientesDesdeArchivo();
        
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
    
//--------------------------------------------   Metodos correspondientes a la lista de productos 
    
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

//--------------------------------------------   Metodos correspondientes a la lista de clientes
    
    public boolean agregarCliente(cliente cliente) {
        if (contadorClientes < mx) {
            listaClientes[contadorClientes] = cliente;
            contadorClientes++;
            return true;
        }
        return false;
    }
    @FXML
    private void registrarCliente(ActionEvent event) {

        String correo = txtCorreoRegistro.getText().trim();
        String telefono = txtTelefonoRegistro.getText().trim();
        String nombreCompleto = txtUsuarioRegistro.getText().trim();
        String contraseña = txtPasswordRegistro.getText();
        String idcliente = txtIdClienteRegistro.getText().trim();
        
       //---------------------------   Validaciones para el registro de un cliente
       
        // Campos vacios 
        if (correo.isEmpty() || telefono.isEmpty() || nombreCompleto.isEmpty() || contraseña.isEmpty() || idcliente.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Error: Todos los campos son obligatorios para ingresar.\nPor favor complete todos los campos.", 
                "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // ============ VALIDACION DE NUMERO DE IDENTIFICACION ============
        // Formato del ID (solo numeros, entre 6 y 12 digitos)
        if (!validarFormatoId(idcliente)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El numero de identificacion no es valido.\nDebe contener solo numeros (entre 6 y 12 digitos).\nEjemplo: 1234567890", 
                "Identificacion invalida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar que el ID no este duplicado
        if (existeIdCliente(idcliente)) {
            JOptionPane.showMessageDialog(null, 
                "Error: Ya existe una cuenta registrada con este numero de identificacion.\nPor favor verifique sus datos o inicie sesion.", 
                "Identificacion ya registrada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Forma del correo 
        if (!validarCorreoCompleto(correo)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El formato del correo electronico no es valido.\nPor favor ingrese un correo valido.\nEjemplo: usuario@gmail.com\n\nNo se permiten:\n- Caracteres especiales consecutivos\n- Dominios invalidos\n- Correos muy cortos o muy largos", 
                "Correo invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validacion de correo duplicado
        if (existeCorreoCliente(correo)) {
            JOptionPane.showMessageDialog(null, 
                "Error: Ya existe una cuenta registrada con este correo electronico.\nPor favor use otro correo o inicie sesion.", 
                "Correo ya en uso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (existeCorreoAdmin(correo)) {
            JOptionPane.showMessageDialog(null, 
                "Error: Ya existe una cuenta registrada con este correo electronico.\nPor favor use otro correo o inicie sesion.", 
                "Correo ya en uso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar longitud del nombre
        if (nombreCompleto.length() < MIN_NAME_LENGTH || nombreCompleto.length() > MAX_NAME_LENGTH) {
            JOptionPane.showMessageDialog(null, 
                "Error: El nombre completo debe tener entre " + MIN_NAME_LENGTH + 
                " y " + MAX_NAME_LENGTH + " caracteres.", 
                "Nombre invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarNombreCompleto(nombreCompleto)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El nombre completo solo puede contener letras y espacios.", 
                "Nombre invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String telefonoLimpio = telefono.replaceAll("[\\s-]", "");
        if (!validarFormatoTelefono(telefonoLimpio)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El formato del telefono no es valido.\nDebe contener entre 7 y 15 digitos.\nEjemplo: +573001234567", 
                "Telefono invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (contraseña.length() < MIN_PASSWORD_LENGTH || contraseña.length() > MAX_PASSWORD_LENGTH) {
            JOptionPane.showMessageDialog(null, 
                "Error: La contraseña debe tener entre " + MIN_PASSWORD_LENGTH + 
                " y " + MAX_PASSWORD_LENGTH + " caracteres.", 
                "Contraseña invalida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (contraseña.contains(" ")) {
            JOptionPane.showMessageDialog(null, 
                "Error: La contraseña no puede contener espacios.", 
                "Contraseña invalida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        cliente nuevoCliente = new cliente(
            nombreCompleto,                      
            idcliente,                            
            telefonoLimpio,                      
            correo.toLowerCase(),                
            contraseña,                           
            "Agregue una direccion por favor." 
        );
        
        // Agregar a la lista
        if (agregarCliente(nuevoCliente)) {
            if (guardarClienteEnArchivo(nuevoCliente)) {
                JOptionPane.showMessageDialog(null, 
                    "Registro exitoso!\nBienvenido " + nombreCompleto + ".\nAhora puede iniciar sesion.", 
                    "Registro completado", JOptionPane.INFORMATION_MESSAGE);

                limpiarCamposRegistro();
                paneRegistro.setVisible(false);
                paneLogin.setVisible(true);

                correO.setText(correo.toLowerCase());
                password.requestFocus();
            } else {
                eliminarCliente(contadorClientes - 1);
                JOptionPane.showMessageDialog(null, 
                    "Error al guardar el registro. Por favor intente de nuevo.", 
                    "Error de sistema", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error: No se pudo completar el registro.\nEl sistema esta lleno.", 
                "Error de sistema", JOptionPane.ERROR_MESSAGE);
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
            if (listaClientes[i].getCorreoCliente().equalsIgnoreCase(correoCliente)) {
                return true;
            }
        }
        return false;
    }
    
    // Obtener la cantidad de clientes
    public int getCantidadClientes() {
        return contadorClientes;
    }
    
//--------------------------------------------   Metodos para la lista de admins
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

//--------------------------------------------   Metodos auxiliares y validaciones aleatorias
    
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
    
    // Verificar si existe un usuario con ese nombre
    public boolean existeUsuarioCliente(String nombreUsuario) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getNombreCliente().equalsIgnoreCase(nombreUsuario) ||
                listaClientes[i].getIdCliente().equalsIgnoreCase(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean validarFormatoCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(correo).matches();
    }
    
    private boolean validarFormatoTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) return false;
        return PHONE_PATTERN.matcher(telefono).matches();
    }
    
    private boolean validarFormatoId(String id) {
        return ID_PATTERN.matcher(id).matches();
    }
    
    // Verificar si el ID ya existe
    public boolean existeIdCliente(String idCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(idCliente)) {
                return true;
            }
        }
        return false;
    }
    
    // Validar formato de correo
    private boolean validarCorreoCompleto(String correo) {
        if (!EMAIL_PATTERN.matcher(correo).matches()) {
            return false;
        }

        if (correo.contains("..") || correo.contains("--") || correo.contains("__")) {
            return false;
        }

        if (correo.length() < 6 || correo.length() > 100) {
            return false;
        }

        String[] partes = correo.split("@");
        if (partes.length != 2) {
            return false;
        }
        
        String dominio = partes[1].toLowerCase();

        if (!dominio.contains(".")) {
            return false;
        }

        String[] partesdominio = dominio.split("\\.");
        String extension = partesdominio[partesdominio.length - 1];
        if (extension.length() < 2 || extension.length() > 6) {
            return false;
        }
        
        return true;
    }
 
    private boolean validarNombreCompleto(String nombre) {
        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'\\s]+$");
    }
    
    private boolean validarUsuario(String usuario) {
        if (usuario == null || usuario.isEmpty()) return false;
        return usuario.matches("^[a-zA-Z0-9_]+$");
    }
    
    
    //Persistencia archivo clientes
    private boolean guardarClienteEnArchivo(cliente c) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTES_FILE, true))) {
            // Formato: nombre;idCliente;telefono;correo;contrasena;direccion (6 campos)
            String linea = c.getNombreCliente() + ";" + 
                          c.getIdCliente() + ";" +
                          c.getTelefonoCliente() + ";" + 
                          c.getCorreoCliente() + ";" + 
                          c.getContraseñaCliente() + ";" + 
                          c.getDireccionCliente();
            writer.write(linea);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar cliente en archivo: " + e.getMessage());
            return false;
        }
    }
    
     private void cargarClientesDesdeArchivo() {
        File archivo = new File(CLIENTES_FILE);
        if (!archivo.exists()) {
            System.out.println("Archivo de clientes no existe. Se creara al registrar el primer cliente.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] datos = linea.split(";");
                
                // Verificar si tiene 6 campos (formato nuevo con IdCliente)
                if (datos.length >= 6) {
                    cliente c = new cliente(
                        datos[0].trim(),  // NombreCliente
                        datos[1].trim(),  // IdCliente
                        datos[2].trim(),  // TelefonoCliente
                        datos[3].trim(),  // CorreoCliente
                        datos[4].trim(),  // ContraseñaCliente
                        datos[5].trim()   // DireccionCliente
                    );
                    
                    if (!existeCorreoCliente(c.getCorreoCliente())) {
                        if (contadorClientes < mx) {
                            listaClientes[contadorClientes] = c;
                            contadorClientes++;
                        }
                    }
                }
                // Compatibilidad con formato antiguo de 5 campos (sin IdCliente separado)
                else if (datos.length == 5) {
                    cliente c = new cliente(
                        datos[0].trim(),  // NombreCliente
                        datos[0].trim(),  // IdCliente (usa el mismo nombre)
                        datos[1].trim(),  // TelefonoCliente
                        datos[2].trim(),  // CorreoCliente
                        datos[3].trim(),  // ContraseñaCliente
                        datos[4].trim()   // DireccionCliente
                    );
                    
                    if (!existeCorreoCliente(c.getCorreoCliente())) {
                        if (contadorClientes < mx) {
                            listaClientes[contadorClientes] = c;
                            contadorClientes++;
                        }
                    }
                }
            }
            System.out.println("Clientes cargados desde archivo: " + contadorClientes);
        } catch (IOException e) {
            System.err.println("Error al cargar clientes desde archivo: " + e.getMessage());
        }
    }
    
    //Limpiar los campos 
    private void limpiarCamposRegistro() {
        if (txtCorreoRegistro != null) txtCorreoRegistro.clear();
        if (txtTelefonoRegistro != null) txtTelefonoRegistro.clear();
        if (txtUsuarioRegistro != null) txtUsuarioRegistro.clear();
        if (txtPasswordRegistro != null) txtPasswordRegistro.clear();
    }
    
    private void limpiarCamposLogin() {
        if (correO != null) correO.clear();
        if (password != null) password.clear();
    }

    
    @FXML
    private void irACategoriaOfertas(ActionEvent event) {

        System.out.println("Navegando a categoria: Ofertas");

    }
    
    @FXML
    private void irACategoriaTecnologia(ActionEvent event) {
        System.out.println("Navegando a categoria: Tecnologia");
    }
    
    @FXML
    private void irACategoriaHogar(ActionEvent event) {
        System.out.println("Navegando a categoria: Hogar");
    }
    
    @FXML
    private void irACategoriaDeportes(ActionEvent event) {
        System.out.println("Navegando a categoria: Deportes");
    }
    
    @FXML
    private void irACategoriaModa(ActionEvent event) {
        System.out.println("Navegando a categoria: Moda y Belleza");
    }
    
}
