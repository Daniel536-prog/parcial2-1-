package modelo;

public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private String categoria;
    private boolean disponible;
    private String prestatario;

    public Libro(String isbn, String titulo, String autor, String editorial, int anioPublicacion, String categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.categoria = categoria;
        this.disponible = true;
        this.prestatario = null;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getPrestatario() {
        return prestatario;
    }

    /**
     * Prestar el libro a un prestatario. Lanza IllegalStateException si no está disponible.
     */
    public void prestar(String nombrePrestatario) {
        if (!disponible) {
            throw new IllegalStateException("El libro ya está prestado a: " + prestatario);
        }
        if (nombrePrestatario == null || nombrePrestatario.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de prestatario inválido");
        }
        this.disponible = false;
        this.prestatario = nombrePrestatario.trim();
    }

    /**
     * Devolver el libro. Lanza IllegalStateException si ya está disponible.
     */
    public void devolver() {
        if (disponible) {
            throw new IllegalStateException("El libro ya está disponible (no estaba prestado).");
        }
        this.disponible = true;
        this.prestatario = null;
    }

    @Override
    public String toString() {
        String estado = disponible ? "Disponible" : "Prestado a: " + prestatario;
        return String.format("ISBN: %s | Título: %s | Autor: %s | Editorial: %s | Año: %d | Categoría: %s | %s",
                isbn, titulo, autor, editorial, anioPublicacion, categoria, estado);
    }
}
