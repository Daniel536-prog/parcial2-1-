package servicio;

import estructura.ArbolBST;
import java.util.List;
import modelo.Libro;

public class BibliotecaService {
    public  ArbolBST arbol;

    public BibliotecaService() {
        this.arbol = new ArbolBST();
    }

    public ArbolBST getArbol() {
        return arbol;
    }

    public void registrarLibro(Libro libro) {
        arbol.insertar(libro);
    }

    public List<Libro> buscarPorAutor(String autor) {
        return arbol.buscarPorAutor(autor);
    }

    public Libro buscarPorIsbn(String isbn) {
        return arbol.buscarPorIsbn(isbn);
    }

    public void eliminarPorAutor(String autor) {
        arbol.eliminarPorAutor(autor);
    }

    public List<Libro> listarInOrden() {
        return arbol.recorridoInOrden();
    }

    public List<Libro> listarPreOrden() {
        return arbol.recorridoPreOrden();
    }

    public List<Libro> listarPostOrden() {
        return arbol.recorridoPostOrden();
    }

    public void prestarLibro(String isbn, String nombrePrestatario) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro == null) throw new IllegalArgumentException("ISBN no encontrado: " + isbn);
        libro.prestar(nombrePrestatario);
    }

    public void devolverLibro(String isbn) {
        Libro libro = buscarPorIsbn(isbn);
        if (libro == null) throw new IllegalArgumentException("ISBN no encontrado: " + isbn);
        libro.devolver();
    }

    public List<Libro> listarDisponibles() {
        return arbol.listarDisponibles();
    }

    public List<Libro> listarPrestados() {
        return arbol.listarPrestados();
    }

    public List<Libro> buscarPorCategoria(String categoria) {
        return arbol.buscarPorCategoria(categoria);
    }

    /* Estadísticas */
    public int totalLibros() {
        return arbol.contarNodos();
    }

    public int alturaArbol() {
        return arbol.altura();
    }

    public Libro primerAutor() {
        return arbol.encontrarMinimo();
    }

    public Libro ultimoAutor() {
        return arbol.encontrarMaximo();
    }

    public long totalDisponibles() {
        return listarDisponibles().size();
    }

    public long totalPrestados() {
        return listarPrestados().size();
    }
}
