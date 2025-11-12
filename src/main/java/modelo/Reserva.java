package modelo;
import modelo.Ejemplar;
import modelo.Usuario;

// --- MODELO DE RESERVA ---
public class Reserva {
    private Integer idReserva;
    private Usuario usuario;
    private Ejemplar ejemplar;
    private java.sql.Date fechaReserva;

    public Reserva() {}

    public Reserva(Integer idReserva, Usuario usuario, Ejemplar ejemplar, java.sql.Date fechaReserva) {
        this.idReserva = idReserva;
        this.usuario = usuario;
        this.ejemplar = ejemplar;
        this.fechaReserva = fechaReserva;
    }

    // Getters y Setters
    public Integer getIdReserva() { return idReserva; }
    public void setIdReserva(Integer idReserva) { this.idReserva = idReserva; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Ejemplar getEjemplar() { return ejemplar; }
    public void setEjemplar(Ejemplar ejemplar) { this.ejemplar = ejemplar; }

    public java.sql.Date getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(java.sql.Date fechaReserva) { this.fechaReserva = fechaReserva; }
}