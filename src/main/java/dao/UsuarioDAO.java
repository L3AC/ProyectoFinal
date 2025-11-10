  
package dao;
import util.ConexionBD;
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void crearUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, dni, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getDni());
            pstmt.setString(5, usuario.getContrasena()); // En la práctica, hashearla antes de insertar
            pstmt.setString(6, usuario.getRol());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1)); // Obtiene el ID generado
                }
                System.out.println("Usuario creado exitosamente con ID: " + usuario.getId());
            } else {
                System.out.println("No se pudo crear el usuario.");
            }
        }
    }

    public Usuario buscarUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, apellido, email, dni, rol, activo FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("dni"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                );
            }
        }
        return null; // No encontrado
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, email, dni, rol, activo FROM usuarios WHERE activo = TRUE";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("dni"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                ));
            }
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, dni = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getDni());
            pstmt.setInt(5, usuario.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el usuario con ID: " + usuario.getId());
            }
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        // Opción 1: Eliminación lógica (recomendada)
        String sql = "UPDATE usuarios SET activo = FALSE WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Usuario desactivado exitosamente.");
            } else {
                System.out.println("No se encontró el usuario con ID: " + id);
            }
        }
        // Opción 2: Eliminación física (NO recomendada si hay dependencias)
        /*
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        */
    }
}
