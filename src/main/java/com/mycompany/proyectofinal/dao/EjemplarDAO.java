package com.mycompany.proyectofinal.dao;

import com.mycompany.proyectofinal.modelo.Ejemplar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjemplarDAO {

    public void crearEjemplar(Ejemplar ejemplar) throws SQLException {
        String sql = "INSERT INTO ejemplares (titulo, id_tipo_documento, id_autor, id_categoria, isbn, editorial, anio_publicacion, numero_paginas, ubicacion, cantidad_total, cantidad_disponible, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, ejemplar.getTitulo());
            pstmt.setInt(2, ejemplar.getIdTipoDocumento());
            pstmt.setObject(3, ejemplar.getIdAutor()); // Puede ser null
            pstmt.setObject(4, ejemplar.getIdCategoria()); // Puede ser null
            pstmt.setString(5, ejemplar.getIsbn());
            pstmt.setString(6, ejemplar.getEditorial());
            pstmt.setInt(7, ejemplar.getAnioPublicacion());
            pstmt.setInt(8, ejemplar.getNumeroPaginas());
            pstmt.setString(9, ejemplar.getUbicacion());
            pstmt.setInt(10, ejemplar.getCantidadTotal());
            pstmt.setInt(11, ejemplar.getCantidadDisponible());
            pstmt.setString(12, ejemplar.getEstado());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    ejemplar.setId(rs.getInt(1));
                }
                System.out.println("Ejemplar creado exitosamente con ID: " + ejemplar.getId());
            }
        }
    }

    public Ejemplar buscarEjemplarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM ejemplares WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Ejemplar e = new Ejemplar();
                e.setId(rs.getInt("id"));
                e.setTitulo(rs.getString("titulo"));
                e.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                e.setIdAutor(rs.getObject("id_autor", Integer.class)); // Maneja null
                e.setIdCategoria(rs.getObject("id_categoria", Integer.class)); // Maneja null
                e.setIsbn(rs.getString("isbn"));
                e.setEditorial(rs.getString("editorial"));
                e.setAnioPublicacion(rs.getInt("anio_publicacion"));
                e.setNumeroPaginas(rs.getInt("numero_paginas"));
                e.setUbicacion(rs.getString("ubicacion"));
                e.setCantidadTotal(rs.getInt("cantidad_total"));
                e.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                e.setEstado(rs.getString("estado"));
                return e;
            }
        }
        return null; // No encontrado
    }

    public List<Ejemplar> listarEjemplares() throws SQLException {
        List<Ejemplar> ejemplares = new ArrayList<>();
        String sql = "SELECT * FROM ejemplares";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ejemplar e = new Ejemplar();
                e.setId(rs.getInt("id"));
                e.setTitulo(rs.getString("titulo"));
                e.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                e.setIdAutor(rs.getObject("id_autor", Integer.class));
                e.setIdCategoria(rs.getObject("id_categoria", Integer.class));
                e.setIsbn(rs.getString("isbn"));
                e.setEditorial(rs.getString("editorial"));
                e.setAnioPublicacion(rs.getInt("anio_publicacion"));
                e.setNumeroPaginas(rs.getInt("numero_paginas"));
                e.setUbicacion(rs.getString("ubicacion"));
                e.setCantidadTotal(rs.getInt("cantidad_total"));
                e.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                e.setEstado(rs.getString("estado"));
                ejemplares.add(e);
            }
        }
        return ejemplares;
    }

    public void actualizarEjemplar(Ejemplar ejemplar) throws SQLException {
        String sql = "UPDATE ejemplares SET titulo = ?, id_tipo_documento = ?, id_autor = ?, id_categoria = ?, isbn = ?, editorial = ?, anio_publicacion = ?, numero_paginas = ?, ubicacion = ?, cantidad_total = ?, cantidad_disponible = ?, estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ejemplar.getTitulo());
            pstmt.setInt(2, ejemplar.getIdTipoDocumento());
            pstmt.setObject(3, ejemplar.getIdAutor());
            pstmt.setObject(4, ejemplar.getIdCategoria());
            pstmt.setString(5, ejemplar.getIsbn());
            pstmt.setString(6, ejemplar.getEditorial());
            pstmt.setInt(7, ejemplar.getAnioPublicacion());
            pstmt.setInt(8, ejemplar.getNumeroPaginas());
            pstmt.setString(9, ejemplar.getUbicacion());
            pstmt.setInt(10, ejemplar.getCantidadTotal());
            pstmt.setInt(11, ejemplar.getCantidadDisponible());
            pstmt.setString(12, ejemplar.getEstado());
            pstmt.setInt(13, ejemplar.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Ejemplar actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el ejemplar con ID: " + ejemplar.getId());
            }
        }
    }

    public void eliminarEjemplar(int id) throws SQLException {
        String sql = "DELETE FROM ejemplares WHERE id = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Ejemplar eliminado exitosamente.");
            } else {
                System.out.println("No se encontró el ejemplar con ID: " + id);
            }
        }
    }

    // Método para buscar ejemplares por título (útil para la búsqueda rápida)
    public List<Ejemplar> buscarEjemplaresPorTitulo(String titulo) throws SQLException {
        List<Ejemplar> ejemplares = new ArrayList<>();
        String sql = "SELECT * FROM ejemplares WHERE titulo LIKE ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + titulo + "%"); // Búsqueda parcial
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ejemplar e = new Ejemplar();
                e.setId(rs.getInt("id"));
                e.setTitulo(rs.getString("titulo"));
                e.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                e.setIdAutor(rs.getObject("id_autor", Integer.class));
                e.setIdCategoria(rs.getObject("id_categoria", Integer.class));
                e.setIsbn(rs.getString("isbn"));
                e.setEditorial(rs.getString("editorial"));
                e.setAnioPublicacion(rs.getInt("anio_publicacion"));
                e.setNumeroPaginas(rs.getInt("numero_paginas"));
                e.setUbicacion(rs.getString("ubicacion"));
                e.setCantidadTotal(rs.getInt("cantidad_total"));
                e.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                e.setEstado(rs.getString("estado"));
                ejemplares.add(e);
            }
        }
        return ejemplares;
    }
}
