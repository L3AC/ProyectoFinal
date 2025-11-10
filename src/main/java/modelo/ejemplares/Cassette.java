
package modelo.ejemplares;
import modelo.Ejemplar;

import java.time.LocalTime;

public class Cassette extends Ejemplar {
    private LocalTime duracion;
    private String tipoCinta; // audio, video

    public Cassette() { setTipoDocumento(TipoDocumento.Cassettes); }

    public Cassette(Integer idEjemplar, String titulo, String autor, String ubicacion,
                    Estado estado, LocalTime duracion, String tipoCinta) {
        super(idEjemplar, titulo, autor, ubicacion, TipoDocumento.Cassettes, estado);
        this.duracion = duracion;
        this.tipoCinta = tipoCinta;
    }

    public LocalTime getDuracion() { return duracion; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }
    public String getTipoCinta() { return tipoCinta; }
    public void setTipoCinta(String tipoCinta) { this.tipoCinta = tipoCinta; }
}
