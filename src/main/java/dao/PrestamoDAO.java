package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import modelo.Ejemplar;
import modelo.Prestamo;
import modelo.Usuario;
import modelo.Rol;
import util.ConexionBD;

public class PrestamoDAO {

    public void crearPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, prestamo.getIdUsuario().getIdUsuario());
            stmt.setInt(2, prestamo.getIdEjemplar().getIdEjemplar());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setString(4, prestamo.getEstado());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    prestamo.setIdPrestamo(rs.getInt(1));
                }
            }

            // Actualizar estado del ejemplar a "Prestado"
            actualizarEstadoEjemplar(prestamo.getIdEjemplar().getIdEjemplar(), "Prestado");
        }
    }

    public void devolverPrestamo(int idPrestamo) throws SQLException {
        String sql = "UPDATE Prestamos SET estado = 'Devuelto', fecha_devolucion = ? WHERE id_prestamo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            stmt.setInt(2, idPrestamo);
            stmt.executeUpdate();
        }

        int idEjemplar = obtenerIdEjemplarPorPrestamo(idPrestamo);
        actualizarEstadoEjemplar(idEjemplar, "Disponible");
    }

    private int obtenerIdEjemplarPorPrestamo(int idPrestamo) throws SQLException {
        String sql = "SELECT id_ejemplar FROM Prestamos WHERE id_prestamo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPrestamo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_ejemplar");
                }
            }
        }
        throw new SQLException("Préstamo no encontrado");
    }

    private void actualizarEstadoEjemplar(int idEjemplar, String nuevoEstado) throws SQLException {
        String sql = "UPDATE Ejemplares SET estado = ? WHERE id_ejemplar = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idEjemplar);
            stmt.executeUpdate();
        }
    }

    public double calcularMoraTotal(int idUsuario) throws SQLException {
        String sql = """
            SELECT SUM(DATEDIFF(CURDATE(), DATE_ADD(p.fecha_prestamo, INTERVAL r.dias_prestamo DAY)) * r.mora_diaria) AS mora_total
            FROM Prestamos p
            JOIN Usuarios u ON p.id_usuario = u.id_usuario
            JOIN Roles r ON u.id_rol = r.id_rol
            WHERE p.id_usuario = ? AND p.estado = 'Activo'
            AND DATE_ADD(p.fecha_prestamo, INTERVAL r.dias_prestamo DAY) < CURDATE()
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Double mora = rs.getDouble("mora_total");
                    return mora != null ? mora : 0.0;
                }
            }
        }
        return 0.0;
    }

    public double calcularMoraPorPrestamo(int idPrestamo) throws SQLException {
        String sql = """
            SELECT DATEDIFF(CURDATE(), DATE_ADD(p.fecha_prestamo, INTERVAL r.dias_prestamo DAY)) * r.mora_diaria AS mora
            FROM Prestamos p
            JOIN Usuarios u ON p.id_usuario = u.id_usuario
            JOIN Roles r ON u.id_rol = r.id_rol
            WHERE p.id_prestamo = ? AND p.estado = 'Activo'
            AND DATE_ADD(p.fecha_prestamo, INTERVAL r.dias_prestamo DAY) < CURDATE()
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPrestamo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Double mora = rs.getDouble("mora");
                    return mora != null ? mora : 0.0;
                }
            }
        }
        return 0.0;
    }

    public List<Prestamo> obtenerPrestamosActivos(int idUsuario) throws SQLException {
        String sql = """
            SELECT p.*, e.titulo, e.codigo_ejemplar, u.nombre, u.apellido
            FROM Prestamos p
            JOIN Ejemplares e ON p.id_ejemplar = e.id_ejemplar
            JOIN Usuarios u ON p.id_usuario = u.id_usuario
            WHERE p.id_usuario = ? AND p.estado = 'Activo'
            """;

        List<Prestamo> prestamos = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prestamo prestamo = new Prestamo();
                    prestamo.setIdPrestamo(rs.getInt("id_prestamo"));

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    prestamo.setIdUsuario(usuario);

                    Ejemplar ejemplar = new Ejemplar();
                    ejemplar.setIdEjemplar(rs.getInt("id_ejemplar"));
                    ejemplar.setTitulo(rs.getString("titulo"));
                    ejemplar.setCodigoEjemplar(rs.getString("codigo_ejemplar"));
                    prestamo.setIdEjemplar(ejemplar);

                    prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                    prestamo.setEstado(rs.getString("estado"));
                    prestamo.setFechaDevolucion(rs.getDate("fecha_devolucion"));

                    prestamos.add(prestamo);
                }
            }
        }
        return prestamos;
    }

    // --- NUEVA FUNCIÓN: Buscar préstamos con filtros ---
    public List<Prestamo> buscarPrestamos(String filtro) throws SQLException {
        String sql = """
            SELECT 
                p.id_prestamo AS id_prestamo,
                e.titulo AS titulo,
                e.codigo_ejemplar AS codigo,
                e.tipo_documento AS tipo_documento,
                u.correo AS correo_usuario,
                r.nombre_rol AS nombre_rol,
                p.fecha_prestamo AS fecha_prestamo,
                p.estado AS estado
            FROM Prestamos p
            INNER JOIN Ejemplares e ON p.id_ejemplar = e.id_ejemplar
            INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario
            INNER JOIN Roles r ON u.id_rol = r.id_rol
            WHERE 
                e.titulo LIKE ? OR 
                u.correo LIKE ? OR 
                e.tipo_documento LIKE ?
            ORDER BY p.fecha_prestamo DESC
            """;

        List<Prestamo> prestamos = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String param = "%" + filtro + "%";
            stmt.setString(1, param);
            stmt.setString(2, param);
            stmt.setString(3, param);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prestamo prestamo = new Prestamo();
                    prestamo.setIdPrestamo(rs.getInt("id_prestamo"));

                    Ejemplar ejemplar = new Ejemplar();
                    ejemplar.setTitulo(rs.getString("titulo"));
                    ejemplar.setCodigoEjemplar(rs.getString("codigo"));
                    prestamo.setIdEjemplar(ejemplar);

                    Usuario usuario = new Usuario();
                    usuario.setCorreo(rs.getString("correo_usuario"));
                    Rol rol = new Rol();
                    rol.setNombreRol(rs.getString("nombre_rol"));
                    usuario.setRol(rol);
                    prestamo.setIdUsuario(usuario);

                    prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                    prestamo.setEstado(rs.getString("estado"));

                    prestamos.add(prestamo);
                }
            }
        }
        return prestamos;
    }
}