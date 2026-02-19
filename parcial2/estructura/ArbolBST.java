package estructura;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import modelo.Libro;
import modelo.NodoBST;


/**
 * Árbol Binario de Búsqueda ordenado por apellido del autor.
 */
public class ArbolBST {
    private NodoBST raiz;

    public ArbolBST() {
        this.raiz = null;
    }

    /**
     * Inserta un libro en el árbol.
     * @throws IllegalArgumentException si el autor ya existe (mismo apellido exacto, case-insensitive).
     */
    public void insertar(Libro libro) {
        if (libro == null) throw new IllegalArgumentException("Libro nulo");
        String clave = claveAutor(libro.getAutor());
        raiz = insertarRec(raiz, libro, clave);
    }

    private NodoBST insertarRec(NodoBST nodo, Libro libro, String clave) {
        if (nodo == null) {
            return new NodoBST(libro);
        }
        String claveNodo = claveAutor(nodo.libro.getAutor());
        int cmp = clave.compareToIgnoreCase(claveNodo);
        if (cmp < 0) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, libro, clave);
        } else if (cmp > 0) {
            nodo.derecho = insertarRec(nodo.derecho, libro, clave);
        } else {
            // mismo apellido -> consideramos duplicado para el propósito de la estructura
            throw new IllegalArgumentException("Autor duplicado (apellido igual). Apellido: " + clave);
        }
        return nodo;
    }

    /**
     * Buscar libro(s) por autor: retorna lista con coincidencias exactas por apellido (case-insensitive).
     */
    public List<Libro> buscarPorAutor(String autor) {
        List<Libro> resultados = new ArrayList<>();
        if (autor == null || autor.trim().isEmpty()) return resultados;
        buscarPorAutorRec(raiz, claveAutor(autor), resultados);
        return resultados;
    }

    private void buscarPorAutorRec(NodoBST nodo, String clave, List<Libro> resultados) {
        if (nodo == null) return;
        String claveNodo = claveAutor(nodo.libro.getAutor());
        int cmp = clave.compareToIgnoreCase(claveNodo);
        if (cmp < 0) {
            buscarPorAutorRec(nodo.izquierdo, clave, resultados);
        } else if (cmp > 0) {
            buscarPorAutorRec(nodo.derecho, clave, resultados);
        } else {
            // coincidencia exacta en apellido
            resultados.add(nodo.libro);
            // No tenemos duplicados de clave en distintos nodos (modelo actual los previene),
            // pero si quisieras autores con mismo apellido pero distintos nombres, habría que recorrer ambos sub-árboles.
        }
    }

    /**
     * Eliminar por autor (apellido). Lanza IllegalArgumentException si no existe.
     */
    public void eliminarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor inválido");
        }
        String clave = claveAutor(autor);
        if (!existeApellido(clave)) {
            throw new IllegalArgumentException("Autor no encontrado: " + autor);
        }
        raiz = eliminarRec(raiz, clave);
    }

    private NodoBST eliminarRec(NodoBST nodo, String clave) {
        if (nodo == null) return null;
        String claveNodo = claveAutor(nodo.libro.getAutor());
        int cmp = clave.compareToIgnoreCase(claveNodo);
        if (cmp < 0) {
            nodo.izquierdo = eliminarRec(nodo.izquierdo, clave);
        } else if (cmp > 0) {
            nodo.derecho = eliminarRec(nodo.derecho, clave);
        } else {
            // nodo a eliminar
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            } else if (nodo.izquierdo == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierdo;
            } else {
                // dos hijos: reemplazar por mínimo del subárbol derecho
                NodoBST sucesor = encontrarMinNodo(nodo.derecho);
                nodo.libro = sucesor.libro;
                nodo.derecho = eliminarRec(nodo.derecho, claveAutor(sucesor.libro.getAutor()));
            }
        }
        return nodo;
    }

    private NodoBST encontrarMinNodo(NodoBST nodo) {
        NodoBST actual = nodo;
        while (actual != null && actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    /**
     * Recorridos
     */
    public List<Libro> recorridoInOrden() {
        List<Libro> lista = new ArrayList<>();
        inOrdenRec(raiz, lista);
        return lista;
    }

    private void inOrdenRec(NodoBST nodo, List<Libro> lista) {
        if (nodo == null) return;
        inOrdenRec(nodo.izquierdo, lista);
        lista.add(nodo.libro);
        inOrdenRec(nodo.derecho, lista);
    }

    public List<Libro> recorridoPreOrden() {
        List<Libro> lista = new ArrayList<>();
        preOrdenRec(raiz, lista);
        return lista;
    }

    private void preOrdenRec(NodoBST nodo, List<Libro> lista) {
        if (nodo == null) return;
        lista.add(nodo.libro);
        preOrdenRec(nodo.izquierdo, lista);
        preOrdenRec(nodo.derecho, lista);
    }

    public List<Libro> recorridoPostOrden() {
        List<Libro> lista = new ArrayList<>();
        postOrdenRec(raiz, lista);
        return lista;
    }

    private void postOrdenRec(NodoBST nodo, List<Libro> lista) {
        if (nodo == null) return;
        postOrdenRec(nodo.izquierdo, lista);
        postOrdenRec(nodo.derecho, lista);
        lista.add(nodo.libro);
    }

    /**
     * Encontrar mínimo (primer autor alfabéticamente)
     */
    public Libro encontrarMinimo() {
        if (raiz == null) return null;
        NodoBST min = raiz;
        while (min.izquierdo != null) min = min.izquierdo;
        return min.libro;
    }

    /**
     * Encontrar máximo (último autor alfabéticamente)
     */
    public Libro encontrarMaximo() {
        if (raiz == null) return null;
        NodoBST max = raiz;
        while (max.derecho != null) max = max.derecho;
        return max.libro;
    }

    public int contarNodos() {
        return contarRec(raiz);
    }

    private int contarRec(NodoBST nodo) {
        if (nodo == null) return 0;
        return 1 + contarRec(nodo.izquierdo) + contarRec(nodo.derecho);
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoBST nodo) {
        if (nodo == null) return 0;
        int izq = alturaRec(nodo.izquierdo);
        int der = alturaRec(nodo.derecho);
        return 1 + Math.max(izq, der);
    }

    /**
     * Busca por ISBN (recorre el árbol).
     */
    public Libro buscarPorIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) return null;
        return buscarPorIsbnRec(raiz, isbn.trim());
    }

    private Libro buscarPorIsbnRec(NodoBST nodo, String isbn) {
        if (nodo == null) return null;
        if (Objects.equals(nodo.libro.getIsbn(), isbn)) return nodo.libro;
        Libro izq = buscarPorIsbnRec(nodo.izquierdo, isbn);
        if (izq != null) return izq;
        return buscarPorIsbnRec(nodo.derecho, isbn);
    }

    /**
     * Buscar por categoría (recorrido completo)
     */
    public List<Libro> buscarPorCategoria(String categoria) {
        List<Libro> lista = new ArrayList<>();
        if (categoria == null) return lista;
        buscarPorCategoriaRec(raiz, categoria.trim().toLowerCase(), lista);
        return lista;
    }

    private void buscarPorCategoriaRec(NodoBST nodo, String categoriaLower, List<Libro> lista) {
        if (nodo == null) return;
        buscarPorCategoriaRec(nodo.izquierdo, categoriaLower, lista);
        if (nodo.libro.getCategoria() != null &&
                nodo.libro.getCategoria().toLowerCase().equals(categoriaLower)) {
            lista.add(nodo.libro);
        }
        buscarPorCategoriaRec(nodo.derecho, categoriaLower, lista);
    }

    /**
     * Listar disponibles / prestados
     */
    public List<Libro> listarDisponibles() {
        List<Libro> lista = new ArrayList<>();
        listarPorDisponibilidadRec(raiz, true, lista);
        return lista;
    }

    public List<Libro> listarPrestados() {
        List<Libro> lista = new ArrayList<>();
        listarPorDisponibilidadRec(raiz, false, lista);
        return lista;
    }

    private void listarPorDisponibilidadRec(NodoBST nodo, boolean disponible, List<Libro> lista) {
        if (nodo == null) return;
        listarPorDisponibilidadRec(nodo.izquierdo, disponible, lista);
        if (nodo.libro.isDisponible() == disponible) lista.add(nodo.libro);
        listarPorDisponibilidadRec(nodo.derecho, disponible, lista);
    }

    /**
     * Comprueba si existe un apellido en el árbol.
     */
    private boolean existeApellido(String clave) {
        return existeRec(raiz, clave);
    }

    private boolean existeRec(NodoBST nodo, String clave) {
        if (nodo == null) return false;
        String claveNodo = claveAutor(nodo.libro.getAutor());
        int cmp = clave.compareToIgnoreCase(claveNodo);
        if (cmp < 0) return existeRec(nodo.izquierdo, clave);
        else if (cmp > 0) return existeRec(nodo.derecho, clave);
        else return true;
    }

    /**
     * Extrae el apellido del campo autor para comparar claves.
     * Si la cadena contiene comas (ej. "Borges, Jorge L.") toma la parte antes de la coma.
     * Si no contiene coma, toma la última palabra.
     */
    private String claveAutor(String autor) {
        if (autor == null) return "";
        autor = autor.trim();
        if (autor.contains(",")) {
            String[] parts = autor.split(",", 2);
            return parts[0].trim().toLowerCase();
        } else {
            String[] words = autor.split("\\s+");
            return words[words.length - 1].trim().toLowerCase();
        }
    }
}
