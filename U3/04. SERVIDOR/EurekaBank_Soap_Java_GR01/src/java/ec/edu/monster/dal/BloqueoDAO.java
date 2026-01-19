package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Bloqueo;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO para operaciones de bloqueo de cuentas
 * Implementa bloqueo pesimista con timeout de 2 minutos
 */
public class BloqueoDAO {

    private static final Logger LOGGER = Logger.getLogger(BloqueoDAO.class.getName());
    private static final int TIMEOUT_MINUTOS = 2;

    /**
     * Intenta bloquear una cuenta para una ventanilla
     * 
     * @return true si se logró el bloqueo, false si la cuenta ya está bloqueada
     */
    public boolean bloquearCuenta(String codigoCuenta, String codigoVentanilla) throws SQLException {
        // Primero limpiar bloqueos expirados
        limpiarBloqueosExpirados();

        // Verificar si ya existe un bloqueo activo
        if (estaBloqueada(codigoCuenta)) {
            return false;
        }

        // Crear nuevo bloqueo
        String sql = "INSERT INTO bloqueo_cuenta (chr_cuencodigo, chr_ventcodigo, "
                + "dtt_bloqueo_inicio, dtt_bloqueo_expira, vch_bloqueo_estado) "
                + "VALUES (?, ?, ?, ?, 'ACTIVO')";

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime expiracion = ahora.plusMinutes(TIMEOUT_MINUTOS);

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setString(2, codigoVentanilla);
            ps.setTimestamp(3, Timestamp.valueOf(ahora));
            ps.setTimestamp(4, Timestamp.valueOf(expiracion));
            ps.executeUpdate();

            LOGGER.info("Cuenta " + codigoCuenta + " bloqueada por ventanilla " + codigoVentanilla);
            return true;
        } catch (SQLException e) {
            // Si hay error por clave duplicada, la cuenta ya está bloqueada
            if (e.getErrorCode() == 1062) { // Duplicate entry
                return false;
            }
            throw e;
        }
    }

    /**
     * Libera el bloqueo de una cuenta
     */
    public boolean liberarCuenta(String codigoCuenta, String codigoVentanilla) throws SQLException {
        String sql = "DELETE FROM bloqueo_cuenta "
                + "WHERE chr_cuencodigo = ? AND chr_ventcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setString(2, codigoVentanilla);
            int afectados = ps.executeUpdate();

            if (afectados > 0) {
                LOGGER.info("Cuenta " + codigoCuenta + " liberada por ventanilla " + codigoVentanilla);
            }
            return afectados > 0;
        }
    }

    /**
     * Verifica si una cuenta está bloqueada
     */
    public boolean estaBloqueada(String codigoCuenta) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bloqueo_cuenta "
                + "WHERE chr_cuencodigo = ? AND vch_bloqueo_estado = 'ACTIVO' "
                + "AND dtt_bloqueo_expira > NOW()";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    /**
     * Obtiene el bloqueo activo de una cuenta
     */
    public Bloqueo obtenerBloqueo(String codigoCuenta) throws SQLException {
        String sql = "SELECT chr_cuencodigo, chr_ventcodigo, dtt_bloqueo_inicio, "
                + "dtt_bloqueo_expira, vch_bloqueo_estado "
                + "FROM bloqueo_cuenta "
                + "WHERE chr_cuencodigo = ? AND vch_bloqueo_estado = 'ACTIVO' "
                + "AND dtt_bloqueo_expira > NOW()";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearBloqueo(rs);
            }
            return null;
        }
    }

    /**
     * Obtiene todos los bloqueos activos
     */
    public List<Bloqueo> obtenerBloqueosActivos() throws SQLException {
        String sql = "SELECT chr_cuencodigo, chr_ventcodigo, dtt_bloqueo_inicio, "
                + "dtt_bloqueo_expira, vch_bloqueo_estado "
                + "FROM bloqueo_cuenta "
                + "WHERE vch_bloqueo_estado = 'ACTIVO' AND dtt_bloqueo_expira > NOW()";

        List<Bloqueo> bloqueos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                bloqueos.add(mapearBloqueo(rs));
            }
        }

        return bloqueos;
    }

    /**
     * Extiende el tiempo de un bloqueo existente
     */
    public boolean extenderBloqueo(String codigoCuenta, String codigoVentanilla) throws SQLException {
        String sql = "UPDATE bloqueo_cuenta SET dtt_bloqueo_expira = ? "
                + "WHERE chr_cuencodigo = ? AND chr_ventcodigo = ? "
                + "AND vch_bloqueo_estado = 'ACTIVO'";

        LocalDateTime nuevaExpiracion = LocalDateTime.now().plusMinutes(TIMEOUT_MINUTOS);

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(nuevaExpiracion));
            ps.setString(2, codigoCuenta);
            ps.setString(3, codigoVentanilla);
            int afectados = ps.executeUpdate();

            return afectados > 0;
        }
    }

    /**
     * Limpia los bloqueos que han expirado
     */
    public void limpiarBloqueosExpirados() throws SQLException {
        String sql = "DELETE FROM bloqueo_cuenta WHERE dtt_bloqueo_expira < NOW()";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            int eliminados = ps.executeUpdate();
            if (eliminados > 0) {
                LOGGER.info("Limpiados " + eliminados + " bloqueos expirados");
            }
        }
    }

    /**
     * Libera todos los bloqueos de una ventanilla (cuando se cierra sesión)
     */
    public void liberarTodosDeVentanilla(String codigoVentanilla) throws SQLException {
        String sql = "DELETE FROM bloqueo_cuenta WHERE chr_ventcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoVentanilla);
            int liberados = ps.executeUpdate();
            if (liberados > 0) {
                LOGGER.info("Liberados " + liberados + " bloqueos de ventanilla " + codigoVentanilla);
            }
        }
    }

    /**
     * Mapea un ResultSet a un objeto Bloqueo
     */
    private Bloqueo mapearBloqueo(ResultSet rs) throws SQLException {
        Bloqueo bloqueo = new Bloqueo();
        bloqueo.setCodigoCuenta(rs.getString("chr_cuencodigo"));
        bloqueo.setCodigoVentanilla(rs.getString("chr_ventcodigo"));

        Timestamp inicio = rs.getTimestamp("dtt_bloqueo_inicio");
        if (inicio != null) {
            bloqueo.setFechaInicio(inicio.toLocalDateTime());
        }

        Timestamp expira = rs.getTimestamp("dtt_bloqueo_expira");
        if (expira != null) {
            bloqueo.setFechaExpiracion(expira.toLocalDateTime());
        }

        bloqueo.setEstado(rs.getString("vch_bloqueo_estado"));
        return bloqueo;
    }
}
