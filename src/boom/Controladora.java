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
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.control.MenuItem;
import java.util.ArrayList;
/**
 *
 * @author alejo
 */
public class Controladora {
    
    private ListaProductos ListaProductos;
    
    //Tamaño maximo de los arreglos
    private static final int MAX_USUARIOS = 500;
    private static final int MAX_PRODUCTOS = 500;
    
    // Constantes para validaciones
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final String USUARIOS_FILE = "usuarios.txt";
    
    // Patron de validacion para correo
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^(?=.{6,254}$)" +                    
        "(?=.{1,64}@)" +                    
        "(?!.*\\.\\.)" +                     
        "(?!.*__)" +                         
        "(?!.*--)" +                         
        "[A-Za-z0-9]" +                      
        "[A-Za-z0-9._%+-]{0,62}" +           
        "[A-Za-z0-9]" +                      
        "@" +
        "(?=.{1,255}$)" +
        "(?:[A-Za-z0-9]" +                  
        "(?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?" +
        "\\.)+" +
        "[A-Za-z]{2,63}$" 
    );
    
    // Dominios validos
    private static final String[] DOMINIOS_VALIDOS = {
        "gmail.com", "hotmail.com", "outlook.com", "outlook.es", "yahoo.com", 
        "yahoo.es", "icloud.com", "mail.com", "correo.unicordoba.edu.co" 
    };
    
    // Patron de validacion para ID
    private static final Pattern ID_PATTERN = Pattern.compile(
        "^[0-9]{7,12}$"
    );
    
    // Patron de validacion para telefono
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[0-9]{10,15}$"
    );
    
    // Patrón para nombres y apellidos
    private static final Pattern NOMBRE_PATTERN = Pattern.compile(
        "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'\\s]+$"
    );
    
    //Inicializacion de Datos Compartidos pot Controladora en las Vistas para evitar conflictos
    private static producto[] listaProductos = new producto[MAX_PRODUCTOS];
    private static Usuario[] listaUsuarios = new Usuario[MAX_USUARIOS];
    private static int contadorProductos = 0;
    private static int contadorUsuarios = 0;
    private static boolean datosInicializados = false;
    
    private Usuario usuarioActual;
    
    
    //Paneles
    @FXML private Pane paneLogin;
    @FXML private Pane paneRegistro;
    @FXML private Pane paneRegistroFinal;
    @FXML private Pane paneInicioHome;
    @FXML private Pane panePerfilHome;
    @FXML private Pane paneCarritoHome;

    @FXML private Pane paneFavoritosPerfilHome;
    @FXML private Pane panePedidosPerfilHome;
    @FXML private Pane paneMiCuentaPerfilHome;
    
    @FXML private Pane panePerfilAdmin;
    @FXML private Pane paneEstadisticasAdmin;
    @FXML private Pane paneRestaStockAdmin;
    @FXML private Pane paneInventarioAdmin;
    @FXML private Pane panePedidosAdmin;
    @FXML private Pane paneIngrePersoAdmin;
    
    @FXML private Pane paneRestaNuevo_Producto;
    @FXML private Pane paneEditarInfoAdmin;
    @FXML private Pane paneRestaNuevo_ProductoPaso1;
    @FXML private Pane paneRestaNuevo_ProductoPaso2;
    @FXML private Pane paneRestaNuevo_ProductoPaso3;
    
    //Botones Home
    @FXML private ToggleButton tbtnPedidosPerfilHome;
    @FXML private ToggleButton tbtnFavoritosPerfilHome;
    @FXML private ToggleButton tbtnMiCuentaPerfilHome;
    
    @FXML private ToggleGroup BarraUsuario;
    
    //Barra Home
    @FXML private ToggleButton tbtnOfertasHome;
    @FXML private ToggleButton tbtnTecnologiaHome;
    @FXML private ToggleButton tbtnHogarHome;
    @FXML private ToggleButton tbtnDeportesHome;
    @FXML private ToggleButton tbtnModaBellezaHome;
    
    @FXML private ToggleGroup MenuHome;
    
    //Botones Administración
    @FXML private ToggleButton tbtnPerfilAdmin;
    @FXML private ToggleButton tbtnEstadisticasAdmin;
    @FXML private ToggleButton tbtnRestaStockAdmin;
    @FXML private ToggleButton tbtnInventarioAdmin;
    @FXML private ToggleButton tbtnPedidosAdmin;
    @FXML private ToggleButton tbtnIngrePersoAdmin;
    
    @FXML private ToggleGroup BarraAdmin;
    
    //Login
    @FXML private TextField      txtCorreoLogin;
    @FXML private PasswordField  txtPasswordLogin;

    //Registro Paso 1
    @FXML private TextField txtCorreoRegistro;
    @FXML private TextField txtNombreRegistro;
    @FXML private TextField txtApellidoRegistro;
    @FXML private TextField txtIdClienteRegistro;
    @FXML private TextField txtFechaRegistro;

    //Registro Paso 2
    @FXML private TextField        txtCelularRegistro;
    @FXML private ComboBox<String> comboDepartamento;
    @FXML private ComboBox<String> comboCiudad;
    @FXML private TextField        txtDireccionRegistro;
    @FXML private PasswordField    txtPasswordRegistro;

    //Catalogo
    @FXML private FlowPane contenedorProductosHome;
    
    //Label 
    @FXML private Text lblDireccionHome;
    @FXML private Text lblUsuarioHome;
    @FXML private Text lblNombrePerfilHome;
    @FXML private Text lblAbreNombrePerfilHome;
    @FXML private Text lblCorreoPerfilHome;
    @FXML private Text lblRolPerfilHome;
    
    @FXML private Text lblNombre2PerfilHome;
    @FXML private Text lblCorreo2PerfilHome;
    @FXML private Text lblCelularPerfilHome;
    @FXML private Text lblDocumentoPerfilHome;
    @FXML private Text lblFechaPerfilHome;
    @FXML private Text lblDireccionPerfilHome;
    
    @FXML private Text lblUsuarioAdmin;
    @FXML private Text lblNombrePerfilAdmin;
    @FXML private Text lblAbreNombrePerfilAdmin;
    @FXML private Text lblCorreoPerfilAdmin;
    @FXML private Text lblRolPerfilAdmin;
    
    @FXML private Text lblNombre2PerfilAdmin;
    @FXML private Text lblCorreo2PerfilAdmin;
    @FXML private Text lblCelularPerfilAdmin;
    @FXML private Text lblDocumentoPerfilAdmin;
    @FXML private Text lblFechaPerfilAdmin;
    @FXML private Text lblDireccionPerfilAdmin;
    @FXML private Text lblRol2PerfilAdmin;
    
    
    
    //Constructor
    public Controladora() {
        ListaProductos = new ListaProductos();
        
        if (!datosInicializados) {
            datosInicializados = true;
            cargarUsuariosDesdeArchivo();

        }
    }
   
    //Inicializacion 
    @FXML
    public void initialize() {
        if (paneLogin        != null) paneLogin.setVisible(true);
        if (paneRegistro     != null) paneRegistro.setVisible(false);
        if (paneRegistroFinal!= null) paneRegistroFinal.setVisible(false);

        if (comboDepartamento != null) {
            comboDepartamento.getItems().addAll(DEPTOS.keySet());
            
            comboDepartamento.setOnAction(event -> {
                String depto = comboDepartamento.getValue();
            if (depto != null) {
            comboCiudad.getItems().clear();
            comboCiudad.getItems().addAll(DEPTOS.get(depto));
            comboCiudad.setValue(null);
            }
        });
            
            comboDepartamento.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case ENTER -> {
                        Platform.runLater(() -> {
                            comboCiudad.requestFocus();
                            comboCiudad.show();
                        });
                    }
                }
            });
            
        comboCiudad.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER -> {
                    Platform.runLater(() -> {
                        comboCiudad.hide();
                        txtDireccionRegistro.requestFocus();
                    });
                }
            }
        });
    }
        
    inicializarToggleGroups();
    mostrarPaneAdmin(panePerfilAdmin);

    }
    
    private void inicializarToggleGroups() {
        BarraUsuario = new ToggleGroup();
        if (tbtnFavoritosPerfilHome != null) tbtnFavoritosPerfilHome.setToggleGroup(BarraUsuario);
        if (tbtnPedidosPerfilHome != null) tbtnPedidosPerfilHome.setToggleGroup(BarraUsuario);
        if (tbtnMiCuentaPerfilHome != null) tbtnMiCuentaPerfilHome.setToggleGroup(BarraUsuario);
        
        BarraAdmin = new ToggleGroup();
        if (tbtnPerfilAdmin != null) tbtnPerfilAdmin.setToggleGroup(BarraAdmin);
        if (tbtnEstadisticasAdmin != null) tbtnEstadisticasAdmin.setToggleGroup(BarraAdmin);
        if (tbtnRestaStockAdmin != null) tbtnRestaStockAdmin.setToggleGroup(BarraAdmin);
        if (tbtnInventarioAdmin != null) tbtnInventarioAdmin.setToggleGroup(BarraAdmin);
        if (tbtnPedidosAdmin != null) tbtnPedidosAdmin.setToggleGroup(BarraAdmin);
        if (tbtnIngrePersoAdmin != null) tbtnIngrePersoAdmin.setToggleGroup(BarraAdmin);
        
        MenuHome = new ToggleGroup();
        if (tbtnOfertasHome != null) tbtnOfertasHome.setToggleGroup(MenuHome);
        if (tbtnTecnologiaHome != null) tbtnTecnologiaHome.setToggleGroup(MenuHome);
        if (tbtnHogarHome != null) tbtnHogarHome.setToggleGroup(MenuHome);
        if (tbtnDeportesHome != null) tbtnDeportesHome.setToggleGroup(MenuHome);
        if (tbtnModaBellezaHome != null) tbtnModaBellezaHome.setToggleGroup(MenuHome);
    }
    
    //Autenticación
    
    //Metodo para traer UsuarioActual y poder Intanciarlo en otras Vistas
    public void setUsuarioActual(Usuario u) {
        this.usuarioActual = u;
        cargarDatosUsuario();
    }
  
    //Metodo para iniciar seccion
    @FXML
    private void iniciarSesion(ActionEvent event) {
        
        String user = txtCorreoLogin.getText().trim();
        String contra = txtPasswordLogin.getText();
        
        // Validar campos vacios
        if (user.isEmpty() || contra.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Por favor ingrese su correo y contraseña.", 
                "Campos vacios");
            return;
        }

        String userNormalizado = user.toLowerCase();
        
        // Verificacion y asignacion por rol
        for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = listaUsuarios[i];
            if ((u.getCorreo().equalsIgnoreCase(userNormalizado) || 
                u.getNombre().equalsIgnoreCase(user) ||
                u.getId().equalsIgnoreCase(user)) && 
                u.getContraseña().equals(contra)) {
                
                usuarioActual = u;
                
                if (u.esAdmin()) {
                    abrirVistaAdmin(event);
                } else {
                    abrirVistaHome(event);
                }
                return;
            }
        }
        mostrarAlerta(Alert.AlertType.ERROR, 
            "Usuario o contraseña incorrectos.", 
            "Error de autenticación");
    }
    
    private void abrirVistaAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Administracion.fxml"));
            Parent root = loader.load();
            
            Controladora adminController = loader.getController();
            adminController.setUsuarioActual(usuarioActual);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Panel de Administracion - Boom");
            stage.show();
            
            cerrarVentanaActual(event);
            
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Error al cargar la vista de administración: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void abrirVistaHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Home.fxml"));
            Parent root = loader.load();
            
            Controladora homeController = loader.getController();
            homeController.setUsuarioActual(usuarioActual);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Boom - Bienvenido " + usuarioActual.getNombre());
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();
            
            cerrarVentanaActual(event);
            
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cargar la vista principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cerrarVentanaActual(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }
    
    //Navegacion Paneles
    
    @FXML private void irARegistro(MouseEvent event) {
        limpiarCamposRegistro();
        mostrarPane(paneRegistro, paneLogin, paneRegistroFinal);
    }

    @FXML private void irALogin(MouseEvent event) {
        limpiarCamposLogin();
        mostrarPane(paneLogin, paneRegistro, paneRegistroFinal);
    }

    @FXML private void irAPaso1(MouseEvent event) {
        mostrarPane(paneRegistro, paneRegistroFinal, paneLogin);
        if (txtCorreoRegistro != null) txtCorreoRegistro.requestFocus();
    }
    
    private void mostrarPane(Pane mostrar, Pane... ocultar) {
        for (Pane p : ocultar) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
        if (mostrar != null) {
            mostrar.setVisible(true);
            mostrar.setManaged(true);
        }
    }
    
    private void ocultarTodosLosPadres() {
        Pane[] panes = {paneInicioHome, panePerfilHome, paneCarritoHome};
        for (Pane p : panes) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }

    public void mostrarInicio() {
        ocultarTodosLosPadres();
        if (paneInicioHome != null) {
            paneInicioHome.setVisible(true);
            paneInicioHome.setManaged(true);
        }
    }

    public void mostrarPerfil() {
        ocultarTodosLosPadres();
        if (panePerfilHome != null) {
            panePerfilHome.setVisible(true);
            panePerfilHome.setManaged(true);
        }
        mostrarMiCuentaPerfil();
    }

    public void mostrarCarrito() {
        ocultarTodosLosPadres();
        if (paneCarritoHome != null) {
            paneCarritoHome.setVisible(true);
            paneCarritoHome.setManaged(true);
        }
    }

    private void ocultarSubPanesPerfil() {
        Pane[] panes = {paneFavoritosPerfilHome, panePedidosPerfilHome, paneMiCuentaPerfilHome};
        for (Pane p : panes) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }

    public void mostrarFavoritosPerfilInicio() {
        ocultarTodosLosPadres();
        if (panePerfilHome != null) {
            panePerfilHome.setVisible(true);
            panePerfilHome.setManaged(true);
        }
        ocultarSubPanesPerfil();
        if (paneFavoritosPerfilHome != null) {
            paneFavoritosPerfilHome.setVisible(true);
            paneFavoritosPerfilHome.setManaged(true);
        }
        if (tbtnFavoritosPerfilHome != null) tbtnFavoritosPerfilHome.setSelected(true);
    }
    
    public void mostrarFavoritosPerfil() {
        ocultarSubPanesPerfil();
        if (paneFavoritosPerfilHome != null) {
            paneFavoritosPerfilHome.setVisible(true);
            paneFavoritosPerfilHome.setManaged(true);
        }
    }

    public void mostrarPedidosPerfil() {
        ocultarSubPanesPerfil();
        if (panePedidosPerfilHome != null) {
            panePedidosPerfilHome.setVisible(true);
            panePedidosPerfilHome.setManaged(true);
        }
        if (tbtnPedidosPerfilHome != null) tbtnPedidosPerfilHome.setSelected(true);
    }

    public void mostrarMiCuentaPerfil() {
        ocultarSubPanesPerfil();
        if (paneMiCuentaPerfilHome != null) {
            paneMiCuentaPerfilHome.setVisible(true);
            paneMiCuentaPerfilHome.setManaged(true);
        }
        if (tbtnMiCuentaPerfilHome != null) tbtnMiCuentaPerfilHome.setSelected(true);
    }

    private void ocultarPanesAdmin() {
        Pane[] panes = {
            panePerfilAdmin, paneEstadisticasAdmin, paneRestaStockAdmin,
            paneInventarioAdmin, panePedidosAdmin, paneIngrePersoAdmin,
            paneEditarInfoAdmin, paneRestaNuevo_Producto
        };
        for (Pane p : panes) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }
    
    private void mostrarPaneAdmin(Pane paneMostrar) {
        ocultarPanesAdmin();
        if (paneMostrar != null) {
            paneMostrar.setVisible(true);
            paneMostrar.setManaged(true);
        }
    }
    
    @FXML private void irPerfilAdmin() { mostrarPaneAdmin(panePerfilAdmin); }
    @FXML private void irEstadisticasAdmin() { mostrarPaneAdmin(paneEstadisticasAdmin); }
    @FXML private void irRestaStockAdmin() { mostrarPaneAdmin(paneRestaStockAdmin); }
    @FXML private void irInventarioAdmin() { mostrarPaneAdmin(paneInventarioAdmin); }
    @FXML private void irPedidosAdmin() { mostrarPaneAdmin(panePedidosAdmin); }
    @FXML private void irIngresoPersonalAdmin() { mostrarPaneAdmin(paneIngrePersoAdmin); }
    @FXML private void irEditarInfoAdmin() { mostrarPaneAdmin(paneEditarInfoAdmin); }
    
    private void ocultarPasosNuevoProducto() {
        Pane[] pasos = {paneRestaNuevo_ProductoPaso1, paneRestaNuevo_ProductoPaso2, paneRestaNuevo_ProductoPaso3};
        for (Pane p : pasos) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }
    
    private void mostrarPasoNuevoProducto(Pane pasoMostrar) {
        ocultarPasosNuevoProducto();
        if (pasoMostrar != null) {
            pasoMostrar.setVisible(true);
            pasoMostrar.setManaged(true);
        }
    }
    
    @FXML private void irPaso1NuevoProducto() {
        mostrarPaneAdmin(paneRestaNuevo_Producto);
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso1);
    }

    @FXML private void irPaso2NuevoProducto() { mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso2); }
    @FXML private void irPaso3NuevoProducto() { mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso3); }
    
    //Navegacion Basica Teclado
    
    @FXML private void irAContraseñaLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtPasswordLogin.requestFocus();
        }
    }
    
    @FXML private void irANombreRegistro(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtNombreRegistro.requestFocus();
        }   
    }
    
    @FXML private void irAApellidoRegistro(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtApellidoRegistro.requestFocus();
        }
    }
    
    @FXML private void irADocumentoRegistro(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtIdClienteRegistro.requestFocus();
        }
    }
    
    @FXML private void irAFechaRegistro(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtFechaRegistro.requestFocus();
        }
    }
        
    @FXML private void irADepartamentoRegistro(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            comboDepartamento.show();
            comboDepartamento.requestFocus();
        }
    }
   
    @FXML private void irAContraseñaRegistro(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtPasswordRegistro.requestFocus();
        }
    }
    
    //Carga datos usuario
    private void cargarDatosUsuario() {
        if (usuarioActual == null) return;
        
        //Home

        if (lblUsuarioHome != null) {
            lblUsuarioHome.setText(usuarioActual.getNombre());
        }
        
        if (lblDireccionHome != null) {
            lblDireccionHome.setText(usuarioActual.getDireccion());
        }
        
        if (lblNombrePerfilHome != null) {
            lblNombrePerfilHome.setText(usuarioActual.getNombreCompleto());
        }

        if (lblCorreoPerfilHome != null) {
            lblCorreoPerfilHome.setText(usuarioActual.getCorreo());
        }

        if (lblAbreNombrePerfilHome != null) {
            lblAbreNombrePerfilHome.setText(usuarioActual.getIniciales());
        }
        
        if (lblRolPerfilHome != null) {
            lblRolPerfilHome.setText(usuarioActual.getRol().name());
        }
        
        if (lblNombre2PerfilHome != null) {
            lblNombre2PerfilHome.setText(usuarioActual.getNombre());
        }
        
        if (lblCorreo2PerfilHome != null) {
            lblCorreo2PerfilHome.setText(usuarioActual.getCorreo());
        }
        
        if (lblCelularPerfilHome != null) {
            lblCelularPerfilHome.setText(usuarioActual.getTelefono());
        }

        if (lblDocumentoPerfilHome != null) {
            lblDocumentoPerfilHome.setText(usuarioActual.getId());
        }

        if (lblFechaPerfilHome != null) {
            lblFechaPerfilHome.setText(usuarioActual.getFechaNacimiento());
        }
        
        if (lblDireccionPerfilHome != null) {
            lblDireccionPerfilHome.setText(usuarioActual.getDireccionCompleta());
        }
        
        //Administradora
        
        if (lblUsuarioAdmin != null) {
            lblUsuarioAdmin.setText(usuarioActual.getNombre());
        }
        
        if (lblNombrePerfilAdmin != null) {
            lblNombrePerfilAdmin.setText(usuarioActual.getNombreCompleto());
        }

        if (lblCorreoPerfilAdmin != null) {
            lblCorreoPerfilAdmin.setText(usuarioActual.getCorreo());
        }

        if (lblAbreNombrePerfilAdmin != null) {
            lblAbreNombrePerfilAdmin.setText(usuarioActual.getIniciales());
        }
        
        if (lblRolPerfilAdmin != null) {
            lblRolPerfilAdmin.setText(usuarioActual.getRol().name());
        }
        
        if (lblNombre2PerfilAdmin != null) {
            lblNombre2PerfilAdmin.setText(usuarioActual.getNombreCompleto());
        }
        
        if (lblCorreo2PerfilAdmin != null) {
            lblCorreo2PerfilAdmin.setText(usuarioActual.getCorreo());
        }
        
        if (lblCelularPerfilAdmin != null) {
            lblCelularPerfilAdmin.setText(usuarioActual.getTelefono());
        }

        if (lblDocumentoPerfilAdmin != null) {
            lblDocumentoPerfilAdmin.setText(usuarioActual.getId());
        }

        if (lblFechaPerfilAdmin != null) {
            lblFechaPerfilAdmin.setText(usuarioActual.getFechaNacimiento());
        }
        
        if (lblDireccionPerfilAdmin != null) {
            lblDireccionPerfilAdmin.setText(usuarioActual.getDireccionCompleta());
        }
        
        if (lblRol2PerfilAdmin != null) {
            lblRol2PerfilAdmin.setText(usuarioActual.getRol().name());
        }
    }
    
    //Registro Primer Paso
    @FXML
    private void irPaso2(ActionEvent event) {
        
        String correo   = txtCorreoRegistro.getText().trim();
        String nombre   = txtNombreRegistro.getText().trim();
        String apellido = txtApellidoRegistro.getText().trim();
        String id       = txtIdClienteRegistro.getText().trim();
        String fecha    = txtFechaRegistro.getText().trim();
        
       //Validaciones para el registro de un clienteBoom
       
        // Campos vacios 
        if (correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || id.isEmpty() || fecha.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Error: Todos los campos son obligatorios para ingresar.\nPor favor complete todos los campos.", 
                "Campos vacios");
            return;
        }
        
        // Formato del ID (solo numeros, entre 7 y 12 digitos)
        if (!validarFormatoId(id)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Error: El numero de identificacion no es valido.\nDebe contener solo numeros (entre 7 y 12 digitos).\nEjemplo: 1003345643", 
                "Identificacion invalida");
            return;
        }
        
        // Verificar que el ID no este duplicado
        if (existeIdUsuario(id)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Error: Ya existe una cuenta registrada con este numero de identificacion.\nPor favor verifique sus datos o inicie sesion.", 
                "Identificacion ya registrada");
            return;
        }
        
        // Forma del correo 
        if (!validarCorreoCompleto(correo)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Error: El formato del correo electronico no es valido.\nPor favor ingrese un correo valido.\nEjemplo: usuario@gmail.com\n\nNo se permiten:\n- Caracteres especiales consecutivos\n- Dominios invalidos\n- Correos muy cortos o muy largos", 
                "Correo invalido");
            return;
        }
        
        // Validacion de correo duplicado
        if (existeCorreoUsuario(correo)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Error: Ya existe una cuenta registrada con este correo electrónico.\nPor favor use otro correo o inicie sesión.", 
                "Correo ya en uso");
            return;
        }
        
        // Validacion de nombre y apellido
        if (!validarNombreOApellido(nombre)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Error: El nombre solo puede contener letras y espacios.", 
                "Nombre inválido");
            return;
        }
        
        if (!validarNombreOApellido(apellido)) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Error: El apellido solo puede contener letras y espacios.", 
                "Apellido inválido");
            return;
        }
        
        //Validacion de fecha
        if (!validarFechaYEdad(fecha)) {
        mostrarAlerta(Alert.AlertType.WARNING, "Fecha invalida", 
            "La fecha debe ser real (dd/MM/yyyy) y el usuario debe tener entre 18 y 120 años.");
        return;
    }

        if (paneRegistro     != null) paneRegistro.setVisible(false);
        if (paneRegistroFinal!= null) paneRegistroFinal.setVisible(true);
        if (paneLogin        != null) paneLogin.setVisible(false);
        
        txtCelularRegistro.requestFocus();

    }
    
    //Registro Paso Final
    @FXML
    private void registrarCliente(ActionEvent event) {
        
        String correo          = txtCorreoRegistro.getText().trim();
        String nombre          = txtNombreRegistro.getText().trim();
        String apellido        = txtApellidoRegistro.getText().trim();
        String id              = txtIdClienteRegistro.getText().trim();
        String fechaNacimiento = txtFechaRegistro.getText().trim();

        String celular     = txtCelularRegistro.getText().trim();
        String departamento = comboDepartamento.getValue();
        String ciudad       = comboCiudad.getValue();
        String direccion    = txtDireccionRegistro.getText().trim();
        String contraseña   = txtPasswordRegistro.getText();

        if (celular.isEmpty() || departamento == null || ciudad == null ||
            direccion.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Todos los campos son obligatorios.\nSeleccione Departamento y Ciudad.",
                "Campos vacíos");
            return;
        }

        String celularLimpio = celular.replaceAll("[^0-9]", "");
        if (!validarFormatoTelefono(celularLimpio)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Error: El formato del celular no es valido.\nDebe contener entre 7 y 15 digitos.\nEjemplo: +573001234567", 
                "Celular invalido");
            return;
        }

        if (contraseña.length() < MIN_PASSWORD_LENGTH || contraseña.length() > MAX_PASSWORD_LENGTH) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Error: La contraseña debe tener entre " + MIN_PASSWORD_LENGTH + 
                " y " + MAX_PASSWORD_LENGTH + " caracteres.", 
                "Contraseña invalida");
            return;
        }
        if (contraseña.contains(" ")) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Error: La contraseña no puede contener espacios.", 
                "Contraseña invalida");
            return;
        }

        Usuario nuevo = new Usuario(
            correo.toLowerCase(),
            nombre, 
            apellido,
            id,
            fechaNacimiento,
            celularLimpio,   
            departamento,
            ciudad,
            direccion,
            contraseña
        ); 

        if (agregarUsuario(nuevo)) {
            if (guardarUsuarioEnArchivo(nuevo)) {
                mostrarAlerta(Alert.AlertType.INFORMATION,
                    "¡Registro exitoso!\nBienvenido " + nuevo.getNombreCompleto() + ".\nAhora puede iniciar sesión.",
                    "Registro completado");
                limpiarCamposRegistro();
                if (paneRegistroFinal!= null) paneRegistroFinal.setVisible(false);
                if (paneRegistro     != null) paneRegistro.setVisible(false);
                if (paneLogin        != null) paneLogin.setVisible(true);
                if (txtCorreoLogin   != null) txtCorreoLogin.setText(correo.toLowerCase());
                if (txtPasswordLogin != null) txtPasswordLogin.requestFocus();
            } else {
                eliminarUsuario(contadorUsuarios - 1);
                mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar el registro. Intente de nuevo.",
                    "Error de sistema");
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error: Sistema lleno.",
                "Error de sistema");
        }
    }
    
    //----------------------------------------------------------- Metodos correspondientes a la lista de productos 
    
    // Agregar producto
    public void agregarProducto(String nombre, int cantidad, double precio, String estado, String marca, String categoria, String imagen) {
        String id = ListaProductos.obtenerSiguienteId();
        producto nuevoProducto = new producto(nombre, id, cantidad, precio, estado, marca, categoria, imagen);
        ListaProductos.insertarAlFinal(nuevoProducto);
    }
    
    // Eliminar producto
    public boolean eliminarProducto(String id) {
        return ListaProductos.eliminarPorId(id);
    }
    
    // Obtener todos los productos
    public ArrayList<producto> obtenerProductos() {
        return ListaProductos.obtenerTodos();
    }
    
    // Buscar producto
    public producto buscarProducto(String id) {
        return ListaProductos.buscarPorId(id);
    }


    //----------------------------------------------------------- Metodos correspondientes a la lista de usuarios
    
    // Agregar un usuario
    public boolean agregarUsuario(Usuario usuario) {
        if (contadorUsuarios < MAX_USUARIOS) {
            listaUsuarios[contadorUsuarios++] = usuario;
            return true;
        }
        return false;
    }
    
    // Eliminar un usuario por su posicion 
   public boolean eliminarUsuario(int pos) {
        if (pos >= 0 && pos < contadorUsuarios) {
            for (int i = pos; i < contadorUsuarios - 1; i++) {
                listaUsuarios[i] = listaUsuarios[i + 1];
            }
            listaUsuarios[--contadorUsuarios] = null;
            return true;
        }
        return false;
    }
    
    // Eliminar un usuario por su ID
    public boolean eliminarUsuarioPorId(String id) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getId().equals(id)) {
                return eliminarUsuario(i);
            }
        }
        return false;
    }
    
    // Obtener un usuario por su posicion
    public Usuario obtenerUsuario(int pos) {
        if (pos >= 0 && pos < contadorUsuarios) {
            return listaUsuarios[pos];
        }
        return null;
    }
    
    // Buscar un usuario por su ID
    public Usuario buscarUsuarioPorId(String id) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getId().equals(id)) {
                return listaUsuarios[i];
            }
        }
        return null;
    }
    
    // Buscar posicion de un usuario por su ID
    public int buscarIndiceUsuarioPorId(String id) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    
    // Verificar si un correo de usuario ya existe
    public boolean existeCorreoUsuario(String correo) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getCorreo().equalsIgnoreCase(correo)) {
                return true;
            }
        }
        return false;
    }
    
    //Verificar si esxite id de usuario
    public boolean existeIdUsuario(String id) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    //Verificar si existe usuario por nombre
    public boolean existeUsuarioPorNombre(String nombreUsuario) {
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getNombre().equalsIgnoreCase(nombreUsuario) ||
                listaUsuarios[i].getId().equalsIgnoreCase(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }
    
    // Obtener la cantidad de clientes
    public int getCantidadUsuarios() {
        return contadorUsuarios;
    }
    
    //Obtenemos usuarios filtrados por rol
    public Usuario[] obtenerUsuariosPorRol(Usuario.Rol rol) {
        Usuario[] resultados = new Usuario[contadorUsuarios];
        int contador = 0;
        for (int i = 0; i < contadorUsuarios; i++) {
            if (listaUsuarios[i].getRol() == rol) {
                resultados[contador++] = listaUsuarios[i];
            }
        }
        Usuario[] filtrados = new Usuario[contador];
        System.arraycopy(resultados, 0, filtrados, 0, contador);
        return filtrados;
    }
    
    public int getCantidadClientes() {
        return obtenerUsuariosPorRol(Usuario.Rol.CLIENTE).length;
    }
    
    public int getCantidadAdmins() {
        return obtenerUsuariosPorRol(Usuario.Rol.ADMIN).length;
    }

    //----------------------------------------------------------- Metodos auxiliares y validaciones 
    
    public int getTamanoMaximo() {
        return MAX_USUARIOS;
    }
    
    // Limpiar todos los datos
    public void limpiarTodo() {
        listaProductos = new producto[MAX_PRODUCTOS];
        listaUsuarios = new Usuario[MAX_USUARIOS];
        contadorProductos = 0;
        contadorUsuarios = 0;
    }
    
    private boolean validarFormatoTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) return false;
        return PHONE_PATTERN.matcher(telefono).matches();
    }
    
    private boolean validarFormatoId(String id) {
        return ID_PATTERN.matcher(id).matches();
    }
    
    private boolean validarNombreOApellido(String texto) {
        return NOMBRE_PATTERN.matcher(texto).matches();
    }
    
    // Validar formato de correo
    private boolean validarCorreoCompleto(String correo) {
        
    correo = correo.trim().toLowerCase();

        if (correo.length() < 6 || correo.length() > 254) return false;
        if (!EMAIL_PATTERN.matcher(correo).matches()) return false;
        if (correo.contains(" ")) return false;
        if (correo.chars().filter(c -> c == '@').count() != 1) return false;

        String[] partes = correo.split("@");
        if (partes.length != 2) return false;

        String usuario = partes[0];
        String dominio = partes[1];

        // Validar usuario
        if (usuario.startsWith(".") || usuario.endsWith(".") ||
            usuario.startsWith("_") || usuario.endsWith("_") ||
            usuario.startsWith("-") || usuario.endsWith("-")) {
            return false;
        }

        // Validar dominio
        if (dominio.startsWith("-") || dominio.endsWith("-") ||
            dominio.startsWith(".") || dominio.endsWith(".")) {
            return false;
        }

        String[] niveles = dominio.split("\\.");
        for (String nivel : niveles) {
            if (nivel.isEmpty() || nivel.length() > 63 ||
                nivel.startsWith("-") || nivel.endsWith("-")) {
                return false;
            }
        }

        String tld = niveles[niveles.length - 1];
        if (tld.length() < 2 || tld.length() > 63) return false;

        // Verificar dominio permitido
        for (String d : DOMINIOS_VALIDOS) {
            if (dominio.equalsIgnoreCase(d)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean validarFechaYEdad(String fecha) {
        try {
            if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return false;
            }
            
            String[] partes = fecha.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int año = Integer.parseInt(partes[2]);

            if (mes < 1 || mes > 12 || dia < 1 || año < 1900) {
                return false;
            }
        
            int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        boolean esBisiesto = (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
            if (esBisiesto) {
                diasPorMes[1] = 29;
            }
        
            if (dia > diasPorMes[mes - 1]) {
                return false;
            }
        
            java.time.LocalDate fechaNacimiento = java.time.LocalDate.of(año, mes, dia);
            java.time.LocalDate hoy = java.time.LocalDate.now();
            int edad = java.time.Period.between(fechaNacimiento, hoy).getYears();
        
            return edad >= 18 && edad <= 120;
        
        } catch (Exception e) {
            return false;
        }
    }
    
    //----------------------------------------------------------- Persistencia archivo usuarios
    
    private boolean guardarUsuarioEnArchivo(Usuario u) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE, true))) {
            writer.write(u.toArchivoLinea());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar usuario en archivo: " + e.getMessage());
            return false;
        }
    }
    
    private void cargarUsuariosDesdeArchivo() {
        File archivo = new File(USUARIOS_FILE);
        if (!archivo.exists()) {
            System.out.println("Archivo de usuarios no existe. Se creará al registrar el primer usuario.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                Usuario u = Usuario.fromArchivoLinea(linea);
                if (u != null && !existeCorreoUsuario(u.getCorreo()) && !existeIdUsuario(u.getId()) && contadorUsuarios < MAX_USUARIOS) {
                    listaUsuarios[contadorUsuarios++] = u;
                }
            }
            System.out.println("Usuarios cargados desde archivo: " + contadorUsuarios);
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios desde archivo: " + e.getMessage());
        }
    }
    
    //Limpiar los campos 
    private void limpiarCamposRegistro() {
        if (txtCorreoRegistro       !=null) txtCorreoRegistro.clear();
        if (txtNombreRegistro       !=null) txtNombreRegistro.clear();
        if (txtApellidoRegistro     !=null) txtApellidoRegistro.clear();
        if (txtIdClienteRegistro    !=null) txtIdClienteRegistro.clear();
        if (txtFechaRegistro        !=null) txtFechaRegistro.clear();
        if (txtCelularRegistro      !=null) txtCelularRegistro.clear();
        if (txtDireccionRegistro    !=null) txtDireccionRegistro.clear();
        if (txtPasswordRegistro     !=null) txtPasswordRegistro.clear();
        if (comboDepartamento       !=null) comboDepartamento.setValue(null);
        if (comboCiudad             !=null) { comboCiudad.getItems().clear(); comboCiudad.setValue(null); }
    }
    
    private void limpiarCamposLogin() {
        if (txtCorreoLogin != null) txtCorreoLogin.clear();
        if (txtPasswordLogin != null) txtPasswordLogin.clear();
    }
    
    // Cerrar sesion 
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vista_Login-Registro.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Boom - Iniciar Sesion");
            stage.setMinWidth(800);
            stage.setMinHeight(450);
            stage.setResizable(true);
            stage.centerOnScreen();
            stage.show();
            
            MenuItem menuItem = (MenuItem) event.getSource();
            Stage currentStage = (Stage) menuItem.getParentPopup().getOwnerWindow();
            currentStage.close();
            
            usuarioActual = null;
            
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cerrar sesion: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //----------------------------------------------------------- Dialogo Emergente Nativo JavaFX
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
    
        java.util.Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
    
    
    //----------------------------------------------------------- Datos para los ComboBox: Departamento y Ciudad
    private static final LinkedHashMap<String, String[]> DEPTOS = new LinkedHashMap<>();
    static {
        DEPTOS.put("Amazonas",
    new String[]{"Leticia","Puerto Nariño"});

        DEPTOS.put("Antioquia",
    new String[]{"Medellín","Abejorral","Abriaquí","Alejandría","Amagá","Amalfi","Andes","Angelópolis","Angostura","Anorí","Anza","Apartadó","Arboletes","Argelia","Armenia","Barbosa","Belmira","Bello","Betania","Betulia","Briceño","Buriticá","Cáceres","Caicedo","Caldas","Campamento","Cañasgordas","Caracolí","Caramanta","Carepa","Carolina","Caucasia","Chigorodó","Cisneros","Ciudad Bolívar","Cocorná","Concepción","Concordia","Copacabana","Dabeiba","Donmatías","Ebéjico","El Bagre","El Carmen de Viboral","El Peñol","El Retiro","El Santuario","Entrerríos","Envigado","Fredonia","Frontino","Giraldo","Girardota","Gómez Plata","Granada","Guadalupe","Guarne","Guatapé","Heliconia","Hispania","Itagüí","Ituango","Jardín","Jericó","La Ceja","La Estrella","La Pintada","La Unión","Liborina","Maceo","Marinilla","Medellín","Montebello","Murindó","Mutatá","Nariño","Necoclí","Nechí","Olaya","Peque","Pueblorrico","Puerto Berrío","Puerto Nare","Puerto Triunfo","Remedios","Retiro","Rionegro","Sabanalarga","Sabaneta","Salgar","San Andrés de Cuerquia","San Carlos","San Francisco","San Jerónimo","San José de la Montaña","San Juan de Urabá","San Luis","San Pedro","San Pedro de Urabá","San Rafael","San Roque","San Vicente","Santa Bárbara","Santa Fe de Antioquia","Santa Rosa de Osos","Santo Domingo","Segovia","Sonsón","Sopetrán","Támesis","Tarazá","Tarso","Titiribí","Toledo","Turbo","Uramita","Urrao","Valdivia","Valparaíso","Vegachí","Venecia","Vigía del Fuerte","Yalí","Yarumal","Yolombó","Yondó","Zaragoza"});

        DEPTOS.put("Arauca",
    new String[]{"Arauca","Arauquita","Cravo Norte","Fortul","Puerto Rondón","Saravena","Tame"});

        DEPTOS.put("Atlántico",
    new String[]{"Barranquilla","Baranoa","Campo de la Cruz","Candelaria","Galapa","Juan de Acosta","Luruaco","Malambo","Manatí","Palmar de Varela","Piojó","Polonuevo","Ponedera","Puerto Colombia","Repelón","Sabanagrande","Sabanalarga","Santa Lucía","Santo Tomás","Soledad","Suan","Tubará","Usiacurí"});

        DEPTOS.put("Bogotá D.C.",
    new String[]{"Bogotá"});

        DEPTOS.put("Bolívar",
    new String[]{"Cartagena","Achí","Altos del Rosario","Arenal","Arjona","Arroyohondo","Barranco de Loba","Calamar","Cantagallo","Cicuco","Clemencia","Córdoba","El Carmen de Bolívar","El Guamo","El Peñón","Hatillo de Loba","Magangué","Mahates","Margarita","María la Baja","Montecristo","Mompós","Morales","Pinillos","Regidor","Río Viejo","San Cristóbal","San Estanislao","San Fernando","San Jacinto","San Jacinto del Cauca","San Juan Nepomuceno","San Martín de Loba","San Pablo","Santa Catalina","Santa Rosa","Santa Rosa del Sur","Simití","Soplaviento","Talaigua Nuevo","Tiquisio","Turbaco","Turbaná","Villanueva","Zambrano"});

        DEPTOS.put("Boyacá",
    new String[]{"Tunja","Almeida","Aquitania","Arcabuco","Belén","Berbeo","Betéitiva","Boavita","Boyacá","Briceño","Buenavista","Busbanzá","Caldas","Campohermoso","Cerinza","Chinavita","Chiquinquirá","Chíquiza","Chiscas","Chita","Chitaraque","Chivatá","Ciénega","Cómbita","Coper","Corrales","Covarachía","Cubará","Cucaita","Cuítiva","Duitama","El Cocuy","El Espino","Firavitoba","Floresta","Gachantivá","Gameza","Garagoa","Guacamayas","Guateque","Guayatá","Güicán","Iza","Jenesano","Jericó","La Capilla","La Uvita","Villa de Leyva","Macanal","Maripí","Miraflores","Mongua","Monguí","Moniquirá","Motavita","Muzo","Nobsa","Nuevo Colón","Oicatá","Otanche","Pachavita","Páez","Paipa","Pajarito","Panqueba","Pauna","Paya","Paz del Río","Pesca","Pisba","Puerto Boyacá","Quípama","Ramiriquí","Ráquira","Rondón","Saboyá","Sáchica","Samacá","San Eduardo","San José de Pare","San Luis de Gaceno","San Mateo","San Miguel de Sema","San Pablo de Borbur","Santa María","Santa Rosa de Viterbo","Santa Sofía","Sativanorte","Sativasur","Siachoque","Soatá","Socha","Socotá","Sogamoso","Somondoco","Sora","Soracá","Sotaquirá","Susacón","Sutamarchán","Sutatenza","Tasco","Tenza","Tibaná","Tibasosa","Tinjacá","Tipacoque","Toca","Togüí","Tópaga","Tota","Tununguá","Turmequé","Tuta","Tutazá","Umbita","Ventaquemada","Viracachá","Zetaquira"});

        DEPTOS.put("Caldas",
    new String[]{"Manizales","Aguadas","Anserma","Aranzazu","Belalcázar","Chinchiná","Filadelfia","La Dorada","La Merced","Manzanares","Marmato","Marquetalia","Marulanda","Neira","Norcasia","Pácora","Palestina","Pensilvania","Riosucio","Risaralda","Salamina","Samaná","San José","Supía","Victoria","Villamaría","Viterbo"});

        DEPTOS.put("Caquetá",
    new String[]{"Florencia","Albania","Belén de los Andaquíes","Cartagena del Chairá","Curillo","El Doncello","El Paujil","La Montañita","Milán","Morelia","Puerto Rico","San José del Fragua","San Vicente del Caguán","Solano","Solita","Valparaíso"});

        DEPTOS.put("Casanare",
    new String[]{"Yopal","Aguazul","Chámeza","Hato Corozal","La Salina","Maní","Monterrey","Nunchía","Orocué","Paz de Ariporo","Pore","Recetor","Sabanalarga","Sacama","San Luis de Palenque","Támara","Tauramena","Trinidad","Villanueva"});

        DEPTOS.put("Cauca",
    new String[]{"Popayán","Almaguer","Argelia","Balboa","Bolívar","Buenos Aires","Cajibío","Caldono","Caloto","Corinto","El Tambo","Florencia","Guachené","Guapi","Inzá","Jambaló","La Sierra","La Vega","López de Micay","Mercaderes","Miranda","Morales","Padilla","Páez","Patía","Piamonte","Piendamó","Puerto Tejada","Puracé","Rosas","San Sebastián","Santa Rosa","Santander de Quilichao","Silvia","Sotará","Suárez","Sucre","Timbío","Timbiquí","Toribío","Totoró","Villa Rica"});

        DEPTOS.put("Cesar",
    new String[]{"Valledupar","Aguachica","Agustín Codazzi","Astrea","Becerril","Bosconia","Chimichagua","Chiriguaná","Curumaní","El Copey","El Paso","Gamarra","González","La Gloria","La Jagua de Ibirico","Manaure","Pailitas","Pelaya","Pueblo Bello","Río de Oro","La Paz","San Alberto","San Diego","San Martín","Tamalameque"});

        DEPTOS.put("Chocó",
    new String[]{"Quibdó","Acandí","Alto Baudó","Atrato","Bagadó","Bahía Solano","Bajo Baudó","Belén de Bajirá","Bojayá","Cantón de San Pablo","Carmen del Darién","Cértegui","Condoto","El Carmen de Atrato","El Litoral del San Juan","Istmina","Juradó","Lloró","Medio Atrato","Medio Baudó","Medio San Juan","Nóvita","Nuquí","Río Iró","Río Quito","Riosucio","San José del Palmar","Sipí","Tadó","Unguía","Unión Panamericana"});

        DEPTOS.put("Córdoba",
    new String[]{"Montería","Ayapel","Buenavista","Canalete","Cereté","Chimá","Chinú","Ciénaga de Oro","Cotorra","La Apartada","Lorica","Los Córdobas","Momil","Montelíbano","Moñitos","Planeta Rica","Pueblo Nuevo","Puerto Escondido","Puerto Libertador","Purísima","Sahagún","San Andrés de Sotavento","San Antero","San Bernardo del Viento","San Carlos","San José de Uré","San Pelayo","Tierralta","Tuchín","Valencia"});

        DEPTOS.put("Cundinamarca",
    new String[]{"Facatativá","Agua de Dios","Albán","Anapoima","Anolaima","Apulo","Arbeláez","Beltrán","Bituima","Bojacá","Cabrera","Cachipay","Cajicá","Caparrapí","Cáqueza","Carmen de Carupa","Chaguaní","Chía","Chipaque","Choachí","Chocontá","Cogua","Cota","Cucunubá","El Colegio","El Peñón","El Rosal","Facatativá","Fómeque","Fosca","Funza","Fúquene","Fusagasugá","Gachalá","Gachancipá","Gachetá","Gama","Girardot","Granada","Guachetá","Guaduas","Guasca","Guataquí","Guatavita","Guayabal de Síquima","Guayabetal","Gutiérrez","Jerusalén","Junín","La Calera","La Mesa","La Palma","La Peña","La Vega","Lenguazaque","Machetá","Madrid","Manta","Medina","Mosquera","Nariño","Nemocón","Nilo","Nimaima","Nocaima","Pacho","Paime","Pandi","Paratebueno","Pasca","Puerto Salgar","Pulí","Quebradanegra","Quetame","Quipile","Ricaurte","San Antonio del Tequendama","San Bernardo","San Cayetano","San Francisco","San Juan de Rioseco","Sasaima","Sesquilé","Sibaté","Silvania","Simijaca","Soacha","Sopó","Subachoque","Suesca","Supatá","Susa","Sutatausa","Tabio","Tausa","Tena","Tenjo","Tibacuy","Tibirita","Tocaima","Tocancipá","Topaipí","Ubalá","Ubaque","Villa de San Diego de Ubaté","Une","Útica","Venecia","Vergara","Vianí","Villagómez","Villapinzón","Villeta","Viotá","Yacopí","Zipacón","Zipaquirá"});

        DEPTOS.put("Guainía",
    new String[]{"Inírida"});

        DEPTOS.put("Guaviare",
    new String[]{"San José del Guaviare","Calamar","El Retorno","Miraflores"});

        DEPTOS.put("Huila",
    new String[]{"Neiva","Acevedo","Agrado","Aipe","Algeciras","Altamira","Baraya","Campoalegre","Colombia","Elías","Garzón","Gigante","Guadalupe","Hobo","Íquira","Isnos","La Argentina","La Plata","Nátaga","Oporapa","Paicol","Palermo","Palestina","Pital","Pitalito","Rivera","Saladoblanco","San Agustín","Santa María","Suaza","Tarqui","Tello","Teruel","Tesalia","Timaná","Villavieja","Yaguará"});

        DEPTOS.put("La Guajira",
    new String[]{"Riohacha","Albania","Barrancas","Dibulla","Distracción","El Molino","Fonseca","Hatonuevo","La Jagua del Pilar","Maicao","Manaure","San Juan del Cesar","Uribia","Urumita","Villanueva"});

        DEPTOS.put("Magdalena",
    new String[]{"Santa Marta","Algarrobo","Aracataca","Ariguaní","Cerro de San Antonio","Chibolo","Ciénaga","Concordia","El Banco","El Piñón","El Retén","Fundación","Guamal","Nueva Granada","Pedraza","Pijiño del Carmen","Pivijay","Plato","Puebloviejo","Remolino","Sabanas de San Ángel","Salamina","San Sebastián de Buenavista","San Zenón","Santa Ana","Santa Bárbara de Pinto","Sitionuevo","Tenerife","Zapayán","Zona Bananera"});

        DEPTOS.put("Meta",
    new String[]{"Villavicencio","Acacías","Barranca de Upía","Cabuyaro","Castilla la Nueva","Cubarral","Cumaral","El Calvario","El Castillo","El Dorado","Fuente de Oro","Granada","Guamal","La Macarena","Lejanías","Mapiripán","Mesetas","Puerto Concordia","Puerto Gaitán","Puerto López","Puerto Lleras","Puerto Rico","Restrepo","San Carlos de Guaroa","San Juan de Arama","San Juanito","San Martín","Uribe","Vista Hermosa"});

        DEPTOS.put("Nariño",
    new String[]{"Pasto","Albán","Aldana","Ancuyá","Arboleda","Barbacoas","Belén","Buesaco","Chachagüí","Colón","Consacá","Contadero","Córdoba","Cuaspud","Cumbal","Cumbitara","El Charco","El Peñol","El Rosario","El Tablón","El Tambo","Francisco Pizarro","Funes","Guachucal","Guaitarilla","Gualmatán","Iles","Imués","Ipiales","La Cruz","La Florida","La Llanada","La Tola","La Unión","Leiva","Linares","Los Andes","Magüí","Mallama","Mosquera","Nariño","Olaya Herrera","Ospina","Policarpa","Potosí","Providencia","Puerres","Pupiales","Ricaurte","Roberto Payán","Samaniego","San Bernardo","San Lorenzo","San Pablo","San Pedro de Cartago","Sandoná","Santa Bárbara","Santacruz","Sapuyes","Taminango","Tangua","Tumaco","Túquerres","Yacuanquer"});

        DEPTOS.put("Norte de Santander",
    new String[]{"Cúcuta","Ábrego","Arboledas","Bochalema","Bucarasica","Cácota","Cáchira","Chinácota","Chitagá","Convención","Cucutilla","Durania","El Carmen","El Tarra","El Zulia","Gramalote","Hacarí","Herrán","Labateca","La Esperanza","La Playa","Los Patios","Lourdes","Mutiscua","Ocaña","Pamplona","Pamplonita","Puerto Santander","Ragonvalia","Salazar","San Calixto","San Cayetano","Santiago","Santo Domingo de Silos","Sardinata","Teorama","Tibú","Toledo","Villa Caro","Villa del Rosario"});
    
        DEPTOS.put("Putumayo",
    new String[]{"Mocoa","Colón","Orito","Puerto Asís","Puerto Caicedo","Puerto Guzmán","Puerto Leguízamo","San Francisco","San Miguel","Santiago","Sibundoy","Valle del Guamuez","Villagarzón"});

        DEPTOS.put("Quindío",
    new String[]{"Armenia","Buenavista","Calarcá","Circasia","Córdoba","Filandia","Génova","La Tebaida","Montenegro","Pijao","Quimbaya","Salento"});

        DEPTOS.put("Risaralda",
    new String[]{"Pereira","Apía","Balboa","Belén de Umbría","Dosquebradas","Guática","La Celia","La Virginia","Marsella","Mistrató","Pueblo Rico","Quinchía","Santa Rosa de Cabal","Santuario"});

        DEPTOS.put("San Andrés y Providencia",
    new String[]{"San Andrés","Providencia"});

        DEPTOS.put("Santander",
    new String[]{"Bucaramanga","Aguada","Albania","Aratoca","Barbosa","Barichara","Barrancabermeja","Betulia","Bolívar","Cabrera","California","Capitanejo","Carcasí","Cepitá","Cerrito","Charalá","Charta","Chima","Chipatá","Cimitarra","Concepción","Confines","Contratación","Coromoro","Curití","El Carmen de Chucurí","El Guacamayo","El Peñón","El Playón","Encino","Enciso","Florián","Floridablanca","Galán","Gámbita","Girón","Guaca","Guadalupe","Guapotá","Guavatá","Güepsa","Hato","Jesús María","Jordán","La Belleza","Landázuri","La Paz","Lebrija","Los Santos","Macaravita","Málaga","Matanza","Mogotes","Molagavita","Ocamonte","Oiba","Onzaga","Palmar","Palmas del Socorro","Páramo","Piedecuesta","Pinchote","Puente Nacional","Puerto Parra","Puerto Wilches","Rionegro","Sabana de Torres","San Andrés","San Benito","San Gil","San Joaquín","San José de Miranda","San Miguel","San Vicente de Chucurí","Santa Bárbara","Santa Helena del Opón","Simacota","Socorro","Suaita","Sucre","Suratá","Tona","Valle de San José","Vélez","Vetas","Villanueva","Zapatoca"});

        DEPTOS.put("Sucre",
    new String[]{"Sincelejo","Buenavista","Caimito","Chalán","Colosó","Corozal","Coveñas","El Roble","Galeras","Guaranda","La Unión","Los Palmitos","Majagual","Morroa","Ovejas","Palmito","Sampués","San Benito Abad","San Juan de Betulia","San Marcos","San Onofre","San Pedro","Sincé","Sucre","Tolú","Toluviejo"});

        DEPTOS.put("Tolima",
    new String[]{"Ibagué","Alpujarra","Alvarado","Ambalema","Anzoátegui","Armero","Ataco","Cajamarca","Carmen de Apicalá","Casabianca","Chaparral","Coello","Coyaima","Cunday","Dolores","Espinal","Falán","Flandes","Fresno","Guamo","Herveo","Honda","Icononzo","Lérida","Líbano","Mariquita","Melgar","Murillo","Natagaima","Ortega","Palocabildo","Piedras","Planadas","Prado","Purificación","Rioblanco","Roncesvalles","Rovira","Saldaña","San Antonio","San Luis","Santa Isabel","Suárez","Valle de San Juan","Venadillo","Villahermosa","Villarrica"});

        DEPTOS.put("Valle del Cauca",
    new String[]{"Cali","Alcalá","Andalucía","Ansermanuevo","Argelia","Bolívar","Buenaventura","Buga","Bugalagrande","Caicedonia","Calima","Candelaria","Cartago","Dagua","El Águila","El Cairo","El Cerrito","El Dovio","Florida","Ginebra","Guacarí","Jamundí","La Cumbre","La Unión","La Victoria","Obando","Palmira","Pradera","Restrepo","Riofrío","Roldanillo","San Pedro","Sevilla","Toro","Trujillo","Tuluá","Ulloa","Versalles","Vijes","Yotoco","Yumbo","Zarzal"});

        DEPTOS.put("Vaupés",
    new String[]{"Mitú","Carurú","Pacoa","Papunahua","Taraira","Yavaraté"});

        DEPTOS.put("Vichada",
    new String[]{"Puerto Carreño","Cumaribo","La Primavera","Santa Rosalía"});
        
    }   
    
}
