 
package com.mycompany.proyectofinal.modelo;

public class Ejemplar {
    private int id;
    private String titulo;
    private int idTipoDocumento;
    private Integer idAutor; // Puede ser null
    private Integer idCategoria; // Puede ser null
    private String isbn;
    private String editorial;
    private int anioPublicacion;
    private int numeroPaginas;
    private String ubicacion;
    private int cantidadTotal;
    private int cantidadDisponible;
    private String estado; // 'Disponible', 'Prestado', 'No Disponible', 'En Reparación'

    // Constructor vacío
    public Ejemplar() {}

    // Constructor con parámetros (sin ID)
    public Ejemplar(String titulo, int idTipoDocumento, Integer idAutor, Integer idCategoria,
                    String isbn, String editorial, int anioPublicacion, int numeroPaginas,
                    String ubicacion, int cantidadTotal, String estado) {
        this.titulo = titulo;
        this.idTipoDocumento = idTipoDocumento;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
        this.isbn = isbn;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.numeroPaginas = numeroPaginas;
        this.ubicacion = ubicacion;
        this.cantidadTotal = cantidadTotal;
        this.cantidadDisponible = cantidadTotal; // Inicialmente disponible
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getIdTipoDocumento() { return idTipoDocumento; }
    public void setIdTipoDocumento(int idTipoDocumento) { this.idTipoDocumento = idTipoDocumento; }

    public Integer getIdAutor() { return idAutor; }
    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public int getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(int numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public int getCantidadTotal() { return cantidadTotal; }
    public void setCantidadTotal(int cantidadTotal) { this.cantidadTotal = cantidadTotal; }

    public int getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Ejemplar{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idTipoDocumento=" + idTipoDocumento +
                ", idAutor=" + idAutor +
                ", idCategoria=" + idCategoria +
                ", isbn='" + isbn + '\'' +
                ", editorial='" + editorial + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", numeroPaginas=" + numeroPaginas +
                ", ubicacion='" + ubicacion + '\'' +
                ", cantidadTotal=" + cantidadTotal +
                ", cantidadDisponible=" + cantidadDisponible +
                ", estado='" + estado + '\'' +
                '}';
    }
}