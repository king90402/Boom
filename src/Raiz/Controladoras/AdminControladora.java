/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AdminControladora.java to edit this template
 */


package Raiz.Controladoras;

import Raiz.Modelos.Producto;
import Raiz.Modelos.Usuario;
import Raiz.Servicios.ProductoServicio;
import Raiz.Servicios.SesionServicio;
import Raiz.Servicios.UsuarioServicio;
import Raiz.Utilidades.AlertaUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo total de la vista_administracion

public class AdminControladora {
    
    // Servicios compartidos de unica instancia paa evitar sobrecarga
    
    private final SesionServicio sesionServicio = SesionServicio.getInstancia();
    private final ProductoServicio productoServicio = ProductoServicio.getInstancia();
    private final UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();

    // ----- Atributos de elementos FXML para su uso
    
    // Paneles principales
    
    @FXML private Pane panePerfilAdmin;
    @FXML private Pane paneEstadisticasAdmin;
    @FXML private Pane paneRestaStockAdmin;
    @FXML private Pane paneInventarioAdmin;
    @FXML private Pane panePedidosAdmin;
    @FXML private Pane paneIngrePersoAdmin;
    @FXML private Pane paneEditarInfoAdmin;
    
    // Paneles nuevo producto
    
    @FXML private Pane paneRestaNuevo_Producto;
    @FXML private Pane paneRestaNuevo_ProductoPaso1;
    @FXML private Pane paneRestaNuevo_ProductoPaso2;
    @FXML private Pane paneRestaNuevo_ProductoPaso3;
    
    // Toggle buttons
    
    @FXML private ToggleButton tbtnPerfilAdmin;
    @FXML private ToggleButton tbtnEstadisticasAdmin;
    @FXML private ToggleButton tbtnRestaStockAdmin;
    @FXML private ToggleButton tbtnInventarioAdmin;
    @FXML private ToggleButton tbtnPedidosAdmin;
    @FXML private ToggleButton tbtnIngrePersoAdmin;
    
    private ToggleGroup barraAdmin;
    
    // Textfields nuevo producto
    
    @FXML private TextField txtnombreproductO;
    @FXML private TextField txtmarca;
    @FXML private TextField txtDetalles;
    @FXML private TextField txtprecio;
    @FXML private TextField txtruta;
    @FXML private CheckBox checknuevo;
    @FXML private CheckBox checkviejo;
    @FXML private ImageView previsualizacion;
    
    private String rutaImagenSeleccionada = "Preview Image.png";
    
    // Labels
    
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
    
    // ----- Inicializacion
    
    @FXML
    public void initialize() {
        inicializarToggleGroups();
        cargarDatosUsuario();
        mostrarPaneAdmin(panePerfilAdmin);
    }
    
    // Toggle buttons asigandos a su grupo
    
    private void inicializarToggleGroups() {
        barraAdmin = new ToggleGroup();
        if (tbtnPerfilAdmin != null) tbtnPerfilAdmin.setToggleGroup(barraAdmin);
        if (tbtnEstadisticasAdmin != null) tbtnEstadisticasAdmin.setToggleGroup(barraAdmin);
        if (tbtnRestaStockAdmin != null) tbtnRestaStockAdmin.setToggleGroup(barraAdmin);
        if (tbtnInventarioAdmin != null) tbtnInventarioAdmin.setToggleGroup(barraAdmin);
        if (tbtnPedidosAdmin != null) tbtnPedidosAdmin.setToggleGroup(barraAdmin);
        if (tbtnIngrePersoAdmin != null) tbtnIngrePersoAdmin.setToggleGroup(barraAdmin);
    }
    
    // Carga de datos de usuario logeado
    
    private void cargarDatosUsuario() {
        Usuario usuario = sesionServicio.getUsuarioActual();
        if (usuario == null) return;
        
        if (lblUsuarioAdmin != null) lblUsuarioAdmin.setText(usuario.getNombre());
        if (lblNombrePerfilAdmin != null) lblNombrePerfilAdmin.setText(usuario.getNombreCompleto());
        if (lblCorreoPerfilAdmin != null) lblCorreoPerfilAdmin.setText(usuario.getCorreo());
        if (lblAbreNombrePerfilAdmin != null) lblAbreNombrePerfilAdmin.setText(usuario.getIniciales());
        if (lblRolPerfilAdmin != null) lblRolPerfilAdmin.setText(usuario.getRol().name());
        
        if (lblNombre2PerfilAdmin != null) lblNombre2PerfilAdmin.setText(usuario.getNombreCompleto());
        if (lblCorreo2PerfilAdmin != null) lblCorreo2PerfilAdmin.setText(usuario.getCorreo());
        if (lblCelularPerfilAdmin != null) lblCelularPerfilAdmin.setText(usuario.getCelular());
        if (lblDocumentoPerfilAdmin != null) lblDocumentoPerfilAdmin.setText(usuario.getId());
        if (lblFechaPerfilAdmin != null) lblFechaPerfilAdmin.setText(usuario.getFechaNacimiento());
        if (lblDireccionPerfilAdmin != null) lblDireccionPerfilAdmin.setText(usuario.getDireccionCompleta());
        if (lblRol2PerfilAdmin != null) lblRol2PerfilAdmin.setText(usuario.getRol().name());
    }
    
    // ----- Navegacion
    
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
    
    // Nav. nuevo producto
    
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
    
    @FXML
    private void irPaso1NuevoProducto() {
        mostrarPaneAdmin(paneRestaNuevo_Producto);
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso1);
    }
    
    @FXML
    private void irPaso2NuevoProducto() {
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso2);
    }
    
    @FXML
    private void irPaso3NuevoProducto() {
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso3);
    }
    
    // ----- Gestion productos
    
    // Seleccion de imagen para producto
    
    @FXML
    private void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Producto");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        
        File archivo = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if (archivo != null) {
            rutaImagenSeleccionada = archivo.toURI().toString();
            if (txtruta != null) txtruta.setText(archivo.getName());
            if (previsualizacion != null) previsualizacion.setImage(new Image(rutaImagenSeleccionada));
        }
    }
    
    // Nuevo producto al invetario
  
    @FXML
    private void agregarProducto(ActionEvent event) {
        // Validar campos
        if (txtnombreproductO == null || txtnombreproductO.getText().trim().isEmpty()) {
            AlertaUtil.mostrarAdvertencia("Campo requerido", "Ingrese el nombre del producto.");
            return;
        }
        
        if (txtprecio == null || txtprecio.getText().trim().isEmpty()) {
            AlertaUtil.mostrarAdvertencia("Campo requerido", "Ingrese el precio del producto.");
            return;
        }
        
        String nombre = txtnombreproductO.getText().trim();
        String marca = txtmarca != null ? txtmarca.getText().trim() : "";
        
        double precio;
        try {
            precio = Double.parseDouble(txtprecio.getText().trim());
        } catch (NumberFormatException e) {
            AlertaUtil.mostrarAdvertencia("Precio invalido", "Ingrese un precio numerico valido.");
            return;
        }
        
        String estado = "Nuevo";
        if (checknuevo != null && checknuevo.isSelected()) {
            estado = "Nuevo";
        } else if (checkviejo != null && checkviejo.isSelected()) {
            estado = "Usado";
        }
        
        // Agregar producto via servicio
        productoServicio.agregarProducto(nombre, 1, precio, estado, marca, "General", rutaImagenSeleccionada);
        
        AlertaUtil.mostrarInformacion("Producto agregado", 
            "El producto " + nombre + " ha sido agregado al inventario.");
        
        limpiarCamposProducto();
    }
    
    // Gestion de stock
    
    @FXML
    private void gestionarIngresoProducto(ActionEvent event) {
        if (txtnombreproductO == null || txtnombreproductO.getText().trim().isEmpty()) {
            AlertaUtil.mostrarAdvertencia("Campo requerido", "Ingrese el nombre del producto.");
            return;
        }
        
        String nombre = txtnombreproductO.getText().trim();
        String marca = txtmarca != null ? txtmarca.getText().trim() : "";
        
        double precio = 0;
        try {
            if (txtprecio != null && !txtprecio.getText().trim().isEmpty()) {
                precio = Double.parseDouble(txtprecio.getText().trim());
            }
        } catch (NumberFormatException e) {
            AlertaUtil.mostrarAdvertencia("Precio invalido", "Ingrese un precio numerico valido.");
            return;
        }
        
        String estado = "Nuevo";
        if (checkviejo != null && checkviejo.isSelected()) {
            estado = "Usado";
        }
        
        // Gestionar via servicio
        boolean resultado = productoServicio.gestionarIngresoProducto(
            nombre, 1, precio, estado, marca, "General", rutaImagenSeleccionada
        );
        
        if (resultado) {
            AlertaUtil.mostrarInformacion("Inventario actualizado", 
                "Stock actualizado correctamente.");
        }
        
        limpiarCamposProducto();
    }
    
    // Limpiar formulario de nuevo producto
    
    private void limpiarCamposProducto() {
        if (txtnombreproductO != null) txtnombreproductO.clear();
        if (txtmarca != null) txtmarca.clear();
        if (txtDetalles != null) txtDetalles.clear();
        if (txtprecio != null) txtprecio.clear();
        if (txtruta != null) txtruta.clear();
        if (checknuevo != null) checknuevo.setSelected(false);
        if (checkviejo != null) checkviejo.setSelected(false);
        rutaImagenSeleccionada = "Preview Image.png";
    }
    
    // ----- Estadisticas
    
    public void actualizarEstadisticas() {
        int totalProductos = productoServicio.getCantidadProductos();
        int sinStock = productoServicio.getCantidadSinStock();
        double valorInventario = productoServicio.getValorInventario();
        int totalUsuarios = usuarioServicio.getCantidadUsuarios();
        int totalClientes = usuarioServicio.getCantidadClientes();
        int totalAdmins = usuarioServicio.getCantidadAdmins();
        
    }
    
    // ----- Estadisticas
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            sesionServicio.cerrarSesion();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Raiz/Vistas/Vista_Login-Registro.fxml"));
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
            
        } catch (Exception e) {
            AlertaUtil.mostrarError("Error", "Error al cerrar sesion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
