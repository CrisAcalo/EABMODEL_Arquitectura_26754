package ec.edu.monster.services;

import ec.edu.monster.dal.CuentaDAO;
import ec.edu.monster.dal.MovimientoDAO;
import ec.edu.monster.models.Cuenta;
import ec.edu.monster.models.dto.MovimientoDetalleDTO;
import ec.edu.monster.validators.CuentaValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio de lógica de negocio para reportes y consultas
 * Nota: Esta versión replica exactamente el comportamiento de .NET
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
     * Retorna List directamente (igual que .NET)
     */
    public List<MovimientoDetalleDTO> obtenerMovimientos(String codigoCuenta) throws Exception {
        // Validar que la cuenta existe
        Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
        CuentaValidator.validarExistencia(cuenta);

        // Obtener movimientos
        List<MovimientoDetalleDTO> movimientos = movimientoDAO.listarPorCuentaDescendente(codigoCuenta);

        LOGGER.info("Consultados " + movimientos.size() + " movimientos de cuenta: " + codigoCuenta);
        return movimientos;
    }

    /**
     * Obtiene los movimientos de una cuenta en un rango de fechas
     * Retorna List directamente (igual que .NET)
     */
    public List<MovimientoDetalleDTO> obtenerMovimientosPorRango(String codigoCuenta,
                                                                   LocalDate fechaInicio,
                                                                   LocalDate fechaFin) throws Exception {
        // Validar que la cuenta existe
        Cuenta cuenta = cuentaDAO.obtenerPorCodigo(codigoCuenta);
        CuentaValidator.validarExistencia(cuenta);

        // Validar fechas
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }

        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        // Obtener movimientos en el rango
        List<MovimientoDetalleDTO> movimientos = movimientoDAO.listarPorCuenta(
                codigoCuenta, fechaInicio, fechaFin);

        LOGGER.info("Consultados " + movimientos.size() + " movimientos de cuenta: "
                + codigoCuenta + " entre " + fechaInicio + " y " + fechaFin);

        return movimientos;
    }
}
