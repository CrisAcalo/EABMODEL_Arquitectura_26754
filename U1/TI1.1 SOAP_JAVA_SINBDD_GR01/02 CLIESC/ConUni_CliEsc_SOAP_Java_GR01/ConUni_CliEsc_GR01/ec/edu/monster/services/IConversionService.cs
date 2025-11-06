using ConUni_CliEsc_GR01.ec.edu.monster.models;

namespace ConUni_CliEsc_GR01.ec.edu.monster.services;

/// <summary>
/// Interfaz para servicios de conversión de unidades
/// Permite implementaciones REST y SOAP
/// </summary>
public interface IConversionService
{
    /// <summary>
    /// Convierte unidades de longitud
    /// </summary>
    /// <param name="request">Datos de la conversión</param>
    /// <returns>Resultado de la conversión</returns>
    Task<ConversionResultModel> ConvertirLongitudAsync(ConversionRequest request);

    /// <summary>
    /// Convierte unidades de masa
    /// </summary>
    /// <param name="request">Datos de la conversión</param>
    /// <returns>Resultado de la conversión</returns>
    Task<ConversionResultModel> ConvertirMasaAsync(ConversionRequest request);

    /// <summary>
    /// Convierte unidades de temperatura
    /// </summary>
    /// <param name="request">Datos de la conversión</param>
    /// <returns>Resultado de la conversión</returns>
    Task<ConversionResultModel> ConvertirTemperaturaAsync(ConversionRequest request);
}
