package ec.edu.monster.ws;

import ec.edu.monster.models.dto.MovimientoDetalleDTO;
import ec.edu.monster.services.ReporteService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Web Service SOAP para reportes y consultas
 * Nota: Esta versión replica EXACTAMENTE el comportamiento de .NET
 */
@WebService(serviceName = "ServicioReporte")
public class ServicioReporte {

    private static final Logger LOGGER = Logger.getLogger(ServicioReporte.class.getName());
    private final ReporteService service;

    public ServicioReporte() {
        this.service = new ReporteService();
    }

    /**
     * Obtiene todos los movimientos de una cuenta
     * Retorna List<MovimientoDetalleDTO> directamente (igual que .NET)
     *
     * @param codigoCuenta Código de la cuenta
     * @return Lista de movimientos con información detallada
     */
    @WebMethod(operationName = "obtenerMovimientos")
    public List<MovimientoDetalleDTO> obtenerMovimientos(
            @WebParam(name = "codigoCuenta") String codigoCuenta) {
        try {
            return service.obtenerMovimientos(codigoCuenta);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener movimientos", e);
            // Retornar lista vacía en caso de error (igual que .NET maneja excepciones)
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene los movimientos de una cuenta en un rango de fechas
     * Retorna List<MovimientoDetalleDTO> directamente (igual que .NET)
     *
     * @param codigoCuenta Código de la cuenta
     * @param fechaInicio  Fecha de inicio del rango (formato: yyyy-MM-dd)
     * @param fechaFin     Fecha de fin del rango (formato: yyyy-MM-dd)
     * @return Lista de movimientos filtrados con información detallada
     */
    @WebMethod(operationName = "obtenerMovimientosPorRango")
    public List<MovimientoDetalleDTO> obtenerMovimientosPorRango(
            @WebParam(name = "codigoCuenta") String codigoCuenta,
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin) {
        try {
            // Convertir strings a LocalDate
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);

            return service.obtenerMovimientosPorRango(codigoCuenta, inicio, fin);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener movimientos por rango", e);
            // Retornar lista vacía en caso de error (igual que .NET maneja excepciones)
            return new ArrayList<>();
        }
    }
}
