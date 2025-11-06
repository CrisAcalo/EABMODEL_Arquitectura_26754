using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConUni_Client_DotNet.Models
{
    /// <summary>
    /// Modelo de solicitud para conversiones de unidades
    /// DEBE coincidir con el modelo del servidor
    /// </summary>
    public class ConversionRequest
    {
        public string Valor { get; set; } = string.Empty;
        public string UnidadOrigen { get; set; } = string.Empty;
        public string UnidadDestino { get; set; } = string.Empty;

        public ConversionRequest() { }

        public ConversionRequest(string valor, string unidadOrigen, string unidadDestino)
        {
            Valor = valor;
            UnidadOrigen = unidadOrigen;
            UnidadDestino = unidadDestino;
        }
    }
}