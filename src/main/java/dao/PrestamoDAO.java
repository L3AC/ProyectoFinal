package dao;

import util.ConexionBD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Ejemplar;
import modelo.Prestamo;
import modelo.Usuario;

public class PrestamoDAO {

    private static final Logger logger = LogManager.getLogger(PrestamoDAO.class);

    // === VALIDAR SI USUARIO TIENE MORA PENDIENTE ===
    private boolean tieneMoraPendiente(Connection conn, int idUsuario) throws SQLException {
        String sql = """
            SELECT d.monto_mora
            FROM Devoluciones d
            JOIN Prestamos p ON d.id_prestamo = p.id_prestamo
            WHERE p.id_usuario = ?
              AND d.monto_mora > 0
            LIMIT 1
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si hay al menos una, tiene mora
            }
        }
    }

    // === VERIFICAR LÍMITE DE PRÉSTAMOS ACTIVOS ===
    private boolean haAlcanzadoLimitePrestamos(Connection conn, int idUsuario) throws SQLException {
        String sqlRol = "SELECT r.cant_max_prestamo FROM Roles r JOIN Usuarios u ON u.id_rol = r.id_rol WHERE u.id_usuario = ?";
        int cantMax = 0;
        try (PreparedStatement ps = conn.prepareStatement(sqlRol)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cantMax = rs.getInt("cant_max_prestamo");
                }
            }
        }

        // Si es 0 (como Administrador), no hay límite
        if (cantMax == 0) return false;

        String sqlPrestamosActivos = "SELECT COUNT(*) AS activos FROM Prestamos WHERE id_usuario = ? AND estado = 'Activo'";
        try (PreparedStatement ps = conn.prepareStatement(sqlPrestamosActivos)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int activos = rs.getInt("activos");
                    return activos >= cantMax;
                }
            }
        }
        return false;
    }

    // === REGISTRAR PRÉSTAMO ===
    public boolean registrarPrestamo(int idUsuario, int idEjemplar) {
        String sqlInsertarPrestamo = """
            INSERT INTO Prestamos (id_usuario, id_ejemplar, fecha_prestamo, fecha_limite, estado)
            VALUES (?, ?, ?, ?, 'Activo')
            """;

        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Validar que el usuario exista y cargue su rol (implícito en las validaciones)
            // 2. Verificar mora pendiente
            if (tieneMoraPendiente(conn, idUsuario)) {
                JOptionPane.showMessageDialog(null, "El usuario tiene mora pendiente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                logger.warn("Préstamo rechazado: usuario con mora pendiente. ID: " + idUsuario);
                return false;
            }

            // 3. Verificar límite de préstamos
            if (haAlcanzadoLimitePrestamos(conn, idUsuario)) {
                JOptionPane.showMessageDialog(null, "Límite de préstamos alcanzado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                logger.warn("Préstamo rechazado: límite de préstamos alcanzado. ID: " + idUsuario);
                return false;
            }

            // 4. Obtener días de préstamo desde el rol del usuario
            String sqlDias = """
                SELECT r.dias_prestamo
                FROM Usuarios u
                JOIN Roles r ON u.id_rol = r.id_rol
                WHERE u.id_usuario = ?
                """;
            int diasPrestamo = 0;
            try (PreparedStatement psDias = conn.prepareStatement(sqlDias)) {
                psDias.setInt(1, idUsuario);
                
                try (ResultSet rs = psDias.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false; // Usuario no existe
                    }
                    diasPrestamo = rs.getInt("dias_prestamo");
                }
            }

            // 5. Verificar que el ejemplar esté disponible
            String sqlEjemplar = "SELECT estado FROM Ejemplares WHERE id_ejemplar = ?";
            String estadoEjemplar;
            try (PreparedStatement psEjem = conn.prepareStatement(sqlEjemplar)) {
                psEjem.setInt(1, idEjemplar);
                try (ResultSet rs = psEjem.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false; // Ejemplar no existe
                    }
                    estadoEjemplar = rs.getString("estado");
                }
            }
            if (!"Disponible".equals(estadoEjemplar)) {
                logger.warn("Préstamo fallido: ejemplar no disponible. ID: " + idEjemplar);
                conn.rollback();
                return false;
            }

            // 6. Registrar préstamo
            LocalDate fechaPrestamo = LocalDate.now();
            LocalDate fechaLimite = fechaPrestamo.plusDays(diasPrestamo);

            try (PreparedStatement ps = conn.prepareStatement(sqlInsertarPrestamo, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idEjemplar);
                ps.setDate(3, Date.valueOf(fechaPrestamo));
                ps.setDate(4, Date.valueOf(fechaLimite));

                if (ps.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                // 7. Actualizar estado del ejemplar a 'Prestado'
                String sqlActualizarEjemplar = "UPDATE Ejemplares SET estado = 'Prestado' WHERE id_ejemplar = ?";
                try (PreparedStatement psUpd = conn.prepareStatement(sqlActualizarEjemplar)) {
                    psUpd.setInt(1, idEjemplar);
                    psUpd.executeUpdate();
                }

                conn.commit();
                logger.info("Préstamo registrado: Usuario={}, Ejemplar={}, Límite={}", idUsuario, idEjemplar, fechaLimite);
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error al registrar préstamo: usuario=" + idUsuario + ", ejemplar=" + idEjemplar, e);
            return false;
        }
    }

    // === REGISTRAR DEVOLUCIÓN Y CALCULAR MORA ===
    public boolean registrarDevolucion(int idPrestamo, LocalDate fechaDevolucion) {
        String sqlPrestamo = """
            SELECT p.id_usuario, p.id_ejemplar, p.fecha_limite, p.estado
            FROM Prestamos p
            WHERE p.id_prestamo = ?
            """;

        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false);

            Prestamo prestamo;
            try (PreparedStatement ps = conn.prepareStatement(sqlPrestamo)) {
                ps.setInt(1, idPrestamo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        logger.warn("Devolución fallida: préstamo no encontrado. ID: " + idPrestamo);
                        return false;
                    }
                    if ("Devuelto".equals(rs.getString("estado"))) {
                        logger.warn("Devolución fallida: préstamo ya devuelto. ID: " + idPrestamo);
                        return false;
                    }

                    int idUsuario = rs.getInt("id_usuario");
                    int idEjemplar = rs.getInt("id_ejemplar");
                    LocalDate fechaLimite = rs.getDate("fecha_limite").toLocalDate();

                    // Calcular mora
                    int diasRetraso = 0;
                    double montoMora = 0.0;

                    if (fechaDevolucion.isAfter(fechaLimite)) {
                        diasRetraso = (int) java.time.temporal.ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
                        // Obtener mora diaria del rol del usuario
                        String sqlMoraDiaria = """
                            SELECT r.mora_diaria
                            FROM Usuarios u
                            JOIN Roles r ON u.id_rol = r.id_rol
                            WHERE u.id_usuario = ?
                            """;
                        try (PreparedStatement psMora = conn.prepareStatement(sqlMoraDiaria)) {
                            psMora.setInt(1, idUsuario);
                            try (ResultSet rsMora = psMora.executeQuery()) {
                                if (rsMora.next()) {
                                    double moraDiaria = rsMora.getDouble("mora_diaria");
                                    montoMora = diasRetraso * moraDiaria;
                                }
                            }
                        }
                    }

                    // Insertar devolución
                    String sqlInsertarDevolucion = """
                        INSERT INTO Devoluciones (id_prestamo, fecha_devolucion, dias_retraso, monto_mora)
                        VALUES (?, ?, ?, ?)
                        """;
                    try (PreparedStatement psIns = conn.prepareStatement(sqlInsertarDevolucion)) {
                        psIns.setInt(1, idPrestamo);
                        psIns.setDate(2, Date.valueOf(fechaDevolucion));
                        psIns.setInt(3, diasRetraso);
                        psIns.setBigDecimal(4, java.math.BigDecimal.valueOf(montoMora));
                        psIns.executeUpdate();
                    }

                    // Actualizar estado del préstamo
                    String sqlActualizarPrestamo = "UPDATE Prestamos SET estado = 'Devuelto' WHERE id_prestamo = ?";
                    try (PreparedStatement psUpd = conn.prepareStatement(sqlActualizarPrestamo)) {
                        psUpd.setInt(1, idPrestamo);
                        psUpd.executeUpdate();
                    }

                    // Actualizar estado del ejemplar
                    String sqlActualizarEjemplar = "UPDATE Ejemplares SET estado = 'Disponible' WHERE id_ejemplar = ?";
                    try (PreparedStatement psUpdEj = conn.prepareStatement(sqlActualizarEjemplar)) {
                        psUpdEj.setInt(1, idEjemplar);
                        psUpdEj.executeUpdate();
                    }

                    conn.commit();
                    logger.info("Devolución registrada: Préstamo={}, Retraso={} días, Mora=${}", idPrestamo, diasRetraso, montoMora);
                    return true;
                }
            }

        } catch (SQLException e) {
            logger.error("Error al registrar devolución para préstamo ID: " + idPrestamo, e);
            return false;
        }
    }

    // === LISTAR PRÉSTAMOS ACTIVOS DE UN USUARIO ===
    public List<Prestamo> listarPrestamosActivosPorUsuario(int idUsuario) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = """
            SELECT id_prestamo, id_usuario, id_ejemplar, fecha_prestamo, fecha_limite, estado
            FROM Prestamos
            WHERE id_usuario = ? AND estado = 'Activo'
            ORDER BY fecha_prestamo DESC
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prestamo p = new Prestamo();
                    p.setIdPrestamo(rs.getInt("id_prestamo"));
                    p.setUsuario(new Usuario()); // Podrías cargarlo completo si lo necesitas
                    p.getUsuario().setIdUsuario(rs.getInt("id_usuario"));
                    p.setEjemplar(new Ejemplar());
                    p.getEjemplar().setIdEjemplar(rs.getInt("id_ejemplar"));
                    p.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                    p.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
                    p.setEstado(Prestamo.EstadoPrestamo.valueOf(rs.getString("estado")));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al listar préstamos activos del usuario: " + idUsuario, e);
        }
        return lista;
    }

    // === BUSCAR PRÉSTAMO POR ID ===
    public Prestamo obtenerPrestamoPorId(int idPrestamo) {
        String sql = """
            SELECT id_prestamo, id_usuario, id_ejemplar, fecha_prestamo, fecha_limite, estado
            FROM Prestamos
            WHERE id_prestamo = ?
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prestamo p = new Prestamo();
                    p.setIdPrestamo(rs.getInt("id_prestamo"));
                    p.setUsuario(new Usuario());
                    p.getUsuario().setIdUsuario(rs.getInt("id_usuario"));
                    p.setEjemplar(new Ejemplar());
                    p.getEjemplar().setIdEjemplar(rs.getInt("id_ejemplar"));
                    p.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                    p.setFechaLimite(rs.getDate("fecha_limite").toLocalDate());
                    p.setEstado(Prestamo.EstadoPrestamo.valueOf(rs.getString("estado")));
                    return p;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener préstamo por ID: " + idPrestamo, e);
        }
        return null;
    }
}