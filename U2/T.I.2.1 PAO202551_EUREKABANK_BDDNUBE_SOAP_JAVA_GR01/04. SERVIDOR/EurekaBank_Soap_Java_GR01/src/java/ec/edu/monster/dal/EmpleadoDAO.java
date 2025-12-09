package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Empleado
 * Nota: En esta versión, usuario y clave están directamente en la tabla Empleado (sin SHA)
 */
public class EmpleadoDAO {

    /**
     * Valida las credenciales de un empleado (usuario y clave)
     * Nota: En esta versión las claves están en texto plano (igual que SQL Server)
     */
    public Empleado validarCredenciales(String usuario, String clave) throws SQLException {
        String sql = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave "
                + "FROM empleado "
                + "WHERE vch_emplusuario = ? AND vch_emplclave = ?";

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
                // No devolvemos la clave por seguridad
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
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario "
                + "FROM empleado WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
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
     * Obtiene un empleado por su usuario
     */
    public Empleado obtenerPorUsuario(String usuario) throws SQLException {
        String sql = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario "
                + "FROM empleado "
                + "WHERE vch_emplusuario = ?";

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
     * Registra un nuevo empleado con usuario y clave
     */
    public void registrar(Empleado empleado) throws SQLException {
        String sql = "INSERT INTO empleado (chr_emplcodigo, vch_emplpaterno, "
                + "vch_emplmaterno, vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getCodigo());
            ps.setString(2, empleado.getPaterno());
            ps.setString(3, empleado.getMaterno());
            ps.setString(4, empleado.getNombre());
            ps.setString(5, empleado.getCiudad());
            ps.setString(6, empleado.getDireccion());
            ps.setString(7, empleado.getUsuario());
            ps.setString(8, empleado.getClave());
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza la clave de un empleado
     */
    public void actualizarClave(String codigoEmpleado, String claveNueva) throws SQLException {
        String sql = "UPDATE empleado SET vch_emplclave = ? WHERE chr_emplcodigo = ?";

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
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario FROM empleado";

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = mapearEmpleado(rs);
                empleado.setUsuario(rs.getString("vch_emplusuario"));
                empleados.add(empleado);
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
