using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BanquitoServer_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para respuesta de otorgar crédito
    /// </summary>
    [DataContract]
    public class RespuestaCreditoDTO
    {
        [DataMember]
        public bool Exito { get; set; }

        [DataMember]
        public string Mensaje { get; set; }

        [DataMember]
        public string Cedula { get; set; }

        [DataMember]
        public string NumeroCredito { get; set; }

        [DataMember]
        public decimal MontoCredito { get; set; }

        [DataMember]
        public int NumeroCuotas { get; set; }

        [DataMember]
        public decimal CuotaMensual { get; set; }

        [DataMember]
        public decimal TasaInteres { get; set; }

        [DataMember]
        public List<CuotaAmortizacionDTO> TablaAmortizacion { get; set; }
    }
}
