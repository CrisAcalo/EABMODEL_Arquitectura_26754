namespace ConUni_CliEsc_GR01.ec.edu.monster.models;

/// <summary>
/// Modelo de respuesta de la API de conversión
/// </summary>
public class ConversionResultModel
{
    /// <summary>
    /// Indica si la conversión fue exitosa
    /// </summary>
    public bool Exitoso { get; set; }

    /// <summary>
    /// Resultado de la conversión (presente si Exitoso = true)
    /// </summary>
    public UnidadConversionModel? Resultado { get; set; }

    /// <summary>
    /// Información del error (presente si Exitoso = false)
    /// </summary>
    public ConversionErrorModel? Error { get; set; }
}
