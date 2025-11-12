package modelo;
import modelo.Ejemplar;
import modelo.Usuario;


import java.sql.Date;

public class Prestamo {
    private Integer idPrestamo;
    private Usuario idUsuario; 
    private Ejemplar idEjemplar; 
    private Date fechaPrestamo;
    private String estado; // "Activo" o "Devuelto"
    private Date fechaDevolucion;

    // Constructor vacío
    public Prestamo() {}

    // Constructor con parámetros
    public Prestamo(Integer idPrestamo, Usuario idUsuario, Ejemplar idEjemplar, Date fechaPrestamo, 
                    String estado, Date fechaDevolucion) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idEjemplar = idEjemplar;
        this.fechaPrestamo = fechaPrestamo;
        this.estado = estado;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y Setters
    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Ejemplar getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(Ejemplar idEjemplar) {
        this.idEjemplar = idEjemplar;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}