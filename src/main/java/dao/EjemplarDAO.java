package dao;

import modelo.Ejemplar;
import util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static modelo.Ejemplar.TipoDocumento.Documento;
import modelo.ejemplares.CD;
import modelo.ejemplares.Cassette;
import modelo.ejemplares.DVD;
import modelo.ejemplares.Diccionario;
import modelo.ejemplares.Documento;
import modelo.ejemplares.Libro;
import modelo.ejemplares.Mapa;
import modelo.ejemplares.Periodico;
import modelo.ejemplares.Revista;
import modelo.ejemplares.Tesis;
import modelo.ejemplares.VHS;

public class EjemplarDAO {

    private static final Logger logger = LogManager.getLogger(EjemplarDAO.class);

    // === INSERTAR ===
    public boolean agregarEjemplar(Ejemplar ejemplar) {
        String sqlInsertarGeneral = """
            INSERT INTO Ejemplares (titulo, autor, ubicacion, tipo_documento, estado)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false); // Para transacciones

            try (PreparedStatement psGeneral = conn.prepareStatement(sqlInsertarGeneral, Statement.RETURN_GENERATED_KEYS)) {
                psGeneral.setString(1, ejemplar.getTitulo());
                psGeneral.setString(2, ejemplar.getAutor());
                psGeneral.setString(3, ejemplar.getUbicacion());
                psGeneral.setString(4, ejemplar.getTipoDocumento().name());
                psGeneral.setString(5, ejemplar.getEstado().name());

                int filas = psGeneral.executeUpdate();
                if (filas == 0) {
                    conn.rollback();
                    return false;
                }

                // Obtener el id generado
                try (ResultSet rs = psGeneral.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idEjemplar = rs.getInt(1);

                        // Insertar en la tabla específica según tipo
                        boolean detalleOk = insertarDetalleEjemplar(ejemplar, idEjemplar, conn);
                        if (!detalleOk) {
                            conn.rollback();
                            return false;
                        }

                        conn.commit();
                        ejemplar.setIdEjemplar(idEjemplar);
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            logger.error("Error al agregar ejemplar: " + ejemplar.getTipoDocumento(), e);
            return false;
        }
    }

    private boolean insertarDetalleEjemplar(Ejemplar ejemplar, int idEjemplar, Connection conn) throws SQLException {
        String tipo = ejemplar.getTipoDocumento().name();

        switch (tipo) {
            case "Libro" -> {
                Libro l = (Libro) ejemplar;
                String sql = "INSERT INTO Libros (id_ejemplar, isbn, editorial, edicion) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setString(2, l.getIsbn());
                    ps.setString(3, l.getEditorial());
                    ps.setObject(4, l.getEdicion()); // permite null
                    ps.executeUpdate();
                }
            }
            case "Diccionario" -> {
                Diccionario d = (Diccionario) ejemplar;
                String sql = "INSERT INTO Diccionarios (id_ejemplar, idioma, volumen) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setString(2, d.getIdioma());
                    ps.setObject(3, d.getVolumen());
                    ps.executeUpdate();
                }
            }
            case "Mapas" -> {
                Mapa m = (Mapa) ejemplar;
                String sql = "INSERT INTO Mapas (id_ejemplar, escala, tipo_mapa) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setString(2, m.getEscala());
                    ps.setString(3, m.getTipoMapa());
                    ps.executeUpdate();
                }
            }
            case "Tesis" -> {
                Tesis t = (Tesis) ejemplar;
                String sql = "INSERT INTO Tesis (id_ejemplar, grado_academico, facultad) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setString(2, t.getGradoAcademico());
                    ps.setString(3, t.getFacultad());
                    ps.executeUpdate();
                }
            }
            case "DVD" -> {
                DVD d = (DVD) ejemplar;
                String sql = "INSERT INTO DVDs (id_ejemplar, duracion, genero) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setTime(2, d.getDuracion() != null ? Time.valueOf(d.getDuracion()) : null);
                    ps.setString(3, d.getGenero());
                    ps.executeUpdate();
                }
            }
            case "VHS" -> {
                VHS v = (VHS) ejemplar;
                String sql = "INSERT INTO VHS (id_ejemplar, duracion, genero) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setTime(2, v.getDuracion() != null ? Time.valueOf(v.getDuracion()) : null);
                    ps.setString(3, v.getGenero());
                    ps.executeUpdate();
                }
            }
            case "Cassettes" -> {
                Cassette c = (Cassette) ejemplar;
                String sql = "INSERT INTO Cassettes (id_ejemplar, duracion, tipo_cinta) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setTime(2, c.getDuracion() != null ? Time.valueOf(c.getDuracion()) : null);
                    ps.setString(3, c.getTipoCinta());
                    ps.executeUpdate();
                }
            }
            case "CD" -> {
                CD cd = (CD) ejemplar;
                String sql = "INSERT INTO CDs (id_ejemplar, duracion, genero) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setTime(2, cd.getDuracion() != null ? Time.valueOf(cd.getDuracion()) : null);
                    ps.setString(3, cd.getGenero());
                    ps.executeUpdate();
                }
            }
            case "Documento" -> {
                Documento doc = (Documento) ejemplar;
                String sql = "INSERT INTO Documentos (id_ejemplar, tipo_documento_detalle) VALUES (?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setString(2, doc.getTipoDocumentoDetalle());
                    ps.executeUpdate();
                }
            }
            case "Periodicos" -> {
                Periodico p = (Periodico) ejemplar;
                String sql = "INSERT INTO Periodicos (id_ejemplar, fecha_publicacion, tipo_periodico) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setDate(2, p.getFechaPublicacion() != null ? Date.valueOf(p.getFechaPublicacion()) : null);
                    ps.setString(3, p.getTipoPeriodico());
                    ps.executeUpdate();
                }
            }
            case "Revistas" -> {
                Revista r = (Revista) ejemplar;
                String sql = "INSERT INTO Revistas (id_ejemplar, fecha_publicacion, tipo_revista) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, idEjemplar);
                    ps.setDate(2, r.getFechaPublicacion() != null ? Date.valueOf(r.getFechaPublicacion()) : null);
                    ps.setString(3, r.getTipoRevista());
                    ps.executeUpdate();
                }
            }
            default -> {
                logger.warn("Tipo de documento no soportado: " + tipo);
                return false;
            }
        }
        return true;
    }

    // === LISTAR TODOS ===
    public List<Ejemplar> listarEjemplares() {
        List<Ejemplar> lista = new ArrayList<>();
        String sql = "SELECT id_ejemplar, titulo, autor, ubicacion, tipo_documento, estado FROM Ejemplares ORDER BY id_ejemplar";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_ejemplar");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String ubicacion = rs.getString("ubicacion");
                String tipoStr = rs.getString("tipo_documento");
                String estadoStr = rs.getString("estado");

                Ejemplar.TipoDocumento tipoDoc = Ejemplar.TipoDocumento.valueOf(tipoStr);
                Ejemplar.Estado estadoEnum = Ejemplar.Estado.valueOf(estadoStr);

                Ejemplar ejemplar = obtenerEjemplarPorTipo(conn, id, titulo, autor, ubicacion, tipoDoc, estadoEnum);
                if (ejemplar != null) {
                    lista.add(ejemplar);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al listar ejemplares", e);
        }
        return lista;
    }

    // === OBTENER UN EJEMPLAR COMPLETO POR TIPO ===
    private Ejemplar obtenerEjemplarPorTipo(Connection conn, int id, String titulo, String autor, String ubicacion,
                                            Ejemplar.TipoDocumento tipo, Ejemplar.Estado estado) {
        switch (tipo) {
            case Libro -> {
                String sql = "SELECT isbn, editorial, edicion FROM Libros WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Libro l = new Libro();
                        l.setIdEjemplar(id);
                        l.setTitulo(titulo);
                        l.setAutor(autor);
                        l.setUbicacion(ubicacion);
                        l.setTipoDocumento(tipo);
                        l.setEstado(estado);
                        l.setIsbn(rs.getString("isbn"));
                        l.setEditorial(rs.getString("editorial"));
                        l.setEdicion(rs.getObject("edicion", Integer.class));
                        return l;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Libro con ID: " + id, e);
                }
            }
            case Diccionario -> {
                String sql = "SELECT idioma, volumen FROM Diccionarios WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Diccionario d = new Diccionario();
                        d.setIdEjemplar(id);
                        d.setTitulo(titulo);
                        d.setAutor(autor);
                        d.setUbicacion(ubicacion);
                        d.setTipoDocumento(tipo);
                        d.setEstado(estado);
                        d.setIdioma(rs.getString("idioma"));
                        d.setVolumen(rs.getObject("volumen", Integer.class));
                        return d;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Diccionario con ID: " + id, e);
                }
            }
            case Mapas -> {
                String sql = "SELECT escala, tipo_mapa FROM Mapas WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Mapa m = new Mapa();
                        m.setIdEjemplar(id);
                        m.setTitulo(titulo);
                        m.setAutor(autor);
                        m.setUbicacion(ubicacion);
                        m.setTipoDocumento(tipo);
                        m.setEstado(estado);
                        m.setEscala(rs.getString("escala"));
                        m.setTipoMapa(rs.getString("tipo_mapa"));
                        return m;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Mapa con ID: " + id, e);
                }
            }
            case Tesis -> {
                String sql = "SELECT grado_academico, facultad FROM Tesis WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Tesis t = new Tesis();
                        t.setIdEjemplar(id);
                        t.setTitulo(titulo);
                        t.setAutor(autor);
                        t.setUbicacion(ubicacion);
                        t.setTipoDocumento(tipo);
                        t.setEstado(estado);
                        t.setGradoAcademico(rs.getString("grado_academico"));
                        t.setFacultad(rs.getString("facultad"));
                        return t;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Tesis con ID: " + id, e);
                }
            }
            case DVD -> {
                String sql = "SELECT duracion, genero FROM DVDs WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        DVD d = new DVD();
                        d.setIdEjemplar(id);
                        d.setTitulo(titulo);
                        d.setAutor(autor);
                        d.setUbicacion(ubicacion);
                        d.setTipoDocumento(tipo);
                        d.setEstado(estado);
                        Time time = rs.getTime("duracion");
                        d.setDuracion(time != null ? time.toLocalTime() : null);
                        d.setGenero(rs.getString("genero"));
                        return d;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar DVD con ID: " + id, e);
                }
            }
            case VHS -> {
                String sql = "SELECT duracion, genero FROM VHS WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        VHS v = new VHS();
                        v.setIdEjemplar(id);
                        v.setTitulo(titulo);
                        v.setAutor(autor);
                        v.setUbicacion(ubicacion);
                        v.setTipoDocumento(tipo);
                        v.setEstado(estado);
                        Time time = rs.getTime("duracion");
                        v.setDuracion(time != null ? time.toLocalTime() : null);
                        v.setGenero(rs.getString("genero"));
                        return v;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar VHS con ID: " + id, e);
                }
            }
            case Cassettes -> {
                String sql = "SELECT duracion, tipo_cinta FROM Cassettes WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Cassette c = new Cassette();
                        c.setIdEjemplar(id);
                        c.setTitulo(titulo);
                        c.setAutor(autor);
                        c.setUbicacion(ubicacion);
                        c.setTipoDocumento(tipo);
                        c.setEstado(estado);
                        Time time = rs.getTime("duracion");
                        c.setDuracion(time != null ? time.toLocalTime() : null);
                        c.setTipoCinta(rs.getString("tipo_cinta"));
                        return c;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Cassette con ID: " + id, e);
                }
            }
            case CD -> {
                String sql = "SELECT duracion, genero FROM CDs WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        CD cd = new CD();
                        cd.setIdEjemplar(id);
                        cd.setTitulo(titulo);
                        cd.setAutor(autor);
                        cd.setUbicacion(ubicacion);
                        cd.setTipoDocumento(tipo);
                        cd.setEstado(estado);
                        Time time = rs.getTime("duracion");
                        cd.setDuracion(time != null ? time.toLocalTime() : null);
                        cd.setGenero(rs.getString("genero"));
                        return cd;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar CD con ID: " + id, e);
                }
            }
            case Documento -> {
                String sql = "SELECT tipo_documento_detalle FROM Documentos WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Documento doc = new Documento();
                        doc.setIdEjemplar(id);
                        doc.setTitulo(titulo);
                        doc.setAutor(autor);
                        doc.setUbicacion(ubicacion);
                        doc.setTipoDocumento(tipo);
                        doc.setEstado(estado);
                        doc.setTipoDocumentoDetalle(rs.getString("tipo_documento_detalle"));
                        return doc;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Documento con ID: " + id, e);
                }
            }
            case Periodicos -> {
                String sql = "SELECT fecha_publicacion, tipo_periodico FROM Periodicos WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Periodico p = new Periodico();
                        p.setIdEjemplar(id);
                        p.setTitulo(titulo);
                        p.setAutor(autor);
                        p.setUbicacion(ubicacion);
                        p.setTipoDocumento(tipo);
                        p.setEstado(estado);
                        Date fecha = rs.getDate("fecha_publicacion");
                        p.setFechaPublicacion(fecha != null ? fecha.toLocalDate() : null);
                        p.setTipoPeriodico(rs.getString("tipo_periodico"));
                        return p;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Periódico con ID: " + id, e);
                }
            }
            case Revistas -> {
                String sql = "SELECT fecha_publicacion, tipo_revista FROM Revistas WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        Revista r = new Revista();
                        r.setIdEjemplar(id);
                        r.setTitulo(titulo);
                        r.setAutor(autor);
                        r.setUbicacion(ubicacion);
                        r.setTipoDocumento(tipo);
                        r.setEstado(estado);
                        Date fecha = rs.getDate("fecha_publicacion");
                        r.setFechaPublicacion(fecha != null ? fecha.toLocalDate() : null);
                        r.setTipoRevista(rs.getString("tipo_revista"));
                        return r;
                    }
                } catch (SQLException e) {
                    logger.error("Error al cargar Revista con ID: " + id, e);
                }
            }
        }
        return null;
    }

    // === ACTUALIZAR (OPCIONAL, PERO ÚTIL) ===
    public boolean actualizarEjemplar(Ejemplar ejemplar) {
        String sqlGeneral = """
            UPDATE Ejemplares
            SET titulo = ?, autor = ?, ubicacion = ?, estado = ?
            WHERE id_ejemplar = ?
            """;

        try (Connection conn = ConexionBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psGen = conn.prepareStatement(sqlGeneral)) {
                psGen.setString(1, ejemplar.getTitulo());
                psGen.setString(2, ejemplar.getAutor());
                psGen.setString(3, ejemplar.getUbicacion());
                psGen.setString(4, ejemplar.getEstado().name());
                psGen.setInt(5, ejemplar.getIdEjemplar());
                psGen.executeUpdate();

                boolean ok = actualizarDetalleEjemplar(ejemplar, conn);
                if (!ok) {
                    conn.rollback();
                    return false;
                }

                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar ejemplar ID: " + ejemplar.getIdEjemplar(), e);
            return false;
        }
    }

    private boolean actualizarDetalleEjemplar(Ejemplar ejemplar, Connection conn) throws SQLException {
        String tipo = ejemplar.getTipoDocumento().name();
        int id = ejemplar.getIdEjemplar();

        switch (tipo) {
            case "Libro" -> {
                Libro l = (Libro) ejemplar;
                String sql = "UPDATE Libros SET isbn = ?, editorial = ?, edicion = ? WHERE id_ejemplar = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, l.getIsbn());
                    ps.setString(2, l.getEditorial());
                    ps.setObject(3, l.getEdicion());
                    ps.setInt(4, id);
                    ps.executeUpdate();
                }
            }
            // Puedes agregar los demás casos (Diccionario, DVD, etc.) de forma similar si los necesitas
            default -> {
                logger.warn("Actualización de detalle no implementada para tipo: " + tipo);
                return true; // al menos la parte general se actualizó
            }
        }
        return true;
    }

    // === ELIMINAR ===
    public boolean eliminarEjemplar(int idEjemplar) {
        String sql = "DELETE FROM Ejemplares WHERE id_ejemplar = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEjemplar);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar ejemplar con ID: " + idEjemplar, e);
            return false;
        }
    }
}