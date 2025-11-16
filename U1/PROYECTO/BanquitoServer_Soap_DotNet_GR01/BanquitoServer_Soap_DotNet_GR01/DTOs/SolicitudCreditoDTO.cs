using System.Runtime.Serialization;

namespace BanquitoServer_Soap_DotNet_GR01.DTOs
{
    /// <summary>
    /// DTO para solicitud de crédito
    /// </summary>
    [DataContract]
    public class SolicitudCreditoDTO
    {
        [DataMember]
        public string Cedula { get; set; }

        [DataMember]
        public decimal PrecioElectrodomestico { get; set; }

        [DataMember]
        public int NumeroCuotas { get; set; }
    }
}
