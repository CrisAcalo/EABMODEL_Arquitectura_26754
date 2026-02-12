package ec.edu.monster.ms_cuentas.ws;

import ec.edu.monster.ms_cuentas.dtos.*;
import ec.edu.monster.ms_cuentas.dtos.RespuestaDTO;
import ec.edu.monster.ms_cuentas.models.Cliente;
import ec.edu.monster.ms_cuentas.services.ClienteService;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ClienteEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-cuentas/ws";

    @Autowired
    private ClienteService clienteService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarClientesRequest")
    @ResponsePayload
    public JAXBElement<ListarClientesResponse> listarClientes(@RequestPayload ListarClientesRequest request) {
        List<Cliente> clientes = clienteService.listarTodos();
        ListarClientesResponse response = new ListarClientesResponse();
        response.setClientes(clientes);
        return new JAXBElement<>(new QName(NAMESPACE_URI, "listarClientesResponse"), ListarClientesResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerClienteRequest")
    @ResponsePayload
    public JAXBElement<ObtenerClienteResponse> obtenerCliente(@RequestPayload ObtenerClienteRequest request) {
        ObtenerClienteResponse response = new ObtenerClienteResponse();
        try {
            Cliente cliente = clienteService.obtenerPorCodigo(request.getCodigo()).orElse(null);
            if (cliente != null) {
                response.setRespuesta(RespuestaDTO.exito("Cliente encontrado", cliente));
            } else {
                response.setRespuesta(RespuestaDTO.error("Cliente no encontrado", "CLI001"));
            }
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error: " + e.getMessage(), "CLI000"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "obtenerClienteResponse"), ObtenerClienteResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerClientePorDniRequest")
    @ResponsePayload
    public JAXBElement<ObtenerClientePorDniResponse> obtenerClientePorDni(
            @RequestPayload ObtenerClientePorDniRequest request) {
        ObtenerClientePorDniResponse response = new ObtenerClientePorDniResponse();
        try {
            Cliente cliente = clienteService.obtenerPorDni(request.getDni()).orElse(null);
            if (cliente != null) {
                response.setRespuesta(RespuestaDTO.exito("Cliente encontrado", cliente));
            } else {
                response.setRespuesta(
                        RespuestaDTO.error("Cliente no encontrado con DNI: " + request.getDni(), "CLI002"));
            }
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error: " + e.getMessage(), "CLI000"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "obtenerClientePorDniResponse"),
                ObtenerClientePorDniResponse.class, response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrarClienteRequest")
    @ResponsePayload
    public JAXBElement<RegistrarClienteResponse> registrarCliente(@RequestPayload RegistrarClienteRequest request) {
        RegistrarClienteResponse response = new RegistrarClienteResponse();
        try {
            Cliente registrado = clienteService.registrar(request.getCliente());
            response.setRespuesta(RespuestaDTO.exito("Cliente registrado correctamente", registrado));
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error al registrar cliente: " + e.getMessage(), "CLI006"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "registrarClienteResponse"), RegistrarClienteResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "actualizarClienteRequest")
    @ResponsePayload
    public JAXBElement<ActualizarClienteResponse> actualizarCliente(@RequestPayload ActualizarClienteRequest request) {
        ActualizarClienteResponse response = new ActualizarClienteResponse();
        try {
            Cliente actualizado = clienteService.actualizar(request.getCliente());
            response.setRespuesta(RespuestaDTO.exito("Cliente actualizado correctamente", actualizado));
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error al actualizar cliente: " + e.getMessage(), "CLI007"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "actualizarClienteResponse"), ActualizarClienteResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarClienteRequest")
    @ResponsePayload
    public JAXBElement<EliminarClienteResponse> eliminarCliente(@RequestPayload EliminarClienteRequest request) {
        EliminarClienteResponse response = new EliminarClienteResponse();
        try {
            clienteService.eliminar(request.getCodigo());
            response.setRespuesta(RespuestaDTO.exito("Cliente eliminado correctamente", null));
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error al eliminar cliente: " + e.getMessage(), "CLI008"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "eliminarClienteResponse"), EliminarClienteResponse.class,
                response);
    }
}
