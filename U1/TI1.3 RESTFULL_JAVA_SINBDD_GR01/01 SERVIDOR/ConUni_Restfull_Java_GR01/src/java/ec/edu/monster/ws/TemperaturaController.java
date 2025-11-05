/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ec.edu.monster.ws;

import ec.edu.monster.constants.ConversionConstants.Temperatura;
import ec.edu.monster.models.ConversionRequest;
import ec.edu.monster.models.ConversionResultModel;
import ec.edu.monster.services.TemperaturaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Web Service para conversiones de temperatura
 * Endpoints disponibles (todos POST con JSON):
 * - /api/temperatura - Conversión general (basada en unidadOrigen y unidadDestino)
 *
 * Unidades soportadas: Celsius, Fahrenheit, Kelvin
 *
 * Ejemplo de request:
 * {
 *   "valor": "100",
 *   "unidadOrigen": "Celsius",
 *   "unidadDestino": "Fahrenheit"
 * }
 *
 * @author jeffe
 */
@Path("temperatura")
public class TemperaturaController {

    @Inject
    private TemperaturaService temperaturaService;

    /**
     * Endpoint POST para convertir entre unidades de temperatura
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
        if (Temperatura.CELSIUS.equalsIgnoreCase(origen) && Temperatura.FAHRENHEIT.equalsIgnoreCase(destino)) {
            resultado = temperaturaService.convertirCelsiusAFahrenheit(valor);
        } else if (Temperatura.FAHRENHEIT.equalsIgnoreCase(origen) && Temperatura.CELSIUS.equalsIgnoreCase(destino)) {
            resultado = temperaturaService.convertirFahrenheitACelsius(valor);
        } else if (Temperatura.CELSIUS.equalsIgnoreCase(origen) && Temperatura.KELVIN.equalsIgnoreCase(destino)) {
            resultado = temperaturaService.convertirCelsiusAKelvin(valor);
        } else if (Temperatura.KELVIN.equalsIgnoreCase(origen) && Temperatura.CELSIUS.equalsIgnoreCase(destino)) {
            resultado = temperaturaService.convertirKelvinACelsius(valor);
        } else if (Temperatura.FAHRENHEIT.equalsIgnoreCase(origen) && Temperatura.KELVIN.equalsIgnoreCase(destino)) {
            resultado = temperaturaService.convertirFahrenheitAKelvin(valor);
        } else if (Temperatura.KELVIN.equalsIgnoreCase(origen) && Temperatura.FAHRENHEIT.equalsIgnoreCase(destino)) {
            resultado = temperaturaService.convertirKelvinAFahrenheit(valor);
        } else {
            // Conversión no soportada
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"Conversión no soportada. Unidades válidas: Celsius, Fahrenheit, Kelvin\"}")
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
