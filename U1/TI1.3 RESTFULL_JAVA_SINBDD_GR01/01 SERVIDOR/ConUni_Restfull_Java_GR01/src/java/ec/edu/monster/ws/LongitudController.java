/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ec.edu.monster.ws;

import ec.edu.monster.constants.ConversionConstants.Longitud;
import ec.edu.monster.models.ConversionRequest;
import ec.edu.monster.models.ConversionResultModel;
import ec.edu.monster.services.LongitudService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service para conversiones de longitud
 * Endpoints disponibles (todos POST con JSON):
 * - /api/longitud - Conversión general (basada en unidadOrigen y unidadDestino)
 *
 * Unidades soportadas: Milla, Metro, Pulgada
 *
 * Ejemplo de request:
 * {
 *   "valor": "100",
 *   "unidadOrigen": "Metro",
 *   "unidadDestino": "Milla"
 * }
 *
 * @author jeffe
 */
@Path("longitud")
public class LongitudController {

    @Inject
    private LongitudService longitudService;

    /**
     * Endpoint POST para convertir entre unidades de longitud
     * @param request ConversionRequest con valor, unidadOrigen y unidadDestino
     * @return Response con ConversionResultModel en JSON
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response convertir(ConversionRequest request) {
        ConversionResultModel resultado;

        String origen = request.getUnidadOrigen();
        String destino = request.getUnidadDestino();
        String valor = request.getValor();

        // Determinar qué conversión realizar basándose en origen y destino
        if (Longitud.MILLA.equalsIgnoreCase(origen) && Longitud.METRO.equalsIgnoreCase(destino)) {
            resultado = longitudService.convertirMillaAMetro(valor);
        } else if (Longitud.METRO.equalsIgnoreCase(origen) && Longitud.MILLA.equalsIgnoreCase(destino)) {
            resultado = longitudService.convertirMetroAMilla(valor);
        } else if (Longitud.MILLA.equalsIgnoreCase(origen) && Longitud.PULGADA.equalsIgnoreCase(destino)) {
            resultado = longitudService.convertirMillaAPulgada(valor);
        } else if (Longitud.PULGADA.equalsIgnoreCase(origen) && Longitud.MILLA.equalsIgnoreCase(destino)) {
            resultado = longitudService.convertirPulgadaAMilla(valor);
        } else if (Longitud.METRO.equalsIgnoreCase(origen) && Longitud.PULGADA.equalsIgnoreCase(destino)) {
            resultado = longitudService.convertirMetroAPulgada(valor);
        } else if (Longitud.PULGADA.equalsIgnoreCase(origen) && Longitud.METRO.equalsIgnoreCase(destino)) {
            resultado = longitudService.convertirPulgadaAMetro(valor);
        } else {
            // Conversión no soportada
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Conversión no soportada. Unidades válidas: Milla, Metro, Pulgada\"}")
                .build();
        }

        // Retornar respuesta según el resultado
        if (resultado.isExitoso()) {
            return Response.ok(resultado).build();
        } else {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(resultado)
                .build();
        }
    }
}
