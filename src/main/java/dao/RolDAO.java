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

    /**
     * Lista todos los roles de la base de datos.
     * @return Una lista de objetos Rol.
     */
    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_rol, nombre_rol, cant_max_prestamo, dias_prestamo, mora_diaria FROM Roles";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
    
    /**
     * Obtiene un rol por su ID.
     * @param idRol
     * @return Objeto Rol o null si no se encuentra.
     */
    public Rol obtenerRolPorId(int idRol) {
        String sql = "SELECT * FROM Roles WHERE id_rol = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
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
}