📚 Sistema de Gestión de Biblioteca (BST)

Este proyecto es una aplicación de consola desarrollada en **Java** para el segundo parcial de la asignatura de Estructuras de Datos. Utiliza un **Árbol Binario de Búsqueda (BST)** como motor principal para gestionar un catálogo bibliográfico de forma eficiente.

 🛠️ Especificaciones Técnicas
 Estructura de Datos**: Árbol Binario de Búsqueda (BST) personalizado.
 Criterio de Ordenamiento**: El árbol se organiza alfabéticamente por el "apellido del autor."
 Lógica de Clave: El sistema extrae automáticamente el apellido buscando una coma (ej. "Borges, Jorge") o tomando la última palabra del nombre completo.
 Arquitectura: Implementación modular dividida en paquetes: `modelo`, `estructura`, `servicio` y `vista`.

🧩 Estructura del Código
`modelo.Libro`: Define los atributos del libro (ISBN, Título, Autor, etc.) y gestiona los estados de préstamo y devolución.
`modelo.NodoBST`: Clase fundamental que contiene la referencia al libro y los punteros a los hijos izquierdo y derecho.
`estructura.ArbolBST`: Contiene la lógica recursiva para insertar, eliminar y buscar nodos, además de los tres tipos de recorridos.
`servicio.BibliotecaService`: Capa de servicio que actúa como puente entre la lógica del árbol y la interfaz de usuario, incluyendo cálculos estadísticos.
`vista.MenuPrincipal`: Interfaz de usuario por consola con un menú interactivo de 13 opciones y precarga de autores clásicos.

🚀 Funcionalidades Principales
1. Gestión de Catálogo: Registrar, buscar (por Autor, ISBN o Categoría) y eliminar libros.
2. Visualización de Estructura: Listado de libros mediante recorridos **InOrden**, **PreOrden** y **PostOrden**.
3. Control de Préstamos: Registro de préstamos indicando el nombre del prestatario y gestión de devoluciones.
4. Estadísticas de Biblioteca:
   Altura del árbol y conteo total de libros.
   Identificación del primer y último autor alfabéticamente.
   Reporte de disponibilidad (Total disponibles vs. prestados).

📖 Autores Precargados
Para facilitar las pruebas, el sistema inicia con 8 obras icónicas:
* Gabriel García Márquez
* Jorge Luis Borges
* Julio Cortázar
* Isabel Allende
* Pablo Neruda
* Gabriela Mistral
* Mario Vargas Llosa
* Juan Rulfo

💻 Ejecución
Para iniciar el sistema, ejecute la clase principal:
```bash
java vista.MenuPrincipal
FRASE:"El código es como un libro: los paquetes son sus capítulos, y el Árbol Binario su índice perfecto."
