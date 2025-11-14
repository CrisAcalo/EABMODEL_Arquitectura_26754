package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Empleado
 *
 * @author EurekaBank
 */
public class EmpleadoDAO {

    /**
     * Valida las credenciales de un empleado
     *
     * @param usuario Usuario del empleado
     * @param clave Contraseña del empleado
     * @return Empleado si las credenciales son correctas, null en caso contrario
     * @throws SQLException si hay error en la BD
     */
    public Empleado validarCredenciales(String usuario, String clave) throws SQLException {
        String query = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave "
                + "FROM empleado WHERE vch_emplusuario = ? AND vch_emplclave = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

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
                empleado.setClave(rs.getString("vch_emplclave"));
                return empleado;
            }
        }
        return null;
    }

    /**
     * Obtiene un empleado por su código
     *
     * @param codigo Código del empleado
     * @return Empleado encontrado o null
     * @throws SQLException si hay error en la BD
     */
    public Empleado obtenerPorCodigo(String codigo) throws SQLException {
        String query = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave "
                + "FROM empleado WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigo);
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
                empleado.setClave(rs.getString("vch_emplclave"));
                return empleado;
            }
        }
        return null;
    }

    /**
     * Obtiene un empleado por su usuario
     *
     * @param usuario Usuario del empleado
     * @return Empleado encontrado o null
     * @throws SQLException si hay error en la BD
     */
    public Empleado obtenerPorUsuario(String usuario) throws SQLException {
        String query = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave "
                + "FROM empleado WHERE vch_emplusuario = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, usuario);
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
                empleado.setClave(rs.getString("vch_emplclave"));
                return empleado;
            }
        }
        return null;
    }

    /**
     * Registra un nuevo empleado en la base de datos
     *
     * @param empleado Empleado a registrar
     * @return true si se registró correctamente
     * @throws SQLException si hay error en la BD
     */
    public boolean registrar(Empleado empleado) throws SQLException {
        String query = "INSERT INTO empleado (chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, empleado.getCodigo());
            ps.setString(2, empleado.getPaterno());
            ps.setString(3, empleado.getMaterno());
            ps.setString(4, empleado.getNombre());
            ps.setString(5, empleado.getCiudad());
            ps.setString(6, empleado.getDireccion());
            ps.setString(7, empleado.getUsuario());
            ps.setString(8, empleado.getClave());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza la contraseña de un empleado
     *
     * @param codigo Código del empleado
     * @param claveNueva Nueva contraseña
     * @return true si se actualizó correctamente
     * @throws SQLException si hay error en la BD
     */
    public boolean actualizarClave(String codigo, String claveNueva) throws SQLException {
        String query = "UPDATE empleado SET vch_emplclave = ? WHERE chr_emplcodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, claveNueva);
            ps.setString(2, codigo);

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene todos los empleados
     *
     * @return Lista de empleados
     * @throws SQLException si hay error en la BD
     */
    public List<Empleado> obtenerTodos() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT chr_emplcodigo, vch_emplpaterno, vch_emplmaterno, "
                + "vch_emplnombre, vch_emplciudad, vch_empldireccion, "
                + "vch_emplusuario, vch_emplclave FROM empleado";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigo(rs.getString("chr_emplcodigo"));
                empleado.setPaterno(rs.getString("vch_emplpaterno"));
                empleado.setMaterno(rs.getString("vch_emplmaterno"));
                empleado.setNombre(rs.getString("vch_emplnombre"));
                empleado.setCiudad(rs.getString("vch_emplciudad"));
                empleado.setDireccion(rs.getString("vch_empldireccion"));
                empleado.setUsuario(rs.getString("vch_emplusuario"));
                empleado.setClave(rs.getString("vch_emplclave"));
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    /**
     * Genera el siguiente código de empleado disponible
     *
     * @return Código de empleado (formato: 0001, 0002, etc.)
     * @throws SQLException si hay error en la BD
     */
    public String generarCodigoEmpleado() throws SQLException {
        String querySelect = "SELECT int_contitem FROM contador WHERE vch_conttabla = 'empleado'";
        String queryUpdate = "UPDATE contador SET int_contitem = ? WHERE vch_conttabla = 'empleado'";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement psSelect = conn.prepareStatement(querySelect)) {

            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int contador = rs.getInt("int_contitem");
                int nuevoContador = contador + 1;

                // Actualizar contador
                try (PreparedStatement psUpdate = conn.prepareStatement(queryUpdate)) {
                    psUpdate.setInt(1, nuevoContador);
                    psUpdate.executeUpdate();
                }

                // Retornar código formateado
                return String.format("%04d", nuevoContador);
            } else {
                throw new SQLException("No se encontró el contador para Empleado");
            }
        }
    }
}
