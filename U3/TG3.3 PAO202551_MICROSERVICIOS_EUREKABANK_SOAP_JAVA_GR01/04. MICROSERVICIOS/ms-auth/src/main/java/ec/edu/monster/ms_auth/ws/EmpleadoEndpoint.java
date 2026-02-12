package ec.edu.monster.ms_auth.ws;

import ec.edu.monster.ms_auth.dtos.*;
import ec.edu.monster.ms_auth.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmpleadoEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-auth/ws";

    @Autowired
    private EmpleadoService empleadoService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrarEmpleadoRequest")
    @ResponsePayload
    public RegistrarEmpleadoResponse registrarEmpleado(@RequestPayload RegistrarEmpleadoRequest request) {
        if (request.getEmpleado() == null) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("El objeto empleado es obligatorio");
            error.setCodigoError("EMP000"); // Internal error code for bad request
            return new RegistrarEmpleadoResponse(error);
        }
        RespuestaDTO respuesta = empleadoService.registrar(request.getEmpleado());
        return new RegistrarEmpleadoResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "actualizarEmpleadoRequest")
    @ResponsePayload
    public ActualizarEmpleadoResponse actualizarEmpleado(@RequestPayload ActualizarEmpleadoRequest request) {
        if (request.getEmpleado() == null) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("El objeto empleado es obligatorio");
            error.setCodigoError("EMP000");
            return new ActualizarEmpleadoResponse(error);
        }
        RespuestaDTO respuesta = empleadoService.actualizar(request.getEmpleado());
        return new ActualizarEmpleadoResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarEmpleadoRequest")
    @ResponsePayload
    public EliminarEmpleadoResponse eliminarEmpleado(@RequestPayload EliminarEmpleadoRequest request) {
        RespuestaDTO respuesta = empleadoService.eliminar(request.getCodigo());
        return new EliminarEmpleadoResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerEmpleadoRequest")
    @ResponsePayload
    public ObtenerEmpleadoResponse obtenerEmpleado(@RequestPayload ObtenerEmpleadoRequest request) {
        RespuestaDTO respuesta = empleadoService.obtener(request.getCodigo());
        return new ObtenerEmpleadoResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarEmpleadosRequest")
    @ResponsePayload
    public ListarEmpleadosResponse listarEmpleados(@RequestPayload ListarEmpleadosRequest request) {
        return new ListarEmpleadosResponse(empleadoService.listar());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cambiarClaveRequest")
    @ResponsePayload
    public CambiarClaveResponse cambiarClave(@RequestPayload CambiarClaveRequest request) {
        RespuestaDTO respuesta = empleadoService.cambiarClave(request.getCodigo(), request.getClaveActual(),
                request.getClaveNueva());
        return new CambiarClaveResponse(respuesta);
    }
}
