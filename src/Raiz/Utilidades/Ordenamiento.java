/*
 * Utilidad de Ordenamiento y Busqueda - Proyecto Boom Sincronizado
 */

package Raiz.Utilidades;

import Raiz.Modelos.Producto;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author BoomTeam
 * Metodos de ordenamiento y busqueda
 */

public class Ordenamiento {
    
    // ========================================
    // METODOS DE ORDENAMIENTO
    // ========================================
    
    // ----- BURBUJA -----
    
    public static void burbujaPorPrecio(ArrayList<Producto> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j).getPrecioProducto() > lista.get(j + 1).getPrecioProducto()) {
                    Producto temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
    
    public static void burbujaPorNombre(ArrayList<Producto> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j).getNombreProducto().compareToIgnoreCase(
                    lista.get(j + 1).getNombreProducto()) > 0) {
                    Producto temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
    
    public static <T> void burbuja(ArrayList<T> lista, Comparator<T> comparador) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparador.compare(lista.get(j), lista.get(j + 1)) > 0) {
                    T temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
    
    // ----- SELECCION -----
    
    public static void seleccionPorPrecio(ArrayList<Producto> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < n; j++) {
                if (lista.get(j).getPrecioProducto() < lista.get(indiceMenor).getPrecioProducto()) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                Producto temp = lista.get(i);
                lista.set(i, lista.get(indiceMenor));
                lista.set(indiceMenor, temp);
            }
        }
    }
    
    // ----- INSERCION -----
    
    public static void insercionPorPrecio(ArrayList<Producto> lista) {
        int n = lista.size();
        for (int i = 1; i < n; i++) {
            Producto clave = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j).getPrecioProducto() > clave.getPrecioProducto()) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, clave);
        }
    }
    
    // ----- SHELL SORT -----
    
    public static void shellPorPrecio(ArrayList<Producto> lista) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Producto temp = lista.get(i);
                int j;
                for (j = i; j >= gap && lista.get(j - gap).getPrecioProducto() > temp.getPrecioProducto(); j -= gap) {
                    lista.set(j, lista.get(j - gap));
                }
                lista.set(j, temp);
            }
        }
    }
    
    // ----- QUICKSORT -----
    
    public static void quickSortPorPrecio(ArrayList<Producto> lista) {
        quickSortPrecio(lista, 0, lista.size() - 1);
    }
    
    private static void quickSortPrecio(ArrayList<Producto> lista, int inicio, int fin) {
        if (inicio < fin) {
            int indicePivote = particionPrecio(lista, inicio, fin);
            quickSortPrecio(lista, inicio, indicePivote - 1);
            quickSortPrecio(lista, indicePivote + 1, fin);
        }
    }
    
    private static int particionPrecio(ArrayList<Producto> lista, int inicio, int fin) {
        double pivote = lista.get(fin).getPrecioProducto();
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (lista.get(j).getPrecioProducto() <= pivote) {
                i++;
                Producto temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Producto temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(fin));
        lista.set(fin, temp);
        return i + 1;
    }
    
    public static <T> void quickSort(ArrayList<T> lista, Comparator<T> comparador) {
        quickSortGenerico(lista, 0, lista.size() - 1, comparador);
    }
    
    private static <T> void quickSortGenerico(ArrayList<T> lista, int inicio, int fin, Comparator<T> comparador) {
        if (inicio < fin) {
            int indicePivote = particionGenerica(lista, inicio, fin, comparador);
            quickSortGenerico(lista, inicio, indicePivote - 1, comparador);
            quickSortGenerico(lista, indicePivote + 1, fin, comparador);
        }
    }
    
    private static <T> int particionGenerica(ArrayList<T> lista, int inicio, int fin, Comparator<T> comparador) {
        T pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (comparador.compare(lista.get(j), pivote) <= 0) {
                i++;
                T temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        T temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(fin));
        lista.set(fin, temp);
        return i + 1;
    }
    
    // ========================================
    // METODOS DE BUSQUEDA
    // ========================================
    
    // ----- BUSQUEDA SECUENCIAL -----
    
    public static Producto busquedaSecuencialPorId(ArrayList<Producto> lista, String id) {
        for (Producto p : lista) {
            if (p.getIdProducto().equals(id)) return p;
        }
        return null;
    }
    
    public static ArrayList<Producto> busquedaSecuencialPorNombre(ArrayList<Producto> lista, String nombre) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        String nombreLower = nombre.toLowerCase();
        for (Producto p : lista) {
            if (p.getNombreProducto().toLowerCase().contains(nombreLower)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }
    
    public static <T> int busquedaSecuencial(ArrayList<T> lista, T elemento) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(elemento)) return i;
        }
        return -1;
    }
    
    // ----- BUSQUEDA BINARIA -----
    
    public static Producto busquedaBinariaPorPrecio(ArrayList<Producto> lista, double precio) {
        int inicio = 0;
        int fin = lista.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            double precioMedio = lista.get(medio).getPrecioProducto();
            if (precioMedio == precio) return lista.get(medio);
            if (precioMedio < precio) inicio = medio + 1;
            else fin = medio - 1;
        }
        return null;
    }
    
    public static <T> int busquedaBinaria(ArrayList<T> lista, T elemento, Comparator<T> comparador) {
        int inicio = 0;
        int fin = lista.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int comparacion = comparador.compare(lista.get(medio), elemento);
            if (comparacion == 0) return medio;
            if (comparacion < 0) inicio = medio + 1;
            else fin = medio - 1;
        }
        return -1;
    }
    
    // ========================================
    // COMPARADORES PREDEFINIDOS
    // ========================================
    
    public static final Comparator<Producto> COMPARAR_POR_PRECIO_ASC = 
        (p1, p2) -> Double.compare(p1.getPrecioProducto(), p2.getPrecioProducto());
    
    public static final Comparator<Producto> COMPARAR_POR_PRECIO_DESC = 
        (p1, p2) -> Double.compare(p2.getPrecioProducto(), p1.getPrecioProducto());
    
    public static final Comparator<Producto> COMPARAR_POR_NOMBRE = 
        (p1, p2) -> p1.getNombreProducto().compareToIgnoreCase(p2.getNombreProducto());
    
    public static final Comparator<Producto> COMPARAR_POR_STOCK = 
        (p1, p2) -> Integer.compare(p1.getCantidadProducto(), p2.getCantidadProducto());
    
    public static final Comparator<Producto> COMPARAR_POR_CATEGORIA = 
        (p1, p2) -> p1.getCategoriaProducto().compareToIgnoreCase(p2.getCategoriaProducto());
}
