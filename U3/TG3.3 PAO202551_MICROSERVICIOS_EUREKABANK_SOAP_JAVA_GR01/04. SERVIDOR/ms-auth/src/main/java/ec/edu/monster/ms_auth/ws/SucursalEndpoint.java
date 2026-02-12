package ec.edu.monster.ms_auth.ws;

import ec.edu.monster.ms_auth.dtos.*;
import ec.edu.monster.ms_auth.services.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SucursalEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-auth/ws";

    @Autowired
    private SucursalService sucursalService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrarSucursalRequest")
    @ResponsePayload
    public RegistrarSucursalResponse registrarSucursal(@RequestPayload RegistrarSucursalRequest request) {
        if (request.getSucursal() == null) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("El objeto sucursal es obligatorio");
            error.setCodigoError("SUC000");
            return new RegistrarSucursalResponse(error);
        }
        RespuestaDTO respuesta = sucursalService.registrar(request.getSucursal());
        return new RegistrarSucursalResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "actualizarSucursalRequest")
    @ResponsePayload
    public ActualizarSucursalResponse actualizarSucursal(@RequestPayload ActualizarSucursalRequest request) {
        if (request.getSucursal() == null) {
            RespuestaDTO error = new RespuestaDTO();
            error.setExitoso(false);
            error.setMensaje("El objeto sucursal es obligatorio");
            error.setCodigoError("SUC000");
            return new ActualizarSucursalResponse(error);
        }
        RespuestaDTO respuesta = sucursalService.actualizar(request.getSucursal());
        return new ActualizarSucursalResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarSucursalRequest")
    @ResponsePayload
    public EliminarSucursalResponse eliminarSucursal(@RequestPayload EliminarSucursalRequest request) {
        RespuestaDTO respuesta = sucursalService.eliminar(request.getCodigo());
        return new EliminarSucursalResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerSucursalRequest")
    @ResponsePayload
    public ObtenerSucursalResponse obtenerSucursal(@RequestPayload ObtenerSucursalRequest request) {
        RespuestaDTO respuesta = sucursalService.obtener(request.getCodigo());
        return new ObtenerSucursalResponse(respuesta);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarSucursalesRequest")
    @ResponsePayload
    public ListarSucursalesResponse listarSucursales(@RequestPayload ListarSucursalesRequest request) {
        return new ListarSucursalesResponse(sucursalService.listar());
    }
}
