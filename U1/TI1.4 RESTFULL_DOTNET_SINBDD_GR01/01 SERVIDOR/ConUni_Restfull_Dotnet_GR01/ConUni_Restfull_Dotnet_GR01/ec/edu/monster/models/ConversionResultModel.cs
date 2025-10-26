namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models
{
    /// <summary>
    /// Modelo para representar errores en las conversiones
    /// Adaptado para API RESTful con serialización JSON
    /// </summary>
    public class ConversionErrorModel
    {
        /// <summary>
        /// Código de error único
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
        /// Valor que causó el error
        /// </summary>
        public double? ValorProblematico { get; set; }

        /// <summary>
        /// Unidad del valor problemático
        /// </summary>
        public string? Unidad { get; set; }

        /// <summary>
        /// Timestamp cuando ocurrió el error
        /// </summary>
        public DateTime FechaError { get; set; }

        /// <summary>
        /// Detalles adicionales del error
        /// </summary>
        public string? Detalles { get; set; }

        /// <summary>
        /// Constructor por defecto para serialización
        /// </summary>
        public ConversionErrorModel()
        {
            FechaError = DateTime.Now;
        }

        /// <summary>
        /// Constructor con parámetros
        /// </summary>
        public ConversionErrorModel(string codigoError, string mensaje, string tipoError, double? valorProblematico = null, string? unidad = null, string? detalles = null)
        {
            CodigoError = codigoError;
            Mensaje = mensaje;
            TipoError = tipoError;
            ValorProblematico = valorProblematico;
            Unidad = unidad;
            Detalles = detalles;
            FechaError = DateTime.Now;
        }
    }

    /// <summary>
    /// Resultado de operación que puede contener éxito o error
    /// Adaptado para API RESTful con serialización JSON
    /// </summary>
    public class ConversionResultModel
    {
        /// <summary>
        /// Indica si la operación fue exitosa
        /// </summary>
        public bool Exitoso { get; set; }

        /// <summary>
        /// Resultado de la conversión (solo si es exitoso)
        /// </summary>
        public UnidadConversionModel? Resultado { get; set; }

        /// <summary>
        /// Información del error (solo si no es exitoso)
        /// </summary>
        public ConversionErrorModel? Error { get; set; }

        /// <summary>
        /// Constructor para resultado exitoso
        /// </summary>
        public static ConversionResultModel Exito(UnidadConversionModel conversion)
        {
            return new ConversionResultModel
            {
                Exitoso = true,
                Resultado = conversion,
                Error = null
            };
        }

        /// <summary>
        /// Constructor para resultado con error
        /// </summary>
        public static ConversionResultModel Fallo(ConversionErrorModel error)
        {
            return new ConversionResultModel
            {
                Exitoso = false,
                Resultado = null,
                Error = error
            };
        }
    }
}
