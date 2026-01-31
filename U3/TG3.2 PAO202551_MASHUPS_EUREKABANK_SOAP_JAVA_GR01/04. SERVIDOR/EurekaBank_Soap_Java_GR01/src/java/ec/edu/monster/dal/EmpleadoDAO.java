package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Empleado;
import ec.edu.monster.utils.PasswordUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Empleado
 * Las contraseñas se almacenan con hash SHA-256
 */
public class EmpleadoDAO {

    /**
     * Valida las credenciales de un empleado (usuario y clave)
     * La clave se compara usando hash SHA-256
     */
    public Empleado validarCredenciales(String usuario, String clave) throws SQLException {
        // Primero obtenemos el empleado por usuario para obtener el hash almacenado
        String sql = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave "
                + "FROM empleado "
                + "WHERE vch_emplusuario = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener el hash almacenado
                String hashAlmacenado = rs.getString("vch_emplclave");

                // Verificar la contraseña usando hash
                if (PasswordUtils.verificarPassword(clave, hashAlmacenado)) {
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
     * Obtiene el hash almacenado de un empleado por su usuario
     * Usado internamente para verificación de clave actual
     */
    public String obtenerHashPorUsuario(String usuario) throws SQLException {
        String sql = "SELECT vch_emplclave FROM empleado WHERE vch_emplusuario = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("vch_emplclave");
            }
            return null;
        }
    }

    /**
     * Registra un nuevo empleado con usuario y clave
     * La clave se almacena con hash SHA-256
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
            // Hashear la clave antes de guardar
            ps.setString(8, PasswordUtils.hashPassword(empleado.getClave()));
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza la clave de un empleado
     * La nueva clave se almacena con hash SHA-256
     */
    public void actualizarClave(String codigoEmpleado, String claveNueva) throws SQLException {
        String sql = "UPDATE empleado SET vch_emplclave = ? WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // Hashear la nueva clave antes de guardar
            ps.setString(1, PasswordUtils.hashPassword(claveNueva));
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
     * Genera el siguiente código de empleado e incrementa el contador
     */
    public String generarCodigoEmpleado() throws SQLException {
        String sqlSelect = "SELECT LPAD(int_contitem, int_contlongitud, '0') AS codigo "
                + "FROM contador WHERE vch_conttabla = 'Empleado'";
        String sqlUpdate = "UPDATE contador SET int_contitem = int_contitem + 1 "
                + "WHERE vch_conttabla = 'Empleado'";

        try (Connection conn = ConexionDB.getConnection()) {
            // Primero incrementar el contador
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.executeUpdate();
            }

            // Luego obtener el código generado
            try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
                    ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("codigo");
                }
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

    /**
     * Actualiza los datos de un empleado (sin cambiar usuario ni clave)
     */
    public void actualizar(Empleado empleado) throws SQLException {
        String sql = "UPDATE empleado SET vch_emplpaterno = ?, vch_emplmaterno = ?, "
                + "vch_emplnombre = ?, vch_emplciudad = ?, vch_empldireccion = ? "
                + "WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getPaterno());
            ps.setString(2, empleado.getMaterno());
            ps.setString(3, empleado.getNombre());
            ps.setString(4, empleado.getCiudad());
            ps.setString(5, empleado.getDireccion());
            ps.setString(6, empleado.getCodigo());
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un empleado por su código
     */
    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM empleado WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }
}
