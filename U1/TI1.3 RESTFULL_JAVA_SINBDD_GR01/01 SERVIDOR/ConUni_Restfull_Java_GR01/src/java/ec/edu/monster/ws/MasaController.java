/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ec.edu.monster.ws;

import ec.edu.monster.constants.ConversionConstants.Masa;
import ec.edu.monster.models.ConversionRequest;
import ec.edu.monster.models.ConversionResultModel;
import ec.edu.monster.services.MasaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service para conversiones de masa
 * Endpoints disponibles (todos POST con JSON):
 * - /api/masa - Conversión general (basada en unidadOrigen y unidadDestino)
 *
 * Unidades soportadas: Kilogramo, Quintal, Libra
 *
 * Ejemplo de request:
 * {
 *   "valor": "100",
 *   "unidadOrigen": "Kilogramo",
 *   "unidadDestino": "Libra"
 * }
 *
 * @author jeffe
 */
@Path("masa")
public class MasaController {

    @Inject
    private MasaService masaService;

    /**
     * Endpoint POST para convertir entre unidades de masa
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
        if (Masa.KILOGRAMO.equalsIgnoreCase(origen) && Masa.QUINTAL.equalsIgnoreCase(destino)) {
            resultado = masaService.convertirKilogramoAQuintal(valor);
        } else if (Masa.QUINTAL.equalsIgnoreCase(origen) && Masa.KILOGRAMO.equalsIgnoreCase(destino)) {
            resultado = masaService.convertirQuintalAKilogramo(valor);
        } else if (Masa.KILOGRAMO.equalsIgnoreCase(origen) && Masa.LIBRA.equalsIgnoreCase(destino)) {
            resultado = masaService.convertirKilogramoALibra(valor);
        } else if (Masa.LIBRA.equalsIgnoreCase(origen) && Masa.KILOGRAMO.equalsIgnoreCase(destino)) {
            resultado = masaService.convertirLibraAKilogramo(valor);
        } else if (Masa.QUINTAL.equalsIgnoreCase(origen) && Masa.LIBRA.equalsIgnoreCase(destino)) {
            resultado = masaService.convertirQuintalALibra(valor);
        } else if (Masa.LIBRA.equalsIgnoreCase(origen) && Masa.QUINTAL.equalsIgnoreCase(destino)) {
            resultado = masaService.convertirLibraAQuintal(valor);
        } else {
            // Conversión no soportada
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Conversión no soportada. Unidades válidas: Kilogramo, Quintal, Libra\"}")
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
