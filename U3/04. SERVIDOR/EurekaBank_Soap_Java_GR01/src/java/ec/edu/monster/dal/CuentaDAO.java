package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Cuenta;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Cuenta
 */
public class CuentaDAO {

    /**
     * Obtiene una cuenta por su código
     */
    public Cuenta obtenerPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT chr_cuencodigo, chr_monecodigo, chr_sucucodigo, "
                + "chr_emplcreacuenta, chr_cliecodigo, dec_cuensaldo, "
                + "dtt_cuenfechacreacion, vch_cuenestado, int_cuencontmov, chr_cuenclave "
                + "FROM cuenta WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearCuenta(rs);
            }
            return null;
        }
    }

    /**
     * Valida la clave de una cuenta
     */
    public boolean validarClave(String codigoCuenta, String clave) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cuenta "
                + "WHERE chr_cuencodigo = ? AND chr_cuenclave = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    /**
     * Actualiza el saldo de una cuenta
     */
    public void actualizarSaldo(String codigoCuenta, BigDecimal nuevoSaldo) throws SQLException {
        String sql = "UPDATE cuenta SET dec_cuensaldo = ? WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, nuevoSaldo);
            ps.setString(2, codigoCuenta);
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza el saldo usando una conexión específica (para transacciones)
     */
    public void actualizarSaldo(Connection conn, String codigoCuenta, BigDecimal nuevoSaldo) throws SQLException {
        String sql = "UPDATE cuenta SET dec_cuensaldo = ? WHERE chr_cuencodigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, nuevoSaldo);
            ps.setString(2, codigoCuenta);
            ps.executeUpdate();
        }
    }

    /**
     * Incrementa el contador de movimientos de una cuenta
     */
    public void incrementarContadorMovimientos(String codigoCuenta) throws SQLException {
        String sql = "UPDATE cuenta SET int_cuencontmov = int_cuencontmov + 1 "
                + "WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.executeUpdate();
        }
    }

    /**
     * Incrementa el contador usando una conexión específica (para transacciones)
     */
    public void incrementarContadorMovimientos(Connection conn, String codigoCuenta) throws SQLException {
        String sql = "UPDATE cuenta SET int_cuencontmov = int_cuencontmov + 1 "
                + "WHERE chr_cuencodigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigoCuenta);
            ps.executeUpdate();
        }
    }

    /**
     * Obtiene el saldo actual de una cuenta
     */
    public BigDecimal obtenerSaldo(String codigoCuenta) throws SQLException {
        String sql = "SELECT dec_cuensaldo FROM cuenta WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("dec_cuensaldo");
            }
            return null;
        }
    }

    /**
     * Lista todas las cuentas activas
     */
    public List<Cuenta> listarCuentasActivas() throws SQLException {
        String sql = "SELECT chr_cuencodigo, chr_monecodigo, chr_sucucodigo, "
                + "chr_emplcreacuenta, chr_cliecodigo, dec_cuensaldo, "
                + "dtt_cuenfechacreacion, vch_cuenestado, int_cuencontmov, chr_cuenclave "
                + "FROM cuenta WHERE vch_cuenestado = 'ACTIVO'";

        List<Cuenta> cuentas = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cuentas.add(mapearCuenta(rs));
            }
        }

        return cuentas;
    }

    /**
     * Mapea un ResultSet a un objeto Cuenta
     */
    private Cuenta mapearCuenta(ResultSet rs) throws SQLException {
        Cuenta cuenta = new Cuenta();
        cuenta.setCodigo(rs.getString("chr_cuencodigo"));
        cuenta.setCodigoMoneda(rs.getString("chr_monecodigo"));
        cuenta.setCodigoSucursal(rs.getString("chr_sucucodigo"));
        cuenta.setCodigoEmpleadoCreador(rs.getString("chr_emplcreacuenta"));
        cuenta.setCodigoCliente(rs.getString("chr_cliecodigo"));
        cuenta.setSaldo(rs.getBigDecimal("dec_cuensaldo"));

        Date fecha = rs.getDate("dtt_cuenfechacreacion");
        if (fecha != null) {
            cuenta.setFechaCreacion(fecha.toLocalDate());
        }

        cuenta.setEstado(rs.getString("vch_cuenestado"));
        cuenta.setContadorMovimientos(rs.getInt("int_cuencontmov"));
        cuenta.setClave(rs.getString("chr_cuenclave"));

        return cuenta;
    }

    /**
     * Inserta una nueva cuenta (apertura de cuenta)
     */
    public void insertar(Cuenta cuenta) throws SQLException {
        String sql = "INSERT INTO cuenta (chr_cuencodigo, chr_monecodigo, chr_sucucodigo, "
                + "chr_emplcreacuenta, chr_cliecodigo, dec_cuensaldo, dtt_cuenfechacreacion, "
                + "vch_cuenestado, int_cuencontmov, chr_cuenclave) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cuenta.getCodigo());
            ps.setString(2, cuenta.getCodigoMoneda());
            ps.setString(3, cuenta.getCodigoSucursal());
            ps.setString(4, cuenta.getCodigoEmpleadoCreador());
            ps.setString(5, cuenta.getCodigoCliente());
            ps.setBigDecimal(6, cuenta.getSaldo());
            ps.setDate(7, Date.valueOf(cuenta.getFechaCreacion()));
            ps.setString(8, cuenta.getEstado());
            ps.setInt(9, cuenta.getContadorMovimientos());
            ps.setString(10, cuenta.getClave());
            ps.executeUpdate();
        }
    }

    /**
     * Cancela una cuenta (cambia estado a CANCELADO)
     */
    public void cancelar(String codigoCuenta) throws SQLException {
        String sql = "UPDATE cuenta SET vch_cuenestado = 'CANCELADO' WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.executeUpdate();
        }
    }

    /**
     * Elimina físicamente una cuenta (usar con precaución)
     */
    public void eliminar(String codigoCuenta) throws SQLException {
        String sql = "DELETE FROM cuenta WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.executeUpdate();
        }
    }

    /**
     * Genera el siguiente código de cuenta para una sucursal
     */
    public String generarCodigoCuenta(String codigoSucursal) throws SQLException {
        String sql = "SELECT CONCAT(?, LPAD(int_sucucontcuenta + 1, 5, '0')) AS codigo "
                + "FROM sucursal WHERE chr_sucucodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoSucursal);
            ps.setString(2, codigoSucursal);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("codigo");
            }
            return codigoSucursal + "00001";
        }
    }
}
