using System;

namespace ConUni_CLIWEB_Rest.ec.edu.monster.models
{
    /// <summary>
    /// Modelo para los datos de resultado de conversión
    /// </summary>
    public class ResultadoConversion
    {
        public double ValorOriginal { get; set; }
        public double ValorConvertidoExacto { get; set; }
        public double ValorConvertidoRedondeado { get; set; }
        public string UnidadOrigen { get; set; }
        public string UnidadDestino { get; set; }
        public string TipoConversion { get; set; }
        public double FactorConversion { get; set; }
        public DateTime FechaConversion { get; set; }

        public ResultadoConversion()
        {
            UnidadOrigen = string.Empty;
            UnidadDestino = string.Empty;
            TipoConversion = string.Empty;
            FechaConversion = DateTime.Now;
        }
    }
}