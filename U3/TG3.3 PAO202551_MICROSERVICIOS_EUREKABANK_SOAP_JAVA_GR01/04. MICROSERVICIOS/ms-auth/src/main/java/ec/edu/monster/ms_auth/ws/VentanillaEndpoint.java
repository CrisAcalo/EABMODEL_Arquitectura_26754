package ec.edu.monster.ms_auth.ws;

import ec.edu.monster.ms_auth.dtos.*;
import ec.edu.monster.ms_auth.models.Ventanilla;
import ec.edu.monster.ms_auth.services.VentanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.List;

@Endpoint
public class VentanillaEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-auth/ws";

    @Autowired
    private VentanillaService ventanillaService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrarVentanillaRequest")
    @ResponsePayload
    public RegistrarVentanillaResponse registrarVentanilla(@RequestPayload RegistrarVentanillaRequest request) {
        if (request.getVentanilla() == null) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("El objeto ventanilla es obligatorio");
            error.setCodigoError("VEN000");
            return new RegistrarVentanillaResponse(error);
        }
        RespuestaDTO respuesta = ventanillaService.registrar(request.getVentanilla());
        return new RegistrarVentanillaResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "actualizarVentanillaRequest")
    @ResponsePayload
    public ActualizarVentanillaResponse actualizarVentanilla(@RequestPayload ActualizarVentanillaRequest request) {
        if (request.getVentanilla() == null) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("El objeto ventanilla es obligatorio");
            error.setCodigoError("VEN000");
            return new ActualizarVentanillaResponse(error);
        }
        RespuestaDTO respuesta = ventanillaService.actualizar(request.getVentanilla());
        return new ActualizarVentanillaResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarVentanillaRequest")
    @ResponsePayload
    public EliminarVentanillaResponse eliminarVentanilla(@RequestPayload EliminarVentanillaRequest request) {
        RespuestaDTO respuesta = ventanillaService.eliminar(request.getCodigo());
        return new EliminarVentanillaResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerVentanillaRequest")
    @ResponsePayload
    public ObtenerVentanillaResponse obtenerVentanilla(@RequestPayload ObtenerVentanillaRequest request) {
        RespuestaDTO respuesta = ventanillaService.obtener(request.getCodigo());
        return new ObtenerVentanillaResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarVentanillasRequest")
    @ResponsePayload
    public ListarVentanillasResponse listarVentanillas(@RequestPayload ListarVentanillasRequest request) {
        List<Ventanilla> ventanillas = ventanillaService.listar();
        ListarVentanillasResponse response = new ListarVentanillasResponse();
        response.getVentanillas().addAll(ventanillas);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarVentanillasActivasRequest")
    @ResponsePayload
    public ListarVentanillasActivasResponse listarVentanillasActivas(
            @RequestPayload ListarVentanillasActivasRequest request) {
        List<Ventanilla> ventanillas = ventanillaService.listarActivas();
        ListarVentanillasActivasResponse response = new ListarVentanillasActivasResponse();
        response.getVentanillas().addAll(ventanillas);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "asignarEmpleadoRequest")
    @ResponsePayload
    public AsignarEmpleadoResponse asignarEmpleado(@RequestPayload AsignarEmpleadoRequest request) {
        RespuestaDTO respuesta = ventanillaService.asignarEmpleado(request.getCodigoVentanilla(),
                request.getCodigoEmpleado());
        return new AsignarEmpleadoResponse(respuesta);
    }
}
