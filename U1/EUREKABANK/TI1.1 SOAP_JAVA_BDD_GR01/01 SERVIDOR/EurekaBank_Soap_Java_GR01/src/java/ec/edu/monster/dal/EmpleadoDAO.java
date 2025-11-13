package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Empleado y Usuario
 */
public class EmpleadoDAO {

    /**
     * Valida las credenciales de un empleado (usuario y clave)
     * Nota: La BD usa SHA() para encriptar la clave
     */
    public Empleado validarCredenciales(String usuario, String clave) throws SQLException {
        String sql = "SELECT e.chr_emplcodigo, e.vch_emplpaterno, e.vch_emplmaterno, "
                + "e.vch_emplnombre, e.vch_emplciudad, e.vch_empldireccion, "
                + "u.vch_emplusuario, u.vch_emplestado "
                + "FROM empleado e "
                + "INNER JOIN usuario u ON e.chr_emplcodigo = u.chr_emplcodigo "
                + "WHERE u.vch_emplusuario = ? AND u.vch_emplclave = SHA(?) "
                + "AND u.vch_emplestado = 'ACTIVO'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigo(rs.getString("chr_emplcodigo"));
                empleado.setPaterno(rs.getString("vch_emplpaterno"));
                empleado.setMaterno(rs.getString("vch_emplmaterno"));
                empleado.setNombre(rs.getString("vch_emplnombre"));
                empleado.setCiudad(rs.getString("vch_emplciudad"));
                empleado.setDireccion(rs.getString("vch_empldireccion"));
                empleado.setUsuario(rs.getString("vch_emplusuario"));
                return empleado;
            }
            return null;
        }
    }

    /**
     * Obtiene un empleado por su código
     */
    public Empleado obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion "
                + "FROM empleado WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearEmpleado(rs);
            }
            return null;
        }
    }

    /**
     * Obtiene un empleado por su usuario
     */
    public Empleado obtenerPorUsuario(String usuario) throws SQLException {
        String sql = "SELECT e.chr_emplcodigo, e.vch_emplpaterno, e.vch_emplmaterno, "
                + "e.vch_emplnombre, e.vch_emplciudad, e.vch_empldireccion, "
                + "u.vch_emplusuario "
                + "FROM empleado e "
                + "INNER JOIN usuario u ON e.chr_emplcodigo = u.chr_emplcodigo "
                + "WHERE u.vch_emplusuario = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Empleado empleado = mapearEmpleado(rs);
                empleado.setUsuario(rs.getString("vch_emplusuario"));
                return empleado;
            }
            return null;
        }
    }

    /**
     * Registra un nuevo empleado con su usuario
     */
    public void registrar(Empleado empleado) throws SQLException {
        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            // Insertar empleado
            String sqlEmpleado = "INSERT INTO empleado (chr_emplcodigo, vch_emplpaterno, "
                    + "vch_emplmaterno, vch_emplnombre, vch_emplciudad, vch_empldireccion) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sqlEmpleado)) {
                ps.setString(1, empleado.getCodigo());
                ps.setString(2, empleado.getPaterno());
                ps.setString(3, empleado.getMaterno());
                ps.setString(4, empleado.getNombre());
                ps.setString(5, empleado.getCiudad());
                ps.setString(6, empleado.getDireccion());
                ps.executeUpdate();
            }

            // Insertar usuario (si tiene usuario y clave)
            if (empleado.getUsuario() != null && empleado.getClave() != null) {
                String sqlUsuario = "INSERT INTO usuario (chr_emplcodigo, vch_emplusuario, "
                        + "vch_emplclave, vch_emplestado) VALUES (?, ?, SHA(?), 'ACTIVO')";

                try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {
                    ps.setString(1, empleado.getCodigo());
                    ps.setString(2, empleado.getUsuario());
                    ps.setString(3, empleado.getClave());
                    ps.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    /**
     * Actualiza la clave de un empleado
     */
    public void actualizarClave(String codigoEmpleado, String claveNueva) throws SQLException {
        String sql = "UPDATE usuario SET vch_emplclave = SHA(?) WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, claveNueva);
            ps.setString(2, codigoEmpleado);
            ps.executeUpdate();
        }
    }

    /**
     * Obtiene todos los empleados
     */
    public List<Empleado> obtenerTodos() throws SQLException {
        String sql = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion FROM empleado";

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
        }

        return empleados;
    }

    /**
     * Genera el siguiente código de empleado
     */
    public String generarCodigoEmpleado() throws SQLException {
        String sql = "SELECT LPAD(int_contitem + 1, int_contlongitud, '0') AS codigo "
                + "FROM contador WHERE vch_conttabla = 'Empleado'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("codigo");
            }
            return "0001";
        }
    }

    /**
     * Mapea un ResultSet a un objeto Empleado
     */
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setCodigo(rs.getString("chr_emplcodigo"));
        empleado.setPaterno(rs.getString("vch_emplpaterno"));
        empleado.setMaterno(rs.getString("vch_emplmaterno"));
        empleado.setNombre(rs.getString("vch_emplnombre"));
        empleado.setCiudad(rs.getString("vch_emplciudad"));
        empleado.setDireccion(rs.getString("vch_empldireccion"));
        return empleado;
    }
}
