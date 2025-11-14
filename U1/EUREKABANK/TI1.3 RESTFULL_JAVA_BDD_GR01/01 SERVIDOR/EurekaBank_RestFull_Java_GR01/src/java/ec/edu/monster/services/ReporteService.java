package ec.edu.monster.services;

import ec.edu.monster.db.ConexionDB;
import ec.edu.monster.dtos.MovimientoDetalleDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio de lógica de negocio para reportes
 *
 * @author EurekaBank
 */
public class ReporteService {

    /**
     * Obtiene el reporte de movimientos de una cuenta ordenado por fecha
     * descendente
     *
     * @param codigoCuenta Código de la cuenta
     * @return Lista de movimientos con información detallada
     * @throws SQLException si hay error en la BD
     */
    public List<MovimientoDetalleDTO> obtenerMovimientos(String codigoCuenta) throws SQLException {
        List<MovimientoDetalleDTO> movimientos = new ArrayList<>();
        String query = "SELECT m.chr_cuencodigo, m.int_movinumero, m.dtt_movifecha, "
                + "tm.vch_tipodescripcion, tm.vch_tipoaccion, m.dec_moviimporte, "
                + "CONCAT(e.vch_emplnombre, ' ', e.vch_emplpaterno, ' ', e.vch_emplmaterno) as empleadoNombre, "
                + "m.chr_cuenreferencia "
                + "FROM movimiento m "
                + "INNER JOIN tipomovimiento tm ON m.chr_tipocodigo = tm.chr_tipocodigo "
                + "INNER JOIN empleado e ON m.chr_emplcodigo = e.chr_emplcodigo "
                + "WHERE m.chr_cuencodigo = ? "
                + "ORDER BY m.dtt_movifecha DESC, m.int_movinumero DESC";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MovimientoDetalleDTO dto = new MovimientoDetalleDTO();
                dto.setCodigoCuenta(rs.getString("chr_cuencodigo"));
                dto.setNumeroMovimiento(rs.getInt("int_movinumero"));
                dto.setFecha(rs.getTimestamp("dtt_movifecha"));
                dto.setTipoMovimiento(rs.getString("vch_tipodescripcion"));
                dto.setAccion(rs.getString("vch_tipoaccion"));
                dto.setImporte(rs.getBigDecimal("dec_moviimporte"));
                dto.setEmpleadoNombre(rs.getString("empleadoNombre"));
                dto.setCuentaReferencia(rs.getString("chr_cuenreferencia"));
                movimientos.add(dto);
            }
        }

        return movimientos;
    }

    /**
     * Obtiene el reporte de movimientos de una cuenta en un rango de fechas
     *
     * @param codigoCuenta Código de la cuenta
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de movimientos con información detallada
     * @throws SQLException si hay error en la BD
     */
    public List<MovimientoDetalleDTO> obtenerMovimientosPorRango(String codigoCuenta,
            Date fechaInicio, Date fechaFin) throws SQLException {
        List<MovimientoDetalleDTO> movimientos = new ArrayList<>();
        String query = "SELECT m.chr_cuencodigo, m.int_movinumero, m.dtt_movifecha, "
                + "tm.vch_tipodescripcion, tm.vch_tipoaccion, m.dec_moviimporte, "
                + "CONCAT(e.vch_emplnombre, ' ', e.vch_emplpaterno, ' ', e.vch_emplmaterno) as empleadoNombre, "
                + "m.chr_cuenreferencia "
                + "FROM movimiento m "
                + "INNER JOIN tipomovimiento tm ON m.chr_tipocodigo = tm.chr_tipocodigo "
                + "INNER JOIN empleado e ON m.chr_emplcodigo = e.chr_emplcodigo "
                + "WHERE m.chr_cuencodigo = ? "
                + "AND m.dtt_movifecha BETWEEN ? AND ? "
                + "ORDER BY m.dtt_movifecha DESC, m.int_movinumero DESC";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, codigoCuenta);
            ps.setTimestamp(2, new java.sql.Timestamp(fechaInicio.getTime()));
            ps.setTimestamp(3, new java.sql.Timestamp(fechaFin.getTime()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MovimientoDetalleDTO dto = new MovimientoDetalleDTO();
                dto.setCodigoCuenta(rs.getString("chr_cuencodigo"));
                dto.setNumeroMovimiento(rs.getInt("int_movinumero"));
                dto.setFecha(rs.getTimestamp("dtt_movifecha"));
                dto.setTipoMovimiento(rs.getString("vch_tipodescripcion"));
                dto.setAccion(rs.getString("vch_tipoaccion"));
                dto.setImporte(rs.getBigDecimal("dec_moviimporte"));
                dto.setEmpleadoNombre(rs.getString("empleadoNombre"));
                dto.setCuentaReferencia(rs.getString("chr_cuenreferencia"));
                movimientos.add(dto);
            }
        }

        return movimientos;
    }
}
