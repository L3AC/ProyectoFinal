package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Ejemplar;
import modelo.Prestamo;
import modelo.Reserva;
import modelo.Usuario;
import modelo.Rol;
import util.ConexionBD;

public class ReservaDAO {

    public void crearReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO Reservas (id_usuario, id_ejemplar, fecha_reserva) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reserva.getUsuario().getIdUsuario());
            stmt.setInt(2, reserva.getEjemplar().getIdEjemplar());
            stmt.setDate(3, reserva.getFechaReserva());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setIdReserva(rs.getInt(1));
                }
            }
        }
    }

    public boolean puedeCrearPrestamo(Usuario usuario) throws SQLException {
        if (tieneMora(usuario.getIdUsuario())) {
            return false;
        }

        int prestamosActivos = obtenerCantidadPrestamosActivos(usuario.getIdUsuario());
        if (prestamosActivos >= usuario.getRol().getCantMaxPrestamo()) {
            return false;
        }

        return true;
    }

    private boolean tieneMora(int idUsuario) throws SQLException {
        String sql = """
            SELECT COUNT(*) FROM Prestamos p
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
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private int obtenerCantidadPrestamosActivos(int idUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Prestamos WHERE id_usuario = ? AND estado = 'Activo'";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<Reserva> obtenerReservasPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM Reservas WHERE id_usuario = ?";
        List<Reserva> reservas = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    reserva.setUsuario(usuario);

                    Ejemplar ejemplar = new Ejemplar();
                    ejemplar.setIdEjemplar(rs.getInt("id_ejemplar"));
                    reserva.setEjemplar(ejemplar);

                    reserva.setFechaReserva(rs.getDate("fecha_reserva"));
                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }

    public boolean eliminarReserva(int idReserva) throws SQLException {
        String sql = "DELETE FROM Reservas WHERE id_reserva = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            return stmt.executeUpdate() > 0;
        }
    }

public List<Reserva> buscarReservas(String filtro) throws SQLException {
        String sql = """
            SELECT 
                res.id_reserva,
                res.id_usuario,
                res.id_ejemplar,
                e.titulo AS titulo,
                e.codigo_ejemplar AS codigo,
                e.tipo_documento AS tipo_documento,
                u.correo AS correo_usuario,
                r.nombre_rol,
                r.cant_max_prestamo, 
                r.dias_prestamo,
                r.mora_diaria,
                res.fecha_reserva AS fecha_reserva
            FROM Reservas res
            INNER JOIN Ejemplares e ON res.id_ejemplar = e.id_ejemplar
            INNER JOIN Usuarios u ON res.id_usuario = u.id_usuario
            INNER JOIN Roles r ON u.id_rol = r.id_rol
            WHERE 
                e.titulo LIKE ? OR 
                u.correo LIKE ? OR 
                e.tipo_documento LIKE ?
            ORDER BY res.fecha_reserva DESC
            """;

        List<Reserva> reservas = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String param = "%" + filtro + "%";
            stmt.setString(1, param);
            stmt.setString(2, param);
            stmt.setString(3, param);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = new Reserva();
                    reserva.setIdReserva(rs.getInt("id_reserva"));

                    // Construir Ejemplar
                    Ejemplar ejemplar = new Ejemplar();
                    ejemplar.setIdEjemplar(rs.getInt("id_ejemplar")); 
                    ejemplar.setTitulo(rs.getString("titulo"));
                    ejemplar.setCodigoEjemplar(rs.getString("codigo"));
                    ejemplar.setTipoDocumento(Ejemplar.TipoDocumento.valueOf(rs.getString("tipo_documento")));
                    reserva.setEjemplar(ejemplar);

                    // Construir Usuario
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setCorreo(rs.getString("correo_usuario"));
                    
                    // Construir Rol COMPLETO (Aquí estaba el error antes)
                    Rol rol = new Rol();
                    rol.setNombreRol(rs.getString("nombre_rol"));
                    rol.setCantMaxPrestamo(rs.getInt("cant_max_prestamo")); // <--- ESTO FALTABA
                    rol.setDiasPrestamo(rs.getInt("dias_prestamo"));        // Útil para el préstamo
                    rol.setMoraDiaria(rs.getDouble("mora_diaria"));         // Útil para cálculos
                    
                    usuario.setRol(rol); // Asignamos el rol al usuario
                    
                    reserva.setUsuario(usuario);
                    reserva.setFechaReserva(rs.getDate("fecha_reserva"));

                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }
}