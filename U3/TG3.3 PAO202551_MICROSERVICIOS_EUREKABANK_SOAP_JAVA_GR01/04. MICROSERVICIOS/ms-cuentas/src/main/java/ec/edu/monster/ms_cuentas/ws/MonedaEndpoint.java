package ec.edu.monster.ms_cuentas.ws;

import ec.edu.monster.ms_cuentas.dtos.*;
import ec.edu.monster.ms_cuentas.models.Moneda;
import ec.edu.monster.ms_cuentas.services.MonedaService;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class MonedaEndpoint {

    private static final String NAMESPACE_URI = "http://monster.edu.ec/ms-cuentas/ws";

    @Autowired
    private MonedaService monedaService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarMonedasRequest")
    @ResponsePayload
    public JAXBElement<ListarMonedasResponse> listarMonedas(@RequestPayload ListarMonedasRequest request) {
        List<Moneda> monedas = monedaService.listarTodas();
        ListarMonedasResponse response = new ListarMonedasResponse();
        response.setMonedas(monedas);
        return new JAXBElement<>(new QName(NAMESPACE_URI, "listarMonedasResponse"), ListarMonedasResponse.class,
                response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerMonedaRequest")
    @ResponsePayload
    public JAXBElement<ObtenerMonedaResponse> obtenerMoneda(@RequestPayload ObtenerMonedaRequest request) {
        ObtenerMonedaResponse response = new ObtenerMonedaResponse();
        try {
            Moneda moneda = monedaService.obtenerPorCodigo(request.getCodigo()).orElse(null);
            response.setMoneda(moneda);
        } catch (Exception e) {
            response.setMoneda(null);
        }
        return new JAXBElement<>(new QName(NAMESPACE_URI, "obtenerMonedaResponse"), ObtenerMonedaResponse.class,
                response);
    }
}
