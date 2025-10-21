using System;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.models
{
    /// <summary>
    /// Clase base para representar una conversi�n de unidades
    /// </summary>
    public class UnidadConversion
    {
        /// <summary>
        /// Valor original antes de la conversi�n
        /// </summary>
        public double ValorOriginal { get; set; }

        /// <summary>
        /// Valor convertido exacto despu�s de la conversi�n (sin redondeo)
        /// </summary>
        public double ValorConvertidoExacto { get; set; }

        /// <summary>
        /// Valor convertido redondeado a 2 decimales
        /// </summary>
        public double ValorConvertidoRedondeado { get; set; }

        /// <summary>
        /// Unidad de origen
        /// </summary>
        public string UnidadOrigen { get; set; }

        /// <summary>
        /// Unidad de destino
        /// </summary>
        public string UnidadDestino { get; set; }

        /// <summary>
        /// Factor de conversi�n utilizado
        /// </summary>
        public double FactorConversion { get; set; }

        /// <summary>
        /// Timestamp de cu�ndo se realiz� la conversi�n
        /// </summary>
        public DateTime FechaConversion { get; set; }

        /// <summary>
        /// Constructor por defecto
        /// </summary>
        public UnidadConversion()
        {
            FechaConversion = DateTime.Now;
        }

        /// <summary>
        /// Constructor con par�metros
        /// </summary>
        public UnidadConversion(double valorOriginal, double valorConvertidoExacto, string unidadOrigen, string unidadDestino, double factorConversion)
        {
            ValorOriginal = valorOriginal;
            ValorConvertidoExacto = valorConvertidoExacto;
            ValorConvertidoRedondeado = Math.Round(valorConvertidoExacto, 2);
            UnidadOrigen = unidadOrigen;
            UnidadDestino = unidadDestino;
            FactorConversion = factorConversion;
            FechaConversion = DateTime.Now;
        }

        /// <summary>
        /// Obtiene una representaci�n en texto de la conversi�n
        /// </summary>
        /// <returns>String con la informaci�n de la conversi�n</returns>
        public override string ToString()
        {
            return $"{ValorOriginal} {UnidadOrigen} = {ValorConvertidoRedondeado} {UnidadDestino} (Exacto: {ValorConvertidoExacto})";
        }
    }
}