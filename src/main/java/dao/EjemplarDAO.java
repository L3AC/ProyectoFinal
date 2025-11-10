package dao;

import modelo.Ejemplar;
import util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjemplarDAO {

    public int guardar(Ejemplar ejemplar) {
        String sql = "INSERT INTO ejemplares (codigo_barras, titulo, tipo_documento, ubicacion_fisica, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ejemplar.getCodigoBarras());
            stmt.setString(2, ejemplar.getTitulo());
            stmt.setString(3, ejemplar.getTipoDocumento());
            stmt.setString(4, ejemplar.getUbicacionFisica());
            stmt.setString(5, ejemplar.getEstado());
            stmt.setString(6, ejemplar.getObservaciones());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Devuelve el ID generado
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar ejemplar: " + e.getMessage());
        }
        return -1;
    }

    public void actualizar(Ejemplar ejemplar) {
        String sql = "UPDATE ejemplares SET codigo_barras=?, titulo=?, tipo_documento=?, ubicacion_fisica=?, estado=?, observaciones=? WHERE id_ejemplar=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ejemplar.getCodigoBarras());
            stmt.setString(2, ejemplar.getTitulo());
            stmt.setString(3, ejemplar.getTipoDocumento());
            stmt.setString(4, ejemplar.getUbicacionFisica());
            stmt.setString(5, ejemplar.getEstado());
            stmt.setString(6, ejemplar.getObservaciones());
            stmt.setInt(7, ejemplar.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar ejemplar: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM ejemplares WHERE id_ejemplar=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar ejemplar: " + e.getMessage());
        }
    }

    public List<Ejemplar> listarTodos() {
        List<Ejemplar> ejemplares = new ArrayList<>();
        String sql = "SELECT * FROM ejemplares";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ejemplar e = new Ejemplar();
                e.setId(rs.getInt("id_ejemplar"));
                e.setCodigoBarras(rs.getString("codigo_barras"));
                e.setTitulo(rs.getString("titulo"));
                e.setTipoDocumento(rs.getString("tipo_documento"));
                e.setUbicacionFisica(rs.getString("ubicacion_fisica"));
                e.setEstado(rs.getString("estado"));
                e.setObservaciones(rs.getString("observaciones"));
                ejemplares.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ejemplares: " + e.getMessage());
        }
        return ejemplares;
    }

    public Ejemplar buscarPorId(int id) {
        String sql = "SELECT * FROM ejemplares WHERE id_ejemplar=?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Ejemplar e = new Ejemplar();
                e.setId(rs.getInt("id_ejemplar"));
                e.setCodigoBarras(rs.getString("codigo_barras"));
                e.setTitulo(rs.getString("titulo"));
                e.setTipoDocumento(rs.getString("tipo_documento"));
                e.setUbicacionFisica(rs.getString("ubicacion_fisica"));
                e.setEstado(rs.getString("estado"));
                e.setObservaciones(rs.getString("observaciones"));
                return e;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar ejemplar: " + e.getMessage());
        }
        return null;
    }
}
