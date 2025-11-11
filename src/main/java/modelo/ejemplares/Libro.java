
package modelo.ejemplares;

import modelo.Ejemplar;

public class Libro extends Ejemplar {
    private String isbn;
    private String editorial;
    private Integer edicion;

    public Libro() {
        setTipoDocumento(TipoDocumento.Libro);
    }

    public Libro(Integer idEjemplar, String titulo, String autor, String ubicacion,
                 Estado estado, String isbn, String editorial, Integer edicion) {
        super(idEjemplar, titulo, autor, ubicacion, TipoDocumento.Libro, estado);
        this.isbn = isbn;
        this.editorial = editorial;
        this.edicion = edicion;
    }

    // Getters y Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Integer getEdicion() { return edicion; }
    public void setEdicion(Integer edicion) { this.edicion = edicion; }
}