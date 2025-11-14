package ec.edu.monster.controllers;

import ec.edu.monster.dtos.RespuestaDTO;
import ec.edu.monster.dtos.TransaccionDTO;
import ec.edu.monster.dtos.TransferenciaDTO;
import ec.edu.monster.services.TransaccionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Resource para operaciones de transacciones bancarias
 * Replica la funcionalidad del controlador TransaccionController de .NET
 *
 * @author EurekaBank
 */
@Path("/api/Transaccion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransaccionResource {

    private final TransaccionService transaccionService;

    public TransaccionResource() {
        this.transaccionService = new TransaccionService();
    }

    /**
     * Realiza un depósito en una cuenta bancaria
     *
     * @param datos Datos de la transacción (cuenta, importe, clave, empleado)
     * @return RespuestaDTO con el resultado del depósito
     */
    @POST
    @Path("/deposito")
    public Response realizarDeposito(TransaccionDTO datos) {
        try {
            // Validar que los datos no sean nulos
            if (datos == null || datos.getImporte() == null) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("Los datos de la transacción son inválidos o están incompletos");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            RespuestaDTO resultado = transaccionService.realizarDeposito(datos);

            if (resultado.isExitoso()) {
                return Response.ok(resultado).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(resultado).build();
        } catch (Exception e) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al procesar la solicitud: " + e.getMessage());
            error.setCodigoError("SYS001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    /**
     * Realiza un retiro de una cuenta bancaria
     *
     * @param datos Datos de la transacción (cuenta, importe, clave, empleado)
     * @return RespuestaDTO con el resultado del retiro
     */
    @POST
    @Path("/retiro")
    public Response realizarRetiro(TransaccionDTO datos) {
        try {
            // Validar que los datos no sean nulos
            if (datos == null || datos.getImporte() == null) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("Los datos de la transacción son inválidos o están incompletos");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            RespuestaDTO resultado = transaccionService.realizarRetiro(datos);

            if (resultado.isExitoso()) {
                return Response.ok(resultado).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(resultado).build();
        } catch (Exception e) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al procesar la solicitud: " + e.getMessage());
            error.setCodigoError("SYS001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    /**
     * Realiza una transferencia entre dos cuentas bancarias
     *
     * @param datos Datos de la transferencia (cuenta origen, cuenta destino,
     * importe, empleado)
     * @return RespuestaDTO con el resultado de la transferencia
     */
    @POST
    @Path("/transferencia")
    public Response realizarTransferencia(TransferenciaDTO datos) {
        try {
            // Validar que los datos no sean nulos
            if (datos == null || datos.getImporte() == null) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("Los datos de la transferencia son inválidos o están incompletos");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            RespuestaDTO resultado = transaccionService.realizarTransferencia(datos);

            if (resultado.isExitoso()) {
                return Response.ok(resultado).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(resultado).build();
        } catch (Exception e) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("Error al procesar la solicitud: " + e.getMessage());
            error.setCodigoError("SYS001");
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }
}
