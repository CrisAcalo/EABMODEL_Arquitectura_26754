package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Sucursal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Sucursal
 */
public class SucursalDAO {

    /**
     * Obtiene todas las sucursales
     */
    public List<Sucursal> obtenerTodas() throws SQLException {
        String sql = "SELECT chr_sucucodigo, vch_sucunombre, vch_sucuciudad, "
                + "vch_sucudireccion, int_sucucontcuenta FROM sucursal ORDER BY vch_sucunombre";

        List<Sucursal> sucursales = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sucursales.add(mapearSucursal(rs));
            }
        }

        return sucursales;
    }

    /**
     * Obtiene una sucursal por su código
     */
    public Sucursal obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT chr_sucucodigo, vch_sucunombre, vch_sucuciudad, "
                + "vch_sucudireccion, int_sucucontcuenta FROM sucursal WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearSucursal(rs);
            }
            return null;
        }
    }

    /**
     * Genera el siguiente código de sucursal
     */
    public String generarCodigoSucursal() throws SQLException {
        String sqlUpdate = "UPDATE contador SET int_contitem = int_contitem + 1 "
                + "WHERE vch_conttabla = 'Sucursal'";
        String sqlSelect = "SELECT LPAD(int_contitem, int_contlongitud, '0') AS codigo "
                + "FROM contador WHERE vch_conttabla = 'Sucursal'";

        try (Connection conn = ConexionDB.getConnection()) {
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.executeUpdate();
            }
            try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
                    ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("codigo");
                }
            }
            return "001";
        }
    }

    /**
     * Inserta una nueva sucursal
     */
    public void insertar(Sucursal sucursal) throws SQLException {
        String sql = "INSERT INTO sucursal (chr_sucucodigo, vch_sucunombre, vch_sucuciudad, "
                + "vch_sucudireccion, int_sucucontcuenta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sucursal.getCodigo());
            ps.setString(2, sucursal.getNombre());
            ps.setString(3, sucursal.getCiudad());
            ps.setString(4, sucursal.getDireccion());
            ps.setInt(5, sucursal.getContadorCuenta());
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza una sucursal
     */
    public void actualizar(Sucursal sucursal) throws SQLException {
        String sql = "UPDATE sucursal SET vch_sucunombre = ?, vch_sucuciudad = ?, "
                + "vch_sucudireccion = ? WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sucursal.getNombre());
            ps.setString(2, sucursal.getCiudad());
            ps.setString(3, sucursal.getDireccion());
            ps.setString(4, sucursal.getCodigo());
            ps.executeUpdate();
        }
    }

    /**
     * Incrementa el contador de cuentas de una sucursal
     */
    public void incrementarContadorCuentas(String codigoSucursal) throws SQLException {
        String sql = "UPDATE sucursal SET int_sucucontcuenta = int_sucucontcuenta + 1 "
                + "WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoSucursal);
            ps.executeUpdate();
        }
    }

    /**
     * Elimina una sucursal
     */
    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM sucursal WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Sucursal
     */
    private Sucursal mapearSucursal(ResultSet rs) throws SQLException {
        Sucursal sucursal = new Sucursal();
        sucursal.setCodigo(rs.getString("chr_sucucodigo"));
        sucursal.setNombre(rs.getString("vch_sucunombre"));
        sucursal.setCiudad(rs.getString("vch_sucuciudad"));
        sucursal.setDireccion(rs.getString("vch_sucudireccion"));
        sucursal.setContadorCuenta(rs.getInt("int_sucucontcuenta"));
        return sucursal;
    }
}
