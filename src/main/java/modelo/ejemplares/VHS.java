
package modelo.ejemplares;
import modelo.Ejemplar;
import java.time.LocalTime;

public class VHS extends Ejemplar {
    private LocalTime duracion;
    private String genero;

    public VHS() { setTipoDocumento(TipoDocumento.VHS); }

    public VHS(Integer idEjemplar, String titulo, String autor, String ubicacion,
               Estado estado, LocalTime duracion, String genero) {
        super(idEjemplar, titulo, autor, ubicacion, TipoDocumento.VHS, estado);
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y Setters
    public LocalTime getDuracion() { return duracion; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
