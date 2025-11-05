using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.validators;

namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.ws
{
    /// <summary>
 /// Controlador REST para conversiones de longitud
        /// Soporta conversiones entre: Milla, Metro, Pulgada
      /// </summary>
    [Route("api/[controller]")]
    [ApiController]
    [Produces("application/json")]
    public class LongitudController : ControllerBase
  {
        private readonly LongitudService _longitudService;

        public LongitudController(LongitudService longitudService)
        {
       _longitudService = longitudService;
        }

        /// <summary>
     /// Convierte valores entre diferentes unidades de longitud
        /// </summary>
   /// <param name="request">Solicitud de conversión con valor, unidad origen y unidad destino</param>
      /// <returns>Resultado de la conversión con valores exactos y redondeados</returns>
        /// <response code="200">Conversión exitosa o error de validación</response>
        /// <remarks>
  /// Unidades soportadas: Milla, Metro, Pulgada (case-insensitive)
      /// 
    /// Ejemplo de solicitud:
      /// 
   ///     POST /api/Longitud/convertir
     ///   {
       ///        "valor": "10",
  ///     "unidadOrigen": "Milla",
///        "unidadDestino": "Metro"
      /// }
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
      ("milla", "metro") => _longitudService.ConvertirMillaAMetro(request.Valor),
    ("metro", "milla") => _longitudService.ConvertirMetroAMilla(request.Valor),
        ("milla", "pulgada") => _longitudService.ConvertirMillaAPulgada(request.Valor),
       ("pulgada", "milla") => _longitudService.ConvertirPulgadaAMilla(request.Valor),
      ("metro", "pulgada") => _longitudService.ConvertirMetroAPulgada(request.Valor),
    ("pulgada", "metro") => _longitudService.ConvertirPulgadaAMetro(request.Valor),

   // Conversión de una unidad a sí misma
   _ when origen == destino => HandleSameUnitConversion(request.Valor, request.UnidadOrigen, request.UnidadDestino),

     // Conversión no soportada
      _ => ConversionResultModel.Fallo(
      new ConversionErrorModel(
   ErrorConstants.ERROR_CONVERSION_LONGITUD,
    $"Conversión de {request.UnidadOrigen} a {request.UnidadDestino} no está soportada",
    ErrorConstants.TIPO_CONVERSION,
  TryParseDouble(request.Valor),
        request.UnidadOrigen,
         $"Las unidades soportadas son: {LongitudConstants.MILLA}, {LongitudConstants.METRO}, {LongitudConstants.PULGADA}"
    )
       )
            };

  return Ok(resultado);
        }

        /// <summary>
 /// Maneja conversiones donde origen y destino son la misma unidad
        /// </summary>
        private ConversionResultModel HandleSameUnitConversion(string valorString, string unidadOrigen, string unidadDestino)
  {
 // Primero validar que el valor sea válido
    var error = BaseValidator.ValidarStringPositivo(valorString, unidadOrigen, out double valor);
            if (error != null)
     return ConversionResultModel.Fallo(error);

     return ConversionResultModel.Exito(
   new UnidadConversionModel(
    valor,
   valor,
         CapitalizarUnidad(unidadOrigen),
CapitalizarUnidad(unidadDestino),
     "Longitud",
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
