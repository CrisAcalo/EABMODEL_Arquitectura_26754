package ec.edu.monster.controllers;

import ec.edu.monster.dtos.CambiarClaveRequest;
import ec.edu.monster.dtos.LoginRequest;
import ec.edu.monster.dtos.RespuestaDTO;
import ec.edu.monster.models.Empleado;
import ec.edu.monster.services.AutenticacionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Resource para operaciones de autenticación
 * Replica la funcionalidad del controlador AutenticacionController de .NET
 *
 * @author EurekaBank
 */
@Path("/api/Autenticacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticacionResource {

    private final AutenticacionService autenticacionService;

    public AutenticacionResource() {
        this.autenticacionService = new AutenticacionService();
    }

    /**
     * Autentica un usuario empleado
     *
     * @param request Objeto con usuario y clave
     * @return RespuestaDTO con datos del empleado si es exitoso
     */
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            if (request == null || request.getUsuario() == null || request.getClave() == null) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("Usuario y clave son requeridos");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            RespuestaDTO resultado = autenticacionService.login(
                    request.getUsuario(),
                    request.getClave()
            );

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
     * Registra un nuevo empleado en el sistema
     *
     * @param empleado Datos del empleado a registrar
     * @return RespuestaDTO indicando el resultado de la operación
     */
    @POST
    @Path("/registrar")
    public Response registrarEmpleado(Empleado empleado) {
        try {
            if (empleado == null) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("Los datos del empleado son requeridos");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            RespuestaDTO resultado = autenticacionService.registrarEmpleado(empleado);

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
     * Cambia la clave de un empleado
     *
     * @param request Objeto con código, clave actual y nueva
     * @return RespuestaDTO indicando el resultado de la operación
     */
    @PUT
    @Path("/cambiar-clave")
    public Response cambiarClave(CambiarClaveRequest request) {
        try {
            if (request == null || request.getCodigo() == null ||
                request.getClaveActual() == null || request.getClaveNueva() == null) {
                RespuestaDTO error = new RespuestaDTO();
                error.setExitoso(false);
                error.setMensaje("Código, clave actual y clave nueva son requeridos");
                error.setCodigoError("VAL001");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            RespuestaDTO resultado = autenticacionService.cambiarClave(
                    request.getCodigo(),
                    request.getClaveActual(),
                    request.getClaveNueva()
            );

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
