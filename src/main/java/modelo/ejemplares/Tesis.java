package modelo.ejemplares;
import modelo.Ejemplar;

public class Tesis extends Ejemplar {
    private String gradoAcademico;
    private String facultad;

    public Tesis() {
        setTipoDocumento(TipoDocumento.Tesis);
    }

    public Tesis(Integer idEjemplar, String codigoEjemplar,String titulo, String autor, String ubicacion,
                 Estado estado, String gradoAcademico, String facultad) {
        super(idEjemplar,  codigoEjemplar,titulo, autor, ubicacion, TipoDocumento.Tesis, estado);
        this.gradoAcademico = gradoAcademico;
        this.facultad = facultad;
    }

    public String getGradoAcademico() { return gradoAcademico; }
    public void setGradoAcademico(String gradoAcademico) { this.gradoAcademico = gradoAcademico; }

    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
}
