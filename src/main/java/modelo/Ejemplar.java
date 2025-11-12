package modelo;

public class Ejemplar {
    public enum TipoDocumento {
        Libro, Diccionario, Mapas, Tesis, DVD, VHS, Cassettes, CD, Documento, Periodicos, Revistas
    }

    public enum Estado {
        Disponible, Prestado
    }

    private Integer idEjemplar;
    private String codigoEjemplar; 
    private String titulo;
    private String autor;
    private String ubicacion;
    private TipoDocumento tipoDocumento;
    private Estado estado;

    public Ejemplar() {}

    public Ejemplar(Integer idEjemplar, String codigoEjemplar, String titulo, String autor, 
                    String ubicacion, TipoDocumento tipoDocumento, Estado estado) {
        this.idEjemplar = idEjemplar;
        this.codigoEjemplar = codigoEjemplar;
        this.titulo = titulo;
        this.autor = autor;
        this.ubicacion = ubicacion;
        this.tipoDocumento = tipoDocumento;
        this.estado = estado;
    }

    // Getters y Setters (incluyendo para codigoEjemplar)

    public String getCodigoEjemplar() {
        return codigoEjemplar;
    }

    public void setCodigoEjemplar(String codigoEjemplar) {
        this.codigoEjemplar = codigoEjemplar;
    }

    // ... resto de getters y setters ya existentes

    public Integer getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(Integer idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
