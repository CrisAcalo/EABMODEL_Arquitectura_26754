using System;
using System.Runtime.Serialization;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.models
{
    /// <summary>
    /// Modelo para representar errores en las conversiones
    /// Serializable para WCF
    /// </summary>
    [DataContract]
    public class ConversionError
    {
        /// <summary>
        /// C�digo de error �nico
        /// </summary>
        [DataMember]
        public string CodigoError { get; set; }

        /// <summary>
        /// Mensaje descriptivo del error
        /// </summary>
        [DataMember]
        public string Mensaje { get; set; }

        /// <summary>
        /// Tipo de error (Validacion, Conversion, Sistema)
        /// </summary>
        [DataMember]
        public string TipoError { get; set; }

        /// <summary>
        /// Valor que caus� el error
        /// </summary>
        [DataMember]
        public double? ValorProblematico { get; set; }

        /// <summary>
        /// Unidad del valor problem�tico
        /// </summary>
        [DataMember]
        public string Unidad { get; set; }

        /// <summary>
        /// Timestamp cuando ocurri� el error
        /// </summary>
        [DataMember]
        public DateTime FechaError { get; set; }

        /// <summary>
        /// Detalles adicionales del error
        /// </summary>
        [DataMember]
        public string Detalles { get; set; }

        /// <summary>
        /// Constructor por defecto para serializaci�n
        /// </summary>
        public ConversionError()
        {
            FechaError = DateTime.Now;
        }

        /// <summary>
        /// Constructor con par�metros
        /// </summary>
        public ConversionError(string codigoError, string mensaje, string tipoError, double? valorProblematico = null, string unidad = null, string detalles = null)
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
    /// Resultado de operaci�n que puede contener �xito o error
    /// </summary>
    [DataContract]
    public class ConversionResult
    {
        /// <summary>
        /// Indica si la operaci�n fue exitosa
        /// </summary>
        [DataMember]
        public bool Exitoso { get; set; }

        /// <summary>
        /// Resultado de la conversi�n (solo si es exitoso)
        /// </summary>
        [DataMember]
        public UnidadConversion Resultado { get; set; }

        /// <summary>
        /// Informaci�n del error (solo si no es exitoso)
        /// </summary>
        [DataMember]
        public ConversionError Error { get; set; }

        /// <summary>
        /// Constructor para resultado exitoso
        /// </summary>
        public static ConversionResult Exito(UnidadConversion conversion)
        {
            return new ConversionResult
            {
                Exitoso = true,
                Resultado = conversion,
                Error = null
            };
        }

        /// <summary>
        /// Constructor para resultado con error
        /// </summary>
        public static ConversionResult Fallo(ConversionError error)
        {
            return new ConversionResult
            {
                Exitoso = false,
                Resultado = null,
                Error = error
            };
        }
    }
}