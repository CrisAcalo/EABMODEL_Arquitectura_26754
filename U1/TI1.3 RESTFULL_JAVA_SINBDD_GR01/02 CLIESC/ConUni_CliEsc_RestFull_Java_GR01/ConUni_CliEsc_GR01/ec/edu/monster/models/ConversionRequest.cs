namespace ConUni_CliEsc_GR01.ec.edu.monster.models;

/// <summary>
/// Modelo de solicitud para conversiones de unidades
/// </summary>
public class ConversionRequest
{
    /// <summary>
    /// Valor numérico a convertir
    /// </summary>
    public double Valor { get; set; }

    /// <summary>
    /// Unidad de origen (ej: "Celsius", "Metro", "Kilogramo")
    /// </summary>
    public string UnidadOrigen { get; set; } = string.Empty;

    /// <summary>
    /// Unidad de destino (ej: "Fahrenheit", "Pulgada", "Libra")
    /// </summary>
    public string UnidadDestino { get; set; } = string.Empty;
}
