
package modelo;

public class Rol {
    private Integer idRol;
    private String nombreRol;
    private Integer cantMaxPrestamo;
    private Integer diasPrestamo;
    private Double moraDiaria;

    public Rol() {}
    public Rol(Integer idRol, String nombreRol, Integer cantMaxPrestamo, Integer diasPrestamo, Double moraDiaria) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.cantMaxPrestamo = cantMaxPrestamo;
        this.diasPrestamo = diasPrestamo;
        this.moraDiaria = moraDiaria;
    }

    // Getters y Setters
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
    public Integer getCantMaxPrestamo() { return cantMaxPrestamo; }
    public void setCantMaxPrestamo(Integer cantMaxPrestamo) { this.cantMaxPrestamo = cantMaxPrestamo; }
    public Integer getDiasPrestamo() { return diasPrestamo; }
    public void setDiasPrestamo(Integer diasPrestamo) { this.diasPrestamo = diasPrestamo; }
    public Double getMoraDiaria() { return moraDiaria; }
    public void setMoraDiaria(Double moraDiaria) { this.moraDiaria = moraDiaria; }
}
