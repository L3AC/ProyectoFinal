
package modelo.ejemplares;
import modelo.Ejemplar;

import java.time.LocalDate;

public class Periodico extends Ejemplar {
    private LocalDate fechaPublicacion;
    private String tipoPeriodico;

    public Periodico() {
        setTipoDocumento(TipoDocumento.Periodicos);
    }

    public Periodico(Integer idEjemplar, String titulo, String autor, String ubicacion,
                     Estado estado, LocalDate fechaPublicacion, String tipoPeriodico) {
        super(idEjemplar, titulo, autor, ubicacion, TipoDocumento.Periodicos, estado);
        this.fechaPublicacion = fechaPublicacion;
        this.tipoPeriodico = tipoPeriodico;
    }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public String getTipoPeriodico() { return tipoPeriodico; }
    public void setTipoPeriodico(String tipoPeriodico) { this.tipoPeriodico = tipoPeriodico; }
}
