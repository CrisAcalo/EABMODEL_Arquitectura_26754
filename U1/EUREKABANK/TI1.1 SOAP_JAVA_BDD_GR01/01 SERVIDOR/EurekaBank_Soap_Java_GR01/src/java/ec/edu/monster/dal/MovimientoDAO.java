package ec.edu.monster.dal;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.models.Movimiento;
import ec.edu.monster.models.dto.MovimientoDetalleDTO;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones con la tabla Movimiento
 */
public class MovimientoDAO {

    /**
     * Inserta un nuevo movimiento
     */
    public void insertar(Movimiento movimiento) throws SQLException {
        String sql = "INSERT INTO movimiento (chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, movimiento.getCodigoCuenta());
            ps.setInt(2, movimiento.getNumero());
            ps.setDate(3, Date.valueOf(movimiento.getFecha()));
            ps.setString(4, movimiento.getCodigoEmpleado());
            ps.setString(5, movimiento.getCodigoTipo());
            ps.setBigDecimal(6, movimiento.getImporte());

            if (movimiento.getCuentaReferencia() != null) {
                ps.setString(7, movimiento.getCuentaReferencia());
            } else {
                ps.setNull(7, Types.CHAR);
            }

            ps.executeUpdate();
        }
    }

    /**
     * Inserta un movimiento usando una conexión específica (para transacciones)
     */
    public void insertar(Connection conn, Movimiento movimiento) throws SQLException {
        String sql = "INSERT INTO movimiento (chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movimiento.getCodigoCuenta());
            ps.setInt(2, movimiento.getNumero());
            ps.setDate(3, Date.valueOf(movimiento.getFecha()));
            ps.setString(4, movimiento.getCodigoEmpleado());
            ps.setString(5, movimiento.getCodigoTipo());
            ps.setBigDecimal(6, movimiento.getImporte());

            if (movimiento.getCuentaReferencia() != null) {
                ps.setString(7, movimiento.getCuentaReferencia());
            } else {
                ps.setNull(7, Types.CHAR);
            }

            ps.executeUpdate();
        }
    }

    /**
     * Obtiene el último número de movimiento de una cuenta
     */
    public int obtenerUltimoNumero(String codigoCuenta) throws SQLException {
        String sql = "SELECT COALESCE(MAX(int_movinumero), 0) AS ultimo "
                + "FROM movimiento WHERE chr_cuencodigo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ultimo");
            }
            return 0;
        }
    }

    /**
     * Obtiene el último número usando una conexión específica (para transacciones)
     */
    public int obtenerUltimoNumero(Connection conn, String codigoCuenta) throws SQLException {
        String sql = "SELECT COALESCE(MAX(int_movinumero), 0) AS ultimo "
                + "FROM movimiento WHERE chr_cuencodigo = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("ultimo");
            }
            return 0;
        }
    }

    /**
     * Lista todos los movimientos de una cuenta en un rango de fechas
     */
    public List<MovimientoDetalleDTO> listarPorCuenta(String codigoCuenta,
                                                      LocalDate fechaInicio,
                                                      LocalDate fechaFin) throws SQLException {
        String sql = "SELECT m.chr_cuencodigo, m.int_movinumero, m.dtt_movifecha, "
                + "tm.vch_tipodescripcion, tm.vch_tipoaccion, m.dec_moviimporte, "
                + "CONCAT(e.vch_emplpaterno, ' ', e.vch_emplnombre) AS empleado_nombre, "
                + "m.chr_cuenreferencia "
                + "FROM movimiento m "
                + "INNER JOIN tipomovimiento tm ON m.chr_tipocodigo = tm.chr_tipocodigo "
                + "INNER JOIN empleado e ON m.chr_emplcodigo = e.chr_emplcodigo "
                + "WHERE m.chr_cuencodigo = ? "
                + "AND m.dtt_movifecha BETWEEN ? AND ? "
                + "ORDER BY m.int_movinumero";

        List<MovimientoDetalleDTO> movimientos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setDate(2, Date.valueOf(fechaInicio));
            ps.setDate(3, Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                movimientos.add(mapearMovimientoDetalle(rs));
            }
        }

        return movimientos;
    }

    /**
     * Lista todos los movimientos de una cuenta (sin filtro de fechas)
     */
    public List<MovimientoDetalleDTO> listarPorCuentaDescendente(String codigoCuenta) throws SQLException {
        String sql = "SELECT m.chr_cuencodigo, m.int_movinumero, m.dtt_movifecha, "
                + "tm.vch_tipodescripcion, tm.vch_tipoaccion, m.dec_moviimporte, "
                + "CONCAT(e.vch_emplpaterno, ' ', e.vch_emplnombre) AS empleado_nombre, "
                + "m.chr_cuenreferencia "
                + "FROM movimiento m "
                + "INNER JOIN tipomovimiento tm ON m.chr_tipocodigo = tm.chr_tipocodigo "
                + "INNER JOIN empleado e ON m.chr_emplcodigo = e.chr_emplcodigo "
                + "WHERE m.chr_cuencodigo = ? "
                + "ORDER BY m.int_movinumero DESC";

        List<MovimientoDetalleDTO> movimientos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                movimientos.add(mapearMovimientoDetalle(rs));
            }
        }

        return movimientos;
    }

    /**
     * Obtiene un movimiento específico por cuenta y número
     */
    public Movimiento obtenerPorNumero(String codigoCuenta, int numeroMovimiento) throws SQLException {
        String sql = "SELECT chr_cuencodigo, int_movinumero, dtt_movifecha, "
                + "chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia "
                + "FROM movimiento WHERE chr_cuencodigo = ? AND int_movinumero = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setInt(2, numeroMovimiento);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearMovimiento(rs);
            }
            return null;
        }
    }

    /**
     * Mapea un ResultSet a un objeto Movimiento
     */
    private Movimiento mapearMovimiento(ResultSet rs) throws SQLException {
        Movimiento movimiento = new Movimiento();
        movimiento.setCodigoCuenta(rs.getString("chr_cuencodigo"));
        movimiento.setNumero(rs.getInt("int_movinumero"));

        Date fecha = rs.getDate("dtt_movifecha");
        if (fecha != null) {
            movimiento.setFecha(fecha.toLocalDate());
        }

        movimiento.setCodigoEmpleado(rs.getString("chr_emplcodigo"));
        movimiento.setCodigoTipo(rs.getString("chr_tipocodigo"));
        movimiento.setImporte(rs.getBigDecimal("dec_moviimporte"));
        movimiento.setCuentaReferencia(rs.getString("chr_cuenreferencia"));

        return movimiento;
    }

    /**
     * Mapea un ResultSet a un MovimientoDetalleDTO
     */
    private MovimientoDetalleDTO mapearMovimientoDetalle(ResultSet rs) throws SQLException {
        MovimientoDetalleDTO dto = new MovimientoDetalleDTO();
        dto.setCodigoCuenta(rs.getString("chr_cuencodigo"));
        dto.setNumeroMovimiento(rs.getInt("int_movinumero"));

        Date fecha = rs.getDate("dtt_movifecha");
        if (fecha != null) {
            dto.setFecha(fecha.toLocalDate());
        }

        dto.setTipoMovimiento(rs.getString("vch_tipodescripcion"));
        dto.setAccion(rs.getString("vch_tipoaccion"));
        dto.setImporte(rs.getBigDecimal("dec_moviimporte"));
        dto.setEmpleadoNombre(rs.getString("empleado_nombre"));
        dto.setCuentaReferencia(rs.getString("chr_cuenreferencia"));

        return dto;
    }
}
