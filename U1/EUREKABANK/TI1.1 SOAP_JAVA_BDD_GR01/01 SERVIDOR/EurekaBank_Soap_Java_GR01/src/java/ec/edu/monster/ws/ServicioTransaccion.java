package ec.edu.monster.ws;

import ec.edu.monster.models.dto.RespuestaDTO;
import ec.edu.monster.models.dto.TransaccionDTO;
import ec.edu.monster.models.dto.TransferenciaDTO;
import ec.edu.monster.services.TransaccionService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * Web Service SOAP para transacciones bancarias
 */
@WebService(serviceName = "ServicioTransaccion")
public class ServicioTransaccion {

    private final TransaccionService service;

    public ServicioTransaccion() {
        this.service = new TransaccionService();
    }

    /**
     * Realiza un depósito en una cuenta
     *
     * @param datos Datos de la transacción de depósito
     * @return RespuestaDTO con el resultado del depósito
     */
    @WebMethod(operationName = "realizarDeposito")
    public RespuestaDTO realizarDeposito(
            @WebParam(name = "datos") TransaccionDTO datos) {
        return service.realizarDeposito(datos);
    }

    /**
     * Realiza un retiro de una cuenta
     * IMPORTANTE: Esta operación puede generar múltiples movimientos (retiro + ITF + cargo)
     *
     * @param datos Datos de la transacción de retiro
     * @return RespuestaDTO con el resultado del retiro
     */
    @WebMethod(operationName = "realizarRetiro")
    public RespuestaDTO realizarRetiro(
            @WebParam(name = "datos") TransaccionDTO datos) {
        return service.realizarRetiro(datos);
    }

    /**
     * Realiza una transferencia entre dos cuentas
     * IMPORTANTE: Esta operación afecta dos cuentas en una transacción atómica
     *
     * @param datos Datos de la transferencia
     * @return RespuestaDTO con el resultado de la transferencia
     */
    @WebMethod(operationName = "realizarTransferencia")
    public RespuestaDTO realizarTransferencia(
            @WebParam(name = "datos") TransferenciaDTO datos) {
        return service.realizarTransferencia(datos);
    }
}
