/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/HomeControladora.java to edit this template
 */

package Raiz.Controladoras;

import Raiz.Modelos.Producto;
import Raiz.Modelos.Usuario;
import Raiz.Servicios.ProductoServicio;
import Raiz.Servicios.SesionServicio;
import Raiz.Utilidades.AlertaUtil;
import Raiz.Utilidades.Ordenamiento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo total de la vista_home

public class HomeControladora {
    
    // Servicios compartidos de unica instancia paa evitar sobrecarga
    
    private final SesionServicio sesionService = SesionServicio.getInstancia();
    private final ProductoServicio productoService = ProductoServicio.getInstancia();
    
    // ----- Atributos de elementos FXML para su uso
    
    // Paneles principales
    
    @FXML private Pane paneInicioHome;
    @FXML private Pane panePerfilHome;
    @FXML private Pane paneCarritoHome;
    
    // Subpaneles perfil (pueden ser VBox en el FXML)
    
    @FXML private VBox paneFavoritosPerfilHome;
    @FXML private VBox panePedidosPerfilHome;
    @FXML private VBox paneMiCuentaPerfilHome;
    
    // Toggle buttons
    
    @FXML private ToggleButton tbtnPedidosPerfilHome;
    @FXML private ToggleButton tbtnFavoritosPerfilHome;
    @FXML private ToggleButton tbtnMiCuentaPerfilHome;
    
    @FXML private ToggleButton tbtnOfertasHome;
    @FXML private ToggleButton tbtnTecnologiaHome;
    @FXML private ToggleButton tbtnHogarHome;
    @FXML private ToggleButton tbtnDeportesHome;
    @FXML private ToggleButton tbtnModaBellezaHome;
    
    private ToggleGroup barraUsuario;
    private ToggleGroup menuHome;
    
    // Contenedor productos
    
    @FXML private FlowPane contenedorProductosHome;
    
    // Labels
    
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
    
    // ----- Inicializacion
    
    @FXML
    public void initialize() {
        inicializarToggleGroups();
        cargarDatosUsuario();
        mostrarInicio();
        cargarProductos();
    }
    
    // Toggle buttons asigandos a su grupo
    
    private void inicializarToggleGroups() {
        
        // Barra de usuario (perfil)
        
        barraUsuario = new ToggleGroup();
        if (tbtnFavoritosPerfilHome != null) tbtnFavoritosPerfilHome.setToggleGroup(barraUsuario);
        if (tbtnPedidosPerfilHome != null) tbtnPedidosPerfilHome.setToggleGroup(barraUsuario);
        if (tbtnMiCuentaPerfilHome != null) tbtnMiCuentaPerfilHome.setToggleGroup(barraUsuario);
        
        // Menu de categorias
        
        menuHome = new ToggleGroup();
        if (tbtnOfertasHome != null) tbtnOfertasHome.setToggleGroup(menuHome);
        if (tbtnTecnologiaHome != null) tbtnTecnologiaHome.setToggleGroup(menuHome);
        if (tbtnHogarHome != null) tbtnHogarHome.setToggleGroup(menuHome);
        if (tbtnDeportesHome != null) tbtnDeportesHome.setToggleGroup(menuHome);
        if (tbtnModaBellezaHome != null) tbtnModaBellezaHome.setToggleGroup(menuHome);
    }
    
    // Carga de datos de usuario logeado
    
    private void cargarDatosUsuario() {
        Usuario usuario = sesionService.getUsuarioActual();
        if (usuario == null) return;
        
        if (lblUsuarioHome != null) lblUsuarioHome.setText(usuario.getNombre());
        if (lblDireccionHome != null) lblDireccionHome.setText(usuario.getDireccion());
        
        if (lblNombrePerfilHome != null) lblNombrePerfilHome.setText(usuario.getNombreCompleto());
        if (lblCorreoPerfilHome != null) lblCorreoPerfilHome.setText(usuario.getCorreo());
        if (lblAbreNombrePerfilHome != null) lblAbreNombrePerfilHome.setText(usuario.getIniciales());
        if (lblRolPerfilHome != null) lblRolPerfilHome.setText(usuario.getRol().name());
        
        if (lblNombre2PerfilHome != null) lblNombre2PerfilHome.setText(usuario.getNombre());
        if (lblCorreo2PerfilHome != null) lblCorreo2PerfilHome.setText(usuario.getCorreo());
        if (lblCelularPerfilHome != null) lblCelularPerfilHome.setText(usuario.getCelular());
        if (lblDocumentoPerfilHome != null) lblDocumentoPerfilHome.setText(usuario.getId());
        if (lblFechaPerfilHome != null) lblFechaPerfilHome.setText(usuario.getFechaNacimiento());
        if (lblDireccionPerfilHome != null) lblDireccionPerfilHome.setText(usuario.getDireccionCompleta());
    }
    
    // ----- Catalogo productos
    
    //Carga de todos los productos al catalago (Inicial, proximamente habra cambios y mas apartados para los productos)
    
    private void cargarProductos() {
        if (contenedorProductosHome == null) return;

        contenedorProductosHome.getChildren().clear();
        // Productos ordenados por nombre A→Z usando Ordenamiento.quickSort
        ArrayList<Producto> productos = productoService.obtenerOrdenadosPorNombre();

        for (Producto p : productos) {
            // TODO: renderizar tarjeta de producto en contenedorProductosHome
        }
    }
    
    // Filtro de productos por categoria
    
    @FXML
    private void filtrarPorCategoria(ActionEvent event) {
        if (contenedorProductosHome == null) return;

        ToggleButton seleccionado = (ToggleButton) menuHome.getSelectedToggle();
        if (seleccionado == null) {
            cargarProductos(); // Mostrar todos
            return;
        }

        String categoria = seleccionado.getText();
        // Categoria filtrada y ordenada por precio ascendente usando Ordenamiento
        ArrayList<Producto> filtrados = productoService.buscarPorCategoriaOrdenadoPorPrecio(categoria);

        contenedorProductosHome.getChildren().clear();
        for (Producto p : filtrados) {
            // TODO: renderizar tarjeta de producto en contenedorProductosHome
        }
    }
    
    // ----- Navegacion
    
    private void ocultarTodosLosPaneles() {
        Pane[] panes = {paneInicioHome, panePerfilHome, paneCarritoHome};
        for (Pane p : panes) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }
    
    @FXML
    public void mostrarInicio() {
        ocultarTodosLosPaneles();
        if (paneInicioHome != null) {
            paneInicioHome.setVisible(true);
            paneInicioHome.setManaged(true);
        }
    }
    
    @FXML
    public void mostrarPerfil() {
        ocultarTodosLosPaneles();
        if (panePerfilHome != null) {
            panePerfilHome.setVisible(true);
            panePerfilHome.setManaged(true);
        }
        mostrarMiCuentaPerfil();
    }
    
    @FXML
    public void mostrarCarrito() {
        ocultarTodosLosPaneles();
        if (paneCarritoHome != null) {
            paneCarritoHome.setVisible(true);
            paneCarritoHome.setManaged(true);
        }
    }
    
    private void ocultarSubPanesPerfil() {
        VBox[] panes = {paneFavoritosPerfilHome, panePedidosPerfilHome, paneMiCuentaPerfilHome};
        for (VBox p : panes) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }
    
    @FXML
    public void mostrarFavoritosPerfil() {
        ocultarSubPanesPerfil();
        if (paneFavoritosPerfilHome != null) {
            paneFavoritosPerfilHome.setVisible(true);
            paneFavoritosPerfilHome.setManaged(true);
        }
        if (tbtnFavoritosPerfilHome != null) tbtnFavoritosPerfilHome.setSelected(true);
    }
    
    @FXML
    public void mostrarPedidosPerfil() {
        ocultarSubPanesPerfil();
        if (panePedidosPerfilHome != null) {
            panePedidosPerfilHome.setVisible(true);
            panePedidosPerfilHome.setManaged(true);
        }
        if (tbtnPedidosPerfilHome != null) tbtnPedidosPerfilHome.setSelected(true);
    }
    
    @FXML
    public void mostrarMiCuentaPerfil() {
        ocultarSubPanesPerfil();
        if (paneMiCuentaPerfilHome != null) {
            paneMiCuentaPerfilHome.setVisible(true);
            paneMiCuentaPerfilHome.setManaged(true);
        }
        if (tbtnMiCuentaPerfilHome != null) tbtnMiCuentaPerfilHome.setSelected(true);
    }
    
    @FXML
    public void mostrarFavoritosPerfilInicio() {
        ocultarTodosLosPaneles();
        if (panePerfilHome != null) {
            panePerfilHome.setVisible(true);
            panePerfilHome.setManaged(true);
        }
        mostrarFavoritosPerfil();
    }
    
    // ---- Cerrar sesion
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            sesionService.cerrarSesion();
            
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
