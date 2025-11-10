
package modelo;

import java.time.LocalDate;

public class Devolucion {
    private Integer idDevolucion;
    private Prestamo prestamo;
    private LocalDate fechaDevolucion;
    private Integer diasRetraso;
    private Double montoMora;

    public Devolucion() {}
    public Devolucion(Integer idDevolucion, Prestamo prestamo, LocalDate fechaDevolucion,
                      Integer diasRetraso, Double montoMora) {
        this.idDevolucion = idDevolucion;
        this.prestamo = prestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.diasRetraso = diasRetraso;
        this.montoMora = montoMora;
    }

    // Getters y Setters
    public Integer getIdDevolucion() { return idDevolucion; }
    public void setIdDevolucion(Integer idDevolucion) { this.idDevolucion = idDevolucion; }
    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public Integer getDiasRetraso() { return diasRetraso; }
    public void setDiasRetraso(Integer diasRetraso) { this.diasRetraso = diasRetraso; }
    public Double getMontoMora() { return montoMora; }
    public void setMontoMora(Double montoMora) { this.montoMora = montoMora; }
}
