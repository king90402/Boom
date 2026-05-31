/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AdminControladora.java to edit this template
 */

package Raiz.Controladoras;

import Raiz.Modelos.Producto;
import Raiz.Modelos.Usuario;
import Raiz.Modelos.Historial;
import Raiz.Modelos.Pedido;
import Raiz.Servicios.ProductoServicio;
import Raiz.Servicios.SesionServicio;
import Raiz.Servicios.UsuarioServicio;
import Raiz.Servicios.HistorialServicio;
import Raiz.Servicios.PedidoServicio;
import Raiz.Utilidades.AlertaUtil;
import Raiz.Utilidades.DepartamentosColombia;
import Raiz.Utilidades.Validaciones;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

/**
 * @author alejo
 */

// -------------------- Clase encargada del manejo total de la vista_administracion

public class AdminControladora {
    
    // ---------- Servicios
    private final SesionServicio sesionServicio = SesionServicio.getInstancia();
    private final ProductoServicio productoServicio = ProductoServicio.getInstancia();
    private final UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private final HistorialServicio historialServicio = HistorialServicio.getInstancia();
    private final PedidoServicio pedidoServicio = PedidoServicio.getInstancia();

    // -------------------- Paneles principales
    @FXML private Pane panePerfilAdmin;
    @FXML private Pane paneEditarInfoAdmin;
    @FXML private Pane paneEstadisticasAdmin;
    @FXML private Pane paneInventarioAdmin;
    @FXML private Pane panePedidosAdmin;
    @FXML private Pane paneAdminPersoAdmin;        
    @FXML private Pane paneHistorialAdmin;        
    
    // ---------- Paneles secundarios
    @FXML private Pane paneRestaNuevo_Producto;
    @FXML private Pane paneRestaNuevo_ProductoPaso1;
    @FXML private Pane paneRestaNuevo_ProductoPaso2;
    @FXML private Pane paneRestaNuevo_ProductoPaso3;
    @FXML private Pane paneEditarUsuario;
    
    // ---------- Toggle buttons
    @FXML private ToggleButton tbtnPerfilAdmin;
    @FXML private ToggleButton tbtnEstadisticasAdmin;
    @FXML private ToggleButton tbtnInventarioAdmin;
    @FXML private ToggleButton tbtnPedidosAdmin;
    @FXML private ToggleButton tbtnAdminPersoAdmin;
    @FXML private ToggleButton tbtnHistorialAdmin;
    
    private ToggleGroup barraAdmin;
    
    // ---------- Campos nuevo/editar producto
    @FXML private TextField txtnombreproductO;
    @FXML private TextField txtmarca;
    @FXML private TextArea txtDetalles;
    @FXML private TextField txtprecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtruta;
    @FXML private CheckBox checknuevo;
    @FXML private CheckBox checkviejo;
    @FXML private ImageView previsualizacion;
    @FXML private Button btnGuardarProducto;
    @FXML private ComboBox<String> comboCategoriaProducto;
    @FXML private CheckBox checkEnOferta;
    
    private String rutaImagenSeleccionada = "";
    private Producto productoEditando = null;
    
    // ---------- Campos edicion usuario/cliente
    @FXML private TextField txtCorreoEditarUsuario;
    @FXML private TextField txtNombreEditarUsuario;
    @FXML private TextField txtApellidoEditarUsuario;
    @FXML private TextField txtCelularEditarUsuario;
    @FXML private TextField txtDocumentoEditarUsuario;
    @FXML private TextField txtFechaNacimientoEditarUsuario;
    @FXML private ComboBox<String> comboDepartamentoEditarUsuario;
    @FXML private ComboBox<String> comboCiudadEditarUsuario;
    @FXML private TextField txtDireccionEditarUsuario;
    
    // ---------- Campos edicion info propia 
    @FXML private TextField txtCorreoMiCuenta;
    @FXML private TextField txtNombreMiCuenta;
    @FXML private TextField txtApellidoMiCuenta;
    @FXML private TextField txtCelularMiCuenta;
    @FXML private TextField txtDocumentoMiCuenta;
    @FXML private TextField txtFechaNacimientoMiCuenta;
    @FXML private ComboBox<String> comboDepartamentoMiCuenta;
    @FXML private ComboBox<String> comboCiudadMiCuenta;
    @FXML private TextField txtDireccionMiCuenta;
    
    // ---------- Labels perfil admin
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
    
    // ---------- Labels estadisticas
    @FXML private Text lblBienvenidoEstadisticas;
    @FXML private Text lblVentasMes;
    @FXML private Text lblClientesActivos;
    @FXML private Text lblPedidosRealizados;
    @FXML private Text lblMejorCliente;
    @FXML private BarChart<String, Number> chartVentas;
    @FXML private PieChart chartCategorias;
    
    // ---------- Tabla de productos
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombreProducto;
    @FXML private TableColumn<Producto, String> colMarcaProducto;
    @FXML private TableColumn<Producto, Integer> colStockProducto;
    @FXML private TableColumn<Producto, Double> colPrecioProducto;
    @FXML private TableColumn<Producto, String> colEstadoProducto;
    
    // ---------- Tabla de pedidos
    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, String> colIdPedido;
    @FXML private TableColumn<Pedido, String> colClientePedido;
    @FXML private TableColumn<Pedido, String> colProductosPedido;
    @FXML private TableColumn<Pedido, String> colTotalPedido;
    @FXML private TableColumn<Pedido, String> colFechaPedido;
    
    // ---------- Tabla de usuarios
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colIdUsuario;
    @FXML private TableColumn<Usuario, String> colNombreUsuario;
    @FXML private TableColumn<Usuario, String> colCorreoUsuario;
    @FXML private TableColumn<Usuario, String> colCelularUsuario;
    
    // ---------- Tabla historial
    @FXML private TableView<Historial> tablaHistorial;
    @FXML private TableColumn<Historial, String> colTipoHistorial;
    @FXML private TableColumn<Historial, String> colDescripcionHistorial;
    @FXML private TableColumn<Historial, String> colUsuarioHistorial;
    @FXML private TableColumn<Historial, String> colFechaHistorial;
    @FXML private ComboBox<String> comboFiltroHistorial;
    
    // ---- Usuario seleccionado para edicion
    private Usuario usuarioSeleccionado;
    
    // -------------------- Inicializacion
    @FXML
    public void initialize() {
        inicializarToggleGroups();
        inicializarComboBoxes();
        inicializarTablas();
        cargarDatosUsuario();
        mostrarPaneAdmin(panePerfilAdmin);
    }
    
    // ---------- Inicializacion de ToggleGroups
    private void inicializarToggleGroups() {
        barraAdmin = new ToggleGroup();
        if (tbtnPerfilAdmin != null) tbtnPerfilAdmin.setToggleGroup(barraAdmin);
        if (tbtnEstadisticasAdmin != null) tbtnEstadisticasAdmin.setToggleGroup(barraAdmin);
        if (tbtnInventarioAdmin != null) tbtnInventarioAdmin.setToggleGroup(barraAdmin);
        if (tbtnPedidosAdmin != null) tbtnPedidosAdmin.setToggleGroup(barraAdmin);
        if (tbtnAdminPersoAdmin != null) tbtnAdminPersoAdmin.setToggleGroup(barraAdmin);
        if (tbtnHistorialAdmin != null) tbtnHistorialAdmin.setToggleGroup(barraAdmin);
    }
    
    // ---------- Inicializacion de ComboBoxes
    private void inicializarComboBoxes() {
        if (comboCategoriaProducto != null) {
            comboCategoriaProducto.getItems().addAll(ProductoServicio.CATEGORIAS);
            comboCategoriaProducto.setValue(ProductoServicio.CATEGORIAS[0]);
        }
        if (comboDepartamentoEditarUsuario != null) {
            comboDepartamentoEditarUsuario.getItems().addAll(DepartamentosColombia.getDepartamentos());
            comboDepartamentoEditarUsuario.setOnAction(e -> {
                String depto = comboDepartamentoEditarUsuario.getValue();
                if (depto != null && comboCiudadEditarUsuario != null) {
                    comboCiudadEditarUsuario.getItems().clear();
                    comboCiudadEditarUsuario.getItems().addAll(DepartamentosColombia.getCiudades(depto));
                }
            });
        }
        
        if (comboDepartamentoMiCuenta != null) {
            comboDepartamentoMiCuenta.getItems().addAll(DepartamentosColombia.getDepartamentos());
            comboDepartamentoMiCuenta.setOnAction(e -> {
                String depto = comboDepartamentoMiCuenta.getValue();
                if (depto != null && comboCiudadMiCuenta != null) {
                    comboCiudadMiCuenta.getItems().clear();
                    comboCiudadMiCuenta.getItems().addAll(DepartamentosColombia.getCiudades(depto));
                }
            });
        }
        
        if (comboFiltroHistorial != null) {
            comboFiltroHistorial.getItems().addAll(
                "Todos", "Compras", "Carrito", "Favoritos", "Busquedas", 
                "Sesiones", "Acciones Admin"
            );
            comboFiltroHistorial.setValue("Todos");
            comboFiltroHistorial.setOnAction(e -> cargarHistorial());
        }
    }
    
    // ---------- Inicializacion de todas las tablas
    private void inicializarTablas() {
        inicializarTablaUsuarios();
        inicializarTablaProductos();
        inicializarTablaPedidos();
        inicializarTablaHistorial();
    }
    
    // ---- Tabla de usuarios
    private void inicializarTablaUsuarios() {
        if (tablaUsuarios != null) {
            if (colIdUsuario != null) colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
            if (colNombreUsuario != null) colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
            if (colCorreoUsuario != null) colCorreoUsuario.setCellValueFactory(new PropertyValueFactory<>("correo"));
            if (colCelularUsuario != null) colCelularUsuario.setCellValueFactory(new PropertyValueFactory<>("celular"));
        }
    }
    
    // ---- Tabla de productos
    private void inicializarTablaProductos() {
        if (tablaProductos != null) {
            if (colNombreProducto != null) colNombreProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
            if (colMarcaProducto != null) colMarcaProducto.setCellValueFactory(new PropertyValueFactory<>("marcaProducto"));
            if (colStockProducto != null) colStockProducto.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
            if (colPrecioProducto != null) colPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precioFormateado"));
            if (colEstadoProducto != null) colEstadoProducto.setCellValueFactory(new PropertyValueFactory<>("estadoProducto"));
        }
    }
    
    // ---- Tabla de pedidos
    private void inicializarTablaPedidos() {
        if (tablaPedidos != null) {
            if (colIdPedido != null) colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
            if (colClientePedido != null) colClientePedido.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
            if (colProductosPedido != null) colProductosPedido.setCellValueFactory(new PropertyValueFactory<>("cantidadItems"));
            if (colTotalPedido != null) colTotalPedido.setCellValueFactory(new PropertyValueFactory<>("totalFormateado"));
            if (colFechaPedido != null) colFechaPedido.setCellValueFactory(new PropertyValueFactory<>("fechaFormateada"));
        }
    }
    
    // ---- Tabla de historial
    private void inicializarTablaHistorial() {
        if (tablaHistorial != null) {
            if (colTipoHistorial != null) colTipoHistorial.setCellValueFactory(new PropertyValueFactory<>("tipoTexto"));
            if (colDescripcionHistorial != null) colDescripcionHistorial.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            if (colUsuarioHistorial != null) colUsuarioHistorial.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
            if (colFechaHistorial != null) colFechaHistorial.setCellValueFactory(new PropertyValueFactory<>("fechaFormateada"));
        }
    }
    
    // ---------- Cargar datos del usuario actual en el perfil
    private void cargarDatosUsuario() {
        Usuario usuario = sesionServicio.getUsuarioActual();
        if (usuario == null) return;
        
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
    
    // -------------------- Navegacion entre paneles
    
    // ---------- Ocultar todos los paneles
    private void ocultarPanesAdmin() {
        Pane[] panes = {
            panePerfilAdmin, paneEstadisticasAdmin,
            paneInventarioAdmin, panePedidosAdmin,
            paneEditarInfoAdmin, paneRestaNuevo_Producto, paneAdminPersoAdmin,
            paneHistorialAdmin, paneEditarUsuario
        };
        for (Pane p : panes) {
            if (p != null) {
                p.setVisible(false);
                p.setManaged(false);
            }
        }
    }
    
    // ---------- Mostrar un panel especifico
    private void mostrarPaneAdmin(Pane paneMostrar) {
        ocultarPanesAdmin();
        if (paneMostrar != null) {
            paneMostrar.setVisible(true);
            paneMostrar.setManaged(true);
        }
    }
    
    // ---- Metodos de navegacion
    @FXML private void irPerfilAdmin() { 
        mostrarPaneAdmin(panePerfilAdmin); 
        cargarDatosUsuario();
    }
    
    @FXML private void irEditarInfoAdmin() { 
        mostrarPaneAdmin(paneEditarInfoAdmin); 
        cargarDatosMiCuenta();
    }
    
    @FXML private void irEstadisticasAdmin() { 
        mostrarPaneAdmin(paneEstadisticasAdmin); 
        cargarEstadisticas();
    }
    
    @FXML private void irInventarioAdmin() { 
        mostrarPaneAdmin(paneInventarioAdmin); 
        cargarListaProductos();
    }
    
    @FXML private void irPedidosAdmin() { 
        mostrarPaneAdmin(panePedidosAdmin); 
        cargarListaPedidos();
    }
    
    @FXML private void irAdministracionPersonalAdmin() { 
        mostrarPaneAdmin(paneAdminPersoAdmin);
        cargarListaClientes();
    }
    
    @FXML private void irHistorialAdmin() { 
        mostrarPaneAdmin(paneHistorialAdmin);
        cargarHistorial();
    }
    
    // ==================== ESTADISTICAS ====================
    
    // ---------- Cargar todas las estadisticas
    private void cargarEstadisticas() {
        Usuario admin = sesionServicio.getUsuarioActual();
        if (admin != null && lblBienvenidoEstadisticas != null) {
            lblBienvenidoEstadisticas.setText("Bienvenido, " + admin.getNombre());
        }
        
        // ---- Ventas del mes
        double ventasMes = pedidoServicio.obtenerVentasTotales();
        if (lblVentasMes != null) {
            lblVentasMes.setText(String.format("$%,.0f", ventasMes));
        }
        
        // ---- Clientes activos
        int clientesActivos = usuarioServicio.contarClientes();
        if (lblClientesActivos != null) {
            lblClientesActivos.setText(String.valueOf(clientesActivos));
        }
        
        // ---- Pedidos realizados
        int totalPedidos = pedidoServicio.obtenerTotalPedidos();
        if (lblPedidosRealizados != null) {
            lblPedidosRealizados.setText(String.valueOf(totalPedidos));
        }
        
        // ---- Mejor cliente
        String mejorCliente = pedidoServicio.obtenerMejorCliente();
        if (lblMejorCliente != null) {
            lblMejorCliente.setText(mejorCliente != null ? mejorCliente : "N/A");
        }
        
        // ---- Cargar graficos
        cargarGraficoVentas();
        cargarGraficoCategorias();
    }
    
    // ---- Grafico de barras de ventas
    private void cargarGraficoVentas() {
        if (chartVentas == null) return;
        
        chartVentas.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ventas");
        
        // Datos de ejemplo
        series.getData().add(new XYChart.Data<>("Ene", 1200000));
        series.getData().add(new XYChart.Data<>("Feb", 1500000));
        series.getData().add(new XYChart.Data<>("Mar", 1100000));
        series.getData().add(new XYChart.Data<>("Abr", 1800000));
        series.getData().add(new XYChart.Data<>("May", 2100000));
        
        chartVentas.getData().add(series);
    }
    
    // ---- Grafico de categorias
    private void cargarGraficoCategorias() {
        if (chartCategorias == null) return;
        
        chartCategorias.getData().clear();
        chartCategorias.getData().add(new PieChart.Data("Tecnologia", 45));
        chartCategorias.getData().add(new PieChart.Data("Hogar", 25));
        chartCategorias.getData().add(new PieChart.Data("Moda", 20));
        chartCategorias.getData().add(new PieChart.Data("Otros", 10));
    }
    
    // ==================== GESTION DE PRODUCTOS ====================
    
    // ---------- Cargar lista de productos en la tabla
    private void cargarListaProductos() {
        if (tablaProductos == null) return;
        
        ArrayList<Producto> productos = productoServicio.obtenerTodos();
        ObservableList<Producto> listaObservable = FXCollections.observableArrayList(productos);
        tablaProductos.setItems(listaObservable);
    }
    
    @FXML
    private void controlarSeleccion(ActionEvent event) {

        if (event.getSource() == checknuevo) {
            if (checknuevo.isSelected()) {
                checkviejo.setSelected(false);
            }
        } else if (event.getSource() == checkviejo) {
            if (checkviejo.isSelected()) {
                checknuevo  .setSelected(false);
            }
        }
    }
    
    // ---------- Agregar nuevo producto
    @FXML
    private void agregarNuevoProducto(ActionEvent event) {
        productoEditando = null;
        limpiarCamposProducto();
        mostrarPaneAdmin(paneRestaNuevo_Producto);
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso1);
    }
    
    // ---------- Editar producto seleccionado
    @FXML
    private void editarProductoSeleccionado(ActionEvent event) {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un producto", "Debe seleccionar un producto de la lista.");
            return;
        }
        
        productoEditando = seleccionado;
        cargarDatosProductoEdicion();
        mostrarPaneAdmin(paneRestaNuevo_Producto);
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso1);
    }
    
    // ---- Cargar datos del producto para edicion
    private void cargarDatosProductoEdicion() {
        if (productoEditando == null) return;
        
        if (txtnombreproductO != null) txtnombreproductO.setText(productoEditando.getNombreProducto());
        if (txtmarca != null) txtmarca.setText(productoEditando.getMarcaProducto());
        if (txtDetalles != null) txtDetalles.setText(productoEditando.getDetallesProducto());
        if (comboCategoriaProducto != null) comboCategoriaProducto.setValue(productoEditando.getCategoriaProducto());
        if (txtprecio != null) txtprecio.setText(String.valueOf(productoEditando.getPrecioProducto()));
        if (txtStock != null) txtStock.setText(String.valueOf(productoEditando.getCantidadProducto()));
        
        if (checknuevo != null && checkviejo != null) {
            checknuevo.setSelected("Nuevo".equalsIgnoreCase(productoEditando.getEstadoProducto()));
            checkviejo.setSelected("Usado".equalsIgnoreCase(productoEditando.getEstadoProducto()));
        }
        if (checkEnOferta != null) {
        checkEnOferta.setSelected(productoEditando.isEnOferta());
        }
        
        rutaImagenSeleccionada = productoEditando.getImagenProducto();
        if (txtruta != null) txtruta.setText(rutaImagenSeleccionada);
        
        String rutaString = productoEditando.getImagenProducto();

        if (rutaString != null && !rutaString.isEmpty()) {
            
            try {
 
                java.net.URL urlRecurso = getClass().getResource(rutaString);
        
                if (urlRecurso != null) {
                    previsualizacion.setImage(new Image(urlRecurso.toExternalForm(), true));
                } else {

                    String rutaFisicaProyecto = System.getProperty("user.dir");
                    File archivoFisico = new File(rutaFisicaProyecto + "/src" + rutaString);
            
                    if (archivoFisico.exists()) {
                        previsualizacion.setImage(new Image(archivoFisico.toURI().toString(), true));
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen en edición: " + e.getMessage());
            }
        } else {

        }
    }
    
    // ---------- Visualizar informacion completa del producto
    @FXML
    private void visualizarProductoSeleccionado(ActionEvent event) {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un producto", "Debe seleccionar un producto de la lista.");
            return;
        }
        
        String info = String.format(
            "ID: %s\nNombre: %s\nMarca: %s\nPrecio: %s\nStock: %d\nEstado: %s\nCategoria: %s",
            seleccionado.getIdProducto(),
            seleccionado.getNombreProducto(),
            seleccionado.getMarcaProducto(),
            seleccionado.getPrecioFormateado(),
            seleccionado.getCantidadProducto(),
            seleccionado.getEstadoProducto(),
            seleccionado.getCategoriaProducto()
        );
        
        AlertaUtil.mostrarInformacion("Informacion del Producto", info);
    }
    
    // ---------- Eliminar producto seleccionado
    @FXML
    private void eliminarProductoSeleccionado(ActionEvent event) {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un producto", "Debe seleccionar un producto de la lista.");
            return;
        }
        
        if (AlertaUtil.confirmarAccion("Eliminar Producto", 
            "¿Esta seguro de eliminar el producto: " + seleccionado.getNombreProducto() + "?")) {
            
            String idAdmin = sesionServicio.getIdUsuarioActual();
            if (productoServicio.eliminarProducto(seleccionado.getIdProducto(), idAdmin)) {
                AlertaUtil.mostrarInformacion("Producto eliminado", "El producto ha sido eliminado correctamente.");
                cargarListaProductos();
            } else {
                AlertaUtil.mostrarError("Error", "No se pudo eliminar el producto.");
            }
        }
    }
    
    // ---------- Navegacion entre pasos de nuevo producto
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
    
    @FXML private void irPaso1NuevoProducto() { mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso1); }
    @FXML private void irPaso2NuevoProducto() { mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso2); }
    @FXML private void irPaso3NuevoProducto() { mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso3); }
    
    // ---------- Seleccionar imagen del producto
    @FXML
    private void seleccionarImagen(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar Imagen del Producto");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
    );
    
    File archivo = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
    
    if (archivo != null) {
        try {
            // ---Cargamos la imagen original en memoria
            Image imagenOriginal = new Image(archivo.toURI().toString());
            
            // ---Definimos relacion aspecto
            double targetRatio = 6.0 / 7.0;
            double imageRatio = imagenOriginal.getWidth() / imagenOriginal.getHeight();
            
            int cropX = 0, cropY = 0;
            int cropW = (int) imagenOriginal.getWidth();
            int cropH = (int) imagenOriginal.getHeight();

            if (imageRatio > targetRatio) {
                cropW = (int) (imagenOriginal.getHeight() * targetRatio);
                cropX = (int) ((imagenOriginal.getWidth() - cropW) / 2);
            } else {
                cropH = (int) (imagenOriginal.getWidth() / targetRatio);
                cropY = (int) ((imagenOriginal.getHeight() - cropH) / 2);
            }

            PixelReader lector = imagenOriginal.getPixelReader();
            WritableImage imagenRecortada = new WritableImage(lector, cropX, cropY, cropW, cropH);

            // ---Guardamos la imagen en el proyecto
            String rutaProyecto = System.getProperty("user.dir");
            File directorioDestino = new File(rutaProyecto + "/src/Raiz/Media/Productos");
            
            if (!directorioDestino.exists()) {
                directorioDestino.mkdirs();
            }
            
            String nombreArchivoFinal = "prod_" + System.currentTimeMillis() + ".png"; 
            File archivoDestino = new File(directorioDestino, nombreArchivoFinal);
            
            ImageIO.write(SwingFXUtils.fromFXImage(imagenRecortada, null), "png", archivoDestino);
            
            if (txtruta != null) {
                txtruta.setText(nombreArchivoFinal); 
            }
            
            // ---Guardamos la ruta absoluta 
            rutaImagenSeleccionada ="/Raiz/Media/Productos/" + nombreArchivoFinal; 
            
            if (previsualizacion != null) {
                previsualizacion.setFitWidth(240);
                previsualizacion.setFitHeight(280);
                previsualizacion.setPreserveRatio(false); 
                previsualizacion.setImage(imagenRecortada);
            }
            
        } catch (Exception e) {
            System.err.println("Error procesando o guardando la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
    
    // ---------- Guardar producto (nuevo o editado)
    @FXML
    private void guardarProducto(ActionEvent event) {
        // Validaciones
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
        String detalles = txtDetalles != null ? txtDetalles.getText().trim() : "";
        String categoria = (comboCategoriaProducto != null && comboCategoriaProducto.getValue() != null)
            ? comboCategoriaProducto.getValue()
            : "";
        
        double precio;
        try {
            precio = Double.parseDouble(txtprecio.getText().trim().replace(",", ""));
        } catch (NumberFormatException e) {
            AlertaUtil.mostrarAdvertencia("Precio invalido", "Ingrese un precio numerico valido.");
            return;
        }
        
        int stock = 1;
        if (txtStock != null && !txtStock.getText().trim().isEmpty()) {
            try {
                stock = Integer.parseInt(txtStock.getText().trim());
            } catch (NumberFormatException e) {
                stock = 1;
            }
        }
        
        String estado = "Nuevo";
        if (checknuevo != null && checknuevo.isSelected()) {
            estado = "Nuevo";
        } else if (checkviejo != null && checkviejo.isSelected()) {
            estado = "Usado";
        }
        
        boolean enOferta = checkEnOferta != null && checkEnOferta.isSelected();
        String idAdmin = sesionServicio.getIdUsuarioActual();

        if (productoEditando != null) {
            Producto actualizado = new Producto(
            productoEditando.getIdProducto(),
            nombre, stock, precio, estado, marca, categoria, rutaImagenSeleccionada, detalles, enOferta
        );
            
            if (productoServicio.actualizarProducto(productoEditando.getIdProducto(), actualizado, idAdmin)) {
                AlertaUtil.mostrarInformacion("Producto actualizado", "El producto ha sido actualizado correctamente.");
            } else {
                AlertaUtil.mostrarError("Error", "No se pudo actualizar el producto.");
            }
        } else {

        if (productoServicio.agregarProducto(nombre, stock, precio, estado, marca, categoria, rutaImagenSeleccionada, detalles, enOferta, idAdmin)) {
                AlertaUtil.mostrarInformacion("Producto agregado", "El producto ha sido agregado al inventario.");
            } else {
                AlertaUtil.mostrarError("Error", "No se pudo agregar el producto.");
            }
        }
        
        limpiarCamposProducto();
        productoEditando = null;
        irInventarioAdmin();
    }
    
    // ---- Limpiar campos del formulario de producto
    private void limpiarCamposProducto() {
        if (txtnombreproductO != null) txtnombreproductO.clear();
        if (txtmarca != null) txtmarca.clear();
        if (txtDetalles != null) txtDetalles.clear();
        if (txtprecio != null) txtprecio.clear();
        if (txtStock != null) txtStock.clear();
        if (txtruta != null) txtruta.clear();
        if (checknuevo != null) checknuevo.setSelected(true);
        if (checkviejo != null) checkviejo.setSelected(false);
        if (checkEnOferta != null) checkEnOferta.setSelected(false);
        if (previsualizacion != null) previsualizacion.setImage(null);
        if (comboCategoriaProducto != null) comboCategoriaProducto.setValue(ProductoServicio.CATEGORIAS[0]);
        rutaImagenSeleccionada = "";
    }
    
    // ==================== GESTION DE PEDIDOS ====================
    
    // ---------- Cargar lista de pedidos
    private void cargarListaPedidos() {
        if (tablaPedidos == null) return;
        
        ArrayList<Pedido> pedidos = pedidoServicio.obtenerTodosPedidos();
        ObservableList<Pedido> listaObservable = FXCollections.observableArrayList(pedidos);
        tablaPedidos.setItems(listaObservable);
    }
    
    // ---------- Visualizar pedido seleccionado
    @FXML
    private void visualizarPedidoSeleccionado(ActionEvent event) {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un pedido", "Debe seleccionar un pedido de la lista.");
            return;
        }
        
        StringBuilder productos = new StringBuilder();
        if (seleccionado.getItems() != null) {
            seleccionado.getItems().forEach(item -> {
                productos.append("- ").append(item.getProducto().getNombreProducto())
                         .append(" x").append(item.getCantidad())
                         .append(" ($").append(String.format("%,.0f", item.getSubtotal())).append(")\n");
            });
        }
        
        String info = String.format(
            "ID Pedido: %s\nCliente: %s\nDireccion: %s\nFecha: %s\nTotal: %s\n\nProductos:\n%s",
            seleccionado.getIdPedido(),
            seleccionado.getNombreUsuario(),
            seleccionado.getDireccionEnvio(),
            seleccionado.getFechaFormateada(),
            seleccionado.getTotalFormateado(),
            productos.toString()
        );
        
        AlertaUtil.mostrarInformacion("Detalle del Pedido", info);
    }
    
    // ==================== GESTION DE CLIENTES ====================
    
    // ---------- Cargar lista de clientes
    private void cargarListaClientes() {
        if (tablaUsuarios == null) return;
        
        String idAdminActual = sesionServicio.getIdUsuarioActual();
        ArrayList<Usuario> clientes = usuarioServicio.obtenerClientesParaAdmin(idAdminActual);
        
        ObservableList<Usuario> listaObservable = FXCollections.observableArrayList(clientes);
        tablaUsuarios.setItems(listaObservable);
    }
    
    // ---------- Visualizar informacion del cliente
    @FXML
    private void visualizarUsuarioSeleccionado(ActionEvent event) {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un cliente", "Debe seleccionar un cliente de la lista.");
            return;
        }
        
        String info = String.format(
            "Documento: %s\nNombre: %s\nCorreo: %s\nCelular: %s\nFecha Nacimiento: %s\nDireccion: %s",
            seleccionado.getId(),
            seleccionado.getNombreCompleto(),
            seleccionado.getCorreo(),
            seleccionado.getCelular(),
            seleccionado.getFechaNacimiento(),
            seleccionado.getDireccionCompleta()
        );
        
        AlertaUtil.mostrarInformacion("Informacion del Cliente", info);
    }
    
    // ---------- Editar cliente seleccionado
    @FXML
    private void editarUsuarioSeleccionado(ActionEvent event) {
        usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        
        if (usuarioSeleccionado == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un cliente", "Debe seleccionar un cliente de la lista.");
            return;
        }
        
        if (usuarioSeleccionado.esAdmin()) {
            AlertaUtil.mostrarError("Error", "No puede editar a otro administrador.");
            return;
        }
        
        mostrarPaneAdmin(paneEditarUsuario);
        cargarDatosUsuarioEdicion();
    }
    
    // ---- Cargar datos del usuario en el formulario de edicion
    private void cargarDatosUsuarioEdicion() {
        if (usuarioSeleccionado == null) return;
        
        if (txtCorreoEditarUsuario != null) txtCorreoEditarUsuario.setText(usuarioSeleccionado.getCorreo());
        if (txtNombreEditarUsuario != null) txtNombreEditarUsuario.setText(usuarioSeleccionado.getNombre());
        if (txtApellidoEditarUsuario != null) txtApellidoEditarUsuario.setText(usuarioSeleccionado.getApellido());
        if (txtCelularEditarUsuario != null) txtCelularEditarUsuario.setText(usuarioSeleccionado.getCelular());
        if (txtDocumentoEditarUsuario != null) txtDocumentoEditarUsuario.setText(usuarioSeleccionado.getId());
        if (txtFechaNacimientoEditarUsuario != null) txtFechaNacimientoEditarUsuario.setText(usuarioSeleccionado.getFechaNacimiento());
        if (txtDireccionEditarUsuario != null) txtDireccionEditarUsuario.setText(usuarioSeleccionado.getDireccion());
        
        if (comboDepartamentoEditarUsuario != null) {
            comboDepartamentoEditarUsuario.getItems().clear();
            comboDepartamentoEditarUsuario.getItems().addAll(DepartamentosColombia.getDepartamentos());
            comboDepartamentoEditarUsuario.setValue(usuarioSeleccionado.getDepartamento());
        }
        
        if (comboCiudadEditarUsuario != null && usuarioSeleccionado.getDepartamento() != null) {
            comboCiudadEditarUsuario.getItems().clear();
            comboCiudadEditarUsuario.getItems().addAll(DepartamentosColombia.getCiudades(usuarioSeleccionado.getDepartamento()));
            comboCiudadEditarUsuario.setValue(usuarioSeleccionado.getCiudad());
        }
    }
    
    // ---------- Guardar cambios del cliente
    @FXML
    private void guardarCambiosUsuario(ActionEvent event) {
        if (usuarioSeleccionado == null) return;
        
        String correo = txtCorreoEditarUsuario.getText().trim();
        String nombre = txtNombreEditarUsuario.getText().trim();
        String apellido = txtApellidoEditarUsuario.getText().trim();
        String celular = txtCelularEditarUsuario.getText().trim();
        String fechaNacimiento = txtFechaNacimientoEditarUsuario != null ? txtFechaNacimientoEditarUsuario.getText().trim() : usuarioSeleccionado.getFechaNacimiento();
        String departamento = comboDepartamentoEditarUsuario.getValue();
        String ciudad = comboCiudadEditarUsuario.getValue();
        String direccion = txtDireccionEditarUsuario.getText().trim();
        
        if (correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            AlertaUtil.mostrarCamposVacios();
            return;
        }
        
        Usuario actualizado = new Usuario(
            correo, nombre, apellido, 
            usuarioSeleccionado.getId(),
            fechaNacimiento,
            celular, departamento, ciudad, direccion,
            usuarioSeleccionado.getContraseña(),
            Usuario.Rol.CLIENTE
        );
        
        String idAdmin = sesionServicio.getIdUsuarioActual();
        if (usuarioServicio.editarCliente(usuarioSeleccionado.getId(), actualizado, idAdmin)) {
            AlertaUtil.mostrarInformacion("Cliente actualizado", "Los datos del cliente han sido actualizados.");
            irAdministracionPersonalAdmin();
        } else {
            AlertaUtil.mostrarError("Error", "No se pudieron guardar los cambios.");
        }
    }
    
    // ---------- Cancelar edicion de cliente
    @FXML
    private void cancelarEdicionUsuario(ActionEvent event) {
        usuarioSeleccionado = null;
        irAdministracionPersonalAdmin();
    }
    
    // ---------- Eliminar cliente seleccionado
    @FXML
    private void eliminarUsuarioSeleccionado(ActionEvent event) {
        Usuario usuario = tablaUsuarios.getSelectionModel().getSelectedItem();
        
        if (usuario == null) {
            AlertaUtil.mostrarAdvertencia("Seleccione un cliente", "Debe seleccionar un cliente de la lista.");
            return;
        }
        
        if (usuario.esAdmin()) {
            AlertaUtil.mostrarError("Error", "No puede eliminar a otro administrador.");
            return;
        }
        
        if (AlertaUtil.confirmarEliminacionUsuario(usuario.getNombreCompleto())) {
            String idAdmin = sesionServicio.getIdUsuarioActual();
            if (usuarioServicio.eliminarCliente(usuario.getId(), idAdmin)) {
                AlertaUtil.mostrarInformacion("Cliente eliminado", 
                    "El cliente " + usuario.getNombreCompleto() + " ha sido eliminado.");
                cargarListaClientes();
            } else {
                AlertaUtil.mostrarError("Error", "No se pudo eliminar el cliente.");
            }
        }
    }
    
    // ==================== MI CUENTA (EDITAR INFO PROPIA) ====================
    
    // ---------- Cargar datos propios para edicion
    private void cargarDatosMiCuenta() {
        Usuario usuario = sesionServicio.getUsuarioActual();
        if (usuario == null) return;
        
        if (txtCorreoMiCuenta != null) txtCorreoMiCuenta.setText(usuario.getCorreo());
        if (txtNombreMiCuenta != null) txtNombreMiCuenta.setText(usuario.getNombre());
        if (txtApellidoMiCuenta != null) txtApellidoMiCuenta.setText(usuario.getApellido());
        if (txtCelularMiCuenta != null) txtCelularMiCuenta.setText(usuario.getCelular());
        if (txtDocumentoMiCuenta != null) txtDocumentoMiCuenta.setText(usuario.getId());
        if (txtFechaNacimientoMiCuenta != null) txtFechaNacimientoMiCuenta.setText(usuario.getFechaNacimiento());
        if (txtDireccionMiCuenta != null) txtDireccionMiCuenta.setText(usuario.getDireccion());
        
        if (comboDepartamentoMiCuenta != null) {
            comboDepartamentoMiCuenta.getItems().clear();
            comboDepartamentoMiCuenta.getItems().addAll(DepartamentosColombia.getDepartamentos());
            comboDepartamentoMiCuenta.setValue(usuario.getDepartamento());
        }
        
        if (comboCiudadMiCuenta != null && usuario.getDepartamento() != null) {
            comboCiudadMiCuenta.getItems().clear();
            comboCiudadMiCuenta.getItems().addAll(DepartamentosColombia.getCiudades(usuario.getDepartamento()));
            comboCiudadMiCuenta.setValue(usuario.getCiudad());
        }
    }
    
    // ---------- Guardar cambios de mi cuenta
    @FXML
    private void guardarCambiosMiCuenta(ActionEvent event) {
        Usuario usuario = sesionServicio.getUsuarioActual();
        if (usuario == null) return;
        
        String correo = txtCorreoMiCuenta.getText().trim();
        String nombre = txtNombreMiCuenta.getText().trim();
        String apellido = txtApellidoMiCuenta.getText().trim();
        String celular = txtCelularMiCuenta.getText().trim();
        String fechaNacimiento = txtFechaNacimientoMiCuenta != null ? txtFechaNacimientoMiCuenta.getText().trim() : usuario.getFechaNacimiento();
        String departamento = comboDepartamentoMiCuenta.getValue();
        String ciudad = comboCiudadMiCuenta.getValue();
        String direccion = txtDireccionMiCuenta.getText().trim();
        
        if (correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            AlertaUtil.mostrarCamposVacios();
            return;
        }
        
        Usuario actualizado = new Usuario(
            correo, nombre, apellido, 
            usuario.getId(), fechaNacimiento,
            celular, departamento, ciudad, direccion,
            usuario.getContraseña(), usuario.getRol()
        );
        
        if (usuarioServicio.actualizarPerfilPropio(usuario.getId(), actualizado)) {
            sesionServicio.actualizarUsuarioActual(actualizado);
            cargarDatosUsuario();
            AlertaUtil.mostrarInformacion("Datos actualizados", "Tu informacion ha sido actualizada.");
            irPerfilAdmin();
        } else {
            AlertaUtil.mostrarError("Error", "No se pudieron guardar los cambios.");
        }
    }
    
    // ---------- Cancelar edicion de mi cuenta
    @FXML
    private void cancelarEdicionMiCuenta(ActionEvent event) {
        irPerfilAdmin();
    }
    
    // ---------- Mostrar dialogo para cambiar contraseña
    @FXML
    private void mostrarDialogoCambiarPassword(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cambiar Contraseña");
        dialog.setHeaderText("Ingrese su contraseña actual y la nueva contraseña");
        
        // ---- Crear campos de contraseña
        PasswordField passwordActual = new PasswordField();
        passwordActual.setPromptText("Contraseña actual");
        
        PasswordField passwordNueva = new PasswordField();
        passwordNueva.setPromptText("Nueva contraseña");
        
        PasswordField passwordConfirmar = new PasswordField();
        passwordConfirmar.setPromptText("Confirmar nueva contraseña");
        
        // ---- Crear layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        grid.add(new Label("Contraseña actual:"), 0, 0);
        grid.add(passwordActual, 1, 0);
        grid.add(new Label("Nueva contraseña:"), 0, 1);
        grid.add(passwordNueva, 1, 1);
        grid.add(new Label("Confirmar:"), 0, 2);
        grid.add(passwordConfirmar, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String actual = passwordActual.getText();
            String nueva = passwordNueva.getText();
            String confirmar = passwordConfirmar.getText();
            
            Usuario usuario = sesionServicio.getUsuarioActual();
            
            // Validaciones
            if (!usuario.getContraseña().equals(actual)) {
                AlertaUtil.mostrarError("Error", "La contraseña actual es incorrecta.");
                return;
            }
            
            if (!nueva.equals(confirmar)) {
                AlertaUtil.mostrarError("Error", "Las contraseñas nuevas no coinciden.");
                return;
            }
            
            if (!Validaciones.validarContraseña(nueva)) {
                AlertaUtil.mostrarAdvertencia("Contraseña invalida", 
                    "La contraseña debe tener entre 8 y 32 caracteres.");
                return;
            }
            
            // Actualizar contraseña
            Usuario actualizado = new Usuario(
                usuario.getCorreo(), usuario.getNombre(), usuario.getApellido(),
                usuario.getId(), usuario.getFechaNacimiento(),
                usuario.getCelular(), usuario.getDepartamento(), usuario.getCiudad(),
                usuario.getDireccion(), nueva, usuario.getRol()
            );
            
            if (usuarioServicio.actualizarPerfilPropio(usuario.getId(), actualizado)) {
                sesionServicio.actualizarUsuarioActual(actualizado);
                AlertaUtil.mostrarInformacion("Contraseña actualizada", 
                    "Tu contraseña ha sido cambiada exitosamente.");
            } else {
                AlertaUtil.mostrarError("Error", "No se pudo cambiar la contraseña.");
            }
        }
    }
    
    // ==================== HISTORIAL ====================
    
    // ---------- Cargar historial de movimientos
    private void cargarHistorial() {
        if (tablaHistorial == null) return;
        
        String filtro = comboFiltroHistorial != null ? comboFiltroHistorial.getValue() : "Todos";
        ArrayList<Historial> historial;
        
        switch (filtro) {
            case "Compras":
                historial = historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.COMPRA);
                break;
            case "Carrito":
                historial = new ArrayList<>();
                historial.addAll(historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.AGREGAR_CARRITO));
                historial.addAll(historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.QUITAR_CARRITO));
                break;
            case "Favoritos":
                historial = new ArrayList<>();
                historial.addAll(historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.AGREGAR_FAVORITO));
                historial.addAll(historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.QUITAR_FAVORITO));
                break;
            case "Busquedas":
                historial = historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.BUSQUEDA);
                break;
            case "Sesiones":
                historial = new ArrayList<>();
                historial.addAll(historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.INICIO_SESION));
                historial.addAll(historialServicio.filtrarHistorialCompletoPorTipo(Historial.TipoAccion.CIERRE_SESION));
                break;
            case "Acciones Admin":
                historial = historialServicio.obtenerHistorialAdmin();
                break;
            default:
                historial = historialServicio.obtenerHistorialCompleto();
        }
        
        ObservableList<Historial> listaObservable = FXCollections.observableArrayList(historial);
        tablaHistorial.setItems(listaObservable);
    }
    
    // ==================== CERRAR SESION ====================
    
    @FXML
    private void cerrarSesion(ActionEvent event) {
        if (!AlertaUtil.confirmarAccion("Cerrar Sesion", "¿Esta seguro de cerrar sesion?")) {
            return;
        }
        
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
            
            // Cerrar ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (currentStage != null) currentStage.close();
            
        } catch (Exception e) {
            AlertaUtil.mostrarError("Error", "Error al cerrar sesion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
