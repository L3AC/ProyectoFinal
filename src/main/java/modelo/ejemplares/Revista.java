
package modelo.ejemplares;
import modelo.Ejemplar;

import java.time.LocalDate;

public class Revista extends Ejemplar {
    private LocalDate fechaPublicacion;
    private String tipoRevista;

    public Revista() {
        setTipoDocumento(TipoDocumento.Revistas);
    }

    public Revista(Integer idEjemplar, String titulo, String autor, String ubicacion,
                   Estado estado, LocalDate fechaPublicacion, String tipoRevista) {
        super(idEjemplar, titulo, autor, ubicacion, TipoDocumento.Revistas, estado);
        this.fechaPublicacion = fechaPublicacion;
        this.tipoRevista = tipoRevista;
    }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public String getTipoRevista() { return tipoRevista; }
    public void setTipoRevista(String tipoRevista) { this.tipoRevista = tipoRevista; }
}