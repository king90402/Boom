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
import Raiz.Utilidades.Ordenamiento;
import java.io.File;

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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    @FXML private Pane paneTodosProductosHome;
    @FXML private VBox paneInfoProductosHome;
    
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

    // Panel "Todos los productos" — contenedor y filtros
    @FXML private FlowPane contenedorTodosProductos;
    @FXML private ComboBox<String> comboOrdenTodos;
    @FXML private ComboBox<String> comboCategoriaTodos;
    @FXML private ComboBox<String> comboEstadoTodos;
    @FXML private Text lblResultadosTodos;
    
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
    
    @FXML private ImageView imgDetalleProducto;
    @FXML private Text lblNombreDetalleProducto;
    @FXML private Text lblDescripcionDetalleProducto;
    @FXML private Text lblPrecioDetalleProducto;
    @FXML private Text lblEstadoDetalleProducto;
    @FXML private Text lblStockDetalleProducto;
    @FXML private Spinner<Integer> spinnerCantidadDetalle;
    @FXML private Button btnAgregarCarritoDetalle;
    @FXML private Button btnFavoritoDetalle;
    @FXML private Region iconoFavDetalleRegion;

    private Producto productoActualDetalle;
    // ----- Inicializacion
    
    @FXML
    public void initialize() {
        inicializarToggleGroups();
        inicializarComboBoxesTodos();
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
        imageView.setFitHeight(326);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        String RUTA_IMAGEN_DEFECTO = "/Raiz/Media/Error.png";

        String ruta = producto.getImagenProducto();
        boolean imagenCargada = false;
        
        if (ruta != null && !ruta.isEmpty()) {
            
        try {
                
            java.net.URL urlRecurso = getClass().getResource(ruta);
        
            if (urlRecurso != null) {
                imageView.setImage(new Image(urlRecurso.toExternalForm(), true));
                imagenCargada = true;
            } else {

                String rutaFisicaProyecto = System.getProperty("user.dir");
                File archivoFisico = new File(rutaFisicaProyecto + "/src" + ruta);
            
                if (archivoFisico.exists()) {
                imageView.setImage(new Image(archivoFisico.toURI().toString(), true));
                imagenCargada = true;
                }   else {
                    System.err.println("[Tarjeta] Archivo no encontrado:" + ruta);
                }
            }
        } catch (Exception e) {
            System.err.println("[Tarjeta] Excepción crítica al intentar cargar: " + ruta);
            e.printStackTrace(); 
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

         Button btnFavorito = crearBotonFavorito(producto);
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

        btnCarrito.setOnAction(e -> mostrarDetalleProducto(producto));

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

    private Button crearBotonFavorito(Producto producto) {
    Region icono = new Region();
    boolean esFav = favoritosService.esFavorito(producto.getIdProducto());
    icono.getStyleClass().add(esFav ? "icon-corazonf" : "icon-corazon");
    icono.setMaxWidth(Region.USE_PREF_SIZE);
    icono.setMaxHeight(Region.USE_PREF_SIZE);
    icono.setMinWidth(Region.USE_PREF_SIZE);
    icono.setMinHeight(Region.USE_PREF_SIZE);

    Button btn = new Button();
    btn.setMnemonicParsing(false);
    btn.setPrefWidth(40);
    btn.setPrefHeight(40);
    btn.getStyleClass().add("boton-favorito");
    btn.setGraphic(icono);

    aplicarColorCorazon(icono, esFav);

    btn.setOnAction(e -> {
        favoritosService.toggleFavorito(producto);
        boolean ahora = favoritosService.esFavorito(producto.getIdProducto());
        aplicarColorCorazon(icono, ahora);
    });

    return btn;
    }

    private void aplicarColorCorazon(Region icono, boolean esFavorito) {
    if (esFavorito) {
        icono.getStyleClass().remove("icon-corazon");
        if (!icono.getStyleClass().contains("icon-corazonf")) {
            icono.getStyleClass().add("icon-corazonf");
        }
        icono.setStyle("-fx-background-color: #FF3B5C; -fx-scale-x: 1.05; -fx-scale-y: 1.05;");
    } else {
        icono.getStyleClass().remove("icon-corazonf");
        if (!icono.getStyleClass().contains("icon-corazon")) {
            icono.getStyleClass().add("icon-corazon");
        }
      icono.setStyle("-fx-background-color: transparent; -fx-border-color: #111111; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
    }
}
    
    private Label mensajeVacio(String texto) {
        Label lbl = new Label(texto);
        lbl.setStyle("-fx-font-size: 16px; -fx-text-fill: #aaaaaa;");
        FlowPane.setMargin(lbl, new Insets(40));
        return lbl;
    }
    
    private void inicializarComboBoxesTodos() {
        if (comboOrdenTodos != null) {
            comboOrdenTodos.getItems().addAll(
                "Nombre A→Z", "Nombre Z→A", "Precio: menor a mayor", "Precio: mayor a menor"
            );
            comboOrdenTodos.setPromptText("Ordenar por");
        }
        if (comboCategoriaTodos != null) {
            comboCategoriaTodos.getItems().add("Todas");
            comboCategoriaTodos.getItems().addAll(ProductoServicio.CATEGORIAS);
            comboCategoriaTodos.setValue("Todas");
        }
        if (comboEstadoTodos != null) {
            comboEstadoTodos.getItems().addAll("Todos", "Nuevo", "Usado");
            comboEstadoTodos.setValue("Todos");
        }
    }

    // ── Rellena el FlowPane de "Todos los Productos" aplicando los 3 filtros ──
    @FXML
    private void aplicarFiltrosTodos(ActionEvent event) {
        if (contenedorTodosProductos == null) return;

        // 1. Obtener valores seleccionados
        String categoriaSeleccionada = (comboCategoriaTodos != null) ? comboCategoriaTodos.getValue() : "Todas";
        String estadoSeleccionado    = (comboEstadoTodos   != null) ? comboEstadoTodos.getValue()    : "Todos";
        String ordenSeleccionado     = (comboOrdenTodos    != null) ? comboOrdenTodos.getValue()      : null;

        // 2. Obtener lista base según categoría
        ArrayList<Producto> lista;
        if (categoriaSeleccionada == null || categoriaSeleccionada.equals("Todas")) {
            lista = productoService.obtenerTodos();
        } else {
            lista = productoService.buscarPorCategoria(categoriaSeleccionada);
        }

        // 3. Filtrar por estado
        if (estadoSeleccionado != null && !estadoSeleccionado.equals("Todos")) {
            ArrayList<Producto> filtradoEstado = new ArrayList<>();
            for (Producto p : lista) {
                if (estadoSeleccionado.equalsIgnoreCase(p.getEstadoProducto())) {
                    filtradoEstado.add(p);
                }
            }
            lista = filtradoEstado;
        }

        // 4. Ordenar
        if (ordenSeleccionado != null) {
            switch (ordenSeleccionado) {
                case "Nombre A→Z":
                    Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_NOMBRE);
                    break;
                case "Nombre Z→A":
                    Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_NOMBRE);
                    java.util.Collections.reverse(lista);
                    break;
                case "Precio: menor a mayor":
                    Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_PRECIO_ASC);
                    break;
                case "Precio: mayor a menor":
                    Ordenamiento.quickSort(lista, Ordenamiento.COMPARAR_POR_PRECIO_DESC);
                    break;
            }
        }

        // 5. Renderizar
        contenedorTodosProductos.getChildren().clear();
        if (lblResultadosTodos != null) {
            lblResultadosTodos.setText(lista.size() + " resultado" + (lista.size() == 1 ? "" : "s"));
        }
        if (lista.isEmpty()) {
            contenedorTodosProductos.getChildren().add(mensajeVacio("No hay productos con esos filtros."));
        } else {
            for (Producto p : lista) {
                contenedorTodosProductos.getChildren().add(crearTarjetaTodos(p));
            }
        }
    }

    private VBox crearTarjetaTodos(Producto producto) {
        final String CARRITO_SVG =
            "M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 " +
            "50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 " +
            "23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.9" +
            "-58.5L74 58.5H24C10.7 58.5 0 47.8 0 34.5V24zM128 464a48 48 0 1 1 96 0 48 48 0 " +
            "1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z";

        ImageView imageView = new ImageView();
        imageView.setFitWidth(246);
        imageView.setFitHeight(278);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        
        String ruta = producto.getImagenProducto();
        boolean imagenCargada = false;
        
        if (ruta != null && !ruta.isEmpty()) {
            
        try {
                
            java.net.URL urlRecurso = getClass().getResource(ruta);
        
            if (urlRecurso != null) {
                imageView.setImage(new Image(urlRecurso.toExternalForm(), true));
                imagenCargada = true;
            } else {

                String rutaFisicaProyecto = System.getProperty("user.dir");
                File archivoFisico = new File(rutaFisicaProyecto + "/src" + ruta);
            
                if (archivoFisico.exists()) {
                imageView.setImage(new Image(archivoFisico.toURI().toString(), true));
                imagenCargada = true;
                }   else {
                    System.err.println("[Tarjeta] Archivo no encontrado:" + ruta);
                }
            }
        } catch (Exception e) {
            System.err.println("[Tarjeta] Excepción crítica al intentar cargar: " + ruta);
            e.printStackTrace(); 
        }
    }
        
        DropShadow sombra = new DropShadow();
        sombra.setHeight(5); sombra.setWidth(5);
        sombra.setOffsetX(1); sombra.setOffsetY(1); sombra.setRadius(2);
        sombra.setColor(Color.rgb(151, 151, 151));
        imageView.setEffect(sombra);

        // Botón favorito
        Button btnFavorito = crearBotonFavorito(producto);
        StackPane.setAlignment(btnFavorito, Pos.TOP_LEFT);
        StackPane.setMargin(btnFavorito, new Insets(10, 0, 0, 10));

        StackPane stack = new StackPane(imageView, btnFavorito);
        stack.setMaxWidth(Double.MAX_VALUE);
        stack.setMaxHeight(Double.MAX_VALUE);
        stack.setMinHeight(Region.USE_PREF_SIZE);
        stack.setPrefHeight(280);
        HBox.setHgrow(stack, Priority.ALWAYS);
        VBox.setVgrow(stack, Priority.ALWAYS);

        Text txtNombre = new Text(producto.getNombreProducto());
        txtNombre.setStrokeType(StrokeType.OUTSIDE); txtNombre.setStrokeWidth(0);
        txtNombre.setWrappingWidth(239.286);
        txtNombre.setFont(Font.font("Poppins Regular", 14));
        VBox.setMargin(txtNombre, new Insets(10, 0, 0, 0));

        Text txtPrecio = new Text(producto.getPrecioFormateado());
        txtPrecio.setStrokeType(StrokeType.OUTSIDE); txtPrecio.setStrokeWidth(0);
        txtPrecio.setFont(Font.font("Poppins ExtraBold", 20));

        Region iconoCarrito = new Region();
        iconoCarrito.setPrefWidth(18); iconoCarrito.setPrefHeight(18);
        iconoCarrito.setMaxWidth(Region.USE_PREF_SIZE); iconoCarrito.setMaxHeight(Region.USE_PREF_SIZE);
        iconoCarrito.setStyle("-fx-shape: \"" + CARRITO_SVG + "\"; -fx-background-color: #111111;");
        iconoCarrito.getStyleClass().add("boton");
        iconoCarrito.setPadding(new Insets(0, 10, 0, 5));
        Button btnCarrito = new Button();
        btnCarrito.setMnemonicParsing(false);
        btnCarrito.setPrefWidth(60); btnCarrito.setPrefHeight(26);
        btnCarrito.setMaxWidth(Region.USE_PREF_SIZE); btnCarrito.setMinWidth(Region.USE_PREF_SIZE);
        btnCarrito.setAlignment(Pos.CENTER); btnCarrito.setContentDisplay(ContentDisplay.CENTER);
        btnCarrito.setStyle("-fx-background-color: #F7CD83; -fx-border-color: transparent; -fx-cursor: hand;");
        btnCarrito.setGraphic(iconoCarrito);
        HBox.setHgrow(btnCarrito, Priority.NEVER);
        
        btnCarrito.setOnAction(e -> mostrarDetalleProducto(producto));

        Pane espaciador = new Pane();
        espaciador.setPrefWidth(63); espaciador.setPrefHeight(40);
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        HBox hbox = new HBox(txtPrecio, espaciador, btnCarrito);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefWidth(240); hbox.setPrefHeight(42);

        VBox tarjeta = new VBox(stack, txtNombre, hbox);
        tarjeta.setMaxWidth(Double.MAX_VALUE);
        tarjeta.setMaxHeight(Double.MAX_VALUE);
        tarjeta.setMinWidth(Region.USE_PREF_SIZE);
        tarjeta.setMinHeight(Region.USE_PREF_SIZE);
        FlowPane.setMargin(tarjeta, new Insets(0));
        return tarjeta;
    }
    
    // Filtro de productos por categoria
    
    @FXML
    private void filtrarPorCategoria(ActionEvent event) {
        ToggleButton seleccionado = (ToggleButton) menuHome.getSelectedToggle();
        if (seleccionado == null) return;

        // Desmarcar para que el menú no quede "activo" al salir del inicio
        seleccionado.setSelected(false);

        irATodosConCategoria(seleccionado.getText());
    }

    // Los 5 métodos siguientes los usan los botones imagen de categoría del FXML
    @FXML private void filtrarModaBelleza(ActionEvent event) { irATodosConCategoria("Moda y Belleza"); }
    @FXML private void filtrarOfertas(ActionEvent event)     { irATodosConCategoria("Ofertas"); }
    @FXML private void filtrarTecnologia(ActionEvent event)  { irATodosConCategoria("Tecnologia"); }
    @FXML private void filtrarHogar(ActionEvent event)       { irATodosConCategoria("Hogar"); }
    @FXML private void filtrarDeportes(ActionEvent event)    { irATodosConCategoria("Deportes"); }

    private void irATodosConCategoria(String categoria) {

        if (comboCategoriaTodos != null) comboCategoriaTodos.setValue(categoria);
        if (comboEstadoTodos != null)    comboEstadoTodos.setValue("Todos");
        if (comboOrdenTodos  != null)    comboOrdenTodos.setValue(null);
        
        mostrarTodosProductos();
    }
    
    // ----- Navegacion
    
    private void ocultarTodosLosPaneles() {
    javafx.scene.layout.Region[] panes = {
        paneInicioHome, panePerfilHome, paneCarritoHome,
        paneTodosProductosHome, paneInfoProductosHome
    };
    for (javafx.scene.layout.Region p : panes) {
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
    cargarProductos();            
    cargarProductosSecundarios(); 
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
    public void mostrarTodosProductos() {
        ocultarTodosLosPaneles();
        if (paneTodosProductosHome != null) {
            paneTodosProductosHome.setVisible(true);
            paneTodosProductosHome.setManaged(true);
        }
        aplicarFiltrosTodos(null);
    }
    
    @FXML
public void mostrarInfoProductos() {
    ocultarTodosLosPaneles();
    if (paneInfoProductosHome != null) {
        paneInfoProductosHome.setVisible(true);
        paneInfoProductosHome.setManaged(true);
    }
}

public void mostrarDetalleProducto(Producto producto) {
    productoActualDetalle = producto;

    ocultarTodosLosPaneles();
    if (paneInfoProductosHome != null) {
        paneInfoProductosHome.setVisible(true);
        paneInfoProductosHome.setManaged(true);
    }

    if (imgDetalleProducto != null) {
        String ruta = producto.getImagenProducto();
        if (ruta != null && !ruta.isEmpty()) {
            try { imgDetalleProducto.setImage(new Image(ruta, true)); }
            catch (Exception e) { System.err.println("[Detalle] Imagen: " + ruta); }
        }
    }

    if (lblNombreDetalleProducto    != null) lblNombreDetalleProducto.setText(producto.getNombreProducto());
    if (lblDescripcionDetalleProducto != null) lblDescripcionDetalleProducto.setText(
        producto.getDetallesProducto() != null && !producto.getDetallesProducto().isEmpty()
            ? producto.getDetallesProducto() : "Sin descripción.");
    if (lblPrecioDetalleProducto    != null) lblPrecioDetalleProducto.setText(producto.getPrecioFormateado());
    if (lblEstadoDetalleProducto    != null) lblEstadoDetalleProducto.setText(producto.getEstadoProducto());
    if (lblStockDetalleProducto     != null) lblStockDetalleProducto.setText(
        "Stock disponible: " + producto.getCantidadProducto());

    if (spinnerCantidadDetalle != null) {
        int stock = Math.max(1, producto.getCantidadProducto());
        SpinnerValueFactory<Integer> factory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, stock, 1);
        spinnerCantidadDetalle.setValueFactory(factory);
        spinnerCantidadDetalle.setEditable(true);
    }

    if (iconoFavDetalleRegion != null) {
        boolean esFav = favoritosService.esFavorito(producto.getIdProducto());
        aplicarColorCorazon(iconoFavDetalleRegion, esFav);
    }
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
            cargarTarjetasFavoritos();
        }
        if (tbtnFavoritosPerfilHome != null) tbtnFavoritosPerfilHome.setSelected(true);
    }

    private void cargarTarjetasFavoritos() {
        if (paneFavoritosPerfilHome == null) return;

        paneFavoritosPerfilHome.getChildren().clear();

        ArrayList<Producto> favoritos = favoritosService.obtenerFavoritos();

        if (favoritos.isEmpty()) {
            Label vacio = new Label("No tienes productos en favoritos aún.");
            vacio.setStyle("-fx-font-size: 16px; -fx-text-fill: #aaaaaa; -fx-padding: 40px;");
            paneFavoritosPerfilHome.getChildren().add(vacio);
            return;
        }

        Text titulo = new Text("Mis Favoritos (" + favoritos.size() + ")");
        titulo.setFont(Font.font("Poppins Regular", 20));
        titulo.setStrokeType(StrokeType.OUTSIDE);
        titulo.setStrokeWidth(0);
        VBox.setMargin(titulo, new Insets(20, 0, 10, 20));
        paneFavoritosPerfilHome.getChildren().add(titulo);

        FlowPane flow = new FlowPane();
        flow.setHgap(20);
        flow.setVgap(40);
        flow.setPadding(new Insets(10, 20, 20, 20));
        VBox.setVgrow(flow, Priority.ALWAYS);

        for (Producto p : favoritos) {
            flow.getChildren().add(crearTarjetaFavorito(p, flow));
        }

        paneFavoritosPerfilHome.getChildren().add(flow);
    }

   
    private VBox crearTarjetaFavorito(Producto producto, FlowPane contenedor) {
        final String CARRITO_SVG =
            "M0 24C0 10.7 10.7 0 24 0H69.5c22 0 41.5 12.8 50.6 32h411c26.3 0 45.5 25 38.6 " +
            "50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3H170.7l5.4 28.5c2.2 11.3 12.1 19.5 " +
            "23.6 19.5H488c13.3 0 24 10.7 24 24s-10.7 24-24 24H199.7c-34.6 0-64.3-24.6-70.9" +
            "-58.5L74 58.5H24C10.7 58.5 0 47.8 0 34.5V24zM128 464a48 48 0 1 1 96 0 48 48 0 " +
            "1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96z";

        ImageView imageView = new ImageView();
        imageView.setFitWidth(246); imageView.setFitHeight(278);
        imageView.setPickOnBounds(true); imageView.setPreserveRatio(true);
        
        String ruta = producto.getImagenProducto();
        boolean imagenCargada = false;
        
        if (ruta != null && !ruta.isEmpty()) {
            
        try {
                
            java.net.URL urlRecurso = getClass().getResource(ruta);
        
            if (urlRecurso != null) {
                imageView.setImage(new Image(urlRecurso.toExternalForm(), true));
                imagenCargada = true;
            } else {

                String rutaFisicaProyecto = System.getProperty("user.dir");
                File archivoFisico = new File(rutaFisicaProyecto + "/src" + ruta);
            
                if (archivoFisico.exists()) {
                imageView.setImage(new Image(archivoFisico.toURI().toString(), true));
                imagenCargada = true;
                }   else {
                    System.err.println("[Tarjeta] Archivo no encontrado:" + ruta);
                }
            }
        } catch (Exception e) {
            System.err.println("[Tarjeta] Excepción crítica al intentar cargar: " + ruta);
            e.printStackTrace(); 
        }
    }
        
        DropShadow sombra = new DropShadow();
        sombra.setHeight(5); sombra.setWidth(5); sombra.setOffsetX(1); sombra.setOffsetY(1);
        sombra.setRadius(2); sombra.setColor(Color.rgb(151,151,151));
        imageView.setEffect(sombra);

        // Creamos el ícono aquí para tener referencia directa sin cast
        Region iconoFav = new Region();
        iconoFav.getStyleClass().add("icon-corazonf");
        iconoFav.setMaxWidth(Region.USE_PREF_SIZE);
        iconoFav.setMaxHeight(Region.USE_PREF_SIZE);
        iconoFav.setMinWidth(Region.USE_PREF_SIZE);
        iconoFav.setMinHeight(Region.USE_PREF_SIZE);
        aplicarColorCorazon(iconoFav, true); // siempre marcado al entrar a favoritos

        Button btnFavorito = new Button();
        btnFavorito.setMnemonicParsing(false);
        btnFavorito.setPrefWidth(40);
        btnFavorito.setPrefHeight(40);
        btnFavorito.getStyleClass().add("boton-favorito");
        btnFavorito.setGraphic(iconoFav);

        VBox[] ref = new VBox[1];

        btnFavorito.setOnAction(e -> {
            favoritosService.toggleFavorito(producto);
            boolean ahora = favoritosService.esFavorito(producto.getIdProducto());
            aplicarColorCorazon(iconoFav, ahora);
            if (!ahora && contenedor != null && ref[0] != null) {
                contenedor.getChildren().remove(ref[0]);
            }
        });
        StackPane.setAlignment(btnFavorito, Pos.TOP_LEFT);
        StackPane.setAlignment(btnFavorito, Pos.TOP_LEFT);
        StackPane.setMargin(btnFavorito, new Insets(10, 0, 0, 10));

        StackPane stack = new StackPane(imageView, btnFavorito);
        stack.setMaxWidth(Double.MAX_VALUE); stack.setMaxHeight(Double.MAX_VALUE);
        stack.setMinHeight(Region.USE_PREF_SIZE); stack.setPrefHeight(280);

        Text txtNombre = new Text(producto.getNombreProducto());
        txtNombre.setStrokeType(StrokeType.OUTSIDE); txtNombre.setStrokeWidth(0);
        txtNombre.setWrappingWidth(239.286);
        txtNombre.setFont(Font.font("Poppins Regular", 14));
        VBox.setMargin(txtNombre, new Insets(10, 0, 0, 0));

        Text txtPrecio = new Text(producto.getPrecioFormateado());
        txtPrecio.setStrokeType(StrokeType.OUTSIDE); txtPrecio.setStrokeWidth(0);
        txtPrecio.setFont(Font.font("Poppins ExtraBold", 20));

        Region iconoCarrito = new Region();
        iconoCarrito.setPrefWidth(18); iconoCarrito.setPrefHeight(18);
        iconoCarrito.setMaxWidth(Region.USE_PREF_SIZE); iconoCarrito.setMaxHeight(Region.USE_PREF_SIZE);
        iconoCarrito.setStyle("-fx-shape: \"" + CARRITO_SVG + "\"; -fx-background-color: #111111;");
        iconoCarrito.getStyleClass().add("boton");
        iconoCarrito.setPadding(new Insets(0, 10, 0, 5));
        Button btnCarrito = new Button();
        btnCarrito.setMnemonicParsing(false);
        btnCarrito.setPrefWidth(60); btnCarrito.setPrefHeight(26);
        btnCarrito.setMaxWidth(Region.USE_PREF_SIZE); btnCarrito.setMinWidth(Region.USE_PREF_SIZE);
        btnCarrito.setAlignment(Pos.CENTER); btnCarrito.setContentDisplay(ContentDisplay.CENTER);
        btnCarrito.setStyle("-fx-background-color: #F7CD83; -fx-border-color: transparent; -fx-cursor: hand;");
        btnCarrito.setGraphic(iconoCarrito);
        HBox.setHgrow(btnCarrito, Priority.NEVER);
        btnCarrito.setOnAction(e -> mostrarDetalleProducto(producto));

        Pane espaciador = new Pane();
        espaciador.setPrefWidth(63); espaciador.setPrefHeight(40);
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        HBox hbox = new HBox(txtPrecio, espaciador, btnCarrito);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefWidth(240); hbox.setPrefHeight(42);

        VBox tarjeta = new VBox(stack, txtNombre, hbox);
        tarjeta.setMaxWidth(Double.MAX_VALUE); tarjeta.setMaxHeight(Double.MAX_VALUE);
        tarjeta.setMinWidth(Region.USE_PREF_SIZE); tarjeta.setMinHeight(Region.USE_PREF_SIZE);
        FlowPane.setMargin(tarjeta, new Insets(0));

        ref[0] = tarjeta;
        return tarjeta;
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
    @FXML
private void accionAgregarCarritoDetalle(ActionEvent event) {
    if (productoActualDetalle == null) return;
    int cantidad = spinnerCantidadDetalle != null ? spinnerCantidadDetalle.getValue() : 1;
    boolean ok = carritoService.agregarProducto(productoActualDetalle, cantidad);
    if (ok) {
        AlertaUtil.mostrarInformacion("Carrito",
            "\"" + productoActualDetalle.getNombreProducto() + "\" agregado al carrito.");
    } else {
        AlertaUtil.mostrarAdvertencia("Carrito",
            "No se pudo agregar. Verifica el stock disponible.");
    }
}

@FXML
private void accionComprarAhoraDetalle(ActionEvent event) {
    if (productoActualDetalle == null) return;
    int cantidad = spinnerCantidadDetalle != null ? spinnerCantidadDetalle.getValue() : 1;
    boolean ok = carritoService.agregarProducto(productoActualDetalle, cantidad);
    if (ok) {
        AlertaUtil.mostrarInformacion("Compra",
            "\"" + productoActualDetalle.getNombreProducto() + "\" listo. Ve al carrito para finalizar.");
        mostrarCarrito();
    } else {
        AlertaUtil.mostrarAdvertencia("Compra",
            "No se pudo procesar. Verifica el stock disponible.");
    }
}

@FXML
private void accionFavoritoDetalle(ActionEvent event) {
    if (productoActualDetalle == null) return;
    favoritosService.toggleFavorito(productoActualDetalle);
    boolean ahora = favoritosService.esFavorito(productoActualDetalle.getIdProducto());
    if (iconoFavDetalleRegion != null) aplicarColorCorazon(iconoFavDetalleRegion, ahora);
}
}
