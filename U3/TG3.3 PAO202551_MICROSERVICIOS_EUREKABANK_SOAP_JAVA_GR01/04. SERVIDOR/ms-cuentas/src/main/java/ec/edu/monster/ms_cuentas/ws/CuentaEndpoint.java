package ec.edu.monster.ms_cuentas.ws;

import ec.edu.monster.ms_cuentas.dtos.*;
import ec.edu.monster.ms_cuentas.dtos.RespuestaDTO;
import ec.edu.monster.ms_cuentas.models.Cuenta;
import ec.edu.monster.ms_cuentas.services.CuentaService;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigDecimal;
import java.util.List;

@Endpoint
public class CuentaEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-cuentas/ws";

    @Autowired
    private CuentaService cuentaService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarCuentasActivasRequest")
    @ResponsePayload
    public JAXBElement<ListarCuentasActivasResponse> listarCuentasActivas(
            @RequestPayload ListarCuentasActivasRequest request) {
        List<Cuenta> cuentas = cuentaService.listarActivas();
        ListarCuentasActivasResponse response = new ListarCuentasActivasResponse();
        response.setCuentas(cuentas);
        return new JAXBElement<>(new QName(NAMESPACE_URI, "listarCuentasActivasResponse"),
                ListarCuentasActivasResponse.class, response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerCuentaRequest")
    @ResponsePayload
    public JAXBElement<ObtenerCuentaResponse> obtenerCuenta(@RequestPayload ObtenerCuentaRequest request) {
        ObtenerCuentaResponse response = new ObtenerCuentaResponse();
        try {
            Cuenta cuenta = cuentaService.obtenerPorCodigo(request.getCodigo()).orElse(null);
            if (cuenta != null) {
                response.setRespuesta(RespuestaDTO.exito("Cuenta encontrada", cuenta));
            } else {
                response.setRespuesta(RespuestaDTO.error("Cuenta no encontrada", "CTA001"));
            }
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error: " + e.getMessage(), "CTA000"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "obtenerCuentaResponse"), ObtenerCuentaResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerSaldoRequest")
    @ResponsePayload
    public JAXBElement<ObtenerSaldoResponse> obtenerSaldo(@RequestPayload ObtenerSaldoRequest request) {
        ObtenerSaldoResponse response = new ObtenerSaldoResponse();
        try {
            BigDecimal saldo = cuentaService.obtenerSaldo(request.getCodigoCuenta());
            if (saldo != null) {
                response.setRespuesta(RespuestaDTO.exito("Saldo obtenido", saldo));
            } else {
                response.setRespuesta(RespuestaDTO.error("Cuenta no encontrada", "CTA001"));
            }
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error: " + e.getMessage(), "CTA000"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "obtenerSaldoResponse"), ObtenerSaldoResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "abrirCuentaRequest")
    @ResponsePayload
    public JAXBElement<AbrirCuentaResponse> abrirCuenta(@RequestPayload AbrirCuentaRequest request) {
        AbrirCuentaResponse response = new AbrirCuentaResponse();
        try {
            Cuenta cuenta = cuentaService.abrirCuenta(request.getCuenta());
            response.setRespuesta(RespuestaDTO.exito("Cuenta abierta correctamente", cuenta));
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error al abrir cuenta: " + e.getMessage(), "CTA002"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "abrirCuentaResponse"), AbrirCuentaResponse.class, response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cancelarCuentaRequest")
    @ResponsePayload
    public JAXBElement<CancelarCuentaResponse> cancelarCuenta(@RequestPayload CancelarCuentaRequest request) {
        CancelarCuentaResponse response = new CancelarCuentaResponse();
        try {
            cuentaService.cancelarCuenta(request.getCodigoCuenta());
            response.setRespuesta(RespuestaDTO.exito("Cuenta cancelada correctamente", null));
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error al cancelar cuenta: " + e.getMessage(), "CTA003"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "cancelarCuentaResponse"), CancelarCuentaResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "validarClaveRequest")
    @ResponsePayload
    public JAXBElement<ValidarClaveResponse> validarClave(@RequestPayload ValidarClaveRequest request) {
        ValidarClaveResponse response = new ValidarClaveResponse();
        try {
            boolean valida = cuentaService.validarClave(request.getCodigoCuenta(), request.getClave());
            if (valida) {
                response.setRespuesta(RespuestaDTO.exito("Clave válida", true));
            } else {
                response.setRespuesta(RespuestaDTO.error("Clave inválida", "CTA004"));
            }
        } catch (Exception e) {
            response.setRespuesta(RespuestaDTO.error("Error al validar clave: " + e.getMessage(), "CTA000"));
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "validarClaveResponse"), ValidarClaveResponse.class,
                response);
    }
}
