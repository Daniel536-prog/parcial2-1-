package vista;

import java.util.List;
import java.util.Scanner;
import modelo.Libro;
import servicio.BibliotecaService;

/**
 * Interfaz de consola (Menu principal).
 */
public class MenuPrincipal {
    private BibliotecaService servicio;
    private Scanner sc;

    public MenuPrincipal() {
        servicio = new BibliotecaService();
        sc = new Scanner(System.in);
        precargarDatos();
    }

    private void precargarDatos() {
        // Los libros solicitados (8)
        try {
            servicio.registrarLibro(new Libro("978-0-06", "Cien Años de Soledad", "García M., Gabriel", "Sudamericana", 1967, "Literatura"));
            servicio.registrarLibro(new Libro("978-0-07", "Ficciones", "Borges, Jorge L.", "Sur", 1944, "Ficción"));
            servicio.registrarLibro(new Libro("978-0-08", "Rayuela", "Cortázar, Julio", "Sudamericana", 1963, "Literatura"));
            servicio.registrarLibro(new Libro("978-0-09", "La Casa de los Espíritus", "Allende, Isabel", "Plaza & Janés", 1982, "Ficción"));
            servicio.registrarLibro(new Libro("978-0-10", "Veinte Poemas de Amor", "Neruda, Pablo", "Nascimento", 1924, "Poesía"));
            servicio.registrarLibro(new Libro("978-0-11", "Desolación", "Mistral, Gabriela", "Instituto Hisp.", 1922, "Poesía"));
            servicio.registrarLibro(new Libro("978-0-12", "La Ciudad y los Perros", "Vargas Ll., Mario", "Seix Barral", 1963, "Literatura"));
            servicio.registrarLibro(new Libro("978-0-13", "Pedro Páramo", "Rulfo, Juan", "FCE", 1955, "Literatura"));
        } catch (IllegalArgumentException ex) {
            // en la precarga ignoramos duplicados
        }
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException ex) {
                System.out.println("Opción inválida. Ingrese un número.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("============================================");
        System.out.println("   SISTEMA DE GESTIÓN DE BIBLIOTECA (BST)");
        System.out.println("============================================");
        System.out.println("  1. Registrar nuevo libro");
        System.out.println("  2. Buscar libro por autor");
        System.out.println("  3. Buscar libro por ISBN");
        System.out.println("  4. Eliminar libro del catálogo (por autor)");
        System.out.println("  5. Listar libros (InOrden - alfabético)");
        System.out.println("  6. Listar libros (PreOrden - estructura)");
        System.out.println("  7. Listar libros (PostOrden)");
        System.out.println("  8. Registrar préstamo de libro");
        System.out.println("  9. Registrar devolución de libro");
        System.out.println(" 10. Listar libros disponibles");
        System.out.println(" 11. Listar libros prestados");
        System.out.println(" 12. Buscar libros por categoría");
        System.out.println(" 13. Estadísticas del catálogo");
        System.out.println("  0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                opcionRegistrarLibro();
                break;
            case 2:
                opcionBuscarPorAutor();
                break;
            case 3:
                opcionBuscarPorIsbn();
                break;
            case 4:
                opcionEliminarPorAutor();
                break;
            case 5:
                opcionListarInOrden();
                break;
            case 6:
                opcionListarPreOrden();
                break;
            case 7:
                opcionListarPostOrden();
                break;
            case 8:
                opcionPrestarLibro();
                break;
            case 9:
                opcionDevolverLibro();
                break;
            case 10:
                opcionListarDisponibles();
                break;
            case 11:
                opcionListarPrestados();
                break;
            case 12:
                opcionBuscarPorCategoria();
                break;
            case 13:
                opcionEstadisticas();
                break;
            case 0:
                System.out.println("Saliendo. Gracias.");
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private void opcionRegistrarLibro() {
        try {
            System.out.print("ISBN: ");
            String isbn = sc.nextLine().trim();
            System.out.print("Título: ");
            String titulo = sc.nextLine().trim();
            System.out.print("Autor (ej. Apellido, Nombre): ");
            String autor = sc.nextLine().trim();
            System.out.print("Editorial: ");
            String editorial = sc.nextLine().trim();
            System.out.print("Año de publicación: ");
            int año = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Categoría: ");
            String categoria = sc.nextLine().trim();
            Libro libro = new Libro(isbn, titulo, autor, editorial, año, categoria);
            servicio.registrarLibro(libro);
            System.out.println("Libro registrado correctamente.");
        } catch (NumberFormatException ex) {
            System.out.println("Año inválido.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error al registrar: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex.getMessage());
        }
    }

    private void opcionBuscarPorAutor() {
        System.out.print("Apellido del autor a buscar: ");
        String autor = sc.nextLine().trim();
        List<Libro> resultados = servicio.buscarPorAutor(autor);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros para el autor: " + autor);
        } else {
            System.out.println("Resultados:");
            resultados.forEach(l -> System.out.println(l.toString()));
        }
    }

    private void opcionBuscarPorIsbn() {
        System.out.print("ISBN: ");
        String isbn = sc.nextLine().trim();
        Libro libro = servicio.buscarPorIsbn(isbn);
        if (libro == null) {
            System.out.println("No se encontró libro con ISBN: " + isbn);
        } else {
            System.out.println(libro);
        }
    }

    private void opcionEliminarPorAutor() {
        System.out.print("Apellido del autor a eliminar: ");
        String autor = sc.nextLine().trim();
        try {
            servicio.eliminarPorAutor(autor);
            System.out.println("El autor y su libro fueron eliminados (si existían).");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error al eliminar: " + ex.getMessage());
        }
    }

    private void opcionListarInOrden() {
        List<Libro> lista = servicio.listarInOrden();
        if (lista.isEmpty()) System.out.println("No hay libros en el catálogo.");
        else lista.forEach(l -> System.out.println(l));
    }

    private void opcionListarPreOrden() {
        List<Libro> lista = servicio.listarPreOrden();
        if (lista.isEmpty()) System.out.println("No hay libros en el catálogo.");
        else lista.forEach(l -> System.out.println(l));
    }

    private void opcionListarPostOrden() {
        List<Libro> lista = servicio.listarPostOrden();
        if (lista.isEmpty()) System.out.println("No hay libros en el catálogo.");
        else lista.forEach(l -> System.out.println(l));
    }

    private void opcionPrestarLibro() {
        try {
            System.out.print("ISBN del libro a prestar: ");
            String isbn = sc.nextLine().trim();
            System.out.print("Nombre del prestatario: ");
            String nombre = sc.nextLine().trim();
            servicio.prestarLibro(isbn, nombre);
            System.out.println("Préstamo registrado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Error al prestar: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex.getMessage());
        }
    }

    private void opcionDevolverLibro() {
        try {
            System.out.print("ISBN del libro a devolver: ");
            String isbn = sc.nextLine().trim();
            servicio.devolverLibro(isbn);
            System.out.println("Devolución registrada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Error al devolver: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error inesperado: " + ex.getMessage());
        }
    }

    private void opcionListarDisponibles() {
        List<Libro> lista = servicio.listarDisponibles();
        if (lista.isEmpty()) System.out.println("No hay libros disponibles.");
        else lista.forEach(l -> System.out.println(l));
    }

    private void opcionListarPrestados() {
        List<Libro> lista = servicio.listarPrestados();
        if (lista.isEmpty()) System.out.println("No hay libros prestados.");
        else lista.forEach(l -> System.out.println(l));
    }

    private void opcionBuscarPorCategoria() {
        System.out.print("Categoría: ");
        String categoria = sc.nextLine().trim();
        List<Libro> lista = servicio.buscarPorCategoria(categoria);
        if (lista.isEmpty()) System.out.println("No se encontraron libros en la categoría: " + categoria);
        else lista.forEach(l -> System.out.println(l));
    }

    private void opcionEstadisticas() {
        System.out.println("=== Estadísticas del catálogo ===");
        System.out.println("Total de libros: " + servicio.totalLibros());
        System.out.println("Altura del árbol: " + servicio.alturaArbol());
        Libro primero = servicio.primerAutor();
        Libro ultimo = servicio.ultimoAutor();
        System.out.println("Primer autor (alfabéticamente): " + (primero != null ? primero.getAutor() : "N/A"));
        System.out.println("Último autor (alfabéticamente): " + (ultimo != null ? ultimo.getAutor() : "N/A"));
        System.out.println("Total disponibles: " + servicio.totalDisponibles());
        System.out.println("Total prestados: " + servicio.totalPrestados());
    }

    public static void main(String[] args) {
        MenuPrincipal mp = new MenuPrincipal();
        mp.iniciar();
    }
}
