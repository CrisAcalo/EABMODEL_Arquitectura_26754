package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Ventanilla;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Ventanilla
 */
public class VentanillaDAO {

    /**
     * Obtiene todas las ventanillas
     */
    public List<Ventanilla> obtenerTodas() throws SQLException {
        String sql = "SELECT chr_ventcodigo, vch_ventnombre, chr_emplcodigo, vch_ventestado "
                + "FROM ventanilla ORDER BY chr_ventcodigo";

        List<Ventanilla> ventanillas = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ventanillas.add(mapearVentanilla(rs));
            }
        }

        return ventanillas;
    }

    /**
     * Obtiene una ventanilla por su código
     */
    public Ventanilla obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT chr_ventcodigo, vch_ventnombre, chr_emplcodigo, vch_ventestado "
                + "FROM ventanilla WHERE chr_ventcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearVentanilla(rs);
            }
            return null;
        }
    }

    /**
     * Obtiene ventanillas activas
     */
    public List<Ventanilla> obtenerActivas() throws SQLException {
        String sql = "SELECT chr_ventcodigo, vch_ventnombre, chr_emplcodigo, vch_ventestado "
                + "FROM ventanilla WHERE vch_ventestado = 'ACTIVO' ORDER BY chr_ventcodigo";

        List<Ventanilla> ventanillas = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ventanillas.add(mapearVentanilla(rs));
            }
        }

        return ventanillas;
    }

    /**
     * Inserta una nueva ventanilla
     */
    public void insertar(Ventanilla ventanilla) throws SQLException {
        String sql = "INSERT INTO ventanilla (chr_ventcodigo, vch_ventnombre, chr_emplcodigo, vch_ventestado) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ventanilla.getCodigo());
            ps.setString(2, ventanilla.getNombre());
            if (ventanilla.getCodigoEmpleado() != null) {
                ps.setString(3, ventanilla.getCodigoEmpleado());
            } else {
                ps.setNull(3, Types.CHAR);
            }
            ps.setString(4, ventanilla.getEstado() != null ? ventanilla.getEstado() : "ACTIVO");
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza una ventanilla
     */
    public void actualizar(Ventanilla ventanilla) throws SQLException {
        String sql = "UPDATE ventanilla SET vch_ventnombre = ?, chr_emplcodigo = ?, vch_ventestado = ? "
                + "WHERE chr_ventcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ventanilla.getNombre());
            if (ventanilla.getCodigoEmpleado() != null) {
                ps.setString(2, ventanilla.getCodigoEmpleado());
            } else {
                ps.setNull(2, Types.CHAR);
            }
            ps.setString(3, ventanilla.getEstado());
            ps.setString(4, ventanilla.getCodigo());
            ps.executeUpdate();
        }
    }

    /**
     * Asigna un empleado a una ventanilla
     */
    public void asignarEmpleado(String codigoVentanilla, String codigoEmpleado) throws SQLException {
        String sql = "UPDATE ventanilla SET chr_emplcodigo = ? WHERE chr_ventcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            if (codigoEmpleado != null) {
                ps.setString(1, codigoEmpleado);
            } else {
                ps.setNull(1, Types.CHAR);
            }
            ps.setString(2, codigoVentanilla);
            ps.executeUpdate();
        }
    }

    /**
     * Elimina una ventanilla
     */
    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM ventanilla WHERE chr_ventcodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Ventanilla
     */
    private Ventanilla mapearVentanilla(ResultSet rs) throws SQLException {
        Ventanilla ventanilla = new Ventanilla();
        ventanilla.setCodigo(rs.getString("chr_ventcodigo"));
        ventanilla.setNombre(rs.getString("vch_ventnombre"));
        ventanilla.setCodigoEmpleado(rs.getString("chr_emplcodigo"));
        ventanilla.setEstado(rs.getString("vch_ventestado"));
        return ventanilla;
    }
}
