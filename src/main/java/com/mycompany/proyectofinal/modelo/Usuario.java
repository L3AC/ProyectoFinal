
package com.mycompany.proyectofinal.modelo;
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String dni;
    private String contrasena; 
    private String rol; 
    private boolean activo;
 
    // Constructor vacío
    public Usuario() {}

    // Constructor para registrar
    public Usuario(String nombre, String apellido, String email, String dni, String contrasena, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true; 
    }

    // Constructor para cargar datos
    public Usuario(int id, String nombre, String apellido, String email, String dni, String rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.rol = rol;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; } // En la práctica, hashearla antes de asignarla

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", dni='" + dni + '\'' +
                ", rol='" + rol + '\'' +
                ", activo=" + activo +
                '}';
    }
}
