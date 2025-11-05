namespace ConUni_CliEsc_GR01.ec.edu.monster.models;

/// <summary>
/// Modelo de error en la conversión
/// </summary>
public class ConversionErrorModel
{
    /// <summary>
    /// Código de error
    /// </summary>
    public string CodigoError { get; set; } = string.Empty;

    /// <summary>
    /// Mensaje descriptivo del error
    /// </summary>
    public string Mensaje { get; set; } = string.Empty;

    /// <summary>
    /// Tipo de error (Validacion, Conversion, Sistema)
    /// </summary>
    public string TipoError { get; set; } = string.Empty;

    /// <summary>
    /// Valor que causó el problema
    /// </summary>
    public double? ValorProblematico { get; set; }

    /// <summary>
    /// Unidad asociada al error
    /// </summary>
    public string Unidad { get; set; } = string.Empty;

    /// <summary>
    /// Fecha y hora en que ocurrió el error
    /// </summary>
    public DateTime FechaError { get; set; }

    /// <summary>
    /// Detalles adicionales del error
    /// </summary>
    public string Detalles { get; set; } = string.Empty;
}
