package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Moneda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Moneda
 */
public class MonedaDAO {

    /**
     * Obtiene todas las monedas
     */
    public List<Moneda> obtenerTodas() throws SQLException {
        String sql = "SELECT chr_monecodigo, vch_monedescripcion FROM moneda ORDER BY chr_monecodigo";

        List<Moneda> monedas = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                monedas.add(mapearMoneda(rs));
            }
        }

        return monedas;
    }

    /**
     * Obtiene una moneda por su código
     */
    public Moneda obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT chr_monecodigo, vch_monedescripcion FROM moneda WHERE chr_monecodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearMoneda(rs);
            }
            return null;
        }
    }

    /**
     * Inserta una nueva moneda
     */
    public void insertar(Moneda moneda) throws SQLException {
        String sql = "INSERT INTO moneda (chr_monecodigo, vch_monedescripcion) VALUES (?, ?)";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, moneda.getCodigo());
            ps.setString(2, moneda.getDescripcion());
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza una moneda
     */
    public void actualizar(Moneda moneda) throws SQLException {
        String sql = "UPDATE moneda SET vch_monedescripcion = ? WHERE chr_monecodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, moneda.getDescripcion());
            ps.setString(2, moneda.getCodigo());
            ps.executeUpdate();
        }
    }

    /**
     * Elimina una moneda
     */
    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM moneda WHERE chr_monecodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Moneda
     */
    private Moneda mapearMoneda(ResultSet rs) throws SQLException {
        Moneda moneda = new Moneda();
        moneda.setCodigo(rs.getString("chr_monecodigo"));
        moneda.setDescripcion(rs.getString("vch_monedescripcion"));
        return moneda;
    }
}
