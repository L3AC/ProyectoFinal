
package modelo.ejemplares;
import modelo.Ejemplar;

public class Diccionario extends Ejemplar {
    private String idioma;
    private Integer volumen;

    public Diccionario() {
        setTipoDocumento(TipoDocumento.Diccionario);
    }

    public Diccionario(Integer idEjemplar, String titulo, String autor, String ubicacion,
                       Estado estado, String idioma, Integer volumen) {
        super(idEjemplar, titulo, autor, ubicacion, TipoDocumento.Diccionario, estado);
        this.idioma = idioma;
        this.volumen = volumen;
    }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getVolumen() { return volumen; }
    public void setVolumen(Integer volumen) { this.volumen = volumen; }
}
