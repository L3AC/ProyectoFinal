package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Rol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConexionBD;

public class RolDAO {

    private static final Logger logger = LogManager.getLogger(RolDAO.class);

    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_rol, nombre_rol, cant_max_prestamo, dias_prestamo, mora_diaria FROM Roles WHERE id_rol>0";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                rol.setCantMaxPrestamo(rs.getInt("cant_max_prestamo"));
                rol.setDiasPrestamo(rs.getInt("dias_prestamo"));
                rol.setMoraDiaria(rs.getDouble("mora_diaria"));
                lista.add(rol);
            }

        } catch (SQLException e) {
            logger.error("Error al listar roles", e);
        }
        return lista;
    }

    public Rol obtenerRolPorId(int idRol) {
        String sql = "SELECT * FROM Roles WHERE id_rol = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("id_rol"));
                    rol.setNombreRol(rs.getString("nombre_rol"));
                    rol.setCantMaxPrestamo(rs.getInt("cant_max_prestamo"));
                    rol.setDiasPrestamo(rs.getInt("dias_prestamo"));
                    rol.setMoraDiaria(rs.getDouble("mora_diaria"));
                    return rol;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener rol por ID: " + idRol, e);
        }
        return null;
    }

    public boolean editarRol(Rol rol) {
        String sql = "UPDATE Roles SET  cant_max_prestamo = ?, dias_prestamo = ?, mora_diaria = ? WHERE id_rol = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rol.getCantMaxPrestamo());
            ps.setInt(2, rol.getDiasPrestamo());
            ps.setDouble(3, rol.getMoraDiaria());
            ps.setInt(4, rol.getIdRol());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            logger.error("Error al editar rol con ID: " + rol.getIdRol(), e);
            return false;
        }
    }

}
