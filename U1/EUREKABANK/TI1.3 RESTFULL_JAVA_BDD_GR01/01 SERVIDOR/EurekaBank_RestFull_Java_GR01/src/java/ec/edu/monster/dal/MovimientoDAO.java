package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object para la entidad Movimiento
 *
 * @author EurekaBank
 */
public class MovimientoDAO {

    /**
     * Inserta un nuevo movimiento en la base de datos
     *
     * @param conn Conexión a usar (para transacciones)
     * @param movimiento Movimiento a insertar
     * @return true si se insertó correctamente
     * @throws SQLException si hay error en la BD
     */
    public boolean insertar(Connection conn, Movimiento movimiento) throws SQLException {
        String query = "INSERT INTO movimiento (chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movimiento.getCodigoCuenta());
            ps.setInt(2, movimiento.getNumero());
            ps.setTimestamp(3, new java.sql.Timestamp(movimiento.getFecha().getTime()));
            ps.setString(4, movimiento.getCodigoEmpleado());
            ps.setString(5, movimiento.getCodigoTipo());
            ps.setBigDecimal(6, movimiento.getImporte());

            if (movimiento.getCuentaReferencia() != null) {
                ps.setString(7, movimiento.getCuentaReferencia());
            } else {
                ps.setNull(7, java.sql.Types.CHAR);
            }

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Inserta un nuevo movimiento (sin conexión externa)
     *
     * @param movimiento Movimiento a insertar
     * @return true si se insertó correctamente
     * @throws SQLException si hay error en la BD
     */
    public boolean insertar(Movimiento movimiento) throws SQLException {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            return insertar(conn, movimiento);
        }
    }

    /**
     * Obtiene el último número de movimiento de una cuenta (usando conexión de transacción)
     *
     * @param conn Conexión a usar (para transacciones)
     * @param codigoCuenta Código de la cuenta
     * @return Último número de movimiento (0 si no tiene movimientos)
     * @throws SQLException si hay error en la BD
     */
    public int obtenerUltimoNumero(Connection conn, String codigoCuenta) throws SQLException {
        String query = "SELECT IFNULL(MAX(int_movinumero), 0) as ultimo "
                + "FROM movimiento WHERE chr_cuencodigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ultimo");
            }
        }
        return 0;
    }

    /**
     * Obtiene el último número de movimiento de una cuenta (sin conexión externa)
     *
     * @param codigoCuenta Código de la cuenta
     * @return Último número de movimiento (0 si no tiene movimientos)
     * @throws SQLException si hay error en la BD
     */
    public int obtenerUltimoNumero(String codigoCuenta) throws SQLException {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            return obtenerUltimoNumero(conn, codigoCuenta);
        }
    }

    /**
     * Lista los movimientos de una cuenta en un rango de fechas
     *
     * @param codigoCuenta Código de la cuenta
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de movimientos
     * @throws SQLException si hay error en la BD
     */
    public List<Movimiento> listarPorCuenta(String codigoCuenta, Date fechaInicio, Date fechaFin) throws SQLException {
        List<Movimiento> movimientos = new ArrayList<>();
        String query = "SELECT chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia "
                + "FROM movimiento "
                + "WHERE chr_cuencodigo = ? AND dtt_movifecha BETWEEN ? AND ? "
                + "ORDER BY dtt_movifecha DESC, int_movinumero DESC";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ps.setTimestamp(2, new java.sql.Timestamp(fechaInicio.getTime()));
            ps.setTimestamp(3, new java.sql.Timestamp(fechaFin.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setCodigoCuenta(rs.getString("chr_cuencodigo"));
                movimiento.setNumero(rs.getInt("int_movinumero"));
                movimiento.setFecha(rs.getTimestamp("dtt_movifecha"));
                movimiento.setCodigoEmpleado(rs.getString("chr_emplcodigo"));
                movimiento.setCodigoTipo(rs.getString("chr_tipocodigo"));
                movimiento.setImporte(rs.getBigDecimal("dec_moviimporte"));
                movimiento.setCuentaReferencia(rs.getString("chr_cuenreferencia"));
                movimientos.add(movimiento);
            }
        }
        return movimientos;
    }

    /**
     * Lista todos los movimientos de una cuenta ordenados por fecha descendente
     *
     * @param codigoCuenta Código de la cuenta
     * @return Lista de movimientos
     * @throws SQLException si hay error en la BD
     */
    public List<Movimiento> listarPorCuentaDescendente(String codigoCuenta) throws SQLException {
        List<Movimiento> movimientos = new ArrayList<>();
        String query = "SELECT chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia "
                + "FROM movimiento "
                + "WHERE chr_cuencodigo = ? "
                + "ORDER BY dtt_movifecha DESC, int_movinumero DESC";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setCodigoCuenta(rs.getString("chr_cuencodigo"));
                movimiento.setNumero(rs.getInt("int_movinumero"));
                movimiento.setFecha(rs.getTimestamp("dtt_movifecha"));
                movimiento.setCodigoEmpleado(rs.getString("chr_emplcodigo"));
                movimiento.setCodigoTipo(rs.getString("chr_tipocodigo"));
                movimiento.setImporte(rs.getBigDecimal("dec_moviimporte"));
                movimiento.setCuentaReferencia(rs.getString("chr_cuenreferencia"));
                movimientos.add(movimiento);
            }
        }
        return movimientos;
    }

    /**
     * Obtiene un movimiento específico
     *
     * @param codigoCuenta Código de la cuenta
     * @param numeroMovimiento Número del movimiento
     * @return Movimiento encontrado o null
     * @throws SQLException si hay error en la BD
     */
    public Movimiento obtenerPorNumero(String codigoCuenta, int numeroMovimiento) throws SQLException {
        String query = "SELECT chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia "
                + "FROM movimiento "
                + "WHERE chr_cuencodigo = ? AND int_movinumero = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ps.setInt(2, numeroMovimiento);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setCodigoCuenta(rs.getString("chr_cuencodigo"));
                movimiento.setNumero(rs.getInt("int_movinumero"));
                movimiento.setFecha(rs.getTimestamp("dtt_movifecha"));
                movimiento.setCodigoEmpleado(rs.getString("chr_emplcodigo"));
                movimiento.setCodigoTipo(rs.getString("chr_tipocodigo"));
                movimiento.setImporte(rs.getBigDecimal("dec_moviimporte"));
                movimiento.setCuentaReferencia(rs.getString("chr_cuenreferencia"));
                return movimiento;
            }
        }
        return null;
    }
}
