/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Validaciones.java to edit this template
 */

package Raiz.Utilidades;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

/**
 * @author alejo
 */

// --------- Clase de utilidad con metodos de validacion del sistema

public class Validaciones {
    
    // Constantes

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 32;
    public static final int MIN_EDAD = 18;
    public static final int MAX_EDAD = 120;
    
    // Patrones regex

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^(?=.{6,254}$)(?=.{1,64}@)(?!.*\\.\\.)(?!.*__)(?!.*--)" +
        "[A-Za-z0-9][A-Za-z0-9._%+-]{0,62}[A-Za-z0-9]@" +
        "(?=.{1,255}$)(?:[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?\\.)+[A-Za-z]{2,63}$"
    );
    

    private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]{7,12}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    private static final Pattern NOMBRE_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$");
    

    private static final String[] DOMINIOS_VALIDOS = {
        "gmail.com", "hotmail.com", "outlook.com", "outlook.es", 
        "yahoo.com", "yahoo.es", "icloud.com", "mail.com", 
        "correo.unicordoba.edu.co", "live.com", "protonmail.com"
    };
    
    // ----- Metodos de validacion -----


    public static boolean validarFormatoCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(correo.trim().toLowerCase()).matches();
    }


    public static boolean validarDominioCorreo(String correo) {
        if (correo == null || !correo.contains("@")) return false;
        String dominio = correo.substring(correo.lastIndexOf("@") + 1).toLowerCase();
        for (String d : DOMINIOS_VALIDOS) {
            if (dominio.equals(d)) return true;
        }
        return false;
    }
    

    public static boolean validarCorreo(String correo) {
        if (correo == null || correo.isEmpty()) return false;
        correo = correo.trim().toLowerCase();
        if (correo.length() < 6 || correo.length() > 254) return false;
        if (correo.contains(" ")) return false;
        if (correo.chars().filter(c -> c == '@').count() != 1) return false;
        if (!validarFormatoCorreo(correo)) return false;
        return validarDominioCorreo(correo);
    }
    

    public static boolean validarId(String id) {
        if (id == null || id.isEmpty()) return false;
        return ID_PATTERN.matcher(id.trim()).matches();
    }
    

    public static boolean validarCelular(String celular) {
        if (celular == null || celular.isEmpty()) return false;
        return PHONE_PATTERN.matcher(celular.trim()).matches();
    }
    

    public static boolean validarNombre(String texto) {
        if (texto == null || texto.trim().isEmpty()) return false;
        return NOMBRE_PATTERN.matcher(texto.trim()).matches();
    }
    

    public static boolean validarContraseña(String contraseña) {
        if (contraseña == null) return false;
        if (contraseña.length() < MIN_PASSWORD_LENGTH) return false;
        if (contraseña.length() > MAX_PASSWORD_LENGTH) return false;
        if (contraseña.contains(" ")) return false;
        return true;
    }
    

    public static boolean validarFechaYEdad(String fecha) {
        if (fecha == null || fecha.isEmpty()) return false;
        
        try {
            if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) return false;
            
            String[] partes = fecha.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int año = Integer.parseInt(partes[2]);
            
            if (mes < 1 || mes > 12 || dia < 1 || año < 1900) return false;
            
            int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            boolean esBisiesto = (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
            if (esBisiesto) diasPorMes[1] = 29;
            if (dia > diasPorMes[mes - 1]) return false;

            LocalDate fechaNacimiento = LocalDate.of(año, mes, dia);
            LocalDate hoy = LocalDate.now();
            int edad = Period.between(fechaNacimiento, hoy).getYears();

            return edad >= MIN_EDAD && edad <= MAX_EDAD;
            
        } catch (Exception e) {
            return false;
        }
    }
    

    public static boolean noEstaVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    

    public static boolean esPositivo(double numero) { return numero > 0; }

    public static boolean esNoNegativo(double numero) { return numero >= 0; }
    

    public static boolean validarPrecio(String precioStr) {
        if (precioStr == null || precioStr.trim().isEmpty()) return false;
        try {
            double precio = Double.parseDouble(precioStr.trim());
            return precio > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

    public static boolean validarCantidad(String cantidadStr) {
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) return false;
        try {
            int cantidad = Integer.parseInt(cantidadStr.trim());
            return cantidad >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

    public static String[] getDominiosValidos() { return DOMINIOS_VALIDOS.clone(); }

    public static int getMinPasswordLength() { return MIN_PASSWORD_LENGTH; }

    public static int getMaxPasswordLength() { return MAX_PASSWORD_LENGTH; }
}
