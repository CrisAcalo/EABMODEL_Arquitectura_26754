package ec.edu.monster.ws;

import ec.edu.monster.models.dto.RespuestaDTO;
import ec.edu.monster.services.ReporteService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.time.LocalDate;

/**
 * Web Service SOAP para reportes y consultas
 */
@WebService(serviceName = "ServicioReporte")
public class ServicioReporte {

    private final ReporteService service;

    public ServicioReporte() {
        this.service = new ReporteService();
    }

    /**
     * Obtiene todos los movimientos de una cuenta
     *
     * @param codigoCuenta Código de la cuenta
     * @return RespuestaDTO con la lista de movimientos
     */
    @WebMethod(operationName = "obtenerMovimientos")
    public RespuestaDTO obtenerMovimientos(
            @WebParam(name = "codigoCuenta") String codigoCuenta) {
        return service.obtenerMovimientos(codigoCuenta);
    }

    /**
     * Obtiene los movimientos de una cuenta en un rango de fechas
     *
     * @param codigoCuenta Código de la cuenta
     * @param fechaInicio  Fecha de inicio del rango
     * @param fechaFin     Fecha de fin del rango
     * @return RespuestaDTO con la lista de movimientos filtrados
     */
    @WebMethod(operationName = "obtenerMovimientosPorRango")
    public RespuestaDTO obtenerMovimientosPorRango(
            @WebParam(name = "codigoCuenta") String codigoCuenta,
            @WebParam(name = "fechaInicio") String fechaInicio,
            @WebParam(name = "fechaFin") String fechaFin) {

        // Convertir strings a LocalDate
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        return service.obtenerMovimientosPorRango(codigoCuenta, inicio, fin);
    }

    /**
     * Obtiene el estado de cuenta con información general y últimos movimientos
     *
     * @param codigoCuenta Código de la cuenta
     * @return RespuestaDTO con el estado de cuenta
     */
    @WebMethod(operationName = "obtenerEstadoCuenta")
    public RespuestaDTO obtenerEstadoCuenta(
            @WebParam(name = "codigoCuenta") String codigoCuenta) {
        return service.obtenerEstadoCuenta(codigoCuenta);
    }
}
