package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Cuenta;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Cuenta
 *
 * @author EurekaBank
 */
public class CuentaDAO {

    /**
     * Obtiene una cuenta por su código
     *
     * @param codigo Código de la cuenta
     * @return Cuenta encontrada o null
     * @throws SQLException si hay error en la BD
     */
    public Cuenta obtenerPorCodigo(String codigo) throws SQLException {
        String query = "SELECT chr_cuencodigo, chr_monecodigo, chr_sucucodigo, "
                + "chr_emplcreacuenta, chr_cliecodigo, dec_cuensaldo, "
                + "dtt_cuenfechacreacion, vch_cuenestado, int_cuencontmov, chr_cuenclave "
                + "FROM cuenta WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(rs.getString("chr_cuencodigo"));
                cuenta.setCodigoMoneda(rs.getString("chr_monecodigo"));
                cuenta.setCodigoSucursal(rs.getString("chr_sucucodigo"));
                cuenta.setCodigoEmpleadoCreador(rs.getString("chr_emplcreacuenta"));
                cuenta.setCodigoCliente(rs.getString("chr_cliecodigo"));
                cuenta.setSaldo(rs.getBigDecimal("dec_cuensaldo"));
                cuenta.setFechaCreacion(rs.getTimestamp("dtt_cuenfechacreacion"));
                cuenta.setEstado(rs.getString("vch_cuenestado"));
                cuenta.setContadorMovimientos(rs.getInt("int_cuencontmov"));
                cuenta.setClave(rs.getString("chr_cuenclave"));
                return cuenta;
            }
        }
        return null;
    }

    /**
     * Valida la clave de una cuenta
     *
     * @param codigoCuenta Código de la cuenta
     * @param clave Clave a validar
     * @return true si la clave es correcta
     * @throws SQLException si hay error en la BD
     */
    public boolean validarClave(String codigoCuenta, String clave) throws SQLException {
        String query = "SELECT COUNT(*) FROM cuenta "
                + "WHERE chr_cuencodigo = ? AND chr_cuenclave = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Actualiza el saldo de una cuenta
     *
     * @param conn Conexión a usar (para transacciones)
     * @param codigoCuenta Código de la cuenta
     * @param nuevoSaldo Nuevo saldo
     * @return true si se actualizó
     * @throws SQLException si hay error en la BD
     */
    public boolean actualizarSaldo(Connection conn, String codigoCuenta, BigDecimal nuevoSaldo) throws SQLException {
        String query = "UPDATE cuenta SET dec_cuensaldo = ? WHERE chr_cuencodigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setBigDecimal(1, nuevoSaldo);
            ps.setString(2, codigoCuenta);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Actualiza el saldo de una cuenta (sin conexión externa)
     *
     * @param codigoCuenta Código de la cuenta
     * @param nuevoSaldo Nuevo saldo
     * @return true si se actualizó
     * @throws SQLException si hay error en la BD
     */
    public boolean actualizarSaldo(String codigoCuenta, BigDecimal nuevoSaldo) throws SQLException {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            return actualizarSaldo(conn, codigoCuenta, nuevoSaldo);
        }
    }

    /**
     * Incrementa el contador de movimientos de una cuenta
     *
     * @param conn Conexión a usar (para transacciones)
     * @param codigoCuenta Código de la cuenta
     * @return true si se incrementó
     * @throws SQLException si hay error en la BD
     */
    public boolean incrementarContadorMovimientos(Connection conn, String codigoCuenta) throws SQLException {
        String query = "UPDATE cuenta SET int_cuencontmov = int_cuencontmov + 1 "
                + "WHERE chr_cuencodigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codigoCuenta);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Incrementa el contador de movimientos de una cuenta (sin conexión externa)
     *
     * @param codigoCuenta Código de la cuenta
     * @return true si se incrementó
     * @throws SQLException si hay error en la BD
     */
    public boolean incrementarContadorMovimientos(String codigoCuenta) throws SQLException {
        String query = "UPDATE cuenta SET int_cuencontmov = int_cuencontmov + 1 "
                + "WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codigoCuenta);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene el saldo actual de una cuenta
     *
     * @param codigoCuenta Código de la cuenta
     * @return Saldo de la cuenta
     * @throws SQLException si hay error en la BD
     */
    public BigDecimal obtenerSaldo(String codigoCuenta) throws SQLException {
        String query = "SELECT dec_cuensaldo FROM cuenta WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("dec_cuensaldo");
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Lista todas las cuentas activas
     *
     * @return Lista de cuentas activas
     * @throws SQLException si hay error en la BD
     */
    public List<Cuenta> listarCuentasActivas() throws SQLException {
        List<Cuenta> cuentas = new ArrayList<>();
        String query = "SELECT chr_cuencodigo, chr_monecodigo, chr_sucucodigo, "
                + "chr_emplcreacuenta, chr_cliecodigo, dec_cuensaldo, "
                + "dtt_cuenfechacreacion, vch_cuenestado, int_cuencontmov, chr_cuenclave "
                + "FROM cuenta WHERE vch_cuenestado = 'ACTIVO'";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(rs.getString("chr_cuencodigo"));
                cuenta.setCodigoMoneda(rs.getString("chr_monecodigo"));
                cuenta.setCodigoSucursal(rs.getString("chr_sucucodigo"));
                cuenta.setCodigoEmpleadoCreador(rs.getString("chr_emplcreacuenta"));
                cuenta.setCodigoCliente(rs.getString("chr_cliecodigo"));
                cuenta.setSaldo(rs.getBigDecimal("dec_cuensaldo"));
                cuenta.setFechaCreacion(rs.getTimestamp("dtt_cuenfechacreacion"));
                cuenta.setEstado(rs.getString("vch_cuenestado"));
                cuenta.setContadorMovimientos(rs.getInt("int_cuencontmov"));
                cuenta.setClave(rs.getString("chr_cuenclave"));
                cuentas.add(cuenta);
            }
        }
        return cuentas;
    }

    /**
     * Obtiene las cuentas de un cliente específico
     *
     * @param codigoCliente Código del cliente
     * @return Lista de cuentas del cliente
     * @throws SQLException si hay error en la BD
     */
    public List<Cuenta> obtenerCuentasPorCliente(String codigoCliente) throws SQLException {
        List<Cuenta> cuentas = new ArrayList<>();
        String query = "SELECT chr_cuencodigo, chr_monecodigo, chr_sucucodigo, "
                + "chr_emplcreacuenta, chr_cliecodigo, dec_cuensaldo, "
                + "dtt_cuenfechacreacion, vch_cuenestado, int_cuencontmov, chr_cuenclave "
                + "FROM cuenta WHERE chr_cliecodigo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(rs.getString("chr_cuencodigo"));
                cuenta.setCodigoMoneda(rs.getString("chr_monecodigo"));
                cuenta.setCodigoSucursal(rs.getString("chr_sucucodigo"));
                cuenta.setCodigoEmpleadoCreador(rs.getString("chr_emplcreacuenta"));
                cuenta.setCodigoCliente(rs.getString("chr_cliecodigo"));
                cuenta.setSaldo(rs.getBigDecimal("dec_cuensaldo"));
                cuenta.setFechaCreacion(rs.getTimestamp("dtt_cuenfechacreacion"));
                cuenta.setEstado(rs.getString("vch_cuenestado"));
                cuenta.setContadorMovimientos(rs.getInt("int_cuencontmov"));
                cuenta.setClave(rs.getString("chr_cuenclave"));
                cuentas.add(cuenta);
            }
        }
        return cuentas;
    }
}
