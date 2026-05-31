/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/LoginControladora.java to edit this template
 */

package Raiz.Controladoras;

import Raiz.Modelos.Usuario;
import Raiz.Servicios.FavoritosServicio;
import Raiz.Servicios.SesionServicio;
import Raiz.Servicios.UsuarioServicio;
import Raiz.Utilidades.Validaciones;
import Raiz.Utilidades.DepartamentosColombia;
import Raiz.Utilidades.AlertaUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.layout.Region;

/**
 * @author alejo
 */

// --------- Clase encargada del manejo total de la vista_login-registro

public class LoginControladora {
    
    // Servicios compartidos de unica instancia paa evitar sobrecarga
    
    private final UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private final SesionServicio    sesionServicio    = SesionServicio.getInstancia();
    private final FavoritosServicio favoritosServicio = FavoritosServicio.getInstancia();
    
    // ----- Atributos de elementos FXML para su uso
    
    // Paneles principales
    
    @FXML private Pane paneLogin;
    @FXML private Pane paneRegistro;
    @FXML private Pane paneRegistroFinal;
    
    // Textfields inicio sesion
    
    @FXML private TextField txtCorreoLogin;
    @FXML private PasswordField txtPasswordLogin;
    @FXML private TextField txtPasswordLoginVisible;
    @FXML private Region regionIconoOjo;
    @FXML private Label lblPasswordLogin;
     
    private boolean PasswordLoginVisible = false;
    
    // Textfields registro paso 1
    
    @FXML private TextField txtCorreoRegistro;
    @FXML private TextField txtNombreRegistro;
    @FXML private TextField txtApellidoRegistro;
    @FXML private TextField txtIdClienteRegistro;
    @FXML private TextField txtFechaRegistro;
    
    // Textfields registro paso 2
    
    @FXML private TextField txtCelularRegistro;
    @FXML private ComboBox<String> comboDepartamento;
    @FXML private ComboBox<String> comboCiudad;
    @FXML private TextField txtDireccionRegistro;
    @FXML private PasswordField txtPasswordRegistro;
    @FXML private TextField txtPasswordRegistroVisible;
    @FXML private Region regionIconoOjoRegistro;
    @FXML private Label lblPasswordRegistro;
    
    private boolean PasswordRegistroVisible = false;
    
    // ----- Inicializacion
    
    @FXML
    public void initialize() {
 
        mostrarPane(paneLogin, paneRegistro, paneRegistroFinal);
        
        txtPasswordLogin.textProperty().bindBidirectional(txtPasswordLoginVisible.textProperty());
        regionIconoOjo.getStyleClass().add("icono-ojo-cerrado");
        
        txtPasswordRegistro.textProperty().bindBidirectional(txtPasswordRegistroVisible.textProperty());
        regionIconoOjoRegistro.getStyleClass().add("icono-ojo-cerrado");
        
        BooleanBinding mostrarPromptLogin = Bindings.createBooleanBinding(
        () -> txtPasswordLogin.getText().isEmpty() && 
            !txtPasswordLogin.isFocused() && 
            !txtPasswordLoginVisible.isFocused(), 
          
        txtPasswordLogin.textProperty(),
        txtPasswordLogin.focusedProperty(),
        txtPasswordLoginVisible.focusedProperty() 
    );
        
    lblPasswordLogin.visibleProperty().bind(mostrarPromptLogin);


        BooleanBinding mostrarPromptRegistro = Bindings.createBooleanBinding(
        () -> txtPasswordRegistro.getText().isEmpty() && 
            !txtPasswordRegistro.isFocused() && 
            !txtPasswordRegistroVisible.isFocused(),
          
        txtPasswordRegistro.textProperty(),
        txtPasswordRegistro.focusedProperty(),
        txtPasswordRegistroVisible.focusedProperty() 
    );
    lblPasswordRegistro.visibleProperty().bind(mostrarPromptRegistro);
        
        // Inicializar ComboBoxes de departamentos y respectivas ciudades
        
        if (comboDepartamento != null) {
            comboDepartamento.getItems().addAll(DepartamentosColombia.getDepartamentos());
            
            comboDepartamento.setOnAction(event -> {
                String depto = comboDepartamento.getValue();
                if (depto != null) {
                    comboCiudad.getItems().clear();
                    comboCiudad.getItems().addAll(DepartamentosColombia.getCiudades(depto));
                    comboCiudad.setValue(null);
                }
            });
            
            // Navegacion por teclado
            
            comboDepartamento.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    Platform.runLater(() -> {
                        comboCiudad.requestFocus();
                        comboCiudad.show();
                    });
                }
            });
            
            comboCiudad.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    Platform.runLater(() -> {
                        comboCiudad.hide();
                        txtDireccionRegistro.requestFocus();
                    });
                }
            });
        }
    }
    // ----- Metodo para Mostrar/Ocultar Campos Contraseñas
    
    @FXML
    private void alternarVisibilidadContraseña(ActionEvent event) {
        
        PasswordLoginVisible = !PasswordLoginVisible;

        if (PasswordLoginVisible) {
            txtPasswordLoginVisible.setVisible(true);
            txtPasswordLogin.setVisible(false);

            regionIconoOjo.getStyleClass().remove("icono-ojo-cerrado");
            regionIconoOjo.getStyleClass().add("icono-ojo-abierto");
            
        } else {
            txtPasswordLogin.setVisible(true);
            txtPasswordLoginVisible.setVisible(false);

            regionIconoOjo.getStyleClass().remove("icono-ojo-abierto");
            regionIconoOjo.getStyleClass().add("icono-ojo-cerrado");
        }
    }
    
    @FXML
    private void alternarVisibilidadContraseñaRegistro(ActionEvent event) {
        
        PasswordRegistroVisible = !PasswordRegistroVisible;

        if (PasswordRegistroVisible) {
            txtPasswordRegistroVisible.setVisible(true);
            txtPasswordRegistro.setVisible(false);

            regionIconoOjoRegistro.getStyleClass().remove("icono-ojo-cerrado");
            regionIconoOjoRegistro.getStyleClass().add("icono-ojo-abierto");
            
        } else {
            txtPasswordRegistro.setVisible(true);
            txtPasswordRegistroVisible.setVisible(false);

            regionIconoOjoRegistro.getStyleClass().remove("icono-ojo-abierto");
            regionIconoOjoRegistro.getStyleClass().add("icono-ojo-cerrado");
        }
    }
    
    // ----- Autenticacion
    
    @FXML
    private void iniciarSesion(ActionEvent event) {
        
        txtPasswordLogin.setVisible(true);
        txtPasswordLoginVisible.setVisible(false);
        regionIconoOjo.getStyleClass().remove("icono-ojo-abierto");
        regionIconoOjo.getStyleClass().add("icono-ojo-cerrado");
            
        String identificador = txtCorreoLogin.getText().trim();
        String contraseña = txtPasswordLogin.getText();
        
        // Validar campos vacios
        if (identificador.isEmpty() || contraseña.isEmpty()) {
            AlertaUtil.mostrarAdvertencia("Campos vacios", 
                "Por favor ingrese su correo y Contraseña.");
            return;
        }
        
        // Intentar autenticar
        
        Usuario usuario = usuarioServicio.autenticar(identificador, contraseña);
        
        if (usuario != null) {
            
            // Uso del servicio de usuarios
            
            sesionServicio.iniciarSesion(usuario);
            favoritosServicio.cargarFavoritosUsuario(usuario.getId());
            
            
            // Redirigir segun rol
            
            if (usuario.esAdmin()) {
                abrirVistaAdmin(event);
            } else {
                abrirVistaHome(event);
            }
        } else {
            AlertaUtil.mostrarError("Error de autenticacion", 
                "Usuario o Contraseña incorrectos.");
        }
    }
    
    // Abre ventana admnistracion
    
    private void abrirVistaAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Raiz/Vistas/Vista_Administracion.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Panel de Administracion - Boom");
            stage.setResizable(false);
            stage.show();
            
            cerrarVentanaActual(event);
            
        } catch (Exception e) {
            AlertaUtil.mostrarError("Error", "Error al cargar vista de administracion: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Abre ventana home
    
    @FXML
    private void abrirVistaHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Raiz/Vistas/Vista_Home.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Boom - Bienvenido " + sesionServicio.getNombreUsuario());
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();
            
            cerrarVentanaActual(event);
            
        } catch (Exception e) {
            AlertaUtil.mostrarError("Error", "Error al cargar vista principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ----- Registro paso 1
    
    @FXML
    private void irPaso2(ActionEvent event) {
        String correo = txtCorreoRegistro.getText().trim();
        String nombre = txtNombreRegistro.getText().trim();
        String apellido = txtApellidoRegistro.getText().trim();
        String id = txtIdClienteRegistro.getText().trim();
        String fecha = txtFechaRegistro.getText().trim();
        
        // Validar campos vacios
        if (correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || 
            id.isEmpty() || fecha.isEmpty()) {
            AlertaUtil.mostrarAdvertencia("Campos vacios", 
                "Todos los campos son obligatorios.");
            return;
        }
        
        // Validar formato ID
        if (!Validaciones.validarId(id)) {
            AlertaUtil.mostrarAdvertencia("ID invalido", 
                "El numero de identificacion debe contener entre 7 y 12 digitos.");
            return;
        }
        
        // Validar ID duplicado
        if (usuarioServicio.existeId(id)) {
            AlertaUtil.mostrarAdvertencia("ID ya registrado", 
                "Ya existe una cuenta con este numero de identificacion.");
            return;
        }
        
        // Validar formato correo
        if (!Validaciones.validarCorreo(correo)) {
            AlertaUtil.mostrarAdvertencia("Correo invalido", 
                "El formato del correo no es valido o el dominio no esta permitido.");
            return;
        }
        
        // Validar correo duplicado
        if (usuarioServicio.existeCorreo(correo)) {
            AlertaUtil.mostrarAdvertencia("Correo ya registrado", 
                "Ya existe una cuenta con este correo electronico.");
            return;
        }
        
        // Validar nombre y apellido
        if (!Validaciones.validarNombre(nombre)) {
            AlertaUtil.mostrarAdvertencia("Nombre invalido", 
                "El nombre solo puede contener letras y espacios.");
            return;
        }
        
        if (!Validaciones.validarNombre(apellido)) {
            AlertaUtil.mostrarAdvertencia("Apellido invalido", 
                "El apellido solo puede contener letras y espacios.");
            return;
        }
        
        // Validar fecha
        if (!Validaciones.validarFechaYEdad(fecha)) {
            AlertaUtil.mostrarAdvertencia("Fecha invalida", 
                "La fecha debe ser real (dd/MM/yyyy) y el usuario debe ser mayor de edad.");
            return;
        }
        
        mostrarPane(paneRegistroFinal, paneRegistro, paneLogin);
        txtCelularRegistro.requestFocus();
    }
    
    // ----- Registro paso 2 (Final)
    
    @FXML
    private void registrarCliente(ActionEvent event) {
        
        txtPasswordRegistro.setVisible(true);
        txtPasswordRegistroVisible.setVisible(false);
        regionIconoOjoRegistro.getStyleClass().remove("icono-ojo-abierto");
        regionIconoOjoRegistro.getStyleClass().add("icono-ojo-cerrado");

        String correo = txtCorreoRegistro.getText().trim();
        String nombre = txtNombreRegistro.getText().trim();
        String apellido = txtApellidoRegistro.getText().trim();
        String id = txtIdClienteRegistro.getText().trim();
        String fecha = txtFechaRegistro.getText().trim();
        
        String celular = txtCelularRegistro.getText().trim();
        String departamento = comboDepartamento.getValue();
        String ciudad = comboCiudad.getValue();
        String direccion = txtDireccionRegistro.getText().trim();
        String contraseña = txtPasswordRegistro.getText();
        
        // Validar campos vacios
        if (celular.isEmpty() || departamento == null || ciudad == null ||
            direccion.isEmpty() || contraseña.isEmpty()) {
            AlertaUtil.mostrarAdvertencia("Campos vacios", 
                "Todos los campos son obligatorios. Seleccione departamento y ciudad.");
            return;
        }
        
        // Validar celular
        String celularLimpio = celular.replaceAll("[^0-9]", "");
        if (!Validaciones.validarCelular(celularLimpio)) {
            AlertaUtil.mostrarAdvertencia("Celular invalido", 
                "El celular debe contener entre 10 y 15 digitos.");
            return;
        }
        
        // Validar Contraseña
        if (!Validaciones.validarContraseña(contraseña)) {
            AlertaUtil.mostrarAdvertencia("Contraseña invalida", 
                "La Contraseña debe tener entre 8 y 32 caracteres sin espacios.");
            return;
        }
        
        // Crear usuario
        Usuario nuevo = new Usuario(
            correo.toLowerCase(),
            nombre,
            apellido,
            id,
            fecha,
            celularLimpio,
            departamento,
            ciudad,
            direccion,
            contraseña
        );
        
        // Registro
        if (usuarioServicio.registrarUsuario(nuevo)) {
            AlertaUtil.mostrarInformacion("Registro exitoso", 
                "Bienvenido " + nuevo.getNombreCompleto() + "!\nAhora puede iniciar sesion.");
            
            limpiarCamposRegistro();
            mostrarPane(paneLogin, paneRegistro, paneRegistroFinal);
            
            // Pre-llenar correo en login
            txtCorreoLogin.setText(correo.toLowerCase());
            txtPasswordLogin.requestFocus();
        } else {
            AlertaUtil.mostrarError("Error de registro", 
                "No se pudo completar el registro. Intente de nuevo.");
        }
    }
    
    // ----- Navegacion
    
    @FXML
    private void irARegistro(MouseEvent event) {
        
        txtPasswordLogin.setVisible(true);
        txtPasswordLoginVisible.setVisible(false);
        regionIconoOjo.getStyleClass().remove("icono-ojo-abierto");
        regionIconoOjo.getStyleClass().add("icono-ojo-cerrado");
        
        limpiarCamposRegistro();
        mostrarPane(paneRegistro, paneLogin, paneRegistroFinal);
    }
    
    @FXML
    private void irALogin(MouseEvent event) {
        limpiarCamposLogin();
        mostrarPane(paneLogin, paneRegistro, paneRegistroFinal);
    }
    
    @FXML
    private void irAPaso1(MouseEvent event) {
        
        txtPasswordRegistro.setVisible(true);
        txtPasswordRegistroVisible.setVisible(false);
        regionIconoOjoRegistro.getStyleClass().remove("icono-ojo-abierto");
        regionIconoOjoRegistro.getStyleClass().add("icono-ojo-cerrado");
        
        mostrarPane(paneRegistro, paneRegistroFinal, paneLogin);
        if (txtCorreoRegistro != null) {
            txtCorreoRegistro.requestFocus();
        }
    }
    
    // Navegacion por teclado
    
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
    
    // Metodo auxiliar de navegacion
    
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
    
    // Limpiar formulario registro
    private void limpiarCamposRegistro() {
        if (txtCorreoRegistro != null) txtCorreoRegistro.clear();
        if (txtNombreRegistro != null) txtNombreRegistro.clear();
        if (txtApellidoRegistro != null) txtApellidoRegistro.clear();
        if (txtIdClienteRegistro != null) txtIdClienteRegistro.clear();
        if (txtFechaRegistro != null) txtFechaRegistro.clear();
        if (txtCelularRegistro != null) txtCelularRegistro.clear();
        if (txtDireccionRegistro != null) txtDireccionRegistro.clear();
        if (txtPasswordRegistro != null) txtPasswordRegistro.clear();
        if (comboDepartamento != null) comboDepartamento.setValue(null);
        if (comboCiudad != null) {
            comboCiudad.getItems().clear();
            comboCiudad.setValue(null);
        }
    }
    
    // Limpiar formulario inicio sesion
    
    private void limpiarCamposLogin() {
        if (txtCorreoLogin != null) txtCorreoLogin.clear();
        if (txtPasswordLogin != null) txtPasswordLogin.clear();
    }
    
    // Metodo cerrar ventana
    
    private void cerrarVentanaActual(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }
}
