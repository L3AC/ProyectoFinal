 
package modelo;

import java.util.Date;
import java.time.LocalDate;

public class Prestamo {
    public enum EstadoPrestamo {
        Activo, Devuelto
    }

    private Integer idPrestamo;
    private Usuario usuario;
    private Ejemplar ejemplar;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private EstadoPrestamo estado;

    public Prestamo() {}
    public Prestamo(Integer idPrestamo, Usuario usuario, Ejemplar ejemplar,
                    LocalDate fechaPrestamo, LocalDate fechaLimite, EstadoPrestamo estado) {
        this.idPrestamo = idPrestamo;
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Integer idPrestamo) { this.idPrestamo = idPrestamo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
}