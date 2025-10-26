﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;

namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.ws
{
    /// <summary>
    /// Controlador REST para conversiones de temperatura
    /// Soporta conversiones entre: Celsius, Fahrenheit, Kelvin
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    [Produces("application/json")]
    public class TemperaturaController : ControllerBase
    {
        private readonly TemperaturaService _temperaturaService;

        public TemperaturaController(TemperaturaService temperaturaService)
        {
            _temperaturaService = temperaturaService;
        }

        /// <summary>
        /// Convierte valores entre diferentes unidades de temperatura
        /// </summary>
        /// <param name="request">Solicitud de conversión con valor, unidad origen y unidad destino</param>
        /// <returns>Resultado de la conversión con valores exactos y redondeados</returns>
        /// <response code="200">Conversión exitosa o error de validación</response>
        /// <remarks>
        /// Unidades soportadas: Celsius, Fahrenheit, Kelvin (case-insensitive)
        /// 
        /// **IMPORTANTE:** Las temperaturas están limitadas por el cero absoluto:
        /// - Celsius: -273.15°C
        /// - Fahrenheit: -459.67°F
        /// - Kelvin: 0K
        /// 
        /// Ejemplo de solicitud:
        /// 
        ///     POST /api/Temperatura/convertir
        ///     {
        ///        "valor": 25,
        ///        "unidadOrigen": "Celsius",
        ///        "unidadDestino": "Fahrenheit"
        ///     }
        /// 
        /// </remarks>
        [HttpPost("convertir")]
        [ProducesResponseType(typeof(ConversionResultModel), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ConversionResultModel), StatusCodes.Status400BadRequest)]
        public ActionResult<ConversionResultModel> Convertir([FromBody] ConversionRequest request)
        {
            // Normalizar las unidades a minúsculas para comparación
            var origen = request.UnidadOrigen.Trim().ToLower();
            var destino = request.UnidadDestino.Trim().ToLower();

            // Determinar qué conversión realizar basándose en origen y destino
            var resultado = (origen, destino) switch
            {
                ("celsius", "fahrenheit") => _temperaturaService.ConvertirCelsiusAFahrenheit(request.Valor),
                ("fahrenheit", "celsius") => _temperaturaService.ConvertirFahrenheitACelsius(request.Valor),
                ("celsius", "kelvin") => _temperaturaService.ConvertirCelsiusAKelvin(request.Valor),
                ("kelvin", "celsius") => _temperaturaService.ConvertirKelvinACelsius(request.Valor),
                ("fahrenheit", "kelvin") => _temperaturaService.ConvertirFahrenheitAKelvin(request.Valor),
                ("kelvin", "fahrenheit") => _temperaturaService.ConvertirKelvinAFahrenheit(request.Valor),

                // Conversión de una unidad a sí misma
                _ when origen == destino => ConversionResultModel.Exito(
                    new UnidadConversionModel(
                        request.Valor,
                        request.Valor,
                        CapitalizarUnidad(request.UnidadOrigen),
                        CapitalizarUnidad(request.UnidadDestino),
                        "Temperatura",
                        1.0
                    )
                ),

                // Conversión no soportada
                _ => ConversionResultModel.Fallo(
                    new ConversionErrorModel(
                        ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                        $"Conversión de {request.UnidadOrigen} a {request.UnidadDestino} no está soportada",
                        ErrorConstants.TIPO_CONVERSION,
                        request.Valor,
                        request.UnidadOrigen,
                        $"Las unidades soportadas son: {TemperaturaConstants.CELSIUS}, {TemperaturaConstants.FAHRENHEIT}, {TemperaturaConstants.KELVIN}"
                    )
                )
            };

            return Ok(resultado);
        }

        /// <summary>
        /// Capitaliza el nombre de la unidad (Primera letra en mayúscula)
        /// </summary>
        private string CapitalizarUnidad(string unidad)
        {
            if (string.IsNullOrWhiteSpace(unidad))
                return string.Empty;

            unidad = unidad.Trim().ToLower();
            return char.ToUpper(unidad[0]) + unidad.Substring(1);
        }
    }
}
