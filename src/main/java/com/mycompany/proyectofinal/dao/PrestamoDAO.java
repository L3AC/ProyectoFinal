
package com.mycompany.proyectofinal.dao;
import com.mycompany.proyectofinal.modelo.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private final ConfiguracionDAO gestionConfig = new ConfiguracionDAO();

    public void realizarPrestamo(int idUsuario, int idEjemplar, LocalDate fechaDevolucionPrevista) throws SQLException {
        Connection conn = null;
        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Verificar si el usuario existe y no tiene mora
            String sqlUsuario = "SELECT rol FROM usuarios WHERE id = ? AND activo = TRUE";
            try (PreparedStatement pstmtUsuario = conn.prepareStatement(sqlUsuario)) {
                pstmtUsuario.setInt(1, idUsuario);
                ResultSet rsUsuario = pstmtUsuario.executeQuery();
                if (!rsUsuario.next()) {
                    throw new SQLException("Usuario no encontrado o inactivo.");
                }
                String rolUsuario = rsUsuario.getString("rol");

                // Verificar mora (simplificado: busca préstamos no devueltos con fecha prevista vencida)
                String sqlMora = "SELECT COUNT(*) FROM prestamos WHERE id_usuario = ? AND estado = 'Prestado' AND fecha_devolucion_prevista < ?";
                try (PreparedStatement pstmtMora = conn.prepareStatement(sqlMora)) {
                    pstmtMora.setInt(1, idUsuario);
                    pstmtMora.setDate(2, Date.valueOf(LocalDate.now()));
                    ResultSet rsMora = pstmtMora.executeQuery();
                    if (rsMora.next() && rsMora.getInt(1) > 0) {
                        throw new SQLException("El usuario tiene préstamos vencidos (en mora). No se puede realizar el préstamo.");
                    }
                }

                // 2. Verificar cantidad máxima de préstamos permitidos
                int maxLibros = Integer.parseInt(gestionConfig.obtenerValorPorClave(rolUsuario.equals("Profesor") ? "max_libros_profesor" : "max_libros_alumno"));
                String sqlPrestamosActuales = "SELECT COUNT(*) FROM prestamos WHERE id_usuario = ? AND estado = 'Prestado'";
                try (PreparedStatement pstmtPrestamos = conn.prepareStatement(sqlPrestamosActuales)) {
                    pstmtPrestamos.setInt(1, idUsuario);
                    ResultSet rsPrestamos = pstmtPrestamos.executeQuery();
                    if (rsPrestamos.next() && rsPrestamos.getInt(1) >= maxLibros) {
                        throw new SQLException("El usuario ha alcanzado el límite de préstamos (" + maxLibros + ").");
                    }
                }
            }

            // 3. Verificar disponibilidad del ejemplar
            String sqlEjemplar = "SELECT cantidad_disponible, estado FROM ejemplares WHERE id = ?";
            try (PreparedStatement pstmtEjemplar = conn.prepareStatement(sqlEjemplar)) {
                pstmtEjemplar.setInt(1, idEjemplar);
                ResultSet rsEjemplar = pstmtEjemplar.executeQuery();
                if (!rsEjemplar.next() || rsEjemplar.getInt("cantidad_disponible") <= 0 || !"Disponible".equals(rsEjemplar.getString("estado"))) {
                    throw new SQLException("El ejemplar no está disponible para préstamo.");
                }
            }

            // 4. Registrar el préstamo
            String sqlPrestamo = "INSERT INTO prestamos (id_usuario, id_ejemplar, fecha_prestamo, fecha_devolucion_prevista) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmtPrestamo = conn.prepareStatement(sqlPrestamo, Statement.RETURN_GENERATED_KEYS)) {
                pstmtPrestamo.setInt(1, idUsuario);
                pstmtPrestamo.setInt(2, idEjemplar);
                pstmtPrestamo.setDate(3, Date.valueOf(LocalDate.now()));
                pstmtPrestamo.setDate(4, Date.valueOf(fechaDevolucionPrevista));
                pstmtPrestamo.executeUpdate();

                ResultSet rsPrestamoId = pstmtPrestamo.getGeneratedKeys();
                int idPrestamo = -1;
                if (rsPrestamoId.next()) {
                    idPrestamo = rsPrestamoId.getInt(1);
                }
                System.out.println("Préstamo registrado exitosamente con ID: " + idPrestamo);
            }

            // 5. Actualizar cantidad disponible del ejemplar
            String sqlActualizarEjemplar = "UPDATE ejemplares SET cantidad_disponible = cantidad_disponible - 1, estado = CASE WHEN cantidad_disponible - 1 = 0 THEN 'Prestado' ELSE estado END WHERE id = ?";
            try (PreparedStatement pstmtActualizarEjemplar = conn.prepareStatement(sqlActualizarEjemplar)) {
                pstmtActualizarEjemplar.setInt(1, idEjemplar);
                pstmtActualizarEjemplar.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
            System.out.println("Préstamo realizado y stock actualizado correctamente.");
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Deshacer cambios si hay error
            }
            System.err.println("Error al realizar el préstamo: " + e.getMessage());
            throw e; // Relanzar para que la interfaz lo maneje
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar auto-commit
                conn.close();
            }
        }
    }


    public void registrarDevolucion(int idPrestamo) throws SQLException {
        Connection conn = null;
        try {
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Obtener detalles del préstamo
            String sqlPrestamo = "SELECT id_ejemplar, fecha_devolucion_real FROM prestamos WHERE id = ?";
            int idEjemplar;
            try (PreparedStatement pstmtPrestamo = conn.prepareStatement(sqlPrestamo)) {
                pstmtPrestamo.setInt(1, idPrestamo);
                ResultSet rs = pstmtPrestamo.executeQuery();
                if (!rs.next() || rs.getDate("fecha_devolucion_real") != null) {
                    throw new SQLException("Préstamo no encontrado o ya devuelto.");
                }
                idEjemplar = rs.getInt("id_ejemplar");
            }

            // 2. Actualizar el préstamo
            String sqlActualizarPrestamo = "UPDATE prestamos SET fecha_devolucion_real = ?, estado = 'Devuelto' WHERE id = ?";
            try (PreparedStatement pstmtActualizarPrestamo = conn.prepareStatement(sqlActualizarPrestamo)) {
                pstmtActualizarPrestamo.setDate(1, Date.valueOf(LocalDate.now()));
                pstmtActualizarPrestamo.setInt(2, idPrestamo);
                pstmtActualizarPrestamo.executeUpdate();
            }

            // 3. Actualizar cantidad disponible del ejemplar
            String sqlActualizarEjemplar = "UPDATE ejemplares SET cantidad_disponible = cantidad_disponible + 1, estado = CASE WHEN cantidad_disponible + 1 = cantidad_total THEN 'Disponible' ELSE estado END WHERE id = ?";
            try (PreparedStatement pstmtActualizarEjemplar = conn.prepareStatement(sqlActualizarEjemplar)) {
                pstmtActualizarEjemplar.setInt(1, idEjemplar);
                pstmtActualizarEjemplar.executeUpdate();
            }

            conn.commit(); // Confirmar transacción
            System.out.println("Devolución registrada exitosamente.");
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Deshacer cambios si hay error
            }
            System.err.println("Error al registrar la devolución: " + e.getMessage());
            throw e; // Relanzar para que la interfaz lo maneje
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar auto-commit
                conn.close();
            }
        }
    }

    public List<Prestamo> listarPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos ORDER BY fecha_prestamo DESC";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId(rs.getInt("id"));
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdEjemplar(rs.getInt("id_ejemplar"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                p.setFechaDevolucionPrevista(rs.getDate("fecha_devolucion_prevista").toLocalDate());
                Date fechaReal = rs.getDate("fecha_devolucion_real");
                p.setFechaDevolucionReal(fechaReal != null ? fechaReal.toLocalDate() : null);
                p.setEstado(rs.getString("estado"));
                prestamos.add(p);
            }
        }
        return prestamos;
    }

    // Método para calcular mora (simplificado: solo imprime o retorna el valor, la lógica de cobro iría en otra parte)
    public double calcularMora(int idPrestamo) throws SQLException {
        String sql = "SELECT fecha_devolucion_prevista FROM prestamos WHERE id = ? AND estado = 'Prestado'";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idPrestamo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                LocalDate fechaPrevista = rs.getDate("fecha_devolucion_prevista").toLocalDate();
                LocalDate hoy = LocalDate.now();
                if (hoy.isAfter(fechaPrevista)) {
                    long diasMora = java.time.temporal.ChronoUnit.DAYS.between(fechaPrevista, hoy);
                    double moraDiaria = Double.parseDouble(gestionConfig.obtenerValorPorClave("mora_diaria"));
                    double totalMora = diasMora * moraDiaria;
                    System.out.println("Préstamo ID " + idPrestamo + " tiene " + diasMora + " días de mora. Total: $" + totalMora);
                    return totalMora;
                }
            }
        }
        return 0.0; // No hay mora o no encontrado
    }
}
