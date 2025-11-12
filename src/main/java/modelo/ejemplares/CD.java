
package modelo.ejemplares;
import modelo.Ejemplar;

import java.time.LocalTime;

public class CD extends Ejemplar {
    private LocalTime duracion;
    private String genero;

    public CD() { setTipoDocumento(TipoDocumento.CD); }

    public CD(Integer idEjemplar,String codigoEjemplar, String titulo, String autor, String ubicacion,
              Estado estado, LocalTime duracion, String genero) {
        super(idEjemplar,codigoEjemplar, titulo, autor, ubicacion, TipoDocumento.CD, estado);
        this.duracion = duracion;
        this.genero = genero;
    }

    public LocalTime getDuracion() { return duracion; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
