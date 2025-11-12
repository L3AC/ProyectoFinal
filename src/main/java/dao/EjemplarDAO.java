package dao;

import modelo.Ejemplar;
import util.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import modelo.ejemplares.*;

public class EjemplarDAO {

    private static final Logger logger = LogManager.getLogger(EjemplarDAO.class);

    private String obtenerPrefijo(Ejemplar.TipoDocumento tipo) {
        return switch (tipo) {
            case Libro ->
                "LIB";
            case Revistas ->
                "REV";
            case CD ->
                "CDA";
            case DVD ->
                "DVD";
            case Diccionario ->
                "DIC";
            case Mapas ->
                "MAP";
            case Tesis ->
                "TES";
            case VHS ->
                "VHS";
            case Cassettes ->
                "CAS";
            case Documento ->
                "DOC";
            case Periodicos ->
                "PER";
            default ->
                throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        };
    }

    // === INSERTAR ===
    public boolean agregarEjemplar(Ejemplar ejemplar) {
        // 1. Generar el código único
        String prefijo = obtenerPrefijo(ejemplar.getTipoDocumento());
        String sqlGenerarCodigo = """
            SELECT CONCAT(?, '-', LPAD(COALESCE(MAX(CAST(SUBSTRING_INDEX(codigo, '-', -1) AS UNSIGNED)), 0) + 1, 5, '0')) AS nuevo_codigo
            FROM Ejemplares
            WHERE tipo_documento = ?
            """;

        String nuevoCodigo = prefijo + "-00001"; // valor por defecto si no hay registros

        try (Connection conn = ConexionBD.getConnection()) {
            // Generar código
            try (PreparedStatement psCodigo = conn.prepareStatement(sqlGenerarCodigo)) {
                psCodigo.setString(1, prefijo);
                psCodigo.setString(2, ejemplar.getTipoDocumento().name());
                try (ResultSet rs = psCodigo.executeQuery()) {
                    if (rs.next()) {
                        nuevoCodigo = rs.getString("nuevo_codigo");
                    }
                }
            }

            // Asignar el código al ejemplar
            ejemplar.setCodigoEjemplar(nuevoCodigo);

            // 2. Insertar en tabla principal
            String sqlInsertarGeneral = """
                INSERT INTO Ejemplares (codigo, titulo, autor, ubicacion, tipo_documento, estado)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

            conn.setAutoCommit(false);

            try (PreparedStatement psGeneral = conn.prepareStatement(sqlInsertarGeneral, Statement.RETURN_GENERATED_KEYS)) {
                psGeneral.setString(1, ejemplar.getCodigoEjemplar());
                psGeneral.setString(2, ejemplar.getTitulo());
                psGeneral.setString(3, ejemplar.getAutor());
                psGeneral.setString(4, ejemplar.getUbicacion());
                psGeneral.setString(5, ejemplar.getTipoDocumento().name());
                psGeneral.setString(6, ejemplar.getEstado().name());

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
    public List<Ejemplar> buscarEjemplaresPorTitulo(String titulo) {
        List<Ejemplar> lista = new ArrayList<>();
        String sql = "SELECT id_ejemplar, codigo, titulo, autor, ubicacion, tipo_documento, estado FROM Ejemplares WHERE titulo LIKE ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + titulo + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ejemplar e = new Ejemplar();
                    e.setIdEjemplar(rs.getInt("id_ejemplar"));
                    e.setCodigoEjemplar(rs.getString("codigo"));
                    e.setTitulo(rs.getString("titulo"));
                    e.setAutor(rs.getString("autor"));
                    e.setUbicacion(rs.getString("ubicacion"));
                    e.setTipoDocumento(Ejemplar.TipoDocumento.valueOf(rs.getString("tipo_documento")));
                    e.setEstado(Ejemplar.Estado.valueOf(rs.getString("estado")));
                    lista.add(e);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar ejemplares", e);
        }
        return lista;
    }

    public Ejemplar obtenerEjemplarPorId(int idEjemplar) {
        String sqlGeneral = "SELECT id_ejemplar, codigo, titulo, autor, ubicacion, tipo_documento, estado FROM Ejemplares WHERE id_ejemplar = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement psGeneral = conn.prepareStatement(sqlGeneral)) {

            psGeneral.setInt(1, idEjemplar);
            try (ResultSet rs = psGeneral.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_ejemplar");
                    String codigo = rs.getString("codigo");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String ubicacion = rs.getString("ubicacion");
                    String tipoStr = rs.getString("tipo_documento");
                    String estadoStr = rs.getString("estado");

                    Ejemplar.TipoDocumento tipoDoc = Ejemplar.TipoDocumento.valueOf(tipoStr);
                    Ejemplar.Estado estadoEnum = Ejemplar.Estado.valueOf(estadoStr);

                    // Este método ya carga los datos específicos de la tabla correspondiente
                    return obtenerEjemplarPorTipo(conn, id, codigo, titulo, autor, ubicacion, tipoDoc, estadoEnum);
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener ejemplar por ID: " + idEjemplar, e);
        }
        return null; // Retorna null si no se encuentra
    }

    // === OBTENER UN EJEMPLAR COMPLETO POR TIPO ===
    private Ejemplar obtenerEjemplarPorTipo(Connection conn, int id, String codigo, String titulo, String autor, String ubicacion,
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
                        l.setCodigoEjemplar(codigo);
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
                        d.setCodigoEjemplar(codigo);
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
                        m.setCodigoEjemplar(codigo);
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
                        t.setCodigoEjemplar(codigo);
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
                        d.setCodigoEjemplar(codigo);
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
                        v.setCodigoEjemplar(codigo);
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
                        c.setCodigoEjemplar(codigo);
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
                        cd.setCodigoEjemplar(codigo);
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
                        doc.setCodigoEjemplar(codigo);
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
                        p.setCodigoEjemplar(codigo);
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
                        r.setCodigoEjemplar(codigo);
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
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEjemplar);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al desactivar ejemplar con ID: " + idEjemplar, e);
            return false;
        }
    }
    // En EjemplarDAO.java

    public void actualizarEstadoEjemplar(Integer idEjemplar, Ejemplar.Estado nuevoEstado) throws SQLException {
        String sql = "UPDATE Ejemplares SET estado = ? WHERE id_ejemplar = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado.name());
            stmt.setInt(2, idEjemplar);
            stmt.executeUpdate();
        }
    }
}
