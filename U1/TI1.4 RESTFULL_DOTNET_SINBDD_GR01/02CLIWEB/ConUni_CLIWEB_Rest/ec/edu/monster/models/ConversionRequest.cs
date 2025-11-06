using System;

namespace ConUni_CLIWEB_Rest.ec.edu.monster.models
{
    /// <summary>
    /// Modelo de solicitud para conversiones de unidades
    /// </summary>
    public class ConversionRequest
    {
        public string Valor { get; set; }
        public string UnidadOrigen { get; set; }
        public string UnidadDestino { get; set; }

        public ConversionRequest()
        {
            Valor = string.Empty;
            UnidadOrigen = string.Empty;
            UnidadDestino = string.Empty;
        }
    }
}