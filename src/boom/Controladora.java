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
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
/**
 *
 * @author alejo
 */
public class Controladora {
    
    //Tamaño maximo de los arreglos
    private static final int mx = 500;
    
    // Constantes para validaciones
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final String CLIENTES_FILE = "clientes.txt";
    
    // Patron de validacion para correo (mejorado)
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
    
    //Datos para los ComboBox: Departamento y Ciudad
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
    //Inicializacion de Datos Compartidos pot Controladora en las Vistas para evitar conflictos
    private static producto[]    listaProductos    = new producto[mx];
    private static clienteBoom[] listaClientes     = new clienteBoom[mx];
    private static admin[]       listaAdmins       = new admin[mx];
    private static int           contadorProductos = 0;
    private static int           contadorClientes  = 0;
    private static int           contadorAdmins    = 0;
    private static boolean       datosInicializados = false;
    
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
    
    public Controladora() {
        if (!datosInicializados) {
            datosInicializados = true;
            cargarClientesDesdeArchivo();

            // Admin por defecto del equipo
            admin principal = new admin(
                "Diomedes Díaz",
                "Admin principal",
                "+57 3148011595",
                "elcaciquedelajunta2013@outlook.es",
                "Diomedes2013",
                "Cl 26 # 5 - 1957 La junta"
            );
            listaAdmins[contadorAdmins++] = principal;

        }
    }
    
    //Inicializacion Panes y ComboBoxes
    @FXML
    public void initialize() {
        if (paneLogin        != null) paneLogin.setVisible(true);
        if (paneRegistro     != null) paneRegistro.setVisible(false);
        if (paneRegistroFinal!= null) paneRegistroFinal.setVisible(false);

        if (comboDepartamento != null) {
            comboDepartamento.getItems().addAll(DEPTOS.keySet());
            comboDepartamento.setOnAction(e -> {
                String dep = comboDepartamento.getValue();
                comboCiudad.getItems().clear();
                comboCiudad.setValue(null);
                if (dep != null && DEPTOS.containsKey(dep))
                    comboCiudad.getItems().addAll(DEPTOS.get(dep));
            });
        }
        
        BarraUsuario = new ToggleGroup();

        if (tbtnFavoritosPerfilHome != null)
            tbtnFavoritosPerfilHome.setToggleGroup(BarraUsuario);

        if (tbtnPedidosPerfilHome != null)
            tbtnPedidosPerfilHome.setToggleGroup(BarraUsuario);

        if (tbtnMiCuentaPerfilHome != null)
            tbtnMiCuentaPerfilHome.setToggleGroup(BarraUsuario);
        
        BarraAdmin = new ToggleGroup();

        if (tbtnPerfilAdmin != null)
            tbtnPerfilAdmin.setToggleGroup(BarraAdmin);

        if (tbtnEstadisticasAdmin != null)
            tbtnEstadisticasAdmin.setToggleGroup(BarraAdmin);

        if (tbtnRestaStockAdmin != null)
            tbtnRestaStockAdmin.setToggleGroup(BarraAdmin);
        
        if (tbtnInventarioAdmin != null)
            tbtnInventarioAdmin.setToggleGroup(BarraAdmin);

        if (tbtnPedidosAdmin != null)
            tbtnPedidosAdmin.setToggleGroup(BarraAdmin);

        if (tbtnIngrePersoAdmin != null)
            tbtnIngrePersoAdmin.setToggleGroup(BarraAdmin);

    }
  
    //Metodo para iniciar seccion
    @FXML
    private void iniciarSesion(ActionEvent event) {
        String user = txtCorreoLogin.getText().trim();
        String contra = txtPasswordLogin.getText();
        
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
        // Inicio con clienteBoom
        for (int i = 0; i < contadorClientes; i++) {
            clienteBoom c = listaClientes[i];
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
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();
            
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la vista principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Metodo ir a registro
    @FXML
    private void irARegistro(MouseEvent event) {

    limpiarCamposRegistro();

        if (paneLogin != null) {
            paneLogin.setVisible(false);
            paneLogin.setManaged(false);
        }

        if (paneRegistro != null) {
            paneRegistro.setVisible(true);
            paneRegistro.setManaged(true);
        }

        if (paneRegistroFinal != null) {
            paneRegistroFinal.setVisible(false);
            paneRegistroFinal.setManaged(false);
        }
    }

    // Metodo ir a login
    @FXML
    private void irALogin(MouseEvent event) {

        if (paneRegistro != null) {
            paneRegistro.setVisible(false);
            paneRegistro.setManaged(false);
        }

        if (paneRegistroFinal != null) {
            paneRegistroFinal.setVisible(false);
            paneRegistroFinal.setManaged(false);
        }

        if (paneLogin != null) {
            paneLogin.setVisible(true);
            paneLogin.setManaged(true);
        }
    }

    // Metodo ir a registro final
    @FXML
    private void irAPaso1(MouseEvent event) {

        if (paneRegistroFinal != null) {
            paneRegistroFinal.setVisible(true);
            paneRegistroFinal.setManaged(true);
        }

        if (paneRegistro != null) {
            paneRegistro.setVisible(false);
            paneRegistro.setManaged(false);
        }
    }
    
    private void ocultarTodosLosPadres() {

        if (paneInicioHome != null) { paneInicioHome.setVisible(false);
            paneInicioHome.setManaged(false); 
        }
        
        if (panePerfilHome != null) { panePerfilHome.setVisible(false);
            panePerfilHome.setManaged(false);
        }
        
        if (paneCarritoHome != null) { paneCarritoHome.setVisible(false);
            paneCarritoHome.setManaged(false);
        }
    }

    public void mostrarInicio() {

        ocultarTodosLosPadres();

        if (paneInicioHome != null) { paneInicioHome.setVisible(true);
            paneInicioHome.setManaged(true);
        }
    }

    public void mostrarPerfil() {

        ocultarTodosLosPadres();

        if (panePerfilHome != null) { panePerfilHome.setVisible(true);
            panePerfilHome.setManaged(true);
        }
        
        mostrarMiCuentaPerfil();
    }

    public void mostrarCarrito() {

        ocultarTodosLosPadres();

        if (paneCarritoHome != null) { paneCarritoHome.setVisible(true);
            paneCarritoHome.setManaged(true);
        }
        
    }

    private void ocultarSubPanesPerfil() {

        if (paneFavoritosPerfilHome != null) { paneFavoritosPerfilHome.setVisible(false);
            paneFavoritosPerfilHome.setManaged(false);
        }
        
        if (panePedidosPerfilHome != null) { panePedidosPerfilHome.setVisible(false);
            panePedidosPerfilHome.setManaged(false);
        }
        
        if (paneMiCuentaPerfilHome != null) { paneMiCuentaPerfilHome.setVisible(false);
            paneMiCuentaPerfilHome.setManaged(false);
        }   
    }

    public void mostrarFavoritosPerfilInicio() {

        ocultarTodosLosPadres();

        if (panePerfilHome != null) { panePerfilHome.setVisible(true);
            panePerfilHome.setManaged(true);
        }
        
        ocultarSubPanesPerfil();

        if (paneFavoritosPerfilHome != null) { paneFavoritosPerfilHome.setVisible(true);
            paneFavoritosPerfilHome.setManaged(true);
        }
        
        tbtnFavoritosPerfilHome.setSelected(true);
    }
    
    public void mostrarFavoritosPerfil() {

        ocultarSubPanesPerfil();

        if (paneFavoritosPerfilHome != null) { paneFavoritosPerfilHome.setVisible(true);
            paneFavoritosPerfilHome.setManaged(true);
        }
    }

    public void mostrarPedidosPerfil() {

        ocultarSubPanesPerfil();

        if (panePedidosPerfilHome != null) { panePedidosPerfilHome.setVisible(true);
            panePedidosPerfilHome.setManaged(true);
        }
        
        tbtnPedidosPerfilHome.setSelected(true);
    }

    public void mostrarMiCuentaPerfil() {

        ocultarSubPanesPerfil();

        if (paneMiCuentaPerfilHome != null) { paneMiCuentaPerfilHome.setVisible(true);
            paneMiCuentaPerfilHome.setManaged(true);
        }
        
        tbtnMiCuentaPerfilHome.setSelected(true);
    }

    private void ocultarPanesAdmin() {

        Pane[] panes = {

            panePerfilAdmin,
            paneEstadisticasAdmin,
            paneRestaStockAdmin,
            paneInventarioAdmin,
            panePedidosAdmin,
            paneIngrePersoAdmin,
            paneEditarInfoAdmin,
            paneRestaNuevo_Producto
        };

        for (Pane pane : panes) {

            if (pane != null) {
                pane.setVisible(false);
                pane.setManaged(false);
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
    
    @FXML private void irPerfilAdmin() {
        mostrarPaneAdmin(panePerfilAdmin);
    }

    @FXML private void irEstadisticasAdmin() {
        mostrarPaneAdmin(paneEstadisticasAdmin);
    }

    @FXML private void irRestaStockAdmin() {
        mostrarPaneAdmin(paneRestaStockAdmin);
    }

    @FXML private void irInventarioAdmin() {
        mostrarPaneAdmin(paneInventarioAdmin);
    }

    @FXML private void irPedidosAdmin() {
        mostrarPaneAdmin(panePedidosAdmin);
    }

    @FXML private void irIngresoPersonalAdmin() {
        mostrarPaneAdmin(paneIngrePersoAdmin);
    }

    @FXML private void irEditarInfoAdmin() {
        mostrarPaneAdmin(paneEditarInfoAdmin);
    }
    
    private void ocultarPasosNuevoProducto() {

        Pane[] pasos = {

            paneRestaNuevo_ProductoPaso1,
            paneRestaNuevo_ProductoPaso2,
            paneRestaNuevo_ProductoPaso3
        };

        for (Pane paso : pasos) {

            if (paso != null) {
                paso.setVisible(false);
                paso.setManaged(false);
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

    @FXML private void irPaso2NuevoProducto() {
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso2);
    }

    @FXML private void irPaso3NuevoProducto() {
        mostrarPasoNuevoProducto(paneRestaNuevo_ProductoPaso3);
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
            JOptionPane.showMessageDialog(null, 
                "Error: Todos los campos son obligatorios para ingresar.\nPor favor complete todos los campos.", 
                "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // VALIDACIONES DE NUMERO DE IDENTIFICACION
        // Formato del ID (solo numeros, entre 7 y 12 digitos)
        if (!validarFormatoId(id)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El numero de identificacion no es valido.\nDebe contener solo numeros (entre 6 y 12 digitos).\nEjemplo: 1003345643", 
                "Identificacion invalida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar que el ID no este duplicado
        if (existeIdCliente(id)) {
            JOptionPane.showMessageDialog(null, 
                "Error: Ya existe una cuenta registrada con este numero de identificacion.\nPor favor verifique sus datos o inicie sesion.", 
                "Identificacion ya registrada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // VALIDACIONES DE CORREO
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
        
        // VALIDACIONES INFORMACION BASICA
        
        if (!validarNombre(nombre)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El nombre solo puede contener letras y espacios.", 
                "Nombre invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validarApellido(apellido)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El apellido solo puede contener letras y espacios.", 
                "Apellido invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!fecha.isEmpty() && !fecha.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            JOptionPane.showMessageDialog(null,
                "Formato de fecha inválido. Use dd/MM/YYYY\nEjemplo: 15/03/2000",
                "Fecha inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (paneRegistro     != null) paneRegistro.setVisible(false);
        if (paneRegistroFinal!= null) paneRegistroFinal.setVisible(true);
        if (paneLogin        != null) paneLogin.setVisible(false);

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
            JOptionPane.showMessageDialog(null,
                "Todos los campos son obligatorios.\nSeleccione Departamento y Ciudad.",
                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String celularLimpio = celular.replaceAll("[\\s-]", "");
        if (!validarFormatoTelefono(celularLimpio)) {
            JOptionPane.showMessageDialog(null, 
                "Error: El formato del celular no es valido.\nDebe contener entre 7 y 15 digitos.\nEjemplo: +573001234567", 
                "Celular invalido", JOptionPane.WARNING_MESSAGE);
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

        String digitos = celularLimpio.replaceAll("[^0-9]", "");
        String idCliente = digitos.length() >= 7 ? digitos.substring(Math.max(0, digitos.length() - 10)) : digitos;
        if (existeIdCliente(idCliente)) idCliente = idCliente + (System.currentTimeMillis() % 100);

        String nombreCompleto    = nombre + " " + apellido;
        String direccionCompleta = direccion + ", " + ciudad + ", " + departamento;

        clienteBoom nuevo = new clienteBoom(
                correo.toLowerCase(),
                nombre, 
                apellido,
                idCliente, 
                fechaNacimiento,
                celularLimpio,   
                departamento,
                ciudad,
                direccion,
                contraseña); 

        if (agregarCliente(nuevo)) {
            if (guardarClienteEnArchivo(nuevo)) {
                JOptionPane.showMessageDialog(null,
                    "¡Registro exitoso!\nBienvenido " + nombreCompleto + ".\nAhora puede iniciar sesión.",
                    "Registro completado", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposRegistro();
                if (paneRegistroFinal!= null) paneRegistroFinal.setVisible(false);
                if (paneRegistro     != null) paneRegistro.setVisible(false);
                if (paneLogin        != null) paneLogin.setVisible(true);
                if (txtCorreoLogin   != null) txtCorreoLogin.setText(correo.toLowerCase());
                if (txtPasswordLogin != null) txtPasswordLogin.requestFocus();
            } else {
                eliminarCliente(contadorClientes - 1);
                JOptionPane.showMessageDialog(null, "Error al guardar el registro. Intente de nuevo.",
                    "Error de sistema", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Sistema lleno.",
                "Error de sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Metodos correspondientes a la lista de productos 
    
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

    //Metodos correspondientes a la lista de clientes
    
    // Agregar un cliente
    public boolean agregarCliente(clienteBoom cliente) {
        if (contadorClientes < mx) {
            listaClientes[contadorClientes] = cliente;
            contadorClientes++;
            return true;
        }
        return false;
    }
    
    // Eliminar un clienteBoom por su posicion 
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
    
    // Eliminar un clienteBoom por su ID
    public boolean eliminarClientePorId(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(IdCliente)) {
                return eliminarCliente(i);
            }
        }
        return false;
    }
    
    // Obtener un clienteBoom por su posicion
    public clienteBoom obtenerCliente(int pos) {
        if (pos >= 0 && pos < contadorClientes) {
            return listaClientes[pos];
        }
        return null;
    }
    
    // Buscar un clienteBoom por su ID
    public clienteBoom buscarClientePorId(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(IdCliente)) {
                return listaClientes[i];
            }
        }
        return null;
    }
    
    // Buscar posicion de un clienteBoom por su ID
    public int buscarIndiceClientePorId(String IdCliente) {
        for (int i = 0; i < contadorClientes; i++) {
            if (listaClientes[i].getIdCliente().equals(IdCliente)) {
                return i;
            }
        }
        return -1;
    }
    
    // Verificar si un correo de clienteBoom ya existe
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
    
    //Metodos para la lista de admins
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

    //Metodos auxiliares y validaciones aleatorias
    
    // Obtener el tamaño máximo
    public int getTamanoMaximo() {
        return mx;
    }
    
    // Limpiar todos los datos
    public void limpiarTodo() {
        listaProductos = new producto[mx];
        listaClientes = new clienteBoom[mx];
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
        
    correo = correo.trim().toLowerCase();

    if (correo.length() < 6 || correo.length() > 254) {
        return false;
    }

    if (!EMAIL_PATTERN.matcher(correo).matches()) {
        return false;
    }

    if (correo.contains(" ")) {
        return false;
    }

    if (correo.chars().filter(c -> c == '@').count() != 1) {
        return false;
    }

    String[] partes = correo.split("@");

    if (partes.length != 2) {
        return false;
    }

    String usuario = partes[0];
    String dominio = partes[1];

    // Usuario inválido
    if (usuario.startsWith(".") ||
        usuario.endsWith(".") ||
        usuario.startsWith("_") ||
        usuario.endsWith("_") ||
        usuario.startsWith("-") ||
        usuario.endsWith("-")) {

        return false;
    }

    if (dominio.startsWith("-") ||
        dominio.endsWith("-") ||
        dominio.startsWith(".") ||
        dominio.endsWith(".")) {

        return false;
    }

    String[] niveles = dominio.split("\\.");

    for (String nivel : niveles) {

        if (nivel.isEmpty()) {
            return false;
        }

        if (nivel.length() > 63) {
            return false;
        }

        if (nivel.startsWith("-") ||
            nivel.endsWith("-")) {

            return false;
        }
    }

    String tld = niveles[niveles.length - 1];

    if (tld.length() < 2 || tld.length() > 63) {
        return false;
    }

    boolean dominioPermitido = false;

    for (String d : DOMINIOS_VALIDOS) {

        if (dominio.equalsIgnoreCase(d)) {
            dominioPermitido = true;
            break;
        }
    }

    return dominioPermitido;
}
 
    private boolean validarNombre(String nombre) {
        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'\\s]+$");
    }
    
    private boolean validarApellido(String nombre) {
        return nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'\\s]+$");
    } 
    
    
    //Persistencia archivo clientes
    private boolean guardarClienteEnArchivo(clienteBoom c) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTES_FILE, true))) {
            // Formato: correo;nombre;apellido;idCliente;fechaNacimiento;celular;departamento;ciudad;direccion;contrasena (10 campos)
            String linea = c.getCorreoCliente() + ";" + 
                          c.getNombreCliente() + ";" +
                          c.getApellidoCliente() + ";" +
                          c.getIdCliente() + ";" +
                          c.getFechaCliente() + ";" +
                          c.getCelularCliente() + ";" + 
                          c.getDepartamentoCliente() + ";" + 
                          c.getCiudadCliente() + ";" + 
                          c.getDireccionCliente() + ";" + 
                          c.getContraseñaCliente();
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
                
                // Verificar si tiene 10 campos (formato nuevo con IdCliente)
                if (datos.length >= 10) {
                    clienteBoom c = new clienteBoom(
                        datos[0].trim(),  
                        datos[1].trim(), 
                        datos[2].trim(),  
                        datos[3].trim(),  
                        datos[4].trim(),  
                        datos[5].trim(),   
                        datos[6].trim(),  
                        datos[7].trim(),  
                        datos[8].trim(),  
                        datos[9].trim()  
                    );
                    
                    if (!existeCorreoCliente(c.getCorreoCliente())) {
                        if (contadorClientes < mx) {
                            listaClientes[contadorClientes] = c;
                            contadorClientes++;
                        }
                    }
                }
                // Compatibilidad con formato antiguo de 5 campos (sin IdCliente separado)
                else if (datos.length == 10) {
                    clienteBoom c = new clienteBoom(
                        datos[0].trim(),  
                        datos[1].trim(), 
                        datos[2].trim(),  
                        datos[3].trim(),  
                        datos[4].trim(),  
                        datos[5].trim(),   
                        datos[6].trim(),  
                        datos[7].trim(),  
                        datos[8].trim(),  
                        datos[9].trim()  
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
        if (txtCorreoRegistro   !=null) txtCorreoRegistro.clear();
        if (txtNombreRegistro  !=null) txtNombreRegistro.clear();
        if (txtApellidoRegistro !=null) txtApellidoRegistro.clear();
        if (txtFechaRegistro    !=null) txtFechaRegistro.clear();
        if (txtCelularRegistro !=null) txtCelularRegistro.clear();
        if (txtDireccionRegistro!=null) txtDireccionRegistro.clear();
        if (txtPasswordRegistro !=null) txtPasswordRegistro.clear();
        if (comboDepartamento   !=null) comboDepartamento.setValue(null);
        if (comboCiudad         !=null) { comboCiudad.getItems().clear(); comboCiudad.setValue(null); }
    }
    
    private void limpiarCamposLogin() {
        if (txtCorreoLogin != null) txtCorreoLogin.clear();
        if (txtPasswordLogin != null) txtPasswordLogin.clear();
    }

}
