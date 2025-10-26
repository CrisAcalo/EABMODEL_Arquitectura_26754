using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;

namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.ws
{
    /// <summary>
    /// Controlador REST para conversiones de masa
    /// Soporta conversiones entre: Kilogramo, Quintal, Libra
    /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    [Produces("application/json")]
    public class MasaController : ControllerBase
    {
        private readonly MasaService _masaService;

        public MasaController(MasaService masaService)
        {
            _masaService = masaService;
        }

        /// <summary>
        /// Convierte valores entre diferentes unidades de masa
        /// </summary>
        /// <param name="request">Solicitud de conversión con valor, unidad origen y unidad destino</param>
        /// <returns>Resultado de la conversión con valores exactos y redondeados</returns>
        /// <response code="200">Conversión exitosa o error de validación</response>
        /// <remarks>
        /// Unidades soportadas: Kilogramo, Quintal, Libra (case-insensitive)
        ///
        /// Ejemplo de solicitud:
        ///
        ///     POST /api/Masa/convertir
        ///     {
        ///        "valor": 100,
        ///        "unidadOrigen": "Kilogramo",
        ///        "unidadDestino": "Libra"
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
                ("kilogramo", "quintal") => _masaService.ConvertirKilogramoAQuintal(request.Valor),
                ("quintal", "kilogramo") => _masaService.ConvertirQuintalAKilogramo(request.Valor),
                ("kilogramo", "libra") => _masaService.ConvertirKilogramoALibra(request.Valor),
                ("libra", "kilogramo") => _masaService.ConvertirLibraAKilogramo(request.Valor),
                ("quintal", "libra") => _masaService.ConvertirQuintalALibra(request.Valor),
                ("libra", "quintal") => _masaService.ConvertirLibraAQuintal(request.Valor),

                // Conversión de una unidad a sí misma
                _ when origen == destino => ConversionResultModel.Exito(
                    new UnidadConversionModel(
                        request.Valor,
                        request.Valor,
                        CapitalizarUnidad(request.UnidadOrigen),
                        CapitalizarUnidad(request.UnidadDestino),
                        "Masa",
                        1.0
                    )
                ),

                // Conversión no soportada
                _ => ConversionResultModel.Fallo(
                    new ConversionErrorModel(
                        ErrorConstants.ERROR_CONVERSION_MASA,
                        $"Conversión de {request.UnidadOrigen} a {request.UnidadDestino} no está soportada",
                        ErrorConstants.TIPO_CONVERSION,
                        request.Valor,
                        request.UnidadOrigen,
                        $"Las unidades soportadas son: {MasaConstants.KILOGRAMO}, {MasaConstants.QUINTAL}, {MasaConstants.LIBRA}"
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