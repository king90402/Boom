/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module boomCopia {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens Raiz to javafx.fxml;
    opens Raiz.Controladoras to javafx.fxml;  
    opens Raiz.Modelos to javafx.fxml;
    opens Raiz.Estructuras to javafx.fxml;
    opens Raiz.Servicios to javafx.fxml;
    opens Raiz.Utilidades to javafx.fxml;
 
    exports Raiz;
    exports Raiz.Controladoras;
    exports Raiz.Estructuras;
    exports Raiz.Modelos;
    exports Raiz.Servicios;
    exports Raiz.Utilidades;

}
    
