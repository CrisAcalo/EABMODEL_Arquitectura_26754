using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Text.Json.Serialization;

namespace ConUni_Client_DotNet.Models
{
    /// <summary>
    /// Modelo para representar errores en las conversiones
    /// DEBE coincidir EXACTAMENTE con ConversionErrorModel del servidor
    /// </summary>
    public class ConversionError
    {
        public string CodigoError { get; set; } = string.Empty;
        public string Mensaje { get; set; } = string.Empty;
        public string TipoError { get; set; } = string.Empty;
        public double? ValorProblematico { get; set; }
        public string Unidad { get; set; }
        public DateTime FechaError { get; set; }
        public string Detalles { get; set; }
    }

    /// <summary>
    /// Modelo que representa los detalles de una conversión exitosa
    /// DEBE coincidir EXACTAMENTE con UnidadConversionModel del servidor
    /// </summary>
    public class UnidadConversion
    {
        public double ValorOriginal { get; set; }
        public double ValorConvertidoExacto { get; set; }
        public double ValorConvertidoRedondeado { get; set; }
        public string UnidadOrigen { get; set; } = string.Empty;
        public string UnidadDestino { get; set; } = string.Empty;
        public string TipoConversion { get; set; } = string.Empty;
        public double FactorConversion { get; set; }
        public DateTime FechaConversion { get; set; }
    }

    /// <summary>
    /// Resultado de operación que puede contener éxito o error
    /// DEBE coincidir EXACTAMENTE con ConversionResultModel del servidor
    /// </summary>
    public class ConversionResult
    {
        public bool Exitoso { get; set; }
        public UnidadConversion Resultado { get; set; }
        public ConversionError Error { get; set; }
    }
}