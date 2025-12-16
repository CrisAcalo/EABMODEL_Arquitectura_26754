package ec.edu.monster.controllers;

import ec.edu.monster.dtos.MovimientoDetalleDTO;
import ec.edu.monster.dtos.RespuestaDTO;
import ec.edu.monster.services.ReporteService;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Resource para operaciones de reportes y consultas
 * Replica la funcionalidad del controlador ReporteController de .NET
 *
 * @author EurekaBank
 */
@Path("/api/Reporte")
@Produces(MediaType.APPLICATION_JSON)
public class ReporteResource {

    private final ReporteService reporteService;
    private final SimpleDateFormat dateFormat;

    public ReporteResource() {
        this.reporteService = new ReporteService();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * Obtiene todos los movimientos de una cuenta
     *
     * @param codigoCuenta Código de la cuenta
     * @return Lista de movimientos con información detallada
     */
    @GET
    @Path("/movimientos/{codigoCuenta}")
    public Response obtenerMovimientos(@PathParam("codigoCuenta") String codigoCuenta) {
        try {
            if (codigoCuenta == null || codigoCuenta.trim().isEmpty()) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("El código de cuenta es requerido");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            List<MovimientoDetalleDTO> movimientos = reporteService.obtenerMovimientos(codigoCuenta);
            return Response.ok(movimientos).build();
        } catch (SQLException ex) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al obtener movimientos: " + ex.getMessage());
            error.setCodigoError("DB001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (Exception ex) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al procesar la solicitud: " + ex.getMessage());
            error.setCodigoError("SYS001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    /**
     * Obtiene los movimientos de una cuenta en un rango de fechas
     *
     * @param codigoCuenta Código de la cuenta
     * @param fechaInicioStr Fecha de inicio del rango (formato: yyyy-MM-dd)
     * @param fechaFinStr Fecha de fin del rango (formato: yyyy-MM-dd)
     * @return Lista de movimientos filtrados con información detallada
     */
    @GET
    @Path("/movimientos/{codigoCuenta}/rango")
    public Response obtenerMovimientosPorRango(
            @PathParam("codigoCuenta") String codigoCuenta,
            @QueryParam("fechaInicio") String fechaInicioStr,
            @QueryParam("fechaFin") String fechaFinStr) {
        try {
            if (codigoCuenta == null || codigoCuenta.trim().isEmpty()) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("El código de cuenta es requerido");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Date fechaInicio = dateFormat.parse(fechaInicioStr);
            Date fechaFin = dateFormat.parse(fechaFinStr);

            List<MovimientoDetalleDTO> movimientos = reporteService.obtenerMovimientosPorRango(
                    codigoCuenta, fechaInicio, fechaFin);
            return Response.ok(movimientos).build();
        } catch (ParseException ex) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Formato de fecha inválido. Use yyyy-MM-dd");
            error.setCodigoError("VAL002");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (SQLException ex) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al obtener movimientos: " + ex.getMessage());
            error.setCodigoError("DB001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (Exception ex) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al procesar la solicitud: " + ex.getMessage());
            error.setCodigoError("SYS001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }
}
