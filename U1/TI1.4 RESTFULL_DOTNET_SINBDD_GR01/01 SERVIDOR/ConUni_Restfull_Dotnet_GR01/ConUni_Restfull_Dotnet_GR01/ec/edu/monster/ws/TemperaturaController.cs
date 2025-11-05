using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.validators;

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

        // Unidades válidas para temperatura
        private readonly HashSet<string> _unidadesValidas = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
  {
            "celsius", "fahrenheit", "kelvin"
        };

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
        /// "valor": "25",
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

            // Primero validar que ambas unidades sean soportadas
            if (!_unidadesValidas.Contains(origen) || !_unidadesValidas.Contains(destino))
            {
                return Ok(ConversionResultModel.Fallo(
               new ConversionErrorModel(
               ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
                       $"Conversión de {request.UnidadOrigen} a {request.UnidadDestino} no está soportada",
                     ErrorConstants.TIPO_CONVERSION,
           TryParseDouble(request.Valor),
                request.UnidadOrigen,
                    $"Las unidades soportadas son: {TemperaturaConstants.CELSIUS}, {TemperaturaConstants.FAHRENHEIT}, {TemperaturaConstants.KELVIN}"
                      )
                   ));
            }

            // Determinar qué conversión realizar basándose en origen y destino
            var resultado = (origen, destino) switch
            {
                ("celsius", "fahrenheit") => _temperaturaService.ConvertirCelsiusAFahrenheit(request.Valor),
                ("fahrenheit", "celsius") => _temperaturaService.ConvertirFahrenheitACelsius(request.Valor),
                ("celsius", "kelvin") => _temperaturaService.ConvertirCelsiusAKelvin(request.Valor),
                ("kelvin", "celsius") => _temperaturaService.ConvertirKelvinACelsius(request.Valor),
                ("fahrenheit", "kelvin") => _temperaturaService.ConvertirFahrenheitAKelvin(request.Valor),
                ("kelvin", "fahrenheit") => _temperaturaService.ConvertirKelvinAFahrenheit(request.Valor),

                // Conversión de una unidad a sí misma (solo llega aquí si ambas unidades son válidas)
                _ when origen == destino => HandleSameUnitConversion(request.Valor, request.UnidadOrigen, request.UnidadDestino, origen),

                // Esta línea nunca debería ejecutarse debido a la validación anterior
                _ => ConversionResultModel.Fallo(
          new ConversionErrorModel(
                ErrorConstants.ERROR_CONVERSION_TEMPERATURA,
             $"Conversión de {request.UnidadOrigen} a {request.UnidadDestino} no está soportada",
        ErrorConstants.TIPO_CONVERSION,
      TryParseDouble(request.Valor),
     request.UnidadOrigen,
                $"Las unidades soportadas son: {TemperaturaConstants.CELSIUS}, {TemperaturaConstants.FAHRENHEIT}, {TemperaturaConstants.KELVIN}"
        )
      )
            };

            return Ok(resultado);
        }

        /// <summary>
        /// Maneja conversiones donde origen y destino son la misma unidad
        /// </summary>
        private ConversionResultModel HandleSameUnitConversion(string valorString, string unidadOrigen, string unidadDestino, string unidadNormalizada)
        {
            // Validar según el tipo de temperatura
            ConversionErrorModel? error = unidadNormalizada switch
            {
                "celsius" => TemperaturaValidator.ValidarStringTemperaturaCelsius(valorString, out double _),
                "fahrenheit" => TemperaturaValidator.ValidarStringTemperaturaFahrenheit(valorString, out double _),
                "kelvin" => TemperaturaValidator.ValidarStringTemperaturaKelvin(valorString, out double _),
                _ => BaseValidator.ValidarStringPositivo(valorString, unidadOrigen, out double _)
            };

            if (error != null)
                return ConversionResultModel.Fallo(error);

            // Convertir valor validado
            double valor = double.Parse(valorString);

            return ConversionResultModel.Exito(
                  new UnidadConversionModel(
            valor,
              valor,
             CapitalizarUnidad(unidadOrigen),
                   CapitalizarUnidad(unidadDestino),
              "Temperatura",
              1.0
                 )
                  );
        }

        /// <summary>
        /// Intenta convertir string a double para casos de error
        /// </summary>
        private double? TryParseDouble(string value)
        {
            if (double.TryParse(value, out double result))
                return result;
            return null;
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
