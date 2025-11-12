
package modelo.ejemplares;
import modelo.Ejemplar;

public class Mapa extends Ejemplar {
    private String escala;
    private String tipoMapa;

    public Mapa() {
        setTipoDocumento(TipoDocumento.Mapas);
    }

    public Mapa(Integer idEjemplar, String codigoEjemplar,String titulo, String autor, String ubicacion,
                Estado estado, String escala, String tipoMapa) {
        super(idEjemplar,  codigoEjemplar,titulo, autor, ubicacion, TipoDocumento.Mapas, estado);
        this.escala = escala;
        this.tipoMapa = tipoMapa;
    }

    public String getEscala() { return escala; }
    public void setEscala(String escala) { this.escala = escala; }

    public String getTipoMapa() { return tipoMapa; }
    public void setTipoMapa(String tipoMapa) { this.tipoMapa = tipoMapa; }
}
