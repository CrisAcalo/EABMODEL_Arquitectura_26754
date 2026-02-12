package ec.edu.monster.ms_transacciones.ws;

import ec.edu.monster.ms_transacciones.dtos.*;
import ec.edu.monster.ms_transacciones.models.Movimiento;
import ec.edu.monster.ms_transacciones.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class TransaccionEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-transacciones/ws";

    @Autowired
    private TransaccionService transaccionService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "realizarDepositoRequest")
    @ResponsePayload
    public RealizarDepositoResponse realizarDeposito(@RequestPayload RealizarDepositoRequest request) {
        RespuestaDTO respuesta = transaccionService.realizarDeposito(
                request.getCodigoCuenta(),
                request.getClaveCuenta(),
                request.getImporte(),
                request.getCodigoEmpleado());

        RealizarDepositoResponse response = new RealizarDepositoResponse();
        response.setResult(respuesta);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "realizarRetiroRequest")
    @ResponsePayload
    public RealizarRetiroResponse realizarRetiro(@RequestPayload RealizarRetiroRequest request) {
        RespuestaDTO respuesta = transaccionService.realizarRetiro(
                request.getCodigoCuenta(),
                request.getClaveCuenta(),
                request.getImporte(),
                request.getCodigoEmpleado());

        RealizarRetiroResponse response = new RealizarRetiroResponse();
        response.setResult(respuesta);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "realizarTransferenciaRequest")
    @ResponsePayload
    public RealizarTransferenciaResponse realizarTransferencia(
            @RequestPayload RealizarTransferenciaRequest request) {
        RespuestaDTO respuesta = transaccionService.realizarTransferencia(
                request.getCuentaOrigen(),
                request.getCuentaDestino(),
                request.getClaveCuentaOrigen(),
                request.getImporte(),
                request.getCodigoEmpleado());

        RealizarTransferenciaResponse response = new RealizarTransferenciaResponse();
        response.setResult(respuesta);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarMovimientosRequest")
    @ResponsePayload
    public ListarMovimientosResponse listarMovimientos(
            @RequestPayload ListarMovimientosRequest request) {
        List<Movimiento> movimientos = transaccionService.listarMovimientos(
                request.getCodigoCuenta(),
                request.getFechaInicio(),
                request.getFechaFin());

        ListarMovimientosResponse response = new ListarMovimientosResponse();
        response.setMovimientos(movimientos);
        return response;
    }
}
