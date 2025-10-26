namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models
{
    /// <summary>
    /// Modelo para representar una conversión de unidades
    /// Adaptado para API RESTful desde modelo SOAP
    /// </summary>
    public class UnidadConversionModel
    {
        /// <summary>
        /// Valor original a convertir
        /// </summary>
        public double ValorOriginal { get; set; }

        /// <summary>
        /// Valor convertido exacto después de la conversión (sin redondeo)
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

        /// <summary>
        /// Constructor por defecto
        /// </summary>
        public UnidadConversionModel()
        {
            FechaConversion = DateTime.Now;
        }

        /// <summary>
        /// Constructor con parámetros
        /// </summary>
        /// <param name="valorOriginal">Valor a convertir</param>
        /// <param name="valorConvertidoExacto">Valor convertido sin redondeo</param>
        /// <param name="unidadOrigen">Unidad de origen</param>
        /// <param name="unidadDestino">Unidad de destino</param>
        /// <param name="tipoConversion">Tipo de conversión (Longitud, Masa, Temperatura)</param>
        /// <param name="factorConversion">Factor de conversión utilizado</param>
        public UnidadConversionModel(double valorOriginal, double valorConvertidoExacto, string unidadOrigen, string unidadDestino, string tipoConversion, double factorConversion)
        {
            ValorOriginal = valorOriginal;
            ValorConvertidoExacto = valorConvertidoExacto;
            ValorConvertidoRedondeado = Math.Round(valorConvertidoExacto, 2);
            UnidadOrigen = unidadOrigen;
            UnidadDestino = unidadDestino;
            TipoConversion = tipoConversion;
            FactorConversion = factorConversion;
            FechaConversion = DateTime.Now;
        }

        /// <summary>
        /// Obtiene una representación en texto de la conversión
        /// </summary>
        /// <returns>String con la información de la conversión</returns>
        public override string ToString()
        {
            return $"{ValorOriginal} {UnidadOrigen} = {ValorConvertidoRedondeado} {UnidadDestino} (Exacto: {ValorConvertidoExacto}, Tipo: {TipoConversion})";
        }
    }
}
