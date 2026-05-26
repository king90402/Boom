/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/HomeControladora.java to edit this template
 */

package Raiz.Controladoras;

import Raiz.Modelos.Producto;
import Raiz.Modelos.Usuario;
import Raiz.Servicios.CarritoServicio;
import Raiz.Servicios.FavoritosServicio;
import Raiz.Servicios.ProductoServicio;
import Raiz.Servicios.SesionServicio;
import Raiz.Utilidades.AlertaUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo total de la vista_home

public class HomeControladora {
    
    // Servicios compartidos de unica instancia paa evitar sobrecarga
    
    private final SesionServicio    sesionService    = SesionServicio.getInstancia();
    private final ProductoServicio  productoService  = ProductoServicio.getInstancia();
    private final CarritoServicio   carritoService   = CarritoServicio.getInstancia();
    private final FavoritosServicio favoritosService = FavoritosServicio.getInstancia();
    
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
    
    // Contenedores de productos
    
    @FXML private FlowPane contenedorProductosHome;   // sección "Para ti"
    @FXML private FlowPane contenedorProductosHome1;  // sección "También compraron"

    // Botones imagen de categoría 
    @FXML private Button btnModaBellezaImg;
    @FXML private Button btnOfertasImg;
    @FXML private Button btnTecnologiaImg;
    @FXML private Button btnHogarImg;
    @FXML private Button btnDeportesImg;
    
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
        cargarProductosSecundarios();
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
    
    //Carga de todos los productos al catalago 
    
    private void cargarProductos() {
        if (contenedorProductosHome == null) return;

        contenedorProductosHome.getChildren().clear();
        ArrayList<Producto> productos = productoService.obtenerOrdenadosPorNombre();

        if (productos.isEmpty()) {
            contenedorProductosHome.getChildren().add(mensajeVacio("No hay productos disponibles."));
            return;
        }
        for (Producto p : productos) {
            contenedorProductosHome.getChildren().add(crearTarjetaProducto(p));
        }
    }

    // Rellena el segundo FlowPane con hasta 8 productos ordenados por precio DESC
    private void cargarProductosSecundarios() {
        if (contenedorProductosHome1 == null) return;

        contenedorProductosHome1.getChildren().clear();
        ArrayList<Producto> productos = productoService.obtenerOrdenadosPorPrecioDesc();
        int limite = Math.min(productos.size(), 8);

        if (limite == 0) {
            contenedorProductosHome1.getChildren().add(mensajeVacio("Sin recomendaciones por el momento."));
            return;
        }
        for (int i = 0; i < limite; i++) {
            contenedorProductosHome1.getChildren().add(crearTarjetaProducto(productos.get(i)));
        }
    }

    // Construye por código la tarjeta visual de 280x410 px de cada producto
     private VBox crearTarjetaProducto(Producto producto) {

        // ── IMAGEN ────────────────────────────────────────────────────────────
        ImageView imageView = new ImageView();
        imageView.setFitWidth(280);
        imageView.setFitHeight(330);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        String ruta = producto.getImagenProducto();
        if (ruta != null && !ruta.isEmpty()) {
            try {
                imageView.setImage(new Image(ruta, true));
            } catch (Exception e) {
                System.err.println("[Tarjeta] Imagen no cargada: " + ruta);
            }
        }

        // Sombra idéntica al FXML original
        DropShadow sombra = new DropShadow();
        sombra.setHeight(5.0);
        sombra.setWidth(5.0);
        sombra.setOffsetX(1.0);
        sombra.setOffsetY(1.0);
        sombra.setRadius(2.0);
        sombra.setColor(Color.rgb(151, 151, 151)); // 0.592... * 255 ≈ 151
        imageView.setEffect(sombra);

        // ── BOTÓN FAVORITO (ToggleButton con ícono CSS igual al FXML) ─────────
        // El FXML usa una Region con styleClass "icon-corazonf" como graphic
        Region iconoCorazon = new Region();
        iconoCorazon.getStyleClass().add("icon-corazonf");
        iconoCorazon.setMaxWidth(Region.USE_PREF_SIZE);
        iconoCorazon.setMaxHeight(Region.USE_PREF_SIZE);
        iconoCorazon.setMinWidth(Region.USE_PREF_SIZE);
        iconoCorazon.setMinHeight(Region.USE_PREF_SIZE);

        ToggleButton btnFavorito = new ToggleButton();
        btnFavorito.setMnemonicParsing(false);
        btnFavorito.setPrefWidth(40);
        btnFavorito.setPrefHeight(40);
        btnFavorito.getStyleClass().add("boton-favorito");
        btnFavorito.setGraphic(iconoCorazon);
        btnFavorito.setSelected(favoritosService.esFavorito(producto.getIdProducto()));

        btnFavorito.setOnAction(e -> favoritosService.toggleFavorito(producto));

        StackPane.setAlignment(btnFavorito, Pos.TOP_LEFT);
        StackPane.setMargin(btnFavorito, new Insets(10, 0, 0, 10));

        // ── STACK IMAGEN + FAVORITO ───────────────────────────────────────────
        StackPane stackImagen = new StackPane(imageView, btnFavorito);
        stackImagen.setPrefWidth(280);
        stackImagen.setPrefHeight(330);

        // ── NOMBRE DEL PRODUCTO ───────────────────────────────────────────────
        Text txtNombre = new Text(producto.getNombreProducto());
        txtNombre.setStrokeType(StrokeType.OUTSIDE);
        txtNombre.setStrokeWidth(0.0);
        txtNombre.setWrappingWidth(279.286); // valor exacto del FXML
        txtNombre.setFont(Font.font("Poppins Regular", 14.0));
        VBox.setMargin(txtNombre, new Insets(10, 0, 0, 0));

        // ── PRECIO ────────────────────────────────────────────────────────────
        Text txtPrecio = new Text(producto.getPrecioFormateado());
        txtPrecio.setStrokeType(StrokeType.OUTSIDE);
        txtPrecio.setStrokeWidth(0.0);
        txtPrecio.setFont(Font.font("Poppins ExtraBold", 30.0));

        // ── BOTÓN CARRITO (Region con SVG path, igual al FXML) ────────────────
        // El FXML usa una Region con el path SVG del ícono de carrito como graphic
        // y el style "-fx-background-color: #111111" sobre la Region
        final String CARRITO_SVG =
            "M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 " +
            "50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 " +
            "23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.9" +
            "-58.5L74 58.5H24C10.7 58.5 0 47.8 0 34.5V24zM128 464a48 48 0 1 1 96 0 48 48 0 " +
            "1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z";

        Region iconoCarrito = new Region();
        iconoCarrito.setPrefWidth(18);
        iconoCarrito.setPrefHeight(18);
        iconoCarrito.setMaxWidth(Region.USE_PREF_SIZE);
        iconoCarrito.setMaxHeight(Region.USE_PREF_SIZE);
        iconoCarrito.setStyle(
            "-fx-shape: \"" + CARRITO_SVG + "\"; " +
            "-fx-background-color: #111111;"
        );
        iconoCarrito.getStyleClass().add("boton");
        iconoCarrito.setPadding(new Insets(0, 10, 0, 5));

        Button btnCarrito = new Button();
        btnCarrito.setMnemonicParsing(false);
        btnCarrito.setPrefWidth(80);
        btnCarrito.setPrefHeight(27);
        btnCarrito.setMaxWidth(Region.USE_PREF_SIZE);
        btnCarrito.setMinWidth(Region.USE_PREF_SIZE);
        btnCarrito.setAlignment(Pos.CENTER);
        btnCarrito.setContentDisplay(ContentDisplay.CENTER);
        btnCarrito.setStyle("-fx-background-color: #F7CD83; -fx-border-color: transparent; -fx-cursor: hand;");
        btnCarrito.setGraphic(iconoCarrito);
        HBox.setHgrow(btnCarrito, Priority.NEVER);

        btnCarrito.setOnAction(e -> {
            boolean ok = carritoService.agregarProducto(producto, 1);
            if (ok) {
                AlertaUtil.mostrarInformacion("Carrito",
                    "\"" + producto.getNombreProducto() + "\" agregado al carrito.");
            } else {
                AlertaUtil.mostrarAdvertencia("Carrito",
                    "No se pudo agregar. Verifica el stock.");
            }
        });

        // ── SPACER entre precio y botón ───────────────────────────────────────
        Pane espaciador = new Pane();
        espaciador.setPrefWidth(72);
        espaciador.setPrefHeight(40);
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        // ── HBOX precio + espacio + carrito ───────────────────────────────────
        HBox hboxPrecio = new HBox(txtPrecio, espaciador, btnCarrito);
        hboxPrecio.setAlignment(Pos.CENTER_LEFT);
        hboxPrecio.setPrefWidth(200); // valor del FXML
        hboxPrecio.setPrefHeight(100);

        // ── VBOX tarjeta final ────────────────────────────────────────────────
        VBox tarjeta = new VBox(stackImagen, txtNombre, hboxPrecio);
        tarjeta.setMaxWidth(Region.USE_PREF_SIZE);   // maxWidth="-Infinity" en FXML
        tarjeta.setMaxHeight(Region.USE_PREF_SIZE);
        tarjeta.setMinWidth(Region.USE_PREF_SIZE);
        tarjeta.setMinHeight(Region.USE_PREF_SIZE);
        tarjeta.setPrefWidth(280);
        tarjeta.setPrefHeight(410);
        FlowPane.setMargin(tarjeta, new Insets(0));

        return tarjeta;
    }

    private void actualizarEstiloFavorito(ToggleButton btn) {
        btn.setStyle(btn.isSelected() ? "-fx-text-fill: #e74c3c;" : "-fx-text-fill: #aaaaaa;");
    }

    private Label mensajeVacio(String texto) {
        Label lbl = new Label(texto);
        lbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #aaaaaa;");
        FlowPane.setMargin(lbl, new Insets(40));
        return lbl;
    }
    
    // Filtro de productos por categoria
    
    @FXML
    private void filtrarPorCategoria(ActionEvent event) {
        if (contenedorProductosHome == null) return;

        ToggleButton seleccionado = (ToggleButton) menuHome.getSelectedToggle();
        if (seleccionado == null) {
            cargarProductos();
            return;
        }

        String categoria = seleccionado.getText();
        ArrayList<Producto> filtrados = productoService.buscarPorCategoriaOrdenadoPorPrecio(categoria);

        contenedorProductosHome.getChildren().clear();
        if (filtrados.isEmpty()) {
            contenedorProductosHome.getChildren().add(mensajeVacio("No hay productos en \"" + categoria + "\"."));
            return;
        }
        for (Producto p : filtrados) {
            contenedorProductosHome.getChildren().add(crearTarjetaProducto(p));
        }
    }

    // Los 5 métodos siguientes los usan los botones imagen de categoría del FXML
    @FXML private void filtrarModaBelleza(ActionEvent event) { activarToggleYFiltrar(tbtnModaBellezaHome); }
    @FXML private void filtrarOfertas(ActionEvent event)     { activarToggleYFiltrar(tbtnOfertasHome); }
    @FXML private void filtrarTecnologia(ActionEvent event)  { activarToggleYFiltrar(tbtnTecnologiaHome); }
    @FXML private void filtrarHogar(ActionEvent event)       { activarToggleYFiltrar(tbtnHogarHome); }
    @FXML private void filtrarDeportes(ActionEvent event)    { activarToggleYFiltrar(tbtnDeportesHome); }

    private void activarToggleYFiltrar(ToggleButton toggle) {
        if (toggle == null) return;
        toggle.setSelected(true);
        String categoria = toggle.getText();
        ArrayList<Producto> filtrados = productoService.buscarPorCategoriaOrdenadoPorPrecio(categoria);

        if (contenedorProductosHome == null) return;
        contenedorProductosHome.getChildren().clear();
        if (filtrados.isEmpty()) {
            contenedorProductosHome.getChildren().add(mensajeVacio("No hay productos en \"" + categoria + "\"."));
        } else {
            for (Producto p : filtrados) {
                contenedorProductosHome.getChildren().add(crearTarjetaProducto(p));
            }
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
