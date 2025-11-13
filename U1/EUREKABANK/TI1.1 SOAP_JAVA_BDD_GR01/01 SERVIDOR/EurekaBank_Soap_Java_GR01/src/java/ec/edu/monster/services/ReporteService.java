package ec.edu.monster.services;

import ec.edu.monster.constants.MensajesConstants;
import ec.edu.monster.dal.CuentaDAO;
import ec.edu.monster.dal.MovimientoDAO;
import ec.edu.monster.models.Cuenta;
import ec.edu.monster.models.dto.EstadoCuentaDTO;
import ec.edu.monster.models.dto.MovimientoDetalleDTO;
import ec.edu.monster.models.dto.RespuestaDTO;
import ec.edu.monster.validators.CuentaValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de lógica de negocio para reportes y consultas
 */
public class ReporteService {

    private static final Logger LOGGER = Logger.getLogger(ReporteService.class.getName());
    private final CuentaDAO cuentaDAO;
    private final MovimientoDAO movimientoDAO;

    public ReporteService() {
        this.cuentaDAO = new CuentaDAO();
        this.movimientoDAO = new MovimientoDAO();
    }

    /**
     * Obtiene todos los movimientos de una cuenta
     */
    public RespuestaDTO obtenerMovimientos(String codigoCuenta) {
        try {
            // Validar que la cuenta existe
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
            CuentaValidator.validarExistencia(cuenta);

            // Obtener movimientos
            List<MovimientoDetalleDTO> movimientos = movimientoDAO.listarPorCuentaDescendente(codigoCuenta);

            LOGGER.info("Consultados " + movimientos.size() + " movimientos de cuenta: " + codigoCuenta);
            return RespuestaDTO.exito("Movimientos obtenidos correctamente", movimientos);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener movimientos", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    /**
     * Obtiene los movimientos de una cuenta en un rango de fechas
     */
    public RespuestaDTO obtenerMovimientosPorRango(String codigoCuenta,
                                                    LocalDate fechaInicio,
                                                    LocalDate fechaFin) {
        try {
            // Validar que la cuenta existe
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
            CuentaValidator.validarExistencia(cuenta);

            // Validar fechas
            if (fechaInicio == null || fechaFin == null) {
                return RespuestaDTO.error("Las fechas de inicio y fin son obligatorias");
            }

            if (fechaInicio.isAfter(fechaFin)) {
                return RespuestaDTO.error("La fecha de inicio no puede ser posterior a la fecha fin");
            }

            // Obtener movimientos en el rango
            List<MovimientoDetalleDTO> movimientos = movimientoDAO.listarPorCuenta(
                    codigoCuenta, fechaInicio, fechaFin);

            LOGGER.info("Consultados " + movimientos.size() + " movimientos de cuenta: "
                    + codigoCuenta + " entre " + fechaInicio + " y " + fechaFin);

            return RespuestaDTO.exito("Movimientos obtenidos correctamente", movimientos);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener movimientos por rango", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }

    /**
     * Obtiene el estado de cuenta (información de la cuenta con sus movimientos recientes)
     */
    public RespuestaDTO obtenerEstadoCuenta(String codigoCuenta) {
        try {
            // Obtener cuenta
            Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
            CuentaValidator.validarExistencia(cuenta);

            // Obtener últimos 10 movimientos
            List<MovimientoDetalleDTO> movimientos = movimientoDAO.listarPorCuentaDescendente(codigoCuenta);

            // Limitar a 10 movimientos más recientes
            if (movimientos.size() > 10) {
                movimientos = movimientos.subList(0, 10);
            }

            // Crear DTO con la cuenta y sus movimientos
            EstadoCuentaDTO estadoCuenta = new EstadoCuentaDTO(cuenta, movimientos);

            LOGGER.info("Estado de cuenta obtenido para: " + codigoCuenta);
            return RespuestaDTO.exito("Estado de cuenta obtenido correctamente", estadoCuenta);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener estado de cuenta", e);
            return RespuestaDTO.error(e.getMessage());
        }
    }
}
