
package com.mycompany.proyectofinal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracionDAO {

    public String obtenerValorPorClave(String clave) throws SQLException {
        String sql = "SELECT valor FROM configuracion WHERE clave = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, clave);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("valor");
            } else {
                System.err.println("Clave de configuración no encontrada: " + clave);
                return null; // O un valor por defecto
            }
        }
    }

    // Opcional: Método para actualizar un valor de configuración
    public void actualizarValor(String clave, String nuevoValor) throws SQLException {
        String sql = "UPDATE configuracion SET valor = ? WHERE clave = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoValor);
            pstmt.setString(2, clave);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Configuración actualizada: " + clave + " = " + nuevoValor);
            } else {
                System.out.println("No se encontró la clave de configuración: " + clave);
            }
        }
    }
}
