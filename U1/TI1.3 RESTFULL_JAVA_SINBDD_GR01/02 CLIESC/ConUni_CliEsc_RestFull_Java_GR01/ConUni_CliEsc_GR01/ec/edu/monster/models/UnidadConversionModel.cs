namespace ConUni_CliEsc_GR01.ec.edu.monster.models;

/// <summary>
/// Modelo del resultado exitoso de una conversión
/// </summary>
public class UnidadConversionModel
{
    /// <summary>
    /// Valor original ingresado
    /// </summary>
    public double ValorOriginal { get; set; }

    /// <summary>
    /// Valor convertido exacto (sin redondeo)
    /// </summary>
    public double ValorConvertidoExacto { get; set; }

    /// <summary>
    /// Valor convertido redondeado a 2 decimales
    /// </summary>
    public double ValorConvertidoRedondeado { get; set; }

    /// <summary>
    /// Unidad de origen
    /// </summary>
    public string UnidadOrigen { get; set; } = string.Empty;

    /// <summary>
    /// Unidad de destino
    /// </summary>
    public string UnidadDestino { get; set; } = string.Empty;

    /// <summary>
    /// Tipo de conversión (Longitud, Masa, Temperatura)
    /// </summary>
    public string TipoConversion { get; set; } = string.Empty;

    /// <summary>
    /// Factor de conversión utilizado
    /// </summary>
    public double FactorConversion { get; set; }

    /// <summary>
    /// Fecha y hora de la conversión
    /// </summary>
    public DateTime FechaConversion { get; set; }
}
