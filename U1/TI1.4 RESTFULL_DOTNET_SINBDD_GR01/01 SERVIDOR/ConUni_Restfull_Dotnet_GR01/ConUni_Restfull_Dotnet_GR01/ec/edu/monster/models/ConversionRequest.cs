namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models
{
    /// <summary>
    /// Modelo de solicitud para conversiones de unidades
    /// Permite especificar valor, unidad de origen y unidad de destino
    /// </summary>
    public class ConversionRequest
  {
        /// <summary>
        /// Valor numérico a convertir como string (para permitir validaciones)
        /// </summary>
        public string Valor { get; set; } = string.Empty;

        /// <summary>
        /// Unidad de origen (ej: "Milla", "Metro", "Pulgada")
     /// </summary>
  public string UnidadOrigen { get; set; } = string.Empty;

        /// <summary>
        /// Unidad de destino (ej: "Milla", "Metro", "Pulgada")
    /// </summary>
        public string UnidadDestino { get; set; } = string.Empty;
    }
}
