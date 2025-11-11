package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Devolucion;
import modelo.Prestamo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConexionBD;


public class DevolucionDAO {

    private static final Logger logger = LogManager.getLogger(DevolucionDAO.class);

    // === REGISTRAR DEVOLUCIÓN (método independiente, opcional si ya lo tienes en PrestamoDAO) ===
    public boolean registrarDevolucion(Devolucion devolucion) {
        String sql = """
            INSERT INTO Devoluciones (id_prestamo, fecha_devolucion, dias_retraso, monto_mora)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, devolucion.getPrestamo().getIdPrestamo());
            ps.setDate(2, Date.valueOf(devolucion.getFechaDevolucion()));
            ps.setInt(3, devolucion.getDiasRetraso());
            ps.setBigDecimal(4, java.math.BigDecimal.valueOf(devolucion.getMontoMora()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        devolucion.setIdDevolucion(rs.getInt(1));
                        logger.info("Devolución registrada: ID={}, Préstamo={}, Mora=${}", 
                            devolucion.getIdDevolucion(), devolucion.getPrestamo().getIdPrestamo(), devolucion.getMontoMora());
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error al registrar devolución para préstamo ID: " + devolucion.getPrestamo().getIdPrestamo(), e);
        }
        return false;
    }

    // === OBTENER DEVOLUCIÓN POR ID ===
    public Devolucion obtenerPorId(int idDevolucion) {
        String sql = """
            SELECT id_devolucion, id_prestamo, fecha_devolucion, dias_retraso, monto_mora
            FROM Devoluciones
            WHERE id_devolucion = ?
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDevolucion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Devolucion devolucion = new Devolucion();
                    devolucion.setIdDevolucion(rs.getInt("id_devolucion"));

                    Prestamo prestamo = new Prestamo();
                    prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                    devolucion.setPrestamo(prestamo);

                    devolucion.setFechaDevolucion(rs.getDate("fecha_devolucion").toLocalDate());
                    devolucion.setDiasRetraso(rs.getInt("dias_retraso"));
                    devolucion.setMontoMora(rs.getBigDecimal("monto_mora").doubleValue());

                    return devolucion;
                }
            }

        } catch (SQLException e) {
            logger.error("Error al obtener devolución por ID: " + idDevolucion, e);
        }
        return null;
    }

    // === LISTAR TODAS LAS DEVOLUCIONES ===
    public List<Devolucion> listarDevoluciones() {
        List<Devolucion> lista = new ArrayList<>();
        String sql = """
            SELECT id_devolucion, id_prestamo, fecha_devolucion, dias_retraso, monto_mora
            FROM Devoluciones
            ORDER BY fecha_devolucion DESC
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Devolucion devolucion = new Devolucion();
                devolucion.setIdDevolucion(rs.getInt("id_devolucion"));

                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                devolucion.setPrestamo(prestamo);

                devolucion.setFechaDevolucion(rs.getDate("fecha_devolucion").toLocalDate());
                devolucion.setDiasRetraso(rs.getInt("dias_retraso"));
                devolucion.setMontoMora(rs.getBigDecimal("monto_mora").doubleValue());

                lista.add(devolucion);
            }

        } catch (SQLException e) {
            logger.error("Error al listar devoluciones", e);
        }
        return lista;
    }

    // === LISTAR DEVOLUCIONES DE UN PRÉSTAMO ESPECÍFICO ===
    public List<Devolucion> listarDevolucionesPorPrestamo(int idPrestamo) {
        List<Devolucion> lista = new ArrayList<>();
        String sql = """
            SELECT id_devolucion, id_prestamo, fecha_devolucion, dias_retraso, monto_mora
            FROM Devoluciones
            WHERE id_prestamo = ?
            ORDER BY fecha_devolucion DESC
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Devolucion devolucion = new Devolucion();
                    devolucion.setIdDevolucion(rs.getInt("id_devolucion"));

                    Prestamo prestamo = new Prestamo();
                    prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                    devolucion.setPrestamo(prestamo);

                    devolucion.setFechaDevolucion(rs.getDate("fecha_devolucion").toLocalDate());
                    devolucion.setDiasRetraso(rs.getInt("dias_retraso"));
                    devolucion.setMontoMora(rs.getBigDecimal("monto_mora").doubleValue());

                    lista.add(devolucion);
                }
            }

        } catch (SQLException e) {
            logger.error("Error al listar devoluciones para préstamo ID: " + idPrestamo, e);
        }
        return lista;
    }

    // === LISTAR DEVOLUCIONES CON MORA (monto_mora > 0) ===
    public List<Devolucion> listarDevolucionesConMora() {
        List<Devolucion> lista = new ArrayList<>();
        String sql = """
            SELECT id_devolucion, id_prestamo, fecha_devolucion, dias_retraso, monto_mora
            FROM Devoluciones
            WHERE monto_mora > 0
            ORDER BY fecha_devolucion DESC
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Devolucion devolucion = new Devolucion();
                devolucion.setIdDevolucion(rs.getInt("id_devolucion"));

                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                devolucion.setPrestamo(prestamo);

                devolucion.setFechaDevolucion(rs.getDate("fecha_devolucion").toLocalDate());
                devolucion.setDiasRetraso(rs.getInt("dias_retraso"));
                devolucion.setMontoMora(rs.getBigDecimal("monto_mora").doubleValue());

                lista.add(devolucion);
            }

        } catch (SQLException e) {
            logger.error("Error al listar devoluciones con mora", e);
        }
        return lista;
    }

    // === ACTUALIZAR DEVOLUCIÓN (por si se corrige la mora o se registra pago) ===
    public boolean actualizarDevolucion(Devolucion devolucion) {
        String sql = """
            UPDATE Devoluciones
            SET dias_retraso = ?, monto_mora = ?
            WHERE id_devolucion = ?
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, devolucion.getDiasRetraso());
            ps.setBigDecimal(2, java.math.BigDecimal.valueOf(devolucion.getMontoMora()));
            ps.setInt(3, devolucion.getIdDevolucion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Error al actualizar devolución ID: " + devolucion.getIdDevolucion(), e);
            return false;
        }
    }
}
