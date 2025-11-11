package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Rol;
import modelo.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);

    // === LOGIN: Validar credenciales y retornar información del usuario ===
    public Usuario login(String correo, String contrasenaPlana) {
        String sql = """
            SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.contrasena,
                   r.id_rol, r.nombre_rol, r.cant_max_prestamo, r.dias_prestamo, r.mora_diaria
            FROM Usuarios u
            JOIN Roles r ON u.id_rol = r.id_rol
            WHERE u.correo = ?
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashAlmacenado = rs.getString("contrasena");

                    // Verificar contraseña encriptada
                    if (BCrypt.checkpw(contrasenaPlana, hashAlmacenado)) {
                        // Contraseña válida: construir objeto con rol
                        Rol rol = new Rol();
                        rol.setIdRol(rs.getInt("id_rol"));
                        rol.setNombreRol(rs.getString("nombre_rol"));
                        rol.setCantMaxPrestamo(rs.getInt("cant_max_prestamo"));
                        rol.setDiasPrestamo(rs.getInt("dias_prestamo"));
                        rol.setMoraDiaria(rs.getDouble("mora_diaria"));

                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(rs.getInt("id_usuario"));
                        usuario.setNombre(rs.getString("nombre"));
                        usuario.setApellido(rs.getString("apellido"));
                        usuario.setCorreo(rs.getString("correo")); // Puedes usar este como "nombre de usuario"
                        usuario.setRol(rol);

                        logger.info("Login exitoso: Usuario={}, Rol={}", usuario.getCorreo(), rol.getNombreRol());
                        return usuario;
                    } else {
                        logger.warn("Intento de login fallido: contraseña incorrecta para correo: " + correo);
                    }
                } else {
                    logger.warn("Intento de login fallido: correo no encontrado: " + correo);
                }
            }

        } catch (SQLException e) {
            logger.error("Error al intentar login con correo: " + correo, e);
        }
        return null;
    }

    // === REGISTRAR USUARIO (con contraseña encriptada) ===
    public boolean registrarUsuario(Usuario usuario) {
        // Validar que el correo no exista
        if (existeCorreo(usuario.getCorreo())) {
            logger.warn("Intento de registro con correo duplicado: " + usuario.getCorreo());
            return false;
        }

        String sql = """
            INSERT INTO Usuarios (nombre, apellido, correo, contrasena, id_rol)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            // Encriptar contraseña antes de guardar
            String hash = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
            ps.setString(4, hash);
            ps.setInt(5, usuario.getRol().getIdRol());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(rs.getInt(1));
                        logger.info("Usuario registrado: ID={}, Correo={}", usuario.getIdUsuario(), usuario.getCorreo());
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            logger.error("Error al registrar usuario: " + usuario.getCorreo(), e);
        }
        return false;
    }

    // === VERIFICAR SI CORREO EXISTE ===
    private boolean existeCorreo(String correo) {
        String sql = "SELECT 1 FROM Usuarios WHERE correo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            logger.error("Error al verificar existencia de correo: " + correo, e);
            return false;
        }
    }

    // === OBTENER USUARIO POR ID ===
    public Usuario obtenerPorId(int idUsuario) {
        String sql = """
            SELECT u.id_usuario, u.nombre, u.apellido, u.correo,
                   r.id_rol, r.nombre_rol, r.cant_max_prestamo, r.dias_prestamo, r.mora_diaria
            FROM Usuarios u
            JOIN Roles r ON u.id_rol = r.id_rol
            WHERE u.id_usuario = ?
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("id_rol"));
                    rol.setNombreRol(rs.getString("nombre_rol"));
                    rol.setCantMaxPrestamo(rs.getInt("cant_max_prestamo"));
                    rol.setDiasPrestamo(rs.getInt("dias_prestamo"));
                    rol.setMoraDiaria(rs.getDouble("mora_diaria"));

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setRol(rol);
                    return usuario;
                }
            }

        } catch (SQLException e) {
            logger.error("Error al obtener usuario por ID: " + idUsuario, e);
        }
        return null;
    }

    // === LISTAR TODOS LOS USUARIOS ===
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.id_usuario, u.nombre, u.apellido, u.correo,
                   r.id_rol, r.nombre_rol, r.cant_max_prestamo, r.dias_prestamo, r.mora_diaria
            FROM Usuarios u
            JOIN Roles r ON u.id_rol = r.id_rol
            ORDER BY u.id_usuario
            """;

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

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rol);

                lista.add(usuario);
            }

        } catch (SQLException e) {
            logger.error("Error al listar usuarios", e);
        }
        return lista;
    }

    // === ACTUALIZAR USUARIO ===
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = """
            UPDATE Usuarios
            SET nombre = ?, apellido = ?, correo = ?
            WHERE id_usuario = ?
            """;

        // Validar unicidad de correo
        if (existeCorreo(usuario.getCorreo())) {
            Usuario existente = obtenerPorCorreo(usuario.getCorreo());
            if (existente != null && !existente.getIdUsuario().equals(usuario.getIdUsuario())) {
                logger.warn("Actualización cancelada: correo duplicado: " + usuario.getCorreo());
                return false;
            }
        }

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setInt(4, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Error al actualizar usuario ID: " + usuario.getIdUsuario(), e);
            return false;
        }
    }

    // === ELIMINAR USUARIO ===
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar usuario con ID: " + idUsuario, e);
            return false;
        }
    }

    // === OBTENER USUARIO POR CORREO ===
    public Usuario obtenerPorCorreo(String correo) {
        String sql = """
            SELECT u.id_usuario, u.nombre, u.apellido, u.correo,
                   r.id_rol, r.nombre_rol, r.cant_max_prestamo, r.dias_prestamo, r.mora_diaria
            FROM Usuarios u
            JOIN Roles r ON u.id_rol = r.id_rol
            WHERE u.correo = ?
            """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("id_rol"));
                    rol.setNombreRol(rs.getString("nombre_rol"));
                    rol.setCantMaxPrestamo(rs.getInt("cant_max_prestamo"));
                    rol.setDiasPrestamo(rs.getInt("dias_prestamo"));
                    rol.setMoraDiaria(rs.getDouble("mora_diaria"));

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setRol(rol);
                    return usuario;
                }
            }

        } catch (SQLException e) {
            logger.error("Error al obtener usuario por correo: " + correo, e);
        }
        return null;
    }
}